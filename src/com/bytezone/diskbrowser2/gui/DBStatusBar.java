package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.StatusBar;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class DBStatusBar extends StatusBar implements FilterChangeListener
// -----------------------------------------------------------------------------------//
{
  private FilterStatus filterStatus;
  private final Label statusDisplay = new Label ();

  // ---------------------------------------------------------------------------------//
  public DBStatusBar ()
  // ---------------------------------------------------------------------------------//
  {
    Region filler = new Region ();
    HBox.setHgrow (filler, Priority.ALWAYS);
    getChildren ().addAll (filler, statusDisplay);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    super.setFont (font);
    statusDisplay.setFont (font);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFilter (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    this.filterStatus = filterStatus;
    setStatusText ();
  }

  // ---------------------------------------------------------------------------------//
  private void setStatusText ()
  // ---------------------------------------------------------------------------------//
  {
    String filterText = filterStatus.filterActive ? filterStatus.filterValue.isEmpty () ? "NONE" :
    //          (filterStatus.filterReverse ? "~" : "") + 
        filterStatus.filterValue : "OFF";

    String showText = (filterStatus.filterActive && !filterStatus.filterValue.isEmpty ())
        ? filterStatus.filterExclusion ? "Filtered lines" : "All lines" : "All lines";

    statusDisplay.setText (String.format ("Filter: %-20s Show: %-20s", filterText, showText));
  }
}
