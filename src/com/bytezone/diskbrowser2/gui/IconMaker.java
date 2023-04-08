package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;
import com.bytezone.filesystem.ProdosConstants;

import javafx.scene.image.Image;

// -----------------------------------------------------------------------------------//
public class IconMaker
// -----------------------------------------------------------------------------------//
{
  private static String prodos = "-lg-icon.png";
  private static String pascal = "-blue-icon.png";
  private static String dos = "-pink-icon.png";
  private static String cpm = "-black-icon.png";

  final Image zipImage;
  final Image diskImage;
  final Image folderImage;

  // Prodos
  private final Image prodosTextImage;
  private final Image prodosPicImage;
  private final Image prodosBasicImage;
  private final Image prodosBinaryImage;
  private final Image prodosSysImage;
  private final Image prodosVarsImage;
  private final Image prodosXImage;

  // Pascal
  private final Image pascalCodeImage;
  private final Image pascalTextImage;
  private final Image pascalDataImage;
  private final Image pascalGrafImage;
  private final Image pascalPhotoImage;
  private final Image pascalInfoImage;
  private final Image pascalXImage;

  // Dos
  private final Image dosTextImage;
  private final Image dosApplesoftImage;
  private final Image dosBinaryImage;
  private final Image dosIntegerImage;
  private final Image dosXImage;

  // CPM
  private final Image cpmComImage;
  private final Image cpmPrnImage;
  private final Image cpmDocImage;
  private final Image cpmBasImage;
  private final Image cpmAsmImage;
  private final Image cpmOvrImage;
  private final Image cpmMacImage;
  private final Image cpmXImage;

  // ---------------------------------------------------------------------------------//
  public IconMaker ()
  // ---------------------------------------------------------------------------------//
  {
    zipImage = new Image (getClass ().getResourceAsStream ("/resources/Zip-icon.png"));
    diskImage = new Image (getClass ().getResourceAsStream ("/resources/disk.png"));
    folderImage =
        new Image (getClass ().getResourceAsStream ("/resources/folder-icon.png"));

    // Prodos
    prodosTextImage = get ("T" + prodos);
    prodosPicImage = get ("P" + prodos);
    prodosBasicImage = get ("A" + prodos);
    prodosBinaryImage = get ("B" + prodos);
    prodosSysImage = get ("S" + prodos);
    prodosVarsImage = get ("V" + prodos);
    prodosXImage = get ("X" + prodos);

    // Pascal
    pascalCodeImage = get ("C" + pascal);
    pascalTextImage = get ("T" + pascal);
    pascalDataImage = get ("D" + pascal);
    pascalGrafImage = get ("G" + pascal);
    pascalPhotoImage = get ("P" + pascal);
    pascalInfoImage = get ("I" + pascal);
    pascalXImage = get ("X" + pascal);

    // Dos
    dosTextImage = get ("T" + dos);
    dosApplesoftImage = get ("A" + dos);
    dosBinaryImage = get ("B" + dos);
    dosIntegerImage = get ("I" + dos);
    dosXImage = get ("X" + dos);

    // CPM
    cpmComImage = get ("C" + cpm);
    cpmPrnImage = get ("P" + cpm);
    cpmDocImage = get ("D" + cpm);
    cpmBasImage = get ("B" + cpm);
    cpmAsmImage = get ("A" + cpm);
    cpmOvrImage = get ("O" + cpm);
    cpmMacImage = get ("M" + cpm);
    cpmXImage = get ("X" + cpm);
  }

  // ---------------------------------------------------------------------------------//
  Image getImage (AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    FileSystemType fileSystemType = appleFile.getFileSystemType ();

    if (fileSystemType == FileSystemType.DOS)
    {
      return switch (appleFile.getFileType ())
      {
        case 0x04 -> dosBinaryImage;
        case 0x01 -> dosIntegerImage;
        case 0x02 -> dosApplesoftImage;
        case 0x00 -> dosTextImage;
        case 0x08 -> dosXImage;
        case 0x10 -> dosXImage;
        case 0x20 -> dosXImage;
        case 0x40 -> dosXImage;
        default -> dosXImage;
      };
    }

    if (fileSystemType == FileSystemType.PRODOS)
    {
      return switch (appleFile.getFileType ())
      {
        case 0x00 -> prodosXImage;
        case ProdosConstants.FILE_TYPE_TEXT -> prodosTextImage;
        case ProdosConstants.FILE_TYPE_APPLESOFT_BASIC -> prodosBasicImage;
        case ProdosConstants.FILE_TYPE_BINARY -> prodosBinaryImage;
        case ProdosConstants.FILE_TYPE_SYS -> prodosSysImage;
        case ProdosConstants.FILE_TYPE_PIC -> prodosPicImage;
        case ProdosConstants.FILE_TYPE_PNT -> prodosPicImage;
        case ProdosConstants.FILE_TYPE_INTEGER_BASIC_VARS -> prodosVarsImage;
        case ProdosConstants.FILE_TYPE_APPLESOFT_BASIC_VARS -> prodosVarsImage;
        default -> prodosXImage;
      };
    }

    if (fileSystemType == FileSystemType.CPM)
    {
      return switch (appleFile.getFileTypeText ())
      {
        case "COM" -> cpmComImage;
        case "PRN" -> cpmPrnImage;
        case "DOC" -> cpmDocImage;
        case "BAS" -> cpmBasImage;
        case "ASM" -> cpmAsmImage;
        case "OVR" -> cpmOvrImage;
        case "MAC" -> cpmMacImage;
        default -> cpmXImage;
      };
    }

    if (fileSystemType == FileSystemType.PASCAL)
    {
      return switch (appleFile.getFileType ())
      {
        case 0 -> pascalXImage;           // Volume
        case 1 -> pascalXImage;           // Bad
        case 2 -> pascalCodeImage;
        case 3 -> pascalTextImage;
        case 4 -> pascalInfoImage;
        case 5 -> pascalDataImage;
        case 6 -> pascalGrafImage;
        case 7 -> pascalPhotoImage;
        case 8 -> pascalXImage;           // Secure directory
        default -> pascalXImage;
      };
    }

    return pascalXImage;
  }

  // ---------------------------------------------------------------------------------//
  private Image get (String icon)
  // ---------------------------------------------------------------------------------//
  {
    return new Image (getClass ().getResourceAsStream ("/resources/Letter-" + icon));
  }
}
