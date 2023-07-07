package com.bytezone.diskbrowser2.gui;

import java.io.File;

// -----------------------------------------------------------------------------------//
public class TreeHeaderBar extends HeaderBar implements RootFolderChangeListener
// -----------------------------------------------------------------------------------//
{
  private final String home = System.getProperty ("user.home");

  // ---------------------------------------------------------------------------------//
  @Override
  public void rootFolderChanged (File rootFolder)
  // ---------------------------------------------------------------------------------//
  {
    if (rootFolder == null)
    {
      leftLabel.setText ("");
      return;
    }

    //    leftLabel.setText (treeFile.getCatalogLine ());
    //    leftLabel.setText (treeFile.toString ());
    String pathName = rootFolder.getAbsolutePath ();

    if (pathName.startsWith (home))
      pathName = pathName.replaceFirst (home, "~");

    leftLabel.setText (pathName);
  }
}
