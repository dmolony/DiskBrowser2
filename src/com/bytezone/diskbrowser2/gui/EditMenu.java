package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabChangeListener;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

// -----------------------------------------------------------------------------------//
public class EditMenu extends Menu implements TabChangeListener
// -----------------------------------------------------------------------------------//
{
  private final MenuItem copyMenuItem = new MenuItem ("Copy");
  private DBTextTab currentTab;

  // ---------------------------------------------------------------------------------//
  public EditMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    getItems ().addAll (copyMenuItem);
    copyMenuItem.setAccelerator (
        new KeyCodeCombination (KeyCode.C, KeyCombination.SHORTCUT_DOWN));
    copyMenuItem.setOnAction (e -> copyFile ());
  }

  // ---------------------------------------------------------------------------------//
  private void copyFile ()
  // ---------------------------------------------------------------------------------//
  {
    if (currentTab != null)
      currentTab.copyToClipboard ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void tabChanged (Tab fromTab, Tab toTab)
  // ---------------------------------------------------------------------------------//
  {
    if (toTab instanceof DBTextTab textTab)
    {
      copyMenuItem.setDisable (false);
      currentTab = textTab;
    }
    else
    {
      copyMenuItem.setDisable (true);
      currentTab = null;
    }
  }
}
