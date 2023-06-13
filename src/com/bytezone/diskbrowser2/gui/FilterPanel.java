package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

// -----------------------------------------------------------------------------------//
public class FilterPanel extends VBox
// -----------------------------------------------------------------------------------//
{
  private List<CheckBox> checkBoxes = new ArrayList<> ();
  final ToggleGroup group = new ToggleGroup ();
  private final Label labelFilter = new Label ("Filter");
  private final List<FilterListener> listeners = new ArrayList<> ();
  private String filterValue = "";
  private int extensionsSelected;
  private int totalExtensions;

  // ---------------------------------------------------------------------------------//
  public FilterPanel (int[] extensionTotals)
  // ---------------------------------------------------------------------------------//
  {
    super (20);

    totalExtensions = extensionTotals.length;

    setAlignment (Pos.CENTER_LEFT);
    setPrefHeight (50);
    setPadding (new Insets (0, 20, 0, 20));

    TextField textFilter = new TextField ();
    textFilter.textProperty ()
        .addListener ( (observable, oldValue, newValue) -> filterChanged (newValue));

    getChildren ().addAll (labelFilter, textFilter);

    List<String> suffixes = AppleTreeView.fileSystemFactory.getSuffixes ();
    for (int i = 0; i < suffixes.size (); i++)
      addCheckBox (suffixes.get (i).substring (1), extensionTotals[i] > 0);
  }

  // ---------------------------------------------------------------------------------//
  public RadioButton addButton (String name)
  // ---------------------------------------------------------------------------------//
  {
    RadioButton button = new RadioButton (name);
    button.setToggleGroup (group);
    getChildren ().add (button);

    return button;
  }

  // ---------------------------------------------------------------------------------//
  public CheckBox addCheckBox (String name, boolean enabled)
  // ---------------------------------------------------------------------------------//
  {
    CheckBox checkBox = new CheckBox (name);
    getChildren ().add (checkBox);
    checkBoxes.add (checkBox);
    checkBox.selectedProperty ()
        .addListener ( (ov, oldVal, newVal) -> extensionClicked (ov, oldVal, newVal));
    checkBox.setSelected (true);
    checkBox.setDisable (!enabled);
    ++extensionsSelected;

    return checkBox;
  }

  // ---------------------------------------------------------------------------------//
  private void extensionClicked (ObservableValue<? extends Boolean> ov, Boolean old_val,
      Boolean new_val)
  // ---------------------------------------------------------------------------------//
  {
    if (new_val)
      extensionsSelected++;
    else
      extensionsSelected--;

    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  private void filterChanged (String newValue)
  // ---------------------------------------------------------------------------------//
  {
    filterValue = newValue;
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  private void notifyListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (FilterListener listener : listeners)
      listener.filterChanged ();
  }

  // ---------------------------------------------------------------------------------//
  boolean isMatch (AppleTreeFile value)
  // ---------------------------------------------------------------------------------//
  {
    if (!filterValue.isEmpty () && !value.getName ().contains (filterValue))
      return false;

    int extensionNo = value.getExtensionNo ();
    if (extensionNo >= 0 && extensionNo < checkBoxes.size ()
        && !checkBoxes.get (extensionNo).isSelected ())
      return false;

    return true;
  }

  // ---------------------------------------------------------------------------------//
  public void addListener (FilterListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  public boolean filtersActive ()
  // ---------------------------------------------------------------------------------//
  {
    return !filterValue.isEmpty () || extensionsSelected != totalExtensions;
  }

  // ---------------------------------------------------------------------------------//
  public String getFilterText ()
  // ---------------------------------------------------------------------------------//
  {
    return filterValue;
  }

  // ---------------------------------------------------------------------------------//
  public int countExtensionsSelected ()
  // ---------------------------------------------------------------------------------//
  {
    return extensionsSelected;
  }

  // ---------------------------------------------------------------------------------//
  interface FilterListener
  // ---------------------------------------------------------------------------------//
  {
    public void filterChanged ();
  }
}
