package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class DiskLayoutTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  protected DiskLayoutGroup diskLayoutGroup = new DiskLayoutGroup ();
  protected KeyPaneProdos keyPaneProdos = new KeyPaneProdos ();
  protected KeyPaneDos keyPaneDos = new KeyPaneDos ();
  protected KeyPanePascal keyPanePascal = new KeyPanePascal ();
  protected KeyPaneCpm keyPaneCpm = new KeyPaneCpm ();

  //  protected FormattedAppleFile formattedAppleFile;
  protected AppleTreeItem appleTreeItem;
  protected AppleTreeNode appleTreeNode;
  protected AppleFile appleFile;
  protected AppleFileSystem appleFileSystem;
  protected BorderPane borderPane;

  // ---------------------------------------------------------------------------------//
  public DiskLayoutTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    borderPane = new BorderPane ();
    borderPane.setTop (diskLayoutGroup);
    borderPane.setBottom (keyPaneProdos);
    this.setContent (borderPane);

    //    borderPane
    //    .setBackground (new Background (new BackgroundFill (Color.WHITE, null, null)));
  }

  // ---------------------------------------------------------------------------------//
  void setFactory (FormattedAppleBlockFactory formattedAppleBlockFactory)
  // ---------------------------------------------------------------------------------//
  {
    diskLayoutGroup.setFactory (formattedAppleBlockFactory);
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeNode (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeNode = appleTreeNode;
    appleFile = appleTreeNode.getAppleFile ();
    appleFileSystem = appleTreeNode.getAppleFileSystem ();

    if (appleFile != null)
    {
      AppleFileSystem embeddedFs = appleFile.getEmbeddedFileSystem ();
      if (embeddedFs != null && embeddedFs.getTotalBlocks () > 0)     // not hybrid
        appleFileSystem = embeddedFs;
      else
        appleFileSystem = appleFile.getParentFileSystem ();
    }

    if (appleFileSystem == null)
      borderPane.setBottom (null);
    else
      borderPane.setBottom (switch (appleFileSystem.getFileSystemType ())
      {
        case DOS3, DOS4 -> keyPaneDos;
        case PRODOS -> keyPaneProdos;
        case CPM -> keyPaneCpm;
        case PASCAL -> keyPanePascal;
        default -> null;
      });

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    diskLayoutGroup.setFileSystem (appleFileSystem);
    diskLayoutGroup.setAppleFile (appleFile);

    setValid (true);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    diskLayoutGroup.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    diskLayoutGroup.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  public void addClickListener (GridClickListener listener)
  // ---------------------------------------------------------------------------------//
  {
    diskLayoutGroup.addClickListener (listener);
  }
}
