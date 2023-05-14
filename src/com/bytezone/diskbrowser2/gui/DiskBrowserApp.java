package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.util.Arrays;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StageManager;
import com.bytezone.appbase.StatusBar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class DiskBrowserApp extends AppBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_ROOT_FOLDER = "RootFolder";

  private File rootFolder;

  private AppleTreeView appleTree;

  // set three panes for the split pane
  private final SplitPane splitPane = new SplitPane ();
  private TreePane treePane;
  private final OutputTabPane outputTabPane = new OutputTabPane ("Output");
  private final ExtrasTabPane extrasTabPane = new ExtrasTabPane ("Extras");

  private final FilterManager filterManager = new FilterManager ();
  private final DBStatusBar dbStatusBar = new DBStatusBar ();

  private DBStageManager dbStageManager;

  private final FileMenu fileMenu = new FileMenu ("File");
  private final ViewMenu viewMenu = new ViewMenu ("View");

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

    treePane = new TreePane (rootFolder);
    appleTree = treePane.getTree ();

    OutputHeaderBar outputHeaderBar = new OutputHeaderBar ();
    ExtrasHeaderBar extrasHeaderBar = new ExtrasHeaderBar ();

    splitPane.getItems ().addAll (                          //
        treePane,                                           //
        createBorderPane (outputHeaderBar, outputTabPane),
        createBorderPane (extrasHeaderBar, extrasTabPane));

    // menu listeners
    viewMenu.setExclusiveFilterAction (e -> filterManager.toggleFilterExclusion ());
    viewMenu.setFilterAction (e -> filterManager.showWindow ());
    viewMenu.setFontAction (e -> fontManager.showWindow ());
    fileMenu.setRootAction (e -> changeRootFolder ());

    // font change listeners
    fontManager.addFontChangeListener (appleTree);
    fontManager.addFontChangeListener (outputTabPane);
    fontManager.addFontChangeListener (dbStatusBar);

    // filter change listeners (filter parameters)
    filterManager.addFilterListener (dbStatusBar);
    filterManager.addFilterListener (outputTabPane.outputTab);
    filterManager.addFilterListener (viewMenu);

    // treeview listeners
    appleTree.addListener (fileMenu);
    //    appleTree.addListener (outputTabPane.metaTab);
    //    appleTree.addListener (outputTabPane.hexTab);
    //    appleTree.addListener (outputTabPane.outputTab);
    //    appleTree.addListener (outputTabPane.graphicsTab);
    appleTree.addListener (outputTabPane);
    appleTree.addListener (outputHeaderBar);
    appleTree.addListener (extrasHeaderBar);

    // tab change listeners
    outputTabPane.addTabChangeListener (viewMenu);

    // add menus
    menuBar.getMenus ().addAll (fileMenu, viewMenu);

    fileMenu.setOutputWriter (outputTabPane.outputTab);

    saveStateList.addAll (Arrays.asList (   //
        filterManager, outputTabPane, extrasTabPane, fileMenu, appleTree, fontManager));

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
  @Override
  protected StatusBar getStatusBar ()
  // ---------------------------------------------------------------------------------//
  {
    return dbStatusBar;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
    super.keyPressed (keyEvent);

    switch (keyEvent.getCode ())
    {
      case M:       // meta
      case H:       // hex
      case D:       // data
      case G:       // graphics
      case E:       // extras
        outputTabPane.keyPressed (keyEvent);
        keyEvent.consume ();
        break;

      case O:       // options
      case L:       // layout
      case I:       // include
        extrasTabPane.keyPressed (keyEvent);
        keyEvent.consume ();
        break;

      case COMMA:
      case PERIOD:
        fontManager.keyPressed (keyEvent);
        keyEvent.consume ();
        break;

      case F:
        filterManager.keyPressed (keyEvent);
        keyEvent.consume ();
        break;

      default:
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  void changeRootFolder ()
  // ---------------------------------------------------------------------------------//
  {
    if (setRootFolder ())
    {
      treePane.setRootFolder (new AppleTreeItem (new AppleTreeFile (rootFolder)));
      dbStatusBar.setStatusMessage ("Root folder changed");
    }
    else
      dbStatusBar.setStatusMessage ("Root folder unchanged");
  }

  // ---------------------------------------------------------------------------------//
  private void validateRootFolderOrExit ()
  // ---------------------------------------------------------------------------------//
  {
    String rootFolderName = prefs.get (PREFS_ROOT_FOLDER, "");
    if (rootFolderName.isEmpty ())
    {
      AppBase.showAlert (AlertType.INFORMATION, "Disk Image Folder",
          "The Disk Image file folder has not yet been defined. Please choose the "
              + "TOP LEVEL FOLDER where you store your Disk Image files. This folder "
              + "may contain subfolders. It can also be changed at any time.");
    }
    else
    {
      rootFolder = new File (rootFolderName);
      if (!rootFolder.exists ())
        rootFolder = null;
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
    String rootFolderName = rootFolder == null ? "" : rootFolder.getAbsolutePath ();
    DirectoryChooser directoryChooser = new DirectoryChooser ();
    directoryChooser.setTitle ("Set Apple file folder");

    if (rootFolderName.isEmpty ())
      directoryChooser.setInitialDirectory (new File (System.getProperty ("user.home")));
    else
      directoryChooser.setInitialDirectory (rootFolder);

    File file = directoryChooser.showDialog (null);
    if (file != null && file.isDirectory ()
        && !file.getAbsolutePath ().equals (rootFolderName))
    {
      rootFolder = file;
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
