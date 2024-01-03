package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class DiskLayoutCanvas extends Canvas
// -----------------------------------------------------------------------------------//
{
  private static final int UNIT_SIZE = 16;
  private static final int INSET = 10;

  private int maxBlocks;
  private double blockHeight;
  private double blockWidth;
  private int columns;
  private int rows;

  private AppleFileSystem fileSystem;

  // ---------------------------------------------------------------------------------//
  public DiskLayoutCanvas ()
  // ---------------------------------------------------------------------------------//
  {
    super ();
  }

  // ---------------------------------------------------------------------------------//
  public void setFileSystem (AppleFileSystem fileSystem)
  // ---------------------------------------------------------------------------------//
  {
    if (this.fileSystem == fileSystem)
      return;

    this.fileSystem = fileSystem;

    if (fileSystem == null)
    {
      System.out.println ("Null FS in DiskLayoutCanvas");
      clear ();
      return;
    }

    maxBlocks = fileSystem.getTotalBlocks ();
    blockHeight = UNIT_SIZE;
    blockWidth = UNIT_SIZE * (fileSystem.getBlockSize () / 256.0);

    switch (fileSystem.getAddressType ())
    {
      case SECTOR:
        columns = fileSystem.getBlocksPerTrack ();
        rows = (maxBlocks - 1) / columns + 1;
        break;

      case BLOCK:
        //        columns = switch (fileSystem.getBlockSize ())
        //        {
        //          case 128 -> 32;
        //          case 256 -> 16;
        //          case 512 -> 8;
        //          case 1024 -> 4;
        //          default -> 4;         // impossible
        //        };

        columns = 32 / (fileSystem.getBlockSize () / 128);
        rows = (maxBlocks - 1) / columns + 1;

        break;
    }

    setWidth (INSET * 2 + columns * (blockWidth + 1) - 1);
    setHeight (INSET * 2 + rows * (blockHeight + 1) - 1);

    draw ();
  }

  // ---------------------------------------------------------------------------------//
  void clear ()
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    gc.setFill (Color.WHITE);
    gc.fillRect (0, 0, getWidth (), getHeight ());
  }

  // ---------------------------------------------------------------------------------//
  void draw ()
  // ---------------------------------------------------------------------------------//
  {
    clear ();

    GraphicsContext gc = getGraphicsContext2D ();

    int x = INSET;
    int y = INSET;

    gc.setFill (Color.GREEN);
    int blockNo = 0;

    OUT: for (int row = 0; row < rows; row++)
    {
      for (int col = 0; col < columns; col++)
      {
        AppleBlock block = fileSystem.getBlock (blockNo, null);

        if (block.getBlockType () == null)
          gc.setFill (Color.BEIGE);
        else
          gc.setFill (switch (block.getBlockType ())
          {
            case OS_DATA -> Color.GREEN;
            case FILE_DATA -> Color.RED;
            case EMPTY -> Color.WHITE;
            case ORPHAN -> Color.YELLOW;
          });

        gc.fillRect (x, y, blockWidth, blockHeight);
        x += blockWidth + 1;

        if (++blockNo >= fileSystem.getTotalBlocks ())
          break OUT;
      }
      x = INSET;
      y += blockHeight + 1;
    }
  }
}
