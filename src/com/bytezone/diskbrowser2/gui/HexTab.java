package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class HexTab extends DBTextTab implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_HEX_BYTES = 0x20_000;

  TreeFile treeFile;
  //  DataFile dataFile;

  // ---------------------------------------------------------------------------------//
  public HexTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
    textFormatter = new TextFormatterHex ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    List<String> lines = new ArrayList<> ();

    //    if (dataFile == null)
    return lines;

    //    byte[] buffer = dataFile.getDataBuffer ();
    //    return Utility.getHexDumpLines (buffer, 0, Math.min (MAX_HEX_BYTES, buffer.length));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = treeFile;

    //    if (nodeData.isPhysicalSequentialDataset ())
    //      dataFile = nodeData.getDataFile ();
    //    else
    //      dataFile = null;

    refresh ();
  }
}
