package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.ProdosConstants;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class OptionsTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  OptionsPaneApplesoft optionsPaneApplesoft =
      new OptionsPaneApplesoft (FormattedAppleFileFactory.basicPreferences);
  OptionsPaneGraphics optionsPaneGraphics =
      new OptionsPaneGraphics (FormattedAppleFileFactory.graphicsPreferences);
  OptionsPaneAssembler optionsPaneAssembler =
      new OptionsPaneAssembler (FormattedAppleFileFactory.assemblerPreferences);
  OptionsPaneText optionsPaneText =
      new OptionsPaneText (FormattedAppleFileFactory.textPreferences);

  // ---------------------------------------------------------------------------------//
  public OptionsTab (String title, KeyCode keyCode)
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

    switch (appleFile.getFileSystemType ())
    {
      case PRODOS:
        switch (appleFile.getFileType ())
        {
          case ProdosConstants.FILE_TYPE_TEXT:
            setContent (optionsPaneText);
            break;

          case ProdosConstants.FILE_TYPE_BINARY:
            setContent (optionsPaneAssembler);
            break;

          case ProdosConstants.FILE_TYPE_APPLESOFT_BASIC:
            setContent (optionsPaneApplesoft);
            break;

          case ProdosConstants.FILE_TYPE_PIC:
          case ProdosConstants.FILE_TYPE_PNT:
            setContent (optionsPaneGraphics);
            break;

          default:
            setContent (null);
            System.out.println ("no options for PRODOS type " + appleFile.getFileType ());
        }
        break;

      case DOS:
        switch (appleFile.getFileType ())
        {
          case 0:             // text
            setContent (optionsPaneText);
            break;

          case 1:             // integer basic
            setContent (null);
            System.out.println ("integer basic options");
            break;

          case 2:             // applesoft basic
            setContent (optionsPaneApplesoft);
            break;

          case 4:             // binary
            setContent (optionsPaneAssembler);
            break;

          default:
            setContent (null);
            System.out.println ("no options for DOS type: " + appleFile.getFileType ());
        }
        break;

      case PASCAL:
        switch (appleFile.getFileType ())
        {
          case 2:             // code
            setContent (null);
            System.out.println ("Pascal CODE options");
            break;

          case 3:             // text
            setContent (optionsPaneText);
            break;

          default:
            setContent (null);
            System.out
                .println ("no options for PASCAL type: " + appleFile.getFileType ());
        }
        break;

      case CPM:
        switch (appleFile.getFileTypeText ())           // no file type value
        {
          case "TXT":
            setContent (optionsPaneText);
            break;

          default:
            setContent (null);
            System.out
                .println ("no options for CPM type: " + appleFile.getFileTypeText ());
        }
        break;

      default:
        setContent (null);
        System.out.println ("no options for FS type: " + appleFile.getFileSystemType ());
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  void addListener (PreferenceChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    optionsPaneApplesoft.optionsPane2Applesoft.addListener (listener);
    optionsPaneAssembler.optionsPane2Assembler.addListener (listener);
    optionsPaneGraphics.optionsPane2Graphics.addListener (listener);
    optionsPaneText.optionsPane2Text.addListener (listener);
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
