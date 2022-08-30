package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

// -----------------------------------------------------------------------------------//
public class TreeFile
//-----------------------------------------------------------------------------------//
{
  private File file;
  private Path path;
  private AppleFile appleFile;
  private List<AppleFileSystem> skipFiles = new ArrayList<> ();

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
    this.file = null;

    this.appleFile = appleFile;
    name = appleFile.getName ();

    sortString = name.toLowerCase ();
    suffix = "";
    prefix = sortString;
  }

  // ---------------------------------------------------------------------------------//
  public TreeFile (File file)
  // ---------------------------------------------------------------------------------//
  {
    this.path = file.toPath ();
    this.file = file;

    common ();
  }

  // ---------------------------------------------------------------------------------//
  public TreeFile (Path path)
  // ---------------------------------------------------------------------------------//
  {
    this.path = path;
    this.file = path.toFile ();

    common ();
  }

  // ---------------------------------------------------------------------------------//
  private void common ()
  // ---------------------------------------------------------------------------------//
  {
    this.extensionNo = AppleTreeView.factory.getSuffixNumber (file.getName ());

    if (path.getNameCount () == 0)
      name = path.toString ();
    else
      name = path.getName (path.getNameCount () - 1).toString ();

    sortString = name.toLowerCase ();

    if (extensionNo < 0)
    {
      suffix = "";
      prefix = sortString;
    }
    else
    {
      suffix = AppleTreeView.factory.getSuffix (file.getName ());
      prefix = sortString.substring (0, name.length () - suffix.length ());
    }
  }

  // ---------------------------------------------------------------------------------//
  void setAppleFile (AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    assert isLocalFile ();
    assert this.appleFile == null;

    this.appleFile = appleFile;

    while (true)
    {
      List<AppleFile> files = this.appleFile.getFiles ();
      if (files.size () == 1                        //
          && files.get (0).isFileSystem ())         // contains exactly one file system
      {
        skipFiles.add ((AppleFileSystem) this.appleFile);
        this.appleFile = files.get (0);                   // skip level
      }
      else
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  String getName ()
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }

  // ---------------------------------------------------------------------------------//
  String getCatalogLine ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile == null ? name : appleFile.getCatalogLine ();
  }

  // ---------------------------------------------------------------------------------//
  AppleFile getAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile;
  }

  // ---------------------------------------------------------------------------------//
  File getFile ()
  // ---------------------------------------------------------------------------------//
  {
    return file;
  }

  // ---------------------------------------------------------------------------------//
  public Path getPath ()
  // ---------------------------------------------------------------------------------//
  {
    return path;
  }

  // ---------------------------------------------------------------------------------//
  public boolean isLocalFile ()
  // ---------------------------------------------------------------------------------//
  {
    return file != null && file.isFile ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isCompressedLocalFile ()
  // ---------------------------------------------------------------------------------//
  {
    return file != null && (suffix.equals ("zip") || suffix.equals ("gz"));
  }

  // ---------------------------------------------------------------------------------//
  public boolean isLocalDirectory ()
  // ---------------------------------------------------------------------------------//
  {
    return file != null && file.isDirectory ();
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
  public boolean isAppleDataFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null && !appleFile.isFolder () && !appleFile.isFileSystem ();
  }

  // ---------------------------------------------------------------------------------//
  public List<TreeFile> listLocalFiles ()
  // ---------------------------------------------------------------------------------//
  {
    List<TreeFile> fileList = new ArrayList<> ();

    for (File file : this.file.listFiles ())
      if (!file.isHidden ())
        fileList.add (new TreeFile (file));

    return fileList;
  }

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
      text.append (appleFile.catalog ());
    }

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }
}
