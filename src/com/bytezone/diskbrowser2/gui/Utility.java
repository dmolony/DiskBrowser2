package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------//
public class Utility
// ---------------------------------------------------------------------------------//
{
  private static final int MAX_SHORT = 0xFFFF;

  // ---------------------------------------------------------------------------------//
  public static int unsignedShort (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    try
    {
      return (buffer[ptr] & 0xFF)             //
          | ((buffer[ptr + 1] & 0xFF) << 8);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.printf ("Index out of range (unsignedShort): %d > %d%n", ptr,
          buffer.length);
      return 0;
    }
  }

  // ---------------------------------------------------------------------------------//
  public static int signedShort (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    int val = unsignedShort (buffer, ptr);

    return ((val & 0x8000) == 0) ? val : val - MAX_SHORT - 1;
  }

  // ---------------------------------------------------------------------------------//
  public static List<String> getHexDumpLines (byte[] b, int displayOffset)
  // ---------------------------------------------------------------------------------//
  {
    return getHexDumpLines (b, 0, b.length, displayOffset);
  }

  // ---------------------------------------------------------------------------------//
  public static List<String> getHexDumpLines (byte[] b, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    return getHexDumpLines (b, offset, length, 0);
  }

  // ---------------------------------------------------------------------------------//
  public static List<String> getHexDumpLines (byte[] b, int offset, int length,
      int displayOffset)
  // ---------------------------------------------------------------------------------//
  {
    final int lineSize = 16;

    List<String> lines = new ArrayList<> ();
    final StringBuilder hexLine = new StringBuilder ();
    final StringBuilder textLine = new StringBuilder ();

    for (int ptr = offset, max = offset + length; ptr < max; ptr += lineSize)
    {
      hexLine.setLength (0);
      textLine.setLength (0);

      for (int linePtr = 0; linePtr < lineSize; linePtr++)
      {
        int z = ptr + linePtr;
        if (z >= max)
          break;

        hexLine.append (String.format ("%02X ", b[z]));

        int c = b[z] & 0x7F;
        textLine.append (c < 0x40 ? '.' : (char) c);
      }

      lines.add (String.format ("%06X  %-48s %s", displayOffset + ptr,
          hexLine.toString (), textLine.toString ()));
    }

    return lines;
  }

  // ---------------------------------------------------------------------------------//
  public static void dump (byte[] buffer)
  // ---------------------------------------------------------------------------------//
  {
    for (String line : getHexDumpLines (buffer, 0))
      System.out.println (line);
  }
}
