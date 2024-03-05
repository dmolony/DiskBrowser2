package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.bytezone.appbase.FontChangeListener;
import com.bytezone.appbase.SaveState;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.FileSystemFactory;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Callback;

// ---------------------------------------------------------------------------------//
class AppleTreeView extends TreeView<AppleTreeNode>
    implements SaveState, FontChangeListener
// ---------------------------------------------------------------------------------//
{
  static FileSystemFactory fileSystemFactory = new FileSystemFactory ();
  private final FormattedAppleFileFactory formattedAppleFileFactory;

  private static final String PREFS_LAST_PATH = "LastPath";
  private static String SEPARATOR = "|";

  private Font font;

  private final MultipleSelectionModel<TreeItem<AppleTreeNode>> model;
  private final List<TreeNodeListener> treeNodeListeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  AppleTreeView (AppleTreeItem root, FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    super (root);

    this.formattedAppleFileFactory = formattedAppleFileFactory;

    model = getSelectionModel ();
    model.selectedItemProperty ()
        .addListener ( (obs, oldSel, newSel) -> itemSelected ((AppleTreeItem) newSel));

    //    this.focusedProperty ().addListener ( (obs, oldVal, newVal) -> focus (newVal));

    setCellFactory (new Callback<TreeView<AppleTreeNode>, TreeCell<AppleTreeNode>> ()
    {
      @Override
      public TreeCell<AppleTreeNode> call (TreeView<AppleTreeNode> parm)
      {
        TreeCell<AppleTreeNode> cell = new TreeCell<> ()
        {
          private final ImageView imageView = new ImageView ();

          public void updateItem (AppleTreeNode treeFile, boolean empty)
          {
            super.updateItem (treeFile, empty);

            if (empty || treeFile == null)
            {
              setText (null);
              setGraphic (null);
            }
            else
            {
              setText (treeFile.toString ());
              imageView.setImage (treeFile.getImage ());
              setGraphic (imageView);
              setFont (font);
            }
          }
        };

        return cell;
      }
    });
  }

  // ---------------------------------------------------------------------------------//
  private void focus (boolean val)
  // ---------------------------------------------------------------------------------//
  {
    if (val)
      itemSelected ((AppleTreeItem) model.getSelectedItem ());
  }

  // ---------------------------------------------------------------------------------//
  private void itemSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    if (appleTreeItem == null)
    {
      System.out.println ("Should never happen - appleTreeItem is null");
      return;
    }

    AppleTreeNode treeNode = appleTreeItem.getValue ();

    // if the item is selected BEFORE it is opened then we do this one
    treeNode.checkForFileSystem ();       // also called by AppleTreeItem.getChildren()

    if (treeNode.getFormattedAppleFile () == null)
      setFormattedAppleFile (treeNode);

    for (TreeNodeListener listener : treeNodeListeners)
      listener.treeNodeSelected (treeNode);
  }

  // ---------------------------------------------------------------------------------//
  void setFormattedAppleFile (AppleTreeNode treeNode)
  // ---------------------------------------------------------------------------------//
  {
    FormattedAppleFile formattedAppleFile = null;

    AppleFile appleFile = treeNode.getAppleFile ();
    if (formattedAppleFile == null && appleFile != null)
      formattedAppleFile = formattedAppleFileFactory.getFormattedAppleFile (appleFile);

    AppleFileSystem appleFileSystem = treeNode.getAppleFileSystem ();

    if (formattedAppleFile == null && appleFileSystem != null)
      formattedAppleFile =
          formattedAppleFileFactory.getFormattedAppleFile (appleFileSystem);

    File localFile = treeNode.getLocalFile ();

    if (formattedAppleFile == null && localFile != null && localFile.isDirectory ())
      formattedAppleFile = formattedAppleFileFactory.getFormattedAppleFile (localFile);

    assert formattedAppleFile != null;
    treeNode.setFormattedAppleFile (formattedAppleFile);
  }

  // ---------------------------------------------------------------------------------//
  void setRootFolder (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    setRoot (appleTreeItem);
    appleTreeItem.setExpanded (true);
  }

  // ---------------------------------------------------------------------------------//
  private Optional<TreeItem<AppleTreeNode>> getNode (String path)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<AppleTreeNode> node = getRoot ();
    Optional<TreeItem<AppleTreeNode>> optionalNode = Optional.empty ();

    String[] chunks = path.split ("\\" + SEPARATOR);

    for (int i = 2; i < chunks.length; i++)
    {
      model.select (node);
      optionalNode = search (node, chunks[i]);
      if (!optionalNode.isPresent ())
        break;
      node = optionalNode.get ();
    }

    //    setShowRoot (false);        // workaround for stupid javafx bug
    return optionalNode;
  }

  // ---------------------------------------------------------------------------------//
  private Optional<TreeItem<AppleTreeNode>> search (TreeItem<AppleTreeNode> parentNode,
      String name)
  // ---------------------------------------------------------------------------------//
  {
    parentNode.setExpanded (true);

    for (TreeItem<AppleTreeNode> childNode : parentNode.getChildren ())
      if (childNode.getValue ().getName ().equals (name))
        return Optional.of (childNode);

    return Optional.empty ();
  }

  // ---------------------------------------------------------------------------------//
  private String getSelectedItemPath ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder pathBuilder = new StringBuilder ();

    TreeItem<AppleTreeNode> item = model.getSelectedItem ();
    while (item != null)
    {
      pathBuilder.insert (0, SEPARATOR + item.getValue ().getName ());
      item = item.getParent ();
    }

    return pathBuilder.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_LAST_PATH, getSelectedItemPath ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    String lastPath = prefs.get (PREFS_LAST_PATH, "");

    if (!lastPath.isEmpty ())
    {
      Optional<TreeItem<AppleTreeNode>> optionalNode = getNode (lastPath);
      if (optionalNode.isPresent ())
      {
        int row = getRow (optionalNode.get ());
        model.select (row);
        scrollTo (model.getSelectedIndex ());
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    this.font = font;
    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  void addListener (TreeNodeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!treeNodeListeners.contains (listener))
      treeNodeListeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  interface TreeNodeListener
  // ---------------------------------------------------------------------------------//
  {
    public void treeNodeSelected (AppleTreeNode treeNode);
  }
}
