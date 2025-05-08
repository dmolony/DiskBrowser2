package com.bytezone.diskbrowser2.gui;

import static com.bytezone.diskbrowser2.gui.ColorChooser.getFsSubTypeColor;

import javafx.scene.canvas.GraphicsContext;

// -----------------------------------------------------------------------------------//
public class KeyPaneDos extends KeyPane
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  KeyPaneDos ()
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    int x = X_OFFSET;
    int y = Y_OFFSET + LINE_SIZE * 2;

    ScreenCell dos = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (dos, getFsSubTypeColor ("DOS"), "DOS");

    ScreenCell catalog = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (catalog, getFsSubTypeColor ("CATALOG"), "Catalog");

    y += LINE_SIZE;
    ScreenCell vtoc = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (vtoc, getFsSubTypeColor ("VTOC"), "VTOC");

    ScreenCell tslist = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (tslist, getFsSubTypeColor ("TSLIST"), "T/S List");
  }
}
