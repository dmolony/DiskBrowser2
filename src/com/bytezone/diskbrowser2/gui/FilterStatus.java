package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.SaveState;

// -----------------------------------------------------------------------------------//
class FilterStatus implements SaveState
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILTER = "FilterValue";
  private static final String PREFS_FILTER_EXC = "FilterExc";
  private static final String PREFS_FILTER_ACTIVE = "FilterActive";

  String filterValue;         // text to search for
  boolean filterExclusion;    // show output lines with filterValue only / all lines
  boolean filterActive;       // filter on/off

  // Filter Commands
  // cmd-f - shows dialog box to enter/remove filterValue
  //     f - toggles filterActive
  // cmd-F - toggles filterExclusion

  // ---------------------------------------------------------------------------------//
  FilterStatus ()
  // ---------------------------------------------------------------------------------//
  {
    reset ();
  }

  // ---------------------------------------------------------------------------------//
  FilterStatus (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    copy (filterStatus);
  }

  // ---------------------------------------------------------------------------------//
  void copy (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    this.filterValue = filterStatus.filterValue;
    this.filterExclusion = filterStatus.filterExclusion;
    this.filterActive = filterStatus.filterActive;
  }

  // ---------------------------------------------------------------------------------//
  boolean matches (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    return filterStatus != null       //
        && this.filterValue.equals (filterStatus.filterValue)
        && this.filterExclusion == filterStatus.filterExclusion
        && this.filterActive == filterStatus.filterActive;
  }

  // ---------------------------------------------------------------------------------//
  void set (String filterValue, boolean filterExclusion, boolean filterActive)
  // ---------------------------------------------------------------------------------//
  {
    this.filterValue = filterValue;
    this.filterExclusion = filterExclusion;
    this.filterActive = filterActive;
  }

  // ---------------------------------------------------------------------------------//
  void reset ()
  // ---------------------------------------------------------------------------------//
  {
    filterValue = "";
    filterExclusion = false;
    filterActive = false;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_FILTER, filterValue);
    prefs.putBoolean (PREFS_FILTER_EXC, filterExclusion);
    prefs.putBoolean (PREFS_FILTER_ACTIVE, filterActive);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    filterValue = prefs.get (PREFS_FILTER, "");
    filterExclusion = prefs.getBoolean (PREFS_FILTER_EXC, false);
    filterActive = prefs.getBoolean (PREFS_FILTER_ACTIVE, false);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (String.format ("Filter value.... %s%n", filterValue));
    text.append (String.format ("Exclusion....... %s%n", filterExclusion));
    text.append (String.format ("Active.......... %s", filterActive));

    return text.toString ();
  }
}
