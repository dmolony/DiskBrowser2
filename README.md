# Disk Browser ][
This is a rewrite of [DiskBrowser](https://github.com/dmolony/diskbrowser), using JavaFX
 and the new libraries ([AppBase](https://github.com/dmolony/AppBase),
  [AppleFileSystem](https://github.com/dmolony/AppleFileSystem) and
   [AppleFormat](https://github.com/dmolony/AppleFormat)). It is not yet ready
  for release.
  
  The goal is for DiskBrowser ][ to retain all the file display formats of
   DiskBrowser, but with a better interface and a more maintainable code base.
   
### Example Screens
This image shows the main interface change from DiskBrowser. Instead of separate tabs
 for each disk, the file tree expands each disk image in place.
![Teaser](screens/teaser1.png?raw=true "This will change")
File systems within file systems can be accessed. This screen shows the directory
listing of a Pascal Area which is stored on a Prodos disk. All of the pascal files can
be displayed as usual.
![Teaser](screens/teaser7.png?raw=true "Pascal area on a prodos disk image")
This screen shows several SHK disk images stored as LBR files.
![Teaser](screens/teaser8.png?raw=true "SHK files on a prodos disk image")
When a file is selected and the Options pane is active, the options for that
 file type can be altered.
![Teaser](screens/teaser2.png?raw=true "Don't rely on this")
Extra file information has moved to the Extras tab.
![Teaser](screens/teaser3.png?raw=true "Other file types will have different output")
The file tree can be filtered so that only the selected file types are shown.
![Teaser](screens/teaser4.png?raw=true "BXY files")
The Meta tab shows information about the file.
![Teaser](screens/teaser5.png?raw=true "Meta")
The Graphics tab shows pictures.
![Teaser](screens/teaser6.png?raw=true "Graphics")