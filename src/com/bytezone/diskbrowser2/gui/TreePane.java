package com.bytezone.diskbrowser2.gui;

import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
class TreePane extends BorderPane
// -----------------------------------------------------------------------------------//
{
  private final String home = System.getProperty ("user.home");
  private final AppleTreeView tree;
  private final HeaderBar treeHeaderBar = new HeaderBar ();

  // ---------------------------------------------------------------------------------//
  public TreePane (AppleTreeView tree)
  // ---------------------------------------------------------------------------------//
  {
    this.tree = tree;
    setCenter (tree);
    setTop (treeHeaderBar);
    setFolderName ();
  }

  // ---------------------------------------------------------------------------------//
  void setRootFolder (AppleTreeItem treeItem)
  // ---------------------------------------------------------------------------------//
  {
    tree.setRootFolder (treeItem);
    setFolderName ();
  }

  // ---------------------------------------------------------------------------------//
  void setFolderName ()
  // ---------------------------------------------------------------------------------//
  {
    String pathName = tree.getRoot ().getValue ().getFile ().toPath ().toString ();
    if (pathName.startsWith (home))
      pathName = pathName.replace (home, "~");
    treeHeaderBar.leftLabel.setText (pathName);
  }
}
