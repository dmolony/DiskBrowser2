package com.bytezone.diskbrowser2.gui;

import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.appbase.TextTabBase;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// -----------------------------------------------------------------------------------//
public abstract class DBTextTab extends TextTabBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_LINE_WRAP = "LineWrap";

  private static final int PAD = 40;

  DBTextFormatter textFormatter = new DBTextFormatter ();
  private boolean lineWrap;

  // line wrapping
  ChangeListener<? super Number> cl = (obs, oldVal, newVal) ->
  {
    setWrap ();
  };

  // ---------------------------------------------------------------------------------//
  public DBTextTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    textFlow.setLineSpacing (1);

    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    scrollPane.setStyle ("-fx-background: white;-fx-border-color: lightgray;");
    scrollPane.setHbarPolicy (ScrollBarPolicy.AS_NEEDED);
    scrollPane.widthProperty ().addListener (cl);

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
    //    scrollPane.setHbarPolicy (ScrollBarPolicy.NEVER);
    //    textFlow.setMaxWidth (500);

    scrollPane.setVvalue (0);
    scrollPane.setHvalue (0);
  }

  // ---------------------------------------------------------------------------------//
  void toggleLineWrap ()
  // ---------------------------------------------------------------------------------//
  {
    lineWrap = !lineWrap;
    setWrap ();

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  private void setWrap ()
  // ---------------------------------------------------------------------------------//
  {
    if (lineWrap)
      textFlow.setMaxWidth (scrollPane.getWidth () - PAD);
    else
      textFlow.setMaxWidth (99999);
  }

  // ---------------------------------------------------------------------------------//
  boolean getLineWrap ()
  // ---------------------------------------------------------------------------------//
  {
    return lineWrap;
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

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    super.restore (prefs);

    lineWrap = prefs.getBoolean (PREFS_LINE_WRAP + getText (), false);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.putBoolean (PREFS_LINE_WRAP + getText (), lineWrap);

    super.save (prefs);
  }
}