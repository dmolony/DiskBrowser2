package com.bytezone.diskbrowser2.gui;

import javafx.scene.image.Image;

// -----------------------------------------------------------------------------------//
class IconHolder
// -----------------------------------------------------------------------------------//
{
  private static final int FLOPPY_13_SECTOR = 116480;
  private static final int FLOPPY_16_SECTOR = 143360;
  private static final int FLOPPY_800K = 819200;

  static String[] folderIcons = { "folder-open-icon.png",         //
      "folder-icon.png",              //
      "folder.png",                   //
      "Places-folder-apple-icon.png", //
  };

  static String[] diskIcons = { "5floppy-unmount-icon.png", "disk-icon.png",
      "disk-save-yellow-icon.png", "disk.png", "Floppy-Large-icon.png", "Floppy-blue-icon.png",
      "Floppy-brown-icon.png", "Floppy-green-icon.png", "Floppy-light-blue-icon.png",
      "Floppy-orange-icon.png", "Floppy-pink-icon.png", "Floppy-purple-icon.png",
      "Floppy-red-icon.png", "Floppy-yellow-icon.png", "Devices-media-floppy-icon.png" };

  private final Image[] folderIconImages = new Image[folderIcons.length];
  private final Image[] diskIconImages = new Image[diskIcons.length];
  private final Image[][] fileIconImages = new Image[4][26];
  private final Image folderArchiveZipIcon;
  private final Image harddiskIcon;

  //  static String[] folderIcons32 = { "Places-folder-apple-icon-32.png" };

  // ---------------------------------------------------------------------------------//
  public IconHolder ()
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < folderIconImages.length; i++)
      folderIconImages[i] =
          new Image (getClass ().getResourceAsStream ("/resources/" + folderIcons[i]));

    for (int i = 0; i < diskIconImages.length; i++)
      diskIconImages[i] =
          new Image (getClass ().getResourceAsStream ("/resources/" + diskIcons[i]));

    folderArchiveZipIcon =
        new Image (getClass ().getResourceAsStream ("/resources/Folder-Archive-zip-icon.png"));
    harddiskIcon =
        new Image (getClass ().getResourceAsStream ("/resources/Devices-drive-harddisk-icon.png"));

    for (int i = 0, j = 65; i < 26; i++, j++)
    {
      fileIconImages[0][i] = new Image (getClass ()
          .getResourceAsStream (String.format ("/resources/Letter-%s-lg-icon.png", (char) (j))));
      fileIconImages[1][i] = new Image (getClass ()
          .getResourceAsStream (String.format ("/resources/Letter-%s-blue-icon.png", (char) (j))));
      fileIconImages[2][i] = new Image (getClass ()
          .getResourceAsStream (String.format ("/resources/Letter-%s-pink-icon.png", (char) (j))));
      fileIconImages[3][i] = new Image (getClass ()
          .getResourceAsStream (String.format ("/resources/Letter-%s-black-icon.png", (char) (j))));
    }
  }

  // ---------------------------------------------------------------------------------//
  //  Image getFileIconImage (TreeFile fileItem)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    DB2File db2File = fileItem.getDB2File ();
  //    FileSystem fileSystem = db2File.getFileSystem ();
  //    int iconLetter = db2File.getFileTypeChar ();
  //    if (iconLetter < 'A' || iconLetter > 'Z')
  //      iconLetter = 'X';
  //    return fileIconImages[fileSystem.getIconColour ()][iconLetter - 'A'];
  //  }

  // ---------------------------------------------------------------------------------//
  Image getFolderIconImage (TreeFile fileItem)
  // ---------------------------------------------------------------------------------//
  {
    if (fileItem.isCompressedLocalFile ())
      return folderArchiveZipIcon;
    if (fileItem.isLocalDirectory () || fileItem.isAppleFolder ())
      return folderIconImages[1];
    //    if (fileItem.isFileItemContainer ())
    //      return folderIconImages[2];

    int size = (int) fileItem.getLocalFile ().length ();
    switch (size)
    {
      case FLOPPY_13_SECTOR:
        return diskIconImages[4];

      case FLOPPY_16_SECTOR:
      case FLOPPY_16_SECTOR + 64:
        return diskIconImages[3];

      case FLOPPY_800K:
      case FLOPPY_800K + 64:
        return diskIconImages[5];

      default:
        if (size > FLOPPY_800K + 64)
          return harddiskIcon;
        return diskIconImages[6];
    }
  }
}