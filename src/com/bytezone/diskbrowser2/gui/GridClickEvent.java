package com.bytezone.diskbrowser2.gui;

import java.util.List;

import com.bytezone.filesystem.AppleBlock;

// -----------------------------------------------------------------------------------//
public class GridClickEvent
// -----------------------------------------------------------------------------------//
{
  final AppleBlock block;
  final List<AppleBlock> blocks;
  final GridClickType gridClickType;

  enum GridClickType
  {
    SINGLE, MULTI
  }

  // ---------------------------------------------------------------------------------//
  public GridClickEvent (AppleBlock block)
  // ---------------------------------------------------------------------------------//
  {
    this.block = block;
    blocks = null;
    gridClickType = GridClickType.SINGLE;
  }

  // ---------------------------------------------------------------------------------//
  public GridClickEvent (List<AppleBlock> blocks)
  // ---------------------------------------------------------------------------------//
  {
    block = null;
    this.blocks = blocks;
    gridClickType = GridClickType.MULTI;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return block.toString ();
  }
}
