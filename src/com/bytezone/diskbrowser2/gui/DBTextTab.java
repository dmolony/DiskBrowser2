package com.bytezone.diskbrowser2.gui;

import java.util.List;

import com.bytezone.appbase.TextTabBase;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

// -----------------------------------------------------------------------------------//
public abstract class DBTextTab extends TextTabBase
// -----------------------------------------------------------------------------------//
{
  private final TextFlow textFlow = new TextFlow ();
  private final ScrollPane scrollPane = new ScrollPane (textFlow);

  DBTextFormatter textFormatter = new DBTextFormatter ();

  // ---------------------------------------------------------------------------------//
  public DBTextTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    textFlow.setLineSpacing (1);

    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    scrollPane.setStyle ("-fx-background: white;-fx-border-color: lightgray;");
    scrollPane.widthProperty ().addListener ( (obs, oldVal, newVal) ->
    {
      textFlow.setMaxWidth (scrollPane.getWidth () - 40);
    });

    setContent (scrollPane);
  }

  // ---------------------------------------------------------------------------------//
  abstract List<String> getLines ();
  // ---------------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    textFlow.getChildren ().setAll (textFormatter.format (getLines ()));

    //    textFlow.setMaxWidth (Control.USE_PREF_SIZE);
    scrollPane.setHbarPolicy (ScrollBarPolicy.NEVER);
    //    textFlow.setMaxWidth (500);

    scrollPane.setVvalue (0);
    scrollPane.setHvalue (0);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    super.setFont (font);

    textFormatter.setFont (font);

    for (Node node : textFlow.getChildren ())
      ((Text) node).setFont (font);
  }
}