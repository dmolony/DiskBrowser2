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
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();
    appleFileSystem = treeFile.getAppleFileSystem ();
    formattedAppleFile = treeFile.getFormattedAppleFile ();

    refresh ();
  }
}
