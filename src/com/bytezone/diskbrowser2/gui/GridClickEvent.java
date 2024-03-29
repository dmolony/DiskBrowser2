package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleBlock;

// -----------------------------------------------------------------------------------//
public class GridClickEvent
// -----------------------------------------------------------------------------------//
{
  AppleBlock block;

  // ---------------------------------------------------------------------------------//
  public GridClickEvent (AppleBlock block)
  // ---------------------------------------------------------------------------------//
  {
    this.block = block;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return block.toString ();
  }
}
