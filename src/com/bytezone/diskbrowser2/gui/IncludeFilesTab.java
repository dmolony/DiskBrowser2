package com.bytezone.diskbrowser2.gui;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class IncludeFilesTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  OptionsPaneFileFilter optionsPaneFileFilter = new OptionsPaneFileFilter ();
  OptionsPane optionsPane = new OptionsPane (optionsPaneFileFilter);

  // ---------------------------------------------------------------------------------//
  public IncludeFilesTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    setContent (optionsPane);
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
  public void setAppleTreeNode (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeNode = appleTreeNode;

    appleFile = appleTreeNode.getAppleFile ();
    appleFileSystem = appleTreeNode.getAppleFileSystem ();

    refresh ();
  }
}
