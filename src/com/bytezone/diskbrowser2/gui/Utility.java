package com.bytezone.diskbrowser2.gui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------//
public class Utility
// ---------------------------------------------------------------------------------//
{
  static final String[] diskExtensions = { ".dsk", ".do", ".d13", ".po", ".2mg", ".hdv", ".woz",
      ".sdk", ".bsq", ".lbr", ".bxy", ".bny", ".bqy" };
  static final String[] compressionExtensions = { ".zip", ".gz", ".shk" };
  static final String[] validExtensions;

  static
  {
    validExtensions = new String[diskExtensions.length + compressionExtensions.length];
    int ptr = 0;
    for (String s : diskExtensions)
      validExtensions[ptr++] = s;
    for (String s : compressionExtensions)
      validExtensions[ptr++] = s;
  }

  // ---------------------------------------------------------------------------------//
  static boolean isValidExtension (String name)
  // ---------------------------------------------------------------------------------//
  {
    for (String extension : validExtensions)
      if (name.endsWith (extension))
        return true;

    return false;
  }

  // ---------------------------------------------------------------------------------//
  static int getExtensionNumber (Path path)
  // ---------------------------------------------------------------------------------//
  {
    String name = path.getFileName ().toString ().toLowerCase ();

    for (int i = 0; i < validExtensions.length; i++)
      if (name.endsWith (validExtensions[i]))
        return i;

    return -1;
  }

  // ---------------------------------------------------------------------------------//
  static String getExtension (int extensionNumber)
  // ---------------------------------------------------------------------------------//
  {
    if (extensionNumber < 0 || extensionNumber >= validExtensions.length)
      return "";

    return validExtensions[extensionNumber];
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
  public static List<String> getHexDumpLines (byte[] b, int offset, int length, int displayOffset)
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

        int c = b[z] & 0xFF;
        textLine.append (c < 0x40 || c == 0xFF ? '.' : (char) c);
      }
      lines.add (String.format ("%06X  %-48s %s", displayOffset + ptr, hexLine.toString (),
          textLine.toString ()));
    }

    return lines;
  }
}
