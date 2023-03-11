package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;
import com.bytezone.filesystem.ProdosConstants;

import javafx.scene.image.Image;

// -----------------------------------------------------------------------------------//
public class TreeFile
//-----------------------------------------------------------------------------------//
{
  private static String prodos = "-lg-icon.png";
  private static String pascal = "-blue-icon.png";
  private static String dos = "-pink-icon.png";
  private static String cpm = "-black-icon.png";

  private static final Image zipImage =
      new Image (TreeFile.class.getResourceAsStream ("/resources/zip-icon.png"));
  private static final Image diskImage =
      new Image (TreeFile.class.getResourceAsStream ("/resources/disk.png"));
  private static final Image folderImage =
      new Image (TreeFile.class.getResourceAsStream ("/resources/folder-icon.png"));

  // Prodos
  private static final Image prodosTextImage = get ("T" + prodos);
  private static final Image prodosPicImage = get ("P" + prodos);
  private static final Image prodosBasicImage = get ("A" + prodos);
  private static final Image prodosBinaryImage = get ("B" + prodos);
  private static final Image prodosSysImage = get ("S" + prodos);
  private static final Image prodosVarsImage = get ("V" + prodos);
  private static final Image prodosXImage = get ("X" + prodos);

  // Pascal
  private static final Image pascalCodeImage = get ("C" + pascal);
  private static final Image pascalTextImage = get ("T" + pascal);
  private static final Image pascalDataImage = get ("D" + pascal);
  private static final Image pascalGrafImage = get ("G" + pascal);
  private static final Image pascalPhotoImage = get ("P" + pascal);
  private static final Image pascalInfoImage = get ("I" + pascal);
  private static final Image pascalXImage = get ("X" + pascal);

  // Dos
  private static final Image dosTextImage = get ("T" + dos);
  private static final Image dosApplesoftImage = get ("A" + dos);
  private static final Image dosBinaryImage = get ("B" + dos);
  private static final Image dosIntegerImage = get ("I" + dos);
  private static final Image dosXImage = get ("X" + dos);

  // CPM
  private static final Image cpmComImage = get ("C" + cpm);
  private static final Image cpmPrnImage = get ("P" + cpm);
  private static final Image cpmDocImage = get ("D" + cpm);
  private static final Image cpmBasImage = get ("B" + cpm);
  private static final Image cpmAsmImage = get ("A" + cpm);
  private static final Image cpmOvrImage = get ("O" + cpm);
  private static final Image cpmMacImage = get ("M" + cpm);
  private static final Image cpmXImage = get ("X" + cpm);

  private File localFile;                    // local folder or local file with valid extension
  private Path path;

  private AppleFile appleFile;
  private FormattedAppleFile formattedAppleFile;

  //  private List<AppleFileSystem> skipFiles = new ArrayList<> ();

  private int extensionNo;

  private String name;
  private String prefix;
  private String suffix;

  private String sortString;

  // ---------------------------------------------------------------------------------//
  public TreeFile (AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.path = null;
    this.localFile = null;

    this.appleFile = appleFile;

    // hybrid disks have two file systems, so use the file system name 
    // to differentiate them
    if (appleFile.isFileSystem () && ((AppleFileSystem) appleFile).isHybrid ())
      name = ((AppleFileSystem) appleFile).getFileSystemType ().toString ();
    else
      name = appleFile.getFileName ();

    sortString = name.toLowerCase ();
    suffix = "";
    prefix = sortString;
  }

  // ---------------------------------------------------------------------------------//
  // File will be either a local folder or a local file with a valid suffix. A folder's
  // children will be populated, but a file will only be converted to an AppleFileSystem
  // when the tree node is expanded or selected. See setAppleFileSystem() below.
  // ---------------------------------------------------------------------------------//
  public TreeFile (File file)
  // ---------------------------------------------------------------------------------//
  {
    this.path = file.toPath ();
    this.localFile = file;

    if (path.getNameCount () == 0)
      name = path.toString ();
    else
      name = path.getName (path.getNameCount () - 1).toString ();

    sortString = name.toLowerCase ();

    if (file.isDirectory ())
    {
      suffix = "";
      prefix = sortString;
      extensionNo = -1;
    }
    else
    {
      suffix = AppleTreeView.fileSystemFactory.getSuffix (file.getName ());
      prefix = sortString.substring (0, name.length () - suffix.length ());
      extensionNo = AppleTreeView.fileSystemFactory.getSuffixNumber (file.getName ());
    }
  }

  // Called when a local file is selected or expanded.
  // ---------------------------------------------------------------------------------//
  void setAppleFileSystem ()
  // ---------------------------------------------------------------------------------//
  {
    assert isLocalFile ();
    assert appleFile == null;

    appleFile = AppleTreeView.fileSystemFactory.getFileSystem (path);

    //    while (true)
    //    {
    //      List<AppleFile> files = this.appleFile.getFiles ();
    //      if (files.size () == 1                        //
    //          && files.get (0).isFileSystem ())         // contains exactly one file system
    //      {
    //        skipFiles.add ((AppleFileSystem) this.appleFile);
    //        this.appleFile = files.get (0);                   // skip level
    //      }
    //      else
    //        break;
    //    }
    //    if (appleFile instanceof Fs2img fs)
    //    {
    //      skipFiles.add (fs);
    //      this.appleFile = fs.getFiles ().get (0);            // skip level
    //    }
    //    else
    //    this.appleFile = appleFileSystem;
  }

  // ---------------------------------------------------------------------------------//
  String getName ()
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }

  // ---------------------------------------------------------------------------------//
  int getExtensionNo ()
  // ---------------------------------------------------------------------------------//
  {
    return extensionNo;
  }

  // ---------------------------------------------------------------------------------//
  String getCatalogLine ()
  // ---------------------------------------------------------------------------------//
  {
    //    return appleFile == null ? name : appleFile.getCatalogLine ();
    return name;
  }

  // ---------------------------------------------------------------------------------//
  AppleFile getAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile;
  }

  // ---------------------------------------------------------------------------------//
  FormattedAppleFile getFormattedAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    if (formattedAppleFile == null && appleFile != null)
      formattedAppleFile =
          AppleTreeView.formattedAppleFileFactory.getFormattedAppleFile (appleFile);

    return formattedAppleFile;
  }

  // ---------------------------------------------------------------------------------//
  File getLocalFile ()
  // ---------------------------------------------------------------------------------//
  {
    return localFile;
  }

  // ---------------------------------------------------------------------------------//
  public Path getPath ()
  // ---------------------------------------------------------------------------------//
  {
    return path;
  }

  // ---------------------------------------------------------------------------------//
  Image getImage ()
  // ---------------------------------------------------------------------------------//
  {
    if (isCompressedLocalFile ())
      return zipImage;

    if (isLocalDirectory () || isAppleFolder ())
      return folderImage;

    if (isLocalFile () || isAppleFileSystem ())
      return diskImage;

    FileSystemType fileSystemType = appleFile.getFileSystem ().getFileSystemType ();

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
  public boolean isLocalFile ()
  // ---------------------------------------------------------------------------------//
  {
    return localFile != null && localFile.isFile ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isCompressedLocalFile ()
  // ---------------------------------------------------------------------------------//
  {
    return localFile != null && (suffix.equals ("zip") || suffix.equals ("gz"));
  }

  // ---------------------------------------------------------------------------------//
  public boolean isLocalDirectory ()
  // ---------------------------------------------------------------------------------//
  {
    return localFile != null && localFile.isDirectory ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && appleFile.isFile ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleFileSystem ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && appleFile.isFileSystem ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleFolder ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && appleFile.isFolder ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleForkedFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && appleFile.isForkedFile ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleContainer ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && (appleFile.isFileSystem () || appleFile.isFolder ()
        || appleFile.isForkedFile ());
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleDataFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && !(appleFile.isFolder () || appleFile.isFileSystem ()
        || appleFile.isForkedFile ());
  }

  // ---------------------------------------------------------------------------------//
  //  public List<TreeFile> listLocalFiles ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    List<TreeFile> fileList = new ArrayList<> ();
  //
  //    for (File file : this.file.listFiles ())
  //      if (!file.isHidden ())
  //        fileList.add (new TreeFile (file));
  //
  //    return fileList;
  //  }

  // ---------------------------------------------------------------------------------//
  public List<TreeFile> listAppleFiles ()
  // ---------------------------------------------------------------------------------//
  {
    List<TreeFile> fileList = new ArrayList<> ();

    for (AppleFile file : appleFile.getFiles ())
      fileList.add (new TreeFile (file));

    return fileList;
  }

  // ---------------------------------------------------------------------------------//
  public long getLocalFileSize ()
  // ---------------------------------------------------------------------------------//
  {
    try
    {
      if (path == null)
      {
        System.out.println ("null path");
        return -1;
      }
      return Files.size (path);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }

    return -1;
  }

  // ---------------------------------------------------------------------------------//
  String getSortString ()
  // ---------------------------------------------------------------------------------//
  {
    return sortString;
  }

  // ---------------------------------------------------------------------------------//
  public String toDetailedString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    String fileSizeText = "";
    String suffixText = "";

    if (extensionNo >= 0)
    {
      fileSizeText = String.format ("%,d", getLocalFileSize ());
      suffixText = suffix.substring (1);
    }

    if (isLocalFile ())
    {
      text.append (String.format ("Path ............ %s%n", path.toString ()));
      text.append (String.format ("Name ............ %s%n", name));
      text.append (String.format ("Sort string ..... %s%n", sortString));
      text.append (String.format ("Prefix .......... %s%n", prefix));
      text.append (String.format ("Suffix .......... %s%n", suffixText));
      text.append (String.format ("Extension no .... %d%n", extensionNo));
      text.append (String.format ("File size ....... %s", fileSizeText));
    }

    if (isAppleFile ())
    {
      text.append ("\n\n");
      text.append (appleFile.toString ());
    }

    //    if (skipFiles.size () > 0)
    //    {
    //      for (AppleFileSystem fs : skipFiles)
    //      {
    //        text.append ("\n");
    //        text.append (fs.toText ());
    //      }
    //    }

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  private static Image get (String icon)
  // ---------------------------------------------------------------------------------//
  {
    return new Image (TreeFile.class.getResourceAsStream ("/resources/Letter-" + icon));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    try
    {
      if (isAppleFile ())
      {
        if (appleFile.isFork ())
          return appleFile.getFileName ();          // DATA or RESOURCE

        if (true)
          return String.format ("%s %03d %s",         // full file details
              appleFile.getFileTypeText (), appleFile.getTotalBlocks (), name);
        else
          return String.format ("%-30s %s %03d",         // full file details
              name, appleFile.getFileTypeText (), appleFile.getTotalBlocks ());
      }
    }
    catch (UnsupportedOperationException e)       // unfinished - NuFX files
    {
      e.printStackTrace ();
    }

    return name;
  }
}
