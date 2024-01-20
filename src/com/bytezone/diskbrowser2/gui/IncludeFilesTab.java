package com.bytezone.diskbrowser2.gui;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class IncludeFilesTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  OptionsPaneFileFilter optionsPaneFileFilter = new OptionsPaneFileFilter ();

  // ---------------------------------------------------------------------------------//
  public IncludeFilesTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    setContent (optionsPaneFileFilter);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);
  }

  // ---------------------------------------------------------------------------------//
  void addListener (PreferenceChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    optionsPaneFileFilter.addListener (listener);
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    appleTreeFile = appleTreeItem.getValue ();
    appleFile = appleTreeFile.getAppleFile ();
    appleFileSystem = appleTreeFile.getAppleFileSystem ();
    //    formattedAppleFile =
    //        appleFile == null ? null : (FormattedAppleFile) appleFile.getUserData ();

    refresh ();
  }
}
