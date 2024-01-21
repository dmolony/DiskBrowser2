package com.bytezone.diskbrowser2.gui;

import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class ScreenCell
// -----------------------------------------------------------------------------------//
{
  static final List<Color> darkColors =
      Arrays.asList (Color.BLUE, Color.BLUEVIOLET, Color.GRAY);

  private final GraphicsContext gc;

  private final int x;
  private final int y;
  private final int blockWidth;
  private final int blockHeight;

  private Color color = Color.WHITE;
  private boolean selected;

  // ---------------------------------------------------------------------------------//
  public ScreenCell (GraphicsContext gc, int x, int y, int blockWidth, int blockHeight)
  // ---------------------------------------------------------------------------------//
  {
    this.gc = gc;

    this.x = x;
    this.y = y;

    this.blockWidth = blockWidth;
    this.blockHeight = blockHeight;
  }

  // ---------------------------------------------------------------------------------//
  void draw (Color color, boolean selected)
  // ---------------------------------------------------------------------------------//
  {
    if (this.color.equals (color) && this.selected == selected)
      return;

    this.color = color;
    this.selected = selected;

    gc.setFill (color);
    gc.fillRect (x, y, blockWidth - 1, blockHeight - 1);

    if (selected)
    {
      gc.setFill (darkColors.contains (color) ? Color.WHITE : Color.BLACK);
      gc.fillOval (x - 3 + blockWidth / 2, y - 3 + blockHeight / 2, 5, 5);
    }
  }

  // ---------------------------------------------------------------------------------//
  void erase ()
  // ---------------------------------------------------------------------------------//
  {
    gc.clearRect (x, y, blockWidth - 1, blockHeight - 1);
    color = Color.WHITE;
    selected = false;
  }

  // ---------------------------------------------------------------------------------//
  void frame ()
  // ---------------------------------------------------------------------------------//
  {
    gc.setFill (Color.BLACK);
    gc.strokeRect (x, y, blockWidth - 1, blockHeight - 1);
  }

  // ---------------------------------------------------------------------------------//
  void label (String text)
  // ---------------------------------------------------------------------------------//
  {
    gc.setFill (Color.BLACK);
    gc.fillText (text, x + blockWidth + 10, y + 13);
  }
}
