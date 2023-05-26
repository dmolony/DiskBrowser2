package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.ProdosConstants;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class OptionsTab extends DBOptionsTab
// -----------------------------------------------------------------------------------//
{
  OptionsPaneApplesoft optionsPaneApplesoft = new OptionsPaneApplesoft ();

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
            setContent (null);
            System.out.println ("text options");
            break;

          case ProdosConstants.FILE_TYPE_BINARY:
            setContent (null);
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
            setContent (null);
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
            setContent (null);
            System.out.println ("binary options");
            break;

          default:
            setContent (null);
            System.out.println ("no options for DOS type: " + appleFile.getFileType ());
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
