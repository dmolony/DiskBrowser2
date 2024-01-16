package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class DiskLayoutTab extends TabBase //implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  protected DiskLayoutGroup diskLayoutGroup = new DiskLayoutGroup ();

  //  protected FormattedAppleFile formattedAppleFile;
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
    borderPane.setTop (diskLayoutGroup);
    this.setContent (borderPane);
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();
    //    formattedAppleFile = treeFile.getFormattedAppleFile ();

    if (appleFile == null)
      appleFileSystem = treeFile.getAppleFileSystem ();
    else
    {
      if (appleFile.hasEmbeddedFileSystem ())
        appleFileSystem = appleFile.getEmbeddedFileSystem ();
      else
        appleFileSystem = appleFile.getParentFileSystem ();
    }

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
    super.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    super.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  public void addClickListener (GridClickListener listener)
  // ---------------------------------------------------------------------------------//
  {
    diskLayoutGroup.addClickListener (listener);
  }
}
