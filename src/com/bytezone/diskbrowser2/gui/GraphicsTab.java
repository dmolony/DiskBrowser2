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

    if (formattedAppleFile == null)
      clear ();
    else
    {
      Image image = formattedAppleFile.writeImage ();
      if (image == null)
        System.out.println ("image null");
      else
        resize (image, 3);              // convert Image to Canvas while scaling
    }
  }

  // ---------------------------------------------------------------------------------//
  private void resize (Image image, int factor)
  // ---------------------------------------------------------------------------------//
  {
    int width = (int) image.getWidth ();
    int height = (int) image.getHeight ();

    canvas.setWidth (width * factor);
    canvas.setHeight (height * factor);

    GraphicsContext gc = canvas.getGraphicsContext2D ();
    PixelReader pr = image.getPixelReader ();

    int wy = 0;

    for (int y = 0; y < height; y++)
    {
      int wx = 0;

      for (int x = 0; x < width; x++)
      {
        Color c = pr.getColor (x, y);
        gc.setFill (c);
        gc.fillRect (wx, wy, factor, factor);
        wx += factor;
      }

      wy += factor;
    }
  }

  // ---------------------------------------------------------------------------------//
  //  private WritableImage resize (Image oldImage, int factor)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    int width = (int) oldImage.getWidth ();
  //    int height = (int) oldImage.getHeight ();
  //
  //    WritableImage newImage = new WritableImage (width * factor, height * factor);
  //    PixelReader pr = oldImage.getPixelReader ();
  //    PixelWriter pw = newImage.getPixelWriter ();
  //    int wx = 0;
  //    int wy = 0;
  //
  //    for (int y = 0; y < height; y++)
  //      for (int x = 0; x < width; x++)
  //      {
  //        Color c = pr.getColor (x, y);
  //      }
  //  }

  // ---------------------------------------------------------------------------------//
  public void setFormattedAppleFile (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
