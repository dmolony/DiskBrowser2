package com.bytezone.diskbrowser2.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class KeyPaneProdos extends KeyPane
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  KeyPaneProdos ()
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    int x = X_OFFSET;
    int y = Y_OFFSET + LINE_SIZE * 2;

    ScreenCell catalog = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (catalog, Color.BLUEVIOLET, "Catalog");

    ScreenCell index = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (index, Color.GREEN, "Index");

    y += LINE_SIZE;
    ScreenCell bitmap = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (bitmap, Color.TURQUOISE, "Bitmap");

    ScreenCell mIndex = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (mIndex, Color.MEDIUMORCHID, "Master Index");

    y += LINE_SIZE;
    ScreenCell folder = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (folder, Color.DARKORANGE, "Folder");

    ScreenCell fork = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (fork, Color.YELLOWGREEN, "Fork");
  }
}
