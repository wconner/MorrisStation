# MorrisStation

MorrisStation is a cybersecurity adventure game, meant to help middle school aged students learn the basics of cybersecurity.
It uses the LibGDX framework to support cross platform deployment.

## System Requirements
* Running
  * Java Runtime
* Building
  *  JDK 8
  *  Gradle

## Build

To run from the command line:
 gradlew desktop:run
 
 To import into IntelliJ:
  1. Import project from gradlew
  2. Set run configuration
    * Use classpath of module: desktop_main
    * Main class: com.mygdx.game.desktop.DesktopLauncher
    * Working directory: /MorrisStation/core/assets
