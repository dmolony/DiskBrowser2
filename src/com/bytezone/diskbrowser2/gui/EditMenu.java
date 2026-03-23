package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.TabChangeListener;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

// -----------------------------------------------------------------------------------//
public class EditMenu extends Menu implements TabChangeListener, TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private final MenuItem copyMenuItem = new MenuItem ("Copy");
  private final MenuItem refreshMenuItem = new MenuItem ("Refresh");
  private DBTextTab currentTab;

  //  private AppleTreeNode currentTreeNode;
  private AppleTreeItem currentTreeItem;
  private List<RefreshNodeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public EditMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    getItems ().addAll (copyMenuItem, refreshMenuItem);

    copyMenuItem.setAccelerator (
        new KeyCodeCombination (KeyCode.C, KeyCombination.SHORTCUT_DOWN));
    copyMenuItem.setOnAction (e -> copyFile ());

    refreshMenuItem.setAccelerator (
        new KeyCodeCombination (KeyCode.R, KeyCombination.SHORTCUT_DOWN));
    refreshMenuItem.setOnAction (e -> refresh ());
  }

  // ---------------------------------------------------------------------------------//
  private void copyFile ()
  // ---------------------------------------------------------------------------------//
  {
    if (currentTab != null)
      currentTab.copyToClipboard ();
  }

  // ---------------------------------------------------------------------------------//
  private void refresh ()
  // ---------------------------------------------------------------------------------//
  {
    for (RefreshNodeListener listener : listeners)
      listener.refreshNode (currentTreeItem);

    currentTreeItem.refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void addListener (RefreshNodeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void tabChanged (Tab fromTab, Tab toTab)
  // ---------------------------------------------------------------------------------//
  {
    if (toTab instanceof DBTextTab textTab)
    {
      copyMenuItem.setDisable (false);
      currentTab = textTab;
    }
    else
    {
      copyMenuItem.setDisable (true);
      currentTab = null;
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeNode appleTreeNode, AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    currentTreeItem = appleTreeItem;
    //    currentTreeNode = appleTreeItem.getValue ();

    if (appleTreeNode.isAppleFileSystem () && appleTreeNode.isLocalFile ())
    {
      refreshMenuItem.setDisable (false);
      refreshMenuItem.setText ("Refresh " + appleTreeNode.getName ());
    }
    else
    {
      refreshMenuItem.setDisable (true);
      refreshMenuItem.setText ("Refresh");
    }
  }

  // ---------------------------------------------------------------------------------//
  public interface RefreshNodeListener
  // ---------------------------------------------------------------------------------//
  {
    public void refreshNode (AppleTreeItem appleTreeItem);
  }
}
