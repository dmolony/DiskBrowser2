# Disk Browser ][
This is a major rewrite of [DiskBrowser](https://github.com/dmolony/diskbrowser), using JavaFX and the new libraries ([AppBase](https://github.com/dmolony/AppBase), 
[AppleFileSystem](https://github.com/dmolony/AppleFileSystem) and 
[AppleFormat](https://github.com/dmolony/AppleFormat)).
  
The goal is for DiskBrowser ][ to retain all the file display formats of DiskBrowser, but  with a better interface and a more maintainable code base.

[Example screens](resources/screens.md)

## Installation
- Download and install the latest [JDK and JavaFX](https://jdk.java.net/) binaries.
- Download [DiskBrowserApp.jar](https://github.com/dmolony/DiskBrowser2/releases).
- Create executable run file as follows:  

#### MacOS or Linux shell file
```
/path/to/jdk/Contents/Home/bin/java           \
--module-path /path/to/javafx-sdk/lib         \
--add-modules=javafx.controls                 \
-jar /path/to/DiskBrowserApp.jar
```  

#### Windows batch file
```
C:\path\to\jdk\bin\java.exe                   \
--module-path C:\path\to\javafx-sdk\lib       \
--add-modules=javafx.controls                 \
-jar C:\path\to\DiskBrowserApp.jar
```

The first line in each of the above shell files can be replaced with 'java   \\' as it's just the command to execute java on your system. The second line must be the path to wherever you placed the javafx download.

On MacOS it should probably be: /Library/Java/JavaVirtualMachines/javafx-sdk-22

## First Execution
Specify the location of your disk image files using File -> Set Apple Root Folder. Note that this must be a FOLDER, not a file. The specified folder may contain subfolders, these will all appear in the file tree within the application.
