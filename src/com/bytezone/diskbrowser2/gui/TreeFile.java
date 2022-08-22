package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.filesystem.AppleFile;

// -----------------------------------------------------------------------------------//
public class TreeFile
//-----------------------------------------------------------------------------------//
{
  private File file;
  private Path path;
  private AppleFile appleFile;

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
    //    this.appleFile = appleFile;
    setAppleFile (appleFile);
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
    this.extensionNo = Utility.getExtensionNumber (path);

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
      suffix = Utility.getExtension (extensionNo);
      prefix = sortString.substring (0, name.length () - suffix.length ());
    }
  }

  // ---------------------------------------------------------------------------------//
  String getName ()
  // ---------------------------------------------------------------------------------//
  {
    if (name == null)     // temporary
      return "Null";
    return name;
  }

  // ---------------------------------------------------------------------------------//
  void setAppleFile (AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.appleFile = appleFile;
    name = appleFile.getName ();
    System.out.printf ("[%s]%n", name);
    sortString = name.toLowerCase ();
    suffix = "";
    prefix = sortString;
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
  public boolean isFile ()
  // ---------------------------------------------------------------------------------//
  {
    if (file == null)
      return false;
    return file.isFile ();
  }

  // ---------------------------------------------------------------------------------//
  public boolean isAppleFile ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile != null;
  }

  // ---------------------------------------------------------------------------------//
  public boolean isDirectory ()
  // ---------------------------------------------------------------------------------//
  {
    if (file == null)
      return false;
    return file.isDirectory ();
  }

  // ---------------------------------------------------------------------------------//
  public List<TreeFile> listFiles ()
  // ---------------------------------------------------------------------------------//
  {
    List<TreeFile> fileList = new ArrayList<> ();

    for (File file : this.file.listFiles ())
      if (!file.isHidden ())
        fileList.add (new TreeFile (file));

    return fileList;
  }

  // ---------------------------------------------------------------------------------//
  public List<AppleFile> listAppleFiles ()
  // ---------------------------------------------------------------------------------//
  {
    return appleFile.getFiles ();
  }

  // ---------------------------------------------------------------------------------//
  public long getFileSize ()
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
  public String toDetailedString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    String fileSizeText = "";
    String suffixText = "";

    if (extensionNo >= 0)
    {
      fileSizeText = String.format ("%,d", getFileSize ());
      suffixText = suffix.substring (1);
    }

    if (isFile ())
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
