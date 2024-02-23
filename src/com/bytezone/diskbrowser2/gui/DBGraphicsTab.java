package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class DBGraphicsTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  protected FormattedAppleFile formattedAppleFile;
  protected AppleTreeItem appleTreeItem;
  protected AppleTreeNode treeFile;
  protected AppleFile appleFile;
  protected AppleFileSystem appleFileSystem;

  protected final Canvas canvas = new Canvas ();
  protected final ScrollPane scrollPane = new ScrollPane (canvas);

  // ---------------------------------------------------------------------------------//
  public DBGraphicsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    scrollPane.setStyle ("-fx-background: white;-fx-border-color: lightgray;");

    setContent (scrollPane);
  }

  // ---------------------------------------------------------------------------------//
  protected void hide ()
  // ---------------------------------------------------------------------------------//
  {
    canvas.setWidth (1);
    canvas.setHeight (1);
    clearCanvas (Color.WHITE);
  }

  // ---------------------------------------------------------------------------------//
  protected void clearCanvas (Color fillColor)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = canvas.getGraphicsContext2D ();

    gc.setFill (fillColor);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());
  }

  // ---------------------------------------------------------------------------------//
  protected void drawImage (Image image, double scale)
  // ---------------------------------------------------------------------------------//
  {
    canvas.setWidth (image.getWidth () * scale);
    canvas.setHeight (image.getHeight () * scale);

    clearCanvas (Color.WHITE);

    GraphicsContext gc = canvas.getGraphicsContext2D ();
    gc.drawImage (image, 0, 0, canvas.getWidth (), canvas.getHeight ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    // scroll to top/left corner
    scrollPane.setVvalue (0);
    scrollPane.setHvalue (0);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    super.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    super.save (prefs);
  }
}
