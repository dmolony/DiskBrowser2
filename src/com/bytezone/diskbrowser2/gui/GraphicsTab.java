package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class GraphicsTab extends DBGraphicsTab
// -----------------------------------------------------------------------------------//
{
  private static final double SCALE = 2.5;

  private AppleTreeFile treeFile;                    // the item to display
  private AppleFile appleFile;
  private FormattedAppleFile formattedAppleFile;

  // ---------------------------------------------------------------------------------//
  public GraphicsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    // convert Image to Canvas while scaling
    if (formattedAppleFile != null)
      resize (formattedAppleFile.getImage (), SCALE);
  }

  // ---------------------------------------------------------------------------------//
  private void resize (Image image, double scale)
  // ---------------------------------------------------------------------------------//
  {
    double width = image.getWidth ();
    double height = image.getHeight ();

    canvas.setWidth (width * scale);
    canvas.setHeight (height * scale);
    clearCanvas ();

    GraphicsContext gc = canvas.getGraphicsContext2D ();
    PixelReader pixelReader = image.getPixelReader ();

    double wy = 0;

    for (int y = 0; y < height; y++)
    {
      double wx = 0;

      for (int x = 0; x < width; x++)
      {
        Color c = pixelReader.getColor (x, y);
        gc.setFill (c);
        gc.fillRect (wx, wy, scale, scale);
        wx += scale;
      }

      wy += scale;
    }
  }

  // ---------------------------------------------------------------------------------//
  public void setFormattedAppleFile (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
