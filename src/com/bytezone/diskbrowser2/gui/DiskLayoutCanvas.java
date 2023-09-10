package com.bytezone.diskbrowser2.gui;

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
      System.out.println ("Null FS");
      clear ();
      return;
    }

    maxBlocks = fileSystem.getTotalBlocks ();
    blockHeight = UNIT_SIZE;
    blockWidth = UNIT_SIZE * (fileSystem.getBlockSize () / 256.0);

    System.out.println (fileSystem.getFileSystemType ());
    System.out.println (maxBlocks);
    System.out.println (fileSystem.getType ());

    switch (fileSystem.getType ())
    {
      case SECTOR:
        columns = fileSystem.getBlocksPerTrack ();
        rows = (maxBlocks - 1) / columns + 1;
        break;

      case BLOCK:
        columns = switch (fileSystem.getBlockSize ())
        {
          case 128 -> 32;
          case 256 -> 16;
          case 512 -> 8;
          case 1024 -> 4;
          default -> 4;
        };

        if (maxBlocks > 3200)        // find better size
        {
          blockHeight /= 1.3;
          blockWidth /= 2;
          columns = 128;
        }
        else if (maxBlocks > 1600)        // find better size
        {
          blockHeight /= 2;
          blockWidth /= 2;
          columns = 32;
        }

        rows = (maxBlocks - 1) / columns + 1;

        break;
    }

    setWidth (INSET * 2 + columns * blockWidth);
    setHeight (INSET * 2 + rows * blockHeight);

    System.out.printf ("Width: %6f, Height: %6f%n", getWidth (), getHeight ());
    System.out.printf ("Width: %6f, Height: %6f%n", blockWidth, blockHeight);
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
    System.out.printf ("Rows: %d, Columns: %d%n", rows, columns);
    System.out.printf ("Width: %f, Height: %f%n", getWidth (), getHeight ());

    int x = INSET;
    int y = INSET;

    gc.setFill (Color.GREEN);

    for (int row = 0; row < rows; row++)
    {
      for (int col = 0; col < columns; col++)
      {
        gc.fillRect (x, y, blockWidth - 1, blockHeight - 1);
        x += blockWidth;
      }
      x = INSET;
      y += blockHeight;
    }
  }
}
