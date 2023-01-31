package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class GraphicsTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  private TreeFile treeFile;                    // the item to display
  private AppleFile appleFile;

  // ---------------------------------------------------------------------------------//
  public GraphicsTab (String title, KeyCode keyCode)
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
  }

  // ---------------------------------------------------------------------------------//
  public void setFormatter (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    //    treeFile = appleTreeItem.getValue ();
    //    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;

    refresh ();
  }
}
