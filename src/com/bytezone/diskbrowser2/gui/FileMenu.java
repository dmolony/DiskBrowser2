package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

//-----------------------------------------------------------------------------------//
class FileMenu extends Menu implements TreeNodeListener, SaveState
//-----------------------------------------------------------------------------------//
{
  private static final String PREFS_EXTRACT_FOLDER = "ExtractFolder";
  private static final String PREFS_SAVE_FOLDER = "SaveFolder";

  private final MenuItem rootMenuItem = new MenuItem ("Set Apple root folder...");
  private final MenuItem extractMenuItem = new MenuItem ("Extract file...");
  private final MenuItem saveMenuItem = new MenuItem ("Save output...");
  private final MenuItem aboutMenuItem = new MenuItem ("Show version...");

  AppleTreeFile treeFile;
  AppleFile appleFile;        // can only be a data file

  private String saveFolderName;
  private String extractFileName;
  private String extractFolderName;

  private OutputWriter outputWriter;

  // ---------------------------------------------------------------------------------//
  public FileMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    getItems ().addAll (rootMenuItem, new SeparatorMenuItem (), aboutMenuItem);
    rootMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.R, KeyCombination.SHORTCUT_DOWN));
    saveMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.S, KeyCombination.SHORTCUT_DOWN));

    extractMenuItem.setOnAction (e -> extractFile ());
    saveMenuItem.setOnAction (e -> saveFile ());
    aboutMenuItem.setOnAction (e -> about ());
  }

  // ---------------------------------------------------------------------------------//
  void setRootAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    rootMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  void setOutputWriter (OutputWriter outputWriter)
  // ---------------------------------------------------------------------------------//
  {
    this.outputWriter = outputWriter;
    saveMenuItem.setDisable (false);
  }

  // ---------------------------------------------------------------------------------//
  private void about ()
  // ---------------------------------------------------------------------------------//
  {
    AppBase.showAlert (AlertType.INFORMATION, "About DiskBrowserApp",
        "Version: 1.0.0\nReleased: 7 January 2023\nAuthor: Denis Molony");
  }

  // ---------------------------------------------------------------------------------//
  private void saveFile ()
  // ---------------------------------------------------------------------------------//
  {
    if (outputWriter == null)
      return;

    String name = treeFile.getName () + ".txt";

    FileChooser fileChooser = new FileChooser ();
    fileChooser.setTitle ("Save output text to");

    File saveFolder = new File (saveFolderName);
    if (!saveFolder.exists ())
      saveFolder = new File (System.getProperty ("user.home"));

    fileChooser.setInitialDirectory (saveFolder);
    fileChooser.setInitialFileName (name);

    File file = fileChooser.showSaveDialog (null);
    if (file != null)
    {
      outputWriter.write (file);
      saveFolderName = file.getParent ();
    }
  }

  // ---------------------------------------------------------------------------------//
  private void extractFile ()
  // ---------------------------------------------------------------------------------//
  {
    if (appleFile == null)
    {
      System.out.println ("Why am I here?");
      return;
    }

    byte[] buffer = appleFile.read ();

    FileChooser fileChooser = new FileChooser ();
    fileChooser.setTitle ("Extract file to");
    fileChooser.setInitialDirectory (new File (extractFolderName));

    //    String suffix = "." + treeFile.getFileType ().name ();
    fileChooser.setInitialFileName (extractFileName + ".bin");      // fix this later

    File file = fileChooser.showSaveDialog (null);
    if (file != null)
      try
      {
        Files.write (Paths.get (file.getAbsolutePath ()), buffer);
        extractFolderName = file.getParent ();
        AppBase.showAlert (AlertType.INFORMATION, "Success", "File Extracted: " + file.getName ());
      }
      catch (IOException e)
      {
        AppBase.showAlert (AlertType.ERROR, "Error", "File Error: " + e.getMessage ());
      }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    saveFolderName = prefs.get (PREFS_SAVE_FOLDER, System.getProperty ("user.home"));
    if (!new File (saveFolderName).exists ())
      saveFolderName = System.getProperty ("user.home");

    extractFolderName = prefs.get (PREFS_EXTRACT_FOLDER, System.getProperty ("user.home"));
    if (!new File (extractFolderName).exists ())
      extractFolderName = System.getProperty ("user.home");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_SAVE_FOLDER, saveFolderName);
    prefs.put (PREFS_EXTRACT_FOLDER, extractFolderName);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;

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
