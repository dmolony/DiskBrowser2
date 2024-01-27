package com.bytezone.diskbrowser2.gui;

import java.util.Arrays;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.appbase.StageManager;
import com.bytezone.appbase.StatusBar;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.appleformat.FormattedAppleFileFactory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class DiskBrowserApp extends AppBase implements SaveState
// -----------------------------------------------------------------------------------//
{
  static PreferencesManager preferencesManager;
  static FormattedAppleFileFactory formattedAppleFileFactory;
  static FormattedAppleBlockFactory formattedAppleBlockFactory;

  // set three panes for the split pane
  private final SplitPane splitPane = new SplitPane ();
  private TreePane treePane;
  private OutputTabPane outputTabPane;
  private ExtrasTabPane rightTabPane;

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

    Preferences prefs = getPreferences ();
    preferencesManager = new PreferencesManager (prefs);
    formattedAppleFileFactory = new FormattedAppleFileFactory (prefs);
    formattedAppleBlockFactory = new FormattedAppleBlockFactory (prefs);

    treePane = new TreePane (preferencesManager, formattedAppleFileFactory);
    outputTabPane = new OutputTabPane ("Output");
    rightTabPane = new ExtrasTabPane ("Extras", formattedAppleBlockFactory);

    TreeHeaderBar treeHeaderBar = new TreeHeaderBar ();
    OutputHeaderBar outputHeaderBar = new OutputHeaderBar ();
    ExtrasHeaderBar extrasHeaderBar = new ExtrasHeaderBar ();

    splitPane.getItems ().addAll (                          //
        createBorderPane (treeHeaderBar, treePane),
        createBorderPane (outputHeaderBar, outputTabPane),
        createBorderPane (extrasHeaderBar, rightTabPane));

    // menu listeners
    viewMenu.setExclusiveFilterAction (e -> filterManager.toggleFilterExclusion ());
    viewMenu.setFilterAction (e -> filterManager.showWindow ());
    viewMenu.setFontAction (e -> fontManager.showWindow ());

    // root folder change listeners
    fileMenu.addRootFolderChangeListener (treeHeaderBar);
    fileMenu.addRootFolderChangeListener (treePane);

    // font change listeners
    fontManager.addFontChangeListener (treePane);
    fontManager.addFontChangeListener (outputTabPane);
    fontManager.addFontChangeListener (dbStatusBar);

    // filter change listeners (filter parameters)
    filterManager.addFilterListener (dbStatusBar);
    filterManager.addFilterListener (outputTabPane.dataTab);
    filterManager.addFilterListener (viewMenu);

    // treeview listeners
    treePane.addTreeNodeListener (fileMenu);
    treePane.addTreeNodeListener (outputTabPane);
    treePane.addTreeNodeListener (rightTabPane);
    treePane.addTreeNodeListener (outputHeaderBar);
    treePane.addTreeNodeListener (extrasHeaderBar);
    //    treePane.addTreeNodeListener (extrasHeaderBar);

    // grid click listeners
    rightTabPane.diskLayoutTab.addClickListener (outputTabPane);

    // tab change listeners
    outputTabPane.addTabChangeListener (viewMenu);
    rightTabPane.addTabChangeListener (extrasHeaderBar);

    // preference change listeners
    rightTabPane.fileOptionsTab.addListener (outputTabPane.dataTab);
    rightTabPane.fileOptionsTab.addListener (outputTabPane.graphicsTab);
    rightTabPane.fileOptionsTab.addListener (outputTabPane.extrasTab);
    rightTabPane.includeFilesTab.addListener (treePane);

    // suffix totals listeners
    treePane.addSuffixTotalsListener (rightTabPane.includeFilesTab.optionsPaneFileFilter);

    // add menus
    menuBar.getMenus ().addAll (fileMenu, viewMenu);

    fileMenu.setOutputWriter (outputTabPane.dataTab);

    saveStateList.addAll (Arrays.asList (   //
        filterManager, outputTabPane, rightTabPane, fileMenu, treePane, fontManager,
        this));

    return splitPane;
  }

  // ---------------------------------------------------------------------------------//
  private BorderPane createBorderPane (HeaderBar headerBar, Region region)
  // ---------------------------------------------------------------------------------//
  {
    BorderPane borderPane = new BorderPane ();

    borderPane.setTop (headerBar);
    borderPane.setCenter (region);

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
        rightTabPane.keyPressed (keyEvent);
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
  //  void changeRootFolder ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (setRootFolder ())
  //    {
  //      //  treePane.setRootFolder (new AppleTreeItem (new AppleTreeFile (rootFolder)));
  //      treePane.setRootFolder (rootFolder, formattedAppleFileFactory);
  //      dbStatusBar.setStatusMessage ("Root folder changed");
  //    }
  //    else
  //      dbStatusBar.setStatusMessage ("Root folder unchanged");
  //  }

  // ---------------------------------------------------------------------------------//
  //  private void validateRootFolderOrExit ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    String rootFolderName = prefs.get (PREFS_ROOT_FOLDER, "");
  //    if (rootFolderName.isEmpty ())
  //    {
  //      AppBase.showAlert (AlertType.INFORMATION, "Disk Image Folder",
  //          "The Disk Image file folder has not yet been defined. Please choose the "
  //              + "TOP LEVEL FOLDER where you store your Disk Image files. This folder "
  //              + "may contain subfolders. It can also be changed at any time.");
  //    }
  //    else
  //    {
  //      rootFolder = new File (rootFolderName);
  //      if (!rootFolder.exists ())
  //        rootFolder = null;
  //    }
  //
  //    if (rootFolderName.isEmpty () && !setRootFolder ())
  //    {
  //      Platform.exit ();
  //      System.exit (0);
  //    }
  //  }

  // ---------------------------------------------------------------------------------//
  //  private boolean setRootFolder ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    String rootFolderName = rootFolder == null ? "" : rootFolder.getAbsolutePath ();
  //    DirectoryChooser directoryChooser = new DirectoryChooser ();
  //    directoryChooser.setTitle ("Set Apple file folder");
  //
  //    if (rootFolderName.isEmpty ())
  //      directoryChooser.setInitialDirectory (new File (System.getProperty ("user.home")));
  //    else
  //      directoryChooser.setInitialDirectory (rootFolder);
  //
  //    File file = directoryChooser.showDialog (null);
  //    if (file != null && file.isDirectory ()
  //        && !file.getAbsolutePath ().equals (rootFolderName))
  //    {
  //      rootFolder = file;
  //      rootFolderName = file.getAbsolutePath ();
  //      prefs.put (PREFS_ROOT_FOLDER, rootFolderName);
  //      return true;
  //    }
  //
  //    return false;
  //  }

  // ---------------------------------------------------------------------------------//
  public static void main (String[] args)
  // ---------------------------------------------------------------------------------//
  {
    Application.launch (args);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    formattedAppleFileFactory.save ();
    preferencesManager.save ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
  }
}
