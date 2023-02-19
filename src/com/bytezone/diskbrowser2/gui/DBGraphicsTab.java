package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabBase;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class DBGraphicsTab extends TabBase
// -----------------------------------------------------------------------------------//
{
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
  protected void clear ()
  // ---------------------------------------------------------------------------------//
  {
    canvas.setWidth (1);
    canvas.setHeight (1);

    GraphicsContext gc = canvas.getGraphicsContext2D ();

    gc.setFill (Color.WHITE);
    gc.fillRect (0, 0, 1, 1);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    scrollPane.setVvalue (0);
    scrollPane.setHvalue (0);
  }
}
