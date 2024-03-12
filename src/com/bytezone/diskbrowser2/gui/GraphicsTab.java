package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.appleformat.graphics.Animation;
import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;
import com.bytezone.filesystem.ProdosConstants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

// -----------------------------------------------------------------------------------//
public class GraphicsTab extends DBGraphicsTab implements PreferenceChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final double SCALE = 2;
  private static final double FONT_SCALE = 4;
  private static final double ICON_SCALE = 5;

  AppleTreeNode appleTreeNode;
  AppleBlock appleBlock;
  Timeline clock;

  // ---------------------------------------------------------------------------------//
  public GraphicsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    // create animation timer
    clock = new Timeline ();
    clock.setCycleCount (Timeline.INDEFINITE);
  }

  // ---------------------------------------------------------------------------------//
  private void tick (ActionEvent event)
  // ---------------------------------------------------------------------------------//
  {
    if (formattedAppleFile == null)
      return;

    if (formattedAppleFile instanceof Animation animation)
    {
      animation.nextFrame ();
      setValid (false);
      update ();
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    if (formattedAppleFile != null)
    {
      if (formattedAppleFile.getImage () != null)
      {
        double scale = SCALE;

        if (appleFile != null && appleFile.getFileSystemType () == FileSystemType.PRODOS)
        {
          if (appleFile.getFileType () == ProdosConstants.FILE_TYPE_FONT
              || appleFile.getFileType () == ProdosConstants.FILE_TYPE_FNT)
            scale = FONT_SCALE;
          if (appleFile.getFileType () == ProdosConstants.FILE_TYPE_ICN)
            scale = ICON_SCALE;
        }

        drawImage (formattedAppleFile.getImage (), scale);
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeNode (AppleTreeNode treeNode)
  // ---------------------------------------------------------------------------------//
  {
    appleTreeNode = treeNode;
    appleFile = appleTreeNode.getAppleFile ();
    appleFileSystem = appleTreeNode.getAppleFileSystem ();
    formattedAppleFile = appleTreeNode.getFormattedAppleFile ();

    if (formattedAppleFile instanceof Animation animation)
    {
      clock.stop ();
      clock.getKeyFrames ().clear ();
      clock.getKeyFrames ()
          .add (new KeyFrame (Duration.millis (animation.getDelay () * 50),
              (ActionEvent event) -> tick (event)));
      //      System.out.println (toString (clock, animation));
      clock.play ();
    }
    else
      clock.stop ();

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  private String toString (Timeline clock, Animation animation)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append ("Delay .............. %s%n".formatted (clock.getDelay ()));
    text.append ("Rate ............... %f%n".formatted (clock.getCurrentRate ()));
    text.append ("Duration ........... %s%n".formatted (clock.getCycleDuration ()));
    text.append ("Target framerate ... %s%n".formatted (clock.getTargetFramerate ()));
    text.append ("Key frames ......... %d%n".formatted (clock.getKeyFrames ().size ()));
    text.append ("Animation delay .... %d%n".formatted (animation.getDelay ()));
    text.append ("Animation frames ... %d%n".formatted (animation.getSize ()));

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleBlock (AppleBlock appleBlock)
  // ---------------------------------------------------------------------------------//
  {
    appleFile = null;
    this.appleBlock = appleBlock;

    formattedAppleFile = null;

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (ApplePreferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    refresh ();
  }
}
