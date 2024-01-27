package com.bytezone.diskbrowser2.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class KeyPaneCpm extends KeyPane
// -----------------------------------------------------------------------------------//
{

  // ---------------------------------------------------------------------------------//
  KeyPaneCpm ()
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    int x = X_OFFSET;
    int y = Y_OFFSET + LINE_SIZE * 2;

    ScreenCell dos = new ScreenCell (gc, x, y, SIZE_W, SIZE_H);
    draw (dos, Color.GRAY, "DOS");

    ScreenCell catalog = new ScreenCell (gc, x + 150, y, SIZE_W, SIZE_H);
    draw (catalog, Color.BLUEVIOLET, "Catalog");
  }
}
