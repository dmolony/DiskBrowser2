package com.bytezone.diskbrowser2.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class KeyPane extends Canvas
// -----------------------------------------------------------------------------------//
{
  protected static final int SIZE_H = 18;
  protected static final int SIZE_W = SIZE_H * 2;

  protected static final int X_OFFSET = 50;
  protected static final int Y_OFFSET = 10;
  protected static final int LINE_SIZE = 24;

  // ---------------------------------------------------------------------------------//
  public KeyPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (400, 200);

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFont (Font.font ("Consolas", 14));

    int x = X_OFFSET;
    int y = Y_OFFSET;

    ScreenCell empty = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (empty, Color.GHOSTWHITE, "Empty");

    ScreenCell orphan = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (orphan, Color.NAVAJOWHITE, "Orphan");

    y += LINE_SIZE;
    ScreenCell fileData = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (fileData, Color.CRIMSON, "File Data");

    ScreenCell fsData = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (fsData, Color.ROYALBLUE, "File System");
  }

  // ---------------------------------------------------------------------------------//
  protected void draw (ScreenCell screenCell, Color color, String label)
  // ---------------------------------------------------------------------------------//
  {
    screenCell.draw (color, false);
    screenCell.frame ();
    screenCell.label (label);
  }
}
