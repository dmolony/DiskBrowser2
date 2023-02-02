package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class ExtrasTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;
  private FormattedAppleFile formattedAppleFile;

  // ---------------------------------------------------------------------------------//
  public ExtrasTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    return formattedAppleFile == null ? new ArrayList<> () : getLines (MAX_LINES);
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getLines (int maxLines)
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    for (String line : formattedAppleFile.getExtras ().split ("\n"))
      newLines.add (line);

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  public void setFormattedAppleFile (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
