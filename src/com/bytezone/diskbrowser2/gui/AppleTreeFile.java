package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleContainer;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFilePath;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.AppleForkedFile;

import javafx.scene.image.Image;

// -----------------------------------------------------------------------------------//
public class AppleTreeFile
// -----------------------------------------------------------------------------------//
{
  private static IconMaker icons = new IconMaker ();

  private File localFile;         // local folder or local file with valid extension
  private Path path;

  private AppleFile appleFile;
  private AppleFileSystem appleFileSystem;
  private FormattedAppleFile formattedAppleFile;

  private int extensionNo;

  private String name;
  private String prefix;
  private String suffix;

  private String sortString;

  // ---------------------------------------------------------------------------------//
  public AppleTreeFile (AppleFileSystem appleFileSystem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleFileSystem = appleFileSystem;

    if (appleFileSystem.isHybrid ())            // one of two file systems in FsHybrid
      name = appleFileSystem.getFileSystemType ().toString ();
    else
      name = appleFileSystem.getFileName ();

    sortString = name.toLowerCase ();
    suffix = "";
    prefix = sortString;
  }

  // ---------------------------------------------------------------------------------//
  public AppleTreeFile (AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.appleFile = appleFile;

    name = appleFile.getFileName ();

    sortString = name.toLowerCase ();
    suffix = "";
    prefix = sortString;

    if (appleFile.hasEmbeddedFileSystem ())
      appleFileSystem = appleFile.getEmbeddedFileSystem ();
  }

  // ---------------------------------------------------------------------------------//
  // File will be either a local folder or a local file with a valid suffix. A folder's
  // children will be populated, but a file will only be converted to an AppleFileSystem
  // when the tree node is expanded or selected. See setAppleFileSystem() below.
  // ---------------------------------------------------------------------------------//
  public AppleTreeFile (File file)
  // ---------------------------------------------------------------------------------//
  {
    assert !file.isHidden ();

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

  // ---------------------------------------------------------------------------------//
  // Called when a local file (without a file system) is selected or expanded.
  // ---------------------------------------------------------------------------------//
  void readAppleFileSystem ()
  // ---------------------------------------------------------------------------------//
  {
    assert isLocalFile ();
    assert appleFileSystem == null;

    appleFileSystem = AppleTreeView.fileSystemFactory.getFileSystem (path);
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
    return name;
  }

  // ---------------------------------------------------------------------------------//
  AppleFile getAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile;
  }

  // ---------------------------------------------------------------------------------//
  AppleFileSystem getAppleFileSystem ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFileSystem;
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
  void setFormattedAppleFile (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    assert this.formattedAppleFile == null;
    this.formattedAppleFile = formattedAppleFile;
  }

  // ---------------------------------------------------------------------------------//
  FormattedAppleFile getFormattedAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    return formattedAppleFile;
  }

  // ---------------------------------------------------------------------------------//
  Image getImage ()
  // ---------------------------------------------------------------------------------//
  {
    if (isCompressedLocalFile ())
      return icons.zipImage;

    if (isLocalDirectory () || isAppleFolder ())
      return icons.folderImage;

    if (isLocalFile () || isAppleFileSystem ())
      return icons.diskImage;

    return icons.getImage (appleFile);
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
    return appleFile != null;
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleFileSystem ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFileSystem != null;
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
  public boolean isAppleFork ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && appleFile.isFork ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleContainer ()
  // ---------------------------------------------------------------------------------//
  {
    if (appleFileSystem != null)
      return true;

    return appleFile != null
        && (appleFile instanceof AppleContainer || appleFile.isForkedFile ());
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleDataFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && !isAppleContainer ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean hasSubdirectories ()
  // ---------------------------------------------------------------------------------//
  {
    if (appleFile instanceof AppleFilePath afp)
      return afp.getFullFileName ().indexOf (afp.getSeparator ()) > 0;

    return false;
  }

  // ---------------------------------------------------------------------------------//
  List<AppleTreeFile> listAppleFiles ()
  // ---------------------------------------------------------------------------------//
  {
    List<AppleTreeFile> children = new ArrayList<> ();

    if (appleFileSystem != null)
    {
      for (AppleFile file : appleFileSystem.getFiles ())
        if (file.isActualFile ())
          children.add (new AppleTreeFile (file));

      for (AppleFileSystem fs : appleFileSystem.getFileSystems ())
        children.add (new AppleTreeFile (fs));
    }

    if (appleFile != null)
    {
      if (appleFile instanceof AppleContainer ac)
      {
        for (AppleFile file : ac.getFiles ())
          if (file.isActualFile ())
            children.add (new AppleTreeFile (file));

        for (AppleFileSystem fs : ac.getFileSystems ())
          children.add (new AppleTreeFile (fs));
      }

      if (appleFile.isForkedFile ())
        for (AppleFile file : ((AppleForkedFile) appleFile).getForks ())
          children.add (new AppleTreeFile (file));
    }

    return children;
  }

  // ---------------------------------------------------------------------------------//
  private long getLocalFileSize ()
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
  private String toDetailedString ()
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

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  public void dump ()
  // ---------------------------------------------------------------------------------//
  {
    System.out.printf ("--------------------------------------------------------%n");
    System.out.printf ("LocalFile ............ %s%n", localFile);
    System.out.printf ("Path ................. %s%n", path);
    System.out.printf ("AppleFile ............ %s%n",
        appleFile == null ? "null" : appleFile.getFileName ());
    System.out.printf ("AppleFileSystem ...... %s%n",
        appleFileSystem == null ? "null" : appleFileSystem.getFileName ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    if (isAppleFile () && !(appleFile.isFolder () || appleFile.hasEmbeddedFileSystem ()))
      return String.format ("%s %03d %s", appleFile.getFileTypeText (),
          appleFile.getTotalBlocks (), name);

    return name;
  }
}
