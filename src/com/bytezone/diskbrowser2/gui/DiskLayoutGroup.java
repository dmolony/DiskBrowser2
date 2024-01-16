package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class DiskLayoutGroup extends Group
// -----------------------------------------------------------------------------------//
{
  private static final int SIZE_H = 18;
  private static final int SIZE_W = SIZE_H / 2;
  private static final int SIZE_SB = 16;
  private static final int GRID_PADDING = 0;
  private static final int SCREEN_ROWS = 35;
  private static final int MAX_HALF_BLOCKS = 32;

  private static final int GRID_HEIGHT = SCREEN_ROWS * SIZE_H - 1;
  private static final int GRID_WIDTH = MAX_HALF_BLOCKS * SIZE_W - 1;

  private static final int X_OFFSET = 50;
  private static final int Y_OFFSET = 40;

  private static final String ROW_FORMAT = "%04X";
  private static final String COLUMN_FORMAT = "%02X";

  ScrollBar scrollBarV = new ScrollBar ();
  ScrollBar scrollBarH = new ScrollBar ();

  int scrollBarValueV;
  int scrollBarValueH;
  boolean adjusting;

  Stage stage;
  GraphicsContext gc;
  Canvas canvas;

  int diskRows;
  int diskColumns;
  int diskBlockUnits;       // will be 1, 2, 4 or 8

  int screenColumns;        // 32 / diskBlockUnits
  int blockWidth;

  ScreenCell[][] screen;    // visible blocks
  String[] fileNames;

  int firstRow = -1;
  int firstColumn = -1;
  int selectedBlockNo = -1;
  int currentDiskNo = -1;
  List<AppleBlock> selectedBlocks;

  AppleFileSystem fs;
  List<GridClickListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public DiskLayoutGroup ()
  // ---------------------------------------------------------------------------------//
  {
    double canvasWidth = X_OFFSET + GRID_WIDTH + GRID_PADDING;
    double canvasHeight = Y_OFFSET + GRID_HEIGHT + GRID_PADDING;

    canvas = new Canvas (canvasWidth, canvasHeight);

    gc = canvas.getGraphicsContext2D ();
    gc.setFont (Font.font ("Consolas", 14));

    setScrollBars (canvas);

    getChildren ().addAll (scrollBarV, scrollBarH, canvas);
  }

  // ---------------------------------------------------------------------------------//
  void setFileSystem (AppleFileSystem appleFileSystem)
  // ---------------------------------------------------------------------------------//
  {
    if (appleFileSystem == null)
    {
      //      System.out.println ("Null FS in DiskLayoutGroup");
      //      clear ();
      return;
    }

    firstRow = -1;
    firstColumn = -1;
    selectedBlockNo = -1;

    fs = appleFileSystem;
    //    System.out.println (fs);

    buildScreen (appleFileSystem, gc);
    drawGrid ();
  }

  // ---------------------------------------------------------------------------------//
  void setAppleFile (AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    if (appleFile == null)
    {
      selectedBlocks = null;
      selectedBlockNo = -1;
      return;
    }

    selectedBlocks = appleFile.getBlocks ();
    selectedBlockNo = -1;

    drawGrid ();
  }

  // ---------------------------------------------------------------------------------//
  private void drawGrid ()
  // ---------------------------------------------------------------------------------//
  {
    boolean redrawRowHeader = false;

    int firstRow = (int) scrollBarV.getValue ();
    int firstColumn = (int) scrollBarH.getValue ();

    // draw column headers
    if (this.firstColumn != firstColumn)         // horizontal scroll
    {
      this.firstColumn = firstColumn;
      drawColumnHeaders ();
    }

    if (this.firstRow != firstRow)               // vertical scroll
    {
      this.firstRow = firstRow;
      redrawRowHeader = true;
    }

    int totalDiskBlocks = fs.getTotalBlocks ();
    int display = 1;

    // draw each row
    for (int screenRow = 0; screenRow < SCREEN_ROWS; screenRow++)
    {
      if (redrawRowHeader)
        drawRowHeader (firstRow, screenRow, totalDiskBlocks);

      // draw each column
      int blockNo = (firstRow + screenRow) * diskColumns + firstColumn;

      for (int screenColumn = 0; screenColumn < screenColumns; screenColumn++)
      {
        ScreenCell cell = screen[screenRow][screenColumn];

        if (blockNo < totalDiskBlocks)
        {
          AppleBlock block = fs.getBlock (blockNo);

          Color color = getBaseColor (block);
          Color cellColor =                                           //
              display == 0 ? color                                    //
                  : display == 1 ? getFsSubTypeColor (block, color)   //
                      : getFileSubTypeColor (block, color);

          boolean selected = false;
          if (blockNo == selectedBlockNo
              || selectedBlocks != null && selectedBlocks.contains (block))
            selected = true;

          cell.draw (cellColor, selected);
        }
        else
          cell.erase ();

        blockNo++;
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawColumnHeaders ()
  // ---------------------------------------------------------------------------------//
  {
    int x = X_OFFSET;
    int y = Y_OFFSET - 8;

    gc.clearRect (x, y - 50, x + GRID_WIDTH, 50);
    gc.setFill (Color.BLACK);

    for (int i = 0; i < screenColumns; i++)
    {
      String columnText = String.format (COLUMN_FORMAT, firstColumn + i);

      if (diskBlockUnits == 1)              // split heading over two lines
      {
        gc.fillText (columnText.substring (0, 1), x, y - 15);
        gc.fillText (columnText.substring (1), x, y);
      }
      else                                  // single heading line
        gc.fillText (columnText, x, y);

      x += blockWidth;
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawRowHeader (int firstRow, int screenRow, int totalDiskBlocks)
  // ---------------------------------------------------------------------------------//
  {
    int x = 10;
    int y = Y_OFFSET + screenRow * SIZE_H;
    int rowStart = (firstRow + screenRow) * diskColumns;

    gc.clearRect (x, y, X_OFFSET - x - 1, SIZE_H);

    if (rowStart < totalDiskBlocks)
    {
      gc.setFill (Color.BLACK);
      gc.fillText (String.format (ROW_FORMAT, rowStart), x, y + 13);
    }
  }

  // ---------------------------------------------------------------------------------//
  private Color getBaseColor (AppleBlock block)
  // ---------------------------------------------------------------------------------//
  {
    return switch (block.getBlockType ())
    {
      case ORPHAN -> Color.NAVAJOWHITE;
      case EMPTY -> Color.GHOSTWHITE;
      case FILE_DATA -> Color.CRIMSON;
      case FS_DATA -> Color.ROYALBLUE;
      //      case null -> Color.BLACK;
    };
  }

  // ---------------------------------------------------------------------------------//
  private Color getFsSubTypeColor (AppleBlock block, Color color)
  // ---------------------------------------------------------------------------------//
  {
    return switch (block.getBlockSubType ())
    {
      case "DOS" -> Color.GRAY;                     // DOS
      case "VTOC" -> Color.TURQUOISE;               // DOS
      case "TSLIST" -> Color.DARKCYAN;              // DOS
      case "CATALOG" -> Color.BLUEVIOLET;           // COMMON
      case "INDEX" -> Color.GREEN;                  // PRODOS
      case "M-INDEX" -> Color.MEDIUMORCHID;         // PODOS
      case "V-BITMAP" -> Color.TURQUOISE;           // PRODOS
      case "FOLDER" -> Color.DARKORANGE;            // PRODOS
      case "FORK" -> Color.YELLOWGREEN;             // PRODOS
      case "BIN2 HDR" -> Color.GREEN;               // BINARY2
      default -> color;
    };
  }

  // ---------------------------------------------------------------------------------//
  private Color getFileSubTypeColor (AppleBlock block, Color color)
  // ---------------------------------------------------------------------------------//
  {
    return switch (block.getBlockSubType ())
    {
      case "TEXT" -> Color.RED;
      case "INTEGER" -> Color.YELLOW;
      case "APPLESOFT" -> Color.GREEN;
      case "BINARY" -> Color.PURPLE;
      default -> color;
    };
  }

  // ---------------------------------------------------------------------------------//
  private void buildScreen (AppleFileSystem fs, GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    diskBlockUnits = fs.getBlockSize () / 128;          // 1, 2, 4, 8
    if (diskBlockUnits == 0)
    {
      System.out.println ("Zero diskBlockUnits");
      diskBlockUnits = 2;
    }

    // window size (32 x 1, 16 x 2, 8 x 4 or 4 x 8)
    screenColumns = MAX_HALF_BLOCKS / diskBlockUnits;
    blockWidth = SIZE_W * diskBlockUnits;

    // disk display layout
    diskColumns = fs.getBlocksPerTrack ();
    if (diskColumns == 0)
      diskColumns = screenColumns;

    //    if (fs.getTotalBlocks () >= 1600 
    //        && fs.getFileSystemType () == FileSystemType.PRODOS)
    //      diskColumns = 16;

    diskRows = (fs.getTotalBlocks () - 1) / diskColumns + 1;

    // create screen array
    screen = new ScreenCell[SCREEN_ROWS][];
    for (int i = 0; i < SCREEN_ROWS; i++)
      screen[i] = new ScreenCell[screenColumns];

    // populate array with ScreenCell objects
    for (int row = 0; row < SCREEN_ROWS; row++)
      for (int col = 0; col < screenColumns; col++)
      {
        int x = X_OFFSET + col * blockWidth;
        int y = Y_OFFSET + row * SIZE_H;
        screen[row][col] = new ScreenCell (gc, x, y, blockWidth, SIZE_H);
      }

    // set gridlines and border
    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (X_OFFSET - 1, Y_OFFSET - 1, GRID_WIDTH + 2, GRID_HEIGHT + 2);

    // adjust scrollbar values
    scrollBarV.setMax (Math.max (diskRows - SCREEN_ROWS, 0));
    scrollBarV.setDisable (scrollBarV.getMax () == 0);

    scrollBarH.setMax (Math.max (diskColumns - screenColumns, 0));
    scrollBarH.setDisable (scrollBarH.getMax () == 0);
    scrollBarH.setBlockIncrement (screenColumns - 1);

    // set window to top/left
    scrollBarV.setValue (0);
    scrollBarH.setValue (0);
  }

  // ---------------------------------------------------------------------------------//
  private void setScrollBars (Canvas canvas)
  // ---------------------------------------------------------------------------------//
  {
    scrollBarV.setOrientation (Orientation.VERTICAL);
    scrollBarV.setLayoutX (canvas.getWidth () + 1);
    scrollBarV.setLayoutY (Y_OFFSET - 1);
    scrollBarV.setMin (0);
    scrollBarV.setPrefHeight (SCREEN_ROWS * SIZE_H);
    scrollBarV.setPrefWidth (SIZE_SB);
    scrollBarV.setBlockIncrement (SCREEN_ROWS - 1);

    scrollBarH.setOrientation (Orientation.HORIZONTAL);
    scrollBarH.setLayoutX (X_OFFSET - 1);
    scrollBarH.setLayoutY (canvas.getHeight () + 1);
    scrollBarH.setMin (0);
    scrollBarH.setPrefHeight (SIZE_SB);
    scrollBarH.setPrefWidth (MAX_HALF_BLOCKS * SIZE_W);

    scrollBarV.valueProperty ().addListener (new ChangeListener<Number> ()
    {
      @Override
      public void changed (ObservableValue<? extends Number> ov, Number old_val,
          Number new_val)
      {
        if (old_val.intValue () != new_val.intValue () && !adjusting)
          drawGrid ();
      }
    });

    scrollBarH.valueProperty ().addListener (new ChangeListener<Number> ()
    {
      @Override
      public void changed (ObservableValue<? extends Number> ov, Number old_val,
          Number new_val)
      {
        if (old_val.intValue () != new_val.intValue () && !adjusting)
          drawGrid ();
      }
    });

    canvas.setOnKeyPressed (new EventHandler<KeyEvent> ()
    {
      @Override
      public void handle (KeyEvent event)
      {
        if (selectedBlockNo < 0)            // nothing selected
          return;

        int blockNo = switch (event.getCode ())
        {
          case LEFT -> selectedBlockNo - 1;
          case RIGHT -> selectedBlockNo + 1;
          case UP -> selectedBlockNo - diskColumns;
          case DOWN -> selectedBlockNo + diskColumns;
          default -> selectedBlockNo;
        };

        if (selectedBlockNo != blockNo && blockNo >= 0 && blockNo < fs.getTotalBlocks ())
        {
          notifyListeners (selectedBlockNo, blockNo);
          selectedBlockNo = blockNo;
          ensureVisible (blockNo);
          drawGrid ();
          event.consume ();
        }
      }
    });

    canvas.setOnMouseClicked (new EventHandler<MouseEvent> ()
    {
      @Override
      public void handle (MouseEvent event)
      {
        event.consume ();
        canvas.requestFocus ();

        int x = (int) event.getX () - X_OFFSET;
        int y = (int) event.getY () - Y_OFFSET;
        if (x < 0 || x > GRID_WIDTH || y < 0 || y > GRID_HEIGHT)
          return;

        int row = y / SIZE_H + (int) scrollBarV.getValue ();
        int col = x / blockWidth + (int) scrollBarH.getValue ();
        int blockNo = row * diskColumns + col;

        if (blockNo >= 0 && blockNo != selectedBlockNo)
        {
          notifyListeners (selectedBlockNo, blockNo);
          selectedBlockNo = blockNo;
          selectedBlocks = null;
          drawGrid ();
        }
      }
    });

    canvas.setOnScroll (new EventHandler<ScrollEvent> ()
    {
      @Override
      public void handle (ScrollEvent event)
      {
        if (!event.isInertia ())
        {
          //     System.out.println (event);
          //     System.out.printf ("%10s %4d, %4d, %4d, %4d%n", event.getEventType (),
          //          (int) event.getDeltaX (), (int) event.getDeltaY (),
          //          (int) event.getTotalDeltaX (), (int) event.getTotalDeltaY ());

          int valV = scrollBarValueV - (int) event.getTotalDeltaY () / 8;
          if (valV >= 0 && valV <= scrollBarV.getMax ())
            scrollBarV.setValue (valV);

          int valH = scrollBarValueH - (int) event.getTotalDeltaX () / 8;
          if (valH >= 0 && valH <= scrollBarH.getMax ())
            scrollBarH.setValue (valH);
        }
        event.consume ();
      }
    });

    canvas.setOnScrollStarted (new EventHandler<ScrollEvent> ()
    {
      @Override
      public void handle (ScrollEvent event)
      {
        scrollBarValueV = (int) scrollBarV.getValue ();
        scrollBarValueH = (int) scrollBarH.getValue ();
        event.consume ();
      }
    });

    canvas.setOnScrollFinished (new EventHandler<ScrollEvent> ()
    {
      @Override
      public void handle (ScrollEvent event)
      {
        //        if (!event.isInertia ())
        {
          scrollBarValueV = (int) scrollBarV.getValue ();
          scrollBarValueH = (int) scrollBarH.getValue ();
        }
        event.consume ();
      }
    });
  }

  // ---------------------------------------------------------------------------------//
  private void ensureVisible (int blockNo)
  // ---------------------------------------------------------------------------------//
  {
    adjusting = true;

    int screenRow = blockNo / diskColumns;
    int screenColumn = blockNo % diskColumns;

    if (screenColumn < firstColumn)
      scrollBarH.setValue (screenColumn);
    else if (screenColumn >= firstColumn + screenColumns)
      scrollBarH.setValue (screenColumn - screenColumns + 1);

    if (screenRow < firstRow)
      scrollBarV.setValue (screenRow);
    else if (screenRow >= firstRow + SCREEN_ROWS)
      scrollBarV.setValue (screenRow - SCREEN_ROWS + 1);

    adjusting = false;
  }

  // ---------------------------------------------------------------------------------//
  void clear ()
  // ---------------------------------------------------------------------------------//
  {
    gc.setFill (Color.WHITE);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());
  }

  // ---------------------------------------------------------------------------------//
  public void addClickListener (GridClickListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  private void notifyListeners (int previousBlockNo, int selectedBlockNo)
  // ---------------------------------------------------------------------------------//
  {
    AppleBlock block = fs.getBlock (selectedBlockNo);
    if (block == null)
      return;

    GridClickEvent event = new GridClickEvent (block);

    for (GridClickListener listener : listeners)
      listener.gridClick (event);
  }
}
