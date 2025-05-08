package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleBlock.BlockType;

import javafx.scene.paint.Color;

public class ColorChooser
{
  // ---------------------------------------------------------------------------------//
  static Color getBaseColor (AppleBlock block)
  // ---------------------------------------------------------------------------------//
  {
    return getBaseColor (block.getBlockType ());
  }

  // ---------------------------------------------------------------------------------//
  static Color getBaseColor (BlockType blockType)
  // ---------------------------------------------------------------------------------//
  {
    return switch (blockType)
    {
      case ORPHAN -> Color.LIGHTYELLOW;
      case EMPTY -> Color.GHOSTWHITE;
      case FILE_DATA -> Color.CRIMSON;
      case FS_DATA -> Color.ROYALBLUE;
    };
  }

  // ---------------------------------------------------------------------------------//
  static Color getFsSubTypeColor (AppleBlock block)
  // ---------------------------------------------------------------------------------//
  {
    return getFsSubTypeColor (block.getBlockSubType ());
  }

  // ---------------------------------------------------------------------------------//
  static Color getFsSubTypeColor (String blockSubType)
  // ---------------------------------------------------------------------------------//
  {
    return switch (blockSubType)
    {
      case "DOS" -> Color.LIGHTSLATEGRAY;           // DOS
      case "VTOC" -> Color.TURQUOISE;               // DOS
      case "TSLIST" -> Color.DARKCYAN;              // DOS
      case "CATALOG" -> Color.BLUEVIOLET;           // COMMON
      case "INDEX" -> Color.GREEN;                  // PRODOS
      case "M-INDEX" -> Color.MEDIUMORCHID;         // PRODOS
      case "V-BITMAP" -> Color.TURQUOISE;           // PRODOS
      case "FOLDER" -> Color.DARKORANGE;            // PRODOS
      case "FORK" -> Color.YELLOWGREEN;             // PRODOS
      case "BIN2 HDR" -> Color.GREEN;               // BINARY2
      default -> null;
    };
  }

  // ---------------------------------------------------------------------------------//
  static Color getFileSubTypeColor (AppleBlock block)
  // ---------------------------------------------------------------------------------//
  {
    return getFileSubTypeColor (block.getBlockSubType ());
  }

  // ---------------------------------------------------------------------------------//
  static Color getFileSubTypeColor (String blockSubType)
  // ---------------------------------------------------------------------------------//
  {
    return switch (blockSubType)
    {
      case "TEXT" -> Color.RED;
      case "INTEGER" -> Color.YELLOW;
      case "APPLESOFT" -> Color.GREEN;
      case "BINARY" -> Color.PURPLE;
      default -> null;
    };
  }
}
