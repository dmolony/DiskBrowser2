package com.bytezone.diskbrowser2.gui;

import static com.bytezone.diskbrowser2.gui.ColorChooser.getFsSubTypeColor;

import javafx.scene.canvas.GraphicsContext;

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
    draw (catalog, getFsSubTypeColor ("CATALOG"), "Catalog");

    ScreenCell index = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (index, getFsSubTypeColor ("INDEX"), "Index");

    y += LINE_SIZE;
    ScreenCell bitmap = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (bitmap, getFsSubTypeColor ("V-BITMAP"), "Bitmap");

    ScreenCell mIndex = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (mIndex, getFsSubTypeColor ("M-INDEX"), "Master Index");

    y += LINE_SIZE;
    ScreenCell folder = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (folder, getFsSubTypeColor ("FOLDER"), "Folder");

    ScreenCell fork = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (fork, getFsSubTypeColor ("FORK"), "Fork");
  }
}
