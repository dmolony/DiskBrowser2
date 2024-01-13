package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

//-----------------------------------------------------------------------------------//
class FileMenu extends Menu implements TreeNodeListener, SaveState
//-----------------------------------------------------------------------------------//
{
  private static final String PREFS_EXTRACT_FOLDER = "ExtractFolder";
  private static final String PREFS_SAVE_FOLDER = "SaveFolder";
  private static final String PREFS_ROOT_FOLDER = "RootFolder";

  private final MenuItem rootMenuItem = new MenuItem ("Set Apple root folder...");
  private final MenuItem extractMenuItem = new MenuItem ("Extract file...");
  private final MenuItem saveMenuItem = new MenuItem ("Save output...");
  private final MenuItem aboutMenuItem = new MenuItem ("Show version...");

  AppleTreeFile treeFile;
  AppleFile appleFile;        // can only be a data file

  private String saveFolderName;
  private String extractFileName;
  private String extractFolderName;
  private String rootFolderName;

  private OutputWriter outputWriter;

  private List<RootFolderChangeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public FileMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    getItems ().addAll (rootMenuItem, new SeparatorMenuItem (), aboutMenuItem);

    rootMenuItem.setAccelerator (
        new KeyCodeCombination (KeyCode.R, KeyCombination.SHORTCUT_DOWN));
    saveMenuItem.setAccelerator (
        new KeyCodeCombination (KeyCode.S, KeyCombination.SHORTCUT_DOWN));

    extractMenuItem.setOnAction (e -> extractFile ());
    saveMenuItem.setOnAction (e -> saveFile ());
    aboutMenuItem.setOnAction (e -> about ());
    rootMenuItem.setOnAction (e -> getRootFolder ());
  }

  // ---------------------------------------------------------------------------------//
  void getRootFolder ()
  // ---------------------------------------------------------------------------------//
  {
    DirectoryChooser directoryChooser = new DirectoryChooser ();
    directoryChooser.setTitle ("Set Apple file folder");

    if (rootFolderName.isEmpty ())
      directoryChooser.setInitialDirectory (new File (System.getProperty ("user.home")));
    else
      directoryChooser.setInitialDirectory (new File (rootFolderName));

    File file = directoryChooser.showDialog (null);
    if (file != null && file.isDirectory ()
        && !file.getAbsolutePath ().equals (rootFolderName))
    {
      rootFolderName = file.getAbsolutePath ();
      notifyRootFolderListeners (file);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void about ()
  // ---------------------------------------------------------------------------------//
  {
    AppBase.showAlert (AlertType.INFORMATION, "About DiskBrowserApp",
        "Version: 1.0.0\nReleased: 1 January 2024"
            + "\nDownload: github.com/dmolony/DiskBrowser2");
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
        AppBase.showAlert (AlertType.INFORMATION, "Success",
            "File Extracted: " + file.getName ());
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

    extractFolderName =
        prefs.get (PREFS_EXTRACT_FOLDER, System.getProperty ("user.home"));
    if (!new File (extractFolderName).exists ())
      extractFolderName = System.getProperty ("user.home");

    rootFolderName = prefs.get (PREFS_ROOT_FOLDER, "");
    System.out.println (rootFolderName);
    if (!rootFolderName.isEmpty ())
      notifyRootFolderListeners (new File (rootFolderName));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_SAVE_FOLDER, saveFolderName);
    prefs.put (PREFS_EXTRACT_FOLDER, extractFolderName);
    prefs.put (PREFS_ROOT_FOLDER, rootFolderName);
  }

  // ---------------------------------------------------------------------------------//
  void setOutputWriter (OutputWriter outputWriter)
  // ---------------------------------------------------------------------------------//
  {
    this.outputWriter = outputWriter;
    saveMenuItem.setDisable (false);
  }

  // ---------------------------------------------------------------------------------//
  void addRootFolderChangeListener (RootFolderChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  void notifyRootFolderListeners (File rootFolder)
  // ---------------------------------------------------------------------------------//
  {
    for (RootFolderChangeListener listener : listeners)
      listener.rootFolderChanged (rootFolder);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;
  }
}
