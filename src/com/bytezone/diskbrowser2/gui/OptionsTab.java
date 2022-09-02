package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class OptionsTab extends TabBase implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private TreeFile treeFile;                    // the item to display
  private AppleFile appleFile;

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
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = treeFile;
    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;

    refresh ();
  }
}
