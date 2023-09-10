package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class DiskLayoutTab extends TabBase //implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  DiskLayoutCanvas diskLayoutCanvas = new DiskLayoutCanvas ();
  private final ScrollPane scrollPane = new ScrollPane (diskLayoutCanvas);

  protected FormattedAppleFile formattedAppleFile;
  protected AppleTreeItem appleTreeItem;
  protected AppleTreeFile treeFile;
  protected AppleFile appleFile;
  protected AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  public DiskLayoutTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane borderPane = new BorderPane ();
    borderPane.setCenter (scrollPane);
    this.setContent (borderPane);
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();
    formattedAppleFile = treeFile.getFormattedAppleFile ();

    appleFileSystem = appleFile == null ? treeFile.getAppleFileSystem ()
        : appleFile.getParentFileSystem ();

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    diskLayoutCanvas.setFileSystem (appleFileSystem);

    setValid (true);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    super.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    super.save (prefs);
  }
}
