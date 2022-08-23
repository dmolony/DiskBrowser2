package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

//-----------------------------------------------------------------------------------//
class FileMenu extends Menu implements TreeNodeListener, SaveState
//-----------------------------------------------------------------------------------//
{
  private final MenuItem rootMenuItem = new MenuItem ("Set Apple root folder...");
  private final MenuItem aboutMenuItem = new MenuItem ("Show version...");

  TreeFile treeFile;

  // ---------------------------------------------------------------------------------//
  public FileMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    getItems ().addAll (rootMenuItem, new SeparatorMenuItem (), aboutMenuItem);
    rootMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.R, KeyCombination.SHORTCUT_DOWN));

    aboutMenuItem.setOnAction (e -> about ());
  }

  // ---------------------------------------------------------------------------------//
  void setRootAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    rootMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  private void about ()
  // ---------------------------------------------------------------------------------//
  {
    AppBase.showAlert (AlertType.INFORMATION, "About DiskBrowserApp",
        "Version: 1.0.0\nReleased: 7 January 2023\nAuthor: Denis Molony");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    //    saveFolderName = prefs.get (PREFS_SAVE_FOLDER, System.getProperty ("user.home"));
    //    if (!new File (saveFolderName).exists ())
    //      saveFolderName = System.getProperty ("user.home");
    //
    //    extractFolderName = prefs.get (PREFS_EXTRACT_FOLDER, System.getProperty ("user.home"));
    //    if (!new File (extractFolderName).exists ())
    //      extractFolderName = System.getProperty ("user.home");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    //    prefs.put (PREFS_SAVE_FOLDER, saveFolderName);
    //    prefs.put (PREFS_EXTRACT_FOLDER, extractFolderName);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = treeFile;

    //    if (nodeData.isPhysicalSequentialDataset ())
    //    {
    //      dataFile = nodeData.getDataFile ();
    //      set (nodeData.getDatasetName (), nodeData.getDatasetName ());
    //    }
    //    else
    //    {
    //      dataFile = null;
    //      set ("", "");
    //    }
  }
}
