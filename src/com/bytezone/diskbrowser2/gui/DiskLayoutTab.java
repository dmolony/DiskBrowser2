package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// -----------------------------------------------------------------------------------//
public class DiskLayoutTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  protected DiskLayoutGroup diskLayoutGroup = new DiskLayoutGroup ();
  protected KeyPaneProdos keyPaneProdos = new KeyPaneProdos ();
  protected KeyPaneDos keyPaneDos = new KeyPaneDos ();
  protected KeyPanePascal keyPanePascal = new KeyPanePascal ();
  protected KeyPaneCpm keyPaneCpm = new KeyPaneCpm ();

  //  protected AppleTreeItem appleTreeItem;
  protected AppleFile appleFile;
  protected AppleFileSystem appleFileSystem;

  protected BorderPane borderPane = new BorderPane ();
  protected BorderPane keyPane = new BorderPane ();
  protected VBox vbox = new VBox ();

  boolean debug = false;

  // ---------------------------------------------------------------------------------//
  public DiskLayoutTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    this.setContent (borderPane);

    borderPane.setCenter (vbox);
    vbox.getChildren ().addAll (diskLayoutGroup, keyPane);
    keyPane.setCenter (null);

    borderPane.setStyle ("-fx-background: white;-fx-border-color: lightgray;");
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeNode (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    appleFile = appleTreeNode.getAppleFile ();
    appleFileSystem = appleTreeNode.getAppleFileSystem ();    // will be the efs

    // fix this
    AppleFileSystem embeddedFs =
        appleFile == null ? null : appleFile.hasEmbeddedFileSystem ()
            ? appleFile.getEmbeddedFileSystems ().get (0) : null;
    AppleFileSystem parentFs =
        appleFile == null ? null : appleFile.getParentFileSystem ();

    if (debug)
    {
      if (appleFileSystem != null)
        System.out.printf ("FS  : %s%n", appleFileSystem.getFileSystemType ());
      if (appleFile != null)
        System.out.printf ("File: %s%n", appleFile.getFileName ());
      if (embeddedFs != null)
        System.out.printf ("eFS : %s%n", embeddedFs.getFileSystemType ());
      if (parentFs != null)
        System.out.printf ("pFS : %s%n", parentFs.getFileSystemType ());
      System.out.println ("-------------------------------- treenode");
    }

    if (appleFileSystem == null && parentFs != null)
      appleFileSystem = parentFs;

    //    if (embeddedFs != null)       // the tree node always provides it as the AFS
    //    {
    //      FileSystemType pfst = parentFs.getFileSystemType ();
    //      if (pfst == FileSystemType.PRODOS)
    //        appleFileSystem = parentFs;
    //    }

    if (debug)
    {
      if (appleFileSystem != null)
        System.out.printf ("FS  : %s%n", appleFileSystem.getFileSystemType ());
      if (appleFile != null)
        System.out.printf ("File: %s%n", appleFile.getFileName ());
      System.out.println ("-------------------------------- result");
    }

    if (appleFileSystem == null)
      keyPane.setCenter (null);
    else
      keyPane.setCenter (switch (appleFileSystem.getFileSystemType ())
      {
        case DOS3, DOS4 -> keyPaneDos;
        case PRODOS -> keyPaneProdos;
        case CPM -> keyPaneCpm;
        case PASCAL -> keyPanePascal;
        default -> null;
      });

    refresh ();         // calls update()
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    diskLayoutGroup.refresh (appleFileSystem, appleFile);

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
