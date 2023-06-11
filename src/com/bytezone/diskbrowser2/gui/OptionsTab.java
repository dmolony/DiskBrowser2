package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.ProdosConstants;
import com.bytezone.appleformat.assembler.AssemblerPreferences;
import com.bytezone.appleformat.assembler.AssemblerProgram;
import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;
import com.bytezone.appleformat.basic.ApplesoftBasicProgram;
import com.bytezone.appleformat.text.Text;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class OptionsTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  ApplesoftBasicPreferences applesoftBasicPreferences =
      ApplesoftBasicProgram.basicPreferences;
  //  GraphicsPreferences graphicsPreferences =
  AssemblerPreferences assemblerPreferences = AssemblerProgram.assemblerPreferences;
  TextPreferences textPreferences = Text.textPreferences;

  OptionsPaneApplesoft optionsPaneApplesoft =
      new OptionsPaneApplesoft (applesoftBasicPreferences);
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
            System.out.println ("text options");
            break;

          case ProdosConstants.FILE_TYPE_BINARY:
            setContent (optionsPaneAssembler);
            System.out.println ("binary options");
            break;

          case ProdosConstants.FILE_TYPE_APPLESOFT_BASIC:
            setContent (optionsPaneApplesoft);
            System.out.println ("applesoft options");
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
            System.out.println ("text options");
            break;

          case 1:             // integer basic
            setContent (null);
            System.out.println ("integer basic options");
            break;

          case 2:             // applesoft basic
            setContent (optionsPaneApplesoft);
            System.out.println ("applesoft options");
            break;

          case 4:             // binary
            setContent (optionsPaneAssembler);
            System.out.println ("binary options");
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
            System.out.println ("text options");
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
            System.out.println ("CPM text options");
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
