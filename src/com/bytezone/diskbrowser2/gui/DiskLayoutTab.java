package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class DiskLayoutTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  protected DiskLayoutGroup diskLayoutGroup = new DiskLayoutGroup ();
  protected KeyPaneProdos keyPaneProdos = new KeyPaneProdos ();
  protected KeyPaneDos keyPaneDos = new KeyPaneDos ();
  protected KeyPanePascal keyPanePascal = new KeyPanePascal ();
  protected KeyPaneCpm keyPaneCpm = new KeyPaneCpm ();

  protected FormattedAppleFile formattedAppleFile;
  protected AppleTreeItem appleTreeItem;
  protected AppleTreeNode appleTreeFile;
  protected AppleFile appleFile;
  protected AppleFileSystem appleFileSystem;
  protected BorderPane borderPane;

  // ---------------------------------------------------------------------------------//
  public DiskLayoutTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    borderPane = new BorderPane ();
    borderPane.setTop (diskLayoutGroup);
    borderPane.setBottom (keyPaneProdos);
    this.setContent (borderPane);

    //    borderPane
    //    .setBackground (new Background (new BackgroundFill (Color.WHITE, null, null)));
  }

  // ---------------------------------------------------------------------------------//
  void setFactory (FormattedAppleBlockFactory formattedAppleBlockFactory)
  // ---------------------------------------------------------------------------------//
  {
    diskLayoutGroup.setFactory (formattedAppleBlockFactory);
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    appleTreeFile = appleTreeItem.getValue ();
    appleFile = appleTreeFile.getAppleFile ();
    appleFileSystem = appleTreeFile.getAppleFileSystem ();
    formattedAppleFile = appleTreeFile.getFormattedAppleFile ();

    if (appleFile != null)
    {
      if (appleFile.hasEmbeddedFileSystem ())
        appleFileSystem = appleFile.getEmbeddedFileSystem ();
      else
        appleFileSystem = appleFile.getParentFileSystem ();
    }

    if (appleFileSystem == null)
    {
      borderPane.setBottom (null);
    }
    else
      switch (appleFileSystem.getFileSystemType ())
      {
        case DOS:
          borderPane.setBottom (keyPaneDos);
          break;
        case PRODOS:
          borderPane.setBottom (keyPaneProdos);
          break;
        case CPM:
          borderPane.setBottom (keyPaneCpm);
          break;
        case PASCAL:
          borderPane.setBottom (keyPanePascal);
          break;
        default:
          borderPane.setBottom (null);
          break;
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
