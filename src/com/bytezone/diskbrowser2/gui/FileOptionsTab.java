package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.appleformat.FormattedAppleFileFactory;

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

  FormattedAppleFileFactory formattedAppleFileFactory;

  // ---------------------------------------------------------------------------------//
  public FileOptionsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  void setFactory (FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    this.formattedAppleFileFactory = formattedAppleFileFactory;
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

    ApplePreferences preferences = formattedAppleFileFactory.getPreferences (appleFile);

    if (preferences == null || preferences.getOptionsType () == null)
      setContent (null);
    else
      setContent (switch (preferences.getOptionsType ())
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
  public void setAppleTreeNode (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeNode = appleTreeNode;

    appleFile = appleTreeNode.getAppleFile ();
    appleFileSystem = appleTreeNode.getAppleFileSystem ();

    refresh ();
  }
}
