package com.bytezone.diskbrowser2.gui;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class FileOptionsTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  PreferencesPane[] optionPanes = { new OptionsPaneApplesoft (),
      new OptionsPaneAssembler (), new OptionsPaneGraphics (), new OptionsPaneText () };

  OptionsPane optionsPaneApplesoft = new OptionsPane (optionPanes[0]);
  OptionsPane optionsPaneAssembler = new OptionsPane (optionPanes[1]);
  OptionsPane optionsPaneGraphics = new OptionsPane (optionPanes[2]);
  OptionsPane optionsPaneText = new OptionsPane (optionPanes[3]);

  // ---------------------------------------------------------------------------------//
  public FileOptionsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    if (appleFile == null)
    {
      setContent (null);
      return;
    }

    if (formattedAppleFile == null || formattedAppleFile.getOptionsType () == null)
      setContent (null);
    else
      setContent (switch (formattedAppleFile.getOptionsType ())
      {
        case APPLESOFT -> optionsPaneApplesoft;
        case ASSEMBLER -> optionsPaneAssembler;
        case GRAPHICS -> optionsPaneGraphics;
        case TEXT -> optionsPaneText;
      });
  }

  // ---------------------------------------------------------------------------------//
  void addListener (PreferenceChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    for (PreferencesPane optionPane : optionPanes)
      optionPane.addListener (listener);
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    appleTreeFile = appleTreeItem.getValue ();
    appleFile = appleTreeFile.getAppleFile ();
    appleFileSystem = appleTreeFile.getAppleFileSystem ();
    formattedAppleFile = appleTreeFile.getFormattedAppleFile ();

    refresh ();
  }
}
