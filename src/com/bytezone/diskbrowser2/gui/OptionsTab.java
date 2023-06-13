package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.ProdosConstants;
import com.bytezone.appleformat.assembler.AssemblerPreferences;
import com.bytezone.appleformat.assembler.AssemblerProgram;
import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;
import com.bytezone.appleformat.basic.ApplesoftBasicProgram;
import com.bytezone.appleformat.graphics.Graphics;
import com.bytezone.appleformat.graphics.GraphicsPreferences;
import com.bytezone.appleformat.text.Text;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class OptionsTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  ApplesoftBasicPreferences applesoftBasicPreferences =
      ApplesoftBasicProgram.basicPreferences;
  GraphicsPreferences graphicsPreferences = Graphics.graphicsPreferences;
  AssemblerPreferences assemblerPreferences = AssemblerProgram.assemblerPreferences;
  TextPreferences textPreferences = Text.textPreferences;

  OptionsPaneApplesoft optionsPaneApplesoft =
      new OptionsPaneApplesoft (applesoftBasicPreferences);
  OptionsPaneGraphics optionsPaneGraphics = new OptionsPaneGraphics (graphicsPreferences);
  OptionsPaneAssembler optionsPaneAssembler =
      new OptionsPaneAssembler (assemblerPreferences);
  OptionsPaneText optionsPaneText = new OptionsPaneText (textPreferences);

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
