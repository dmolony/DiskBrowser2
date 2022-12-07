package com.bytezone.diskbrowser2.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

// -----------------------------------------------------------------------------------//
class ViewMenu extends Menu //implements SaveState
// -----------------------------------------------------------------------------------//
{
  private final MenuItem fontMenuItem = new MenuItem ("Set Font...");
  private final MenuItem filterMenuItem = new MenuItem ("Set Text Filter...");
  private final CheckMenuItem exclusiveFilterMenuItem = new CheckMenuItem ("Exclusive Filter");

  // ---------------------------------------------------------------------------------//
  public ViewMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    ObservableList<MenuItem> menuItems = getItems ();

    // Filter keys
    KeyCodeCombination cmdShiftF =
        new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN);
    KeyCodeCombination cmdF = new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN);
    KeyCodeCombination cmdAltF =
        new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.ALT_DOWN);

    fontMenuItem.setAccelerator (cmdAltF);
    filterMenuItem.setAccelerator (cmdF);
    exclusiveFilterMenuItem.setAccelerator (cmdShiftF);

    menuItems.add (fontMenuItem);
    menuItems.add (filterMenuItem);
    menuItems.add (exclusiveFilterMenuItem);
  }

  // ---------------------------------------------------------------------------------//
  void setFontAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    fontMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  void setFilterAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    filterMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  void setExclusiveFilterAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    exclusiveFilterMenuItem.setOnAction (action);
  }
}
