package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.util.Arrays;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StageManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class DiskBrowserApp extends AppBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_ROOT_FOLDER = "RootFolder";

  private String rootFolderName;

  private AppleTreeView appleTree;

  // set three panes for the split pane
  private final SplitPane splitPane = new SplitPane ();
  private TreePane treePane;
  private final OutputTabPane outputTabPane = new OutputTabPane ("Output");
  private final OutputTabPane outputTabPane2 = new OutputTabPane ("Output2");

  private final DBStatusBar dbStatusBar = new DBStatusBar ();

  private DBStageManager dbStageManager;

  private final FileMenu fileMenu = new FileMenu ("File");
  //  private final ViewMenu viewMenu = new ViewMenu ("View");

  // ---------------------------------------------------------------------------------//
  @Override
  public void start (Stage primaryStage) throws Exception
  // ---------------------------------------------------------------------------------//
  {
    super.start (primaryStage);

    dbStageManager.setSplitPane (splitPane);      // this must happen after show()
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("DiskBrowser ][");

    // get root folder
    validateRootFolderOrExit ();

    appleTree = new AppleTreeView (rootFolderName);
    treePane = new TreePane (appleTree);

    OutputHeaderBar outputHeaderBar = new OutputHeaderBar ();
    OutputHeaderBar outputHeaderBar2 = new OutputHeaderBar ();

    splitPane.getItems ().addAll (                          //
        treePane,                                           //
        createBorderPane (outputHeaderBar, outputTabPane),
        createBorderPane (outputHeaderBar2, outputTabPane2));

    fileMenu.setRootAction (e -> changeRootFolder ());

    // font change listeners
    fontManager.addFontChangeListener (appleTree);
    fontManager.addFontChangeListener (outputTabPane);
    fontManager.addFontChangeListener (dbStatusBar);

    // treeview listeners
    appleTree.addListener (fileMenu);
    appleTree.addListener (outputTabPane.hexTab);
    appleTree.addListener (outputTabPane.outputTab);
    appleTree.addListener (outputHeaderBar);

    // ensure viewMenu (codepage) is set before xmitTree
    saveStateList.addAll (Arrays.asList (//
        //            filterManager, //
        outputTabPane, outputTabPane2, //fileMenu, viewMenu,
        appleTree, fontManager));

    return splitPane;
  }

  // ---------------------------------------------------------------------------------//
  private BorderPane createBorderPane (HeaderBar headerBar, TabPane tabPane)
  // ---------------------------------------------------------------------------------//
  {
    BorderPane borderPane = new BorderPane ();

    borderPane.setTop (headerBar);
    borderPane.setCenter (tabPane);

    return borderPane;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected Preferences getPreferences ()
  // ---------------------------------------------------------------------------------//
  {
    return Preferences.userNodeForPackage (this.getClass ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected StageManager getStageManager (Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    dbStageManager = new DBStageManager (stage);
    return dbStageManager;
  }

  // ---------------------------------------------------------------------------------//
  void changeRootFolder ()
  // ---------------------------------------------------------------------------------//
  {
    if (setRootFolder ())
    {
      treePane.setRootFolder (new AppleTreeItem (new TreeFile (new File (rootFolderName))));
      dbStatusBar.setStatusMessage ("Root folder changed");
    }
    else
      dbStatusBar.setStatusMessage ("Root folder unchanged");
  }

  // ---------------------------------------------------------------------------------//
  private void validateRootFolderOrExit ()
  // ---------------------------------------------------------------------------------//
  {
    rootFolderName = prefs.get (PREFS_ROOT_FOLDER, "");
    if (rootFolderName.isEmpty ())
    {
      AppBase.showAlert (AlertType.INFORMATION, "Apple folder",
          "The Apple file folder has not yet been defined. Please choose the "
              + "TOP LEVEL FOLDER where you store your Apple files. This folder "
              + "may contain subfolders. It can also be changed at any time.");
    }
    else
    {
      File file = new File (rootFolderName);
      if (!file.exists ())
        rootFolderName = "";
    }

    if (rootFolderName.isEmpty () && !setRootFolder ())
    {
      Platform.exit ();
      System.exit (0);
    }
  }

  // ---------------------------------------------------------------------------------//
  private boolean setRootFolder ()
  // ---------------------------------------------------------------------------------//
  {
    DirectoryChooser directoryChooser = new DirectoryChooser ();
    directoryChooser.setTitle ("Set Apple file folder");

    if (rootFolderName.isEmpty ())
      directoryChooser.setInitialDirectory (new File (System.getProperty ("user.home")));
    else
      directoryChooser.setInitialDirectory (new File (rootFolderName));

    File file = directoryChooser.showDialog (null);
    if (file != null && file.isDirectory () && !file.getAbsolutePath ().equals (rootFolderName))
    {
      rootFolderName = file.getAbsolutePath ();
      prefs.put (PREFS_ROOT_FOLDER, rootFolderName);
      return true;
    }

    return false;
  }

  // ---------------------------------------------------------------------------------//
  public static void main (String[] args)
  // ---------------------------------------------------------------------------------//
  {
    Application.launch (args);
  }
}
