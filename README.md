# The Wild Escape
Minecraft plugin for the game 'The Wild Escape'.

## Info

## Features
* Play solo or with teams.
* WorldBorder mechanics.
* Care Packages across the map. *<<-- still in developement*
* Team/Global chat.
* Custom Death Messages
* Alpha Guard (a guard that protects his land, looking for regular players and threat or kills them)(Optional)

## Commands
### Akgeneen
* `/twe playerlist` - Shows a list of online players and their role/team.
* `/twe reset` - Resets all game settings (including team members, WorldBorder settings, Inventories). (Excluding the CarePackages config)
* `/twe start` - Start the game and teleport players/teams to random locations on the map (inside the worldborder).
* `/twe pause` - Pauses the current game.
* `/twe stop` - Stops the current running game.

### WorldBorder
* `/twe borderset <min-size> <max-size> <steps> <step-duration>` - Stel de WorldBorder in voor als het spel bezig is.
  * `<min-size>` - The minimal size of the border in blocks. *(Example: when '1000' is set, the border can't be smaller than 1000x1000 blocks).*
  * `<max-size>` - The maximal size of the border in blocks. The border will be this size when the game starts. *(Example: when '5000' is set, the game starts with a world border of 5000x5000 blocks)*
  * `<steps>` - How much the border will shrunk in blocks when the time ticks midnight in-game.
  * `<step-duration>` - The time that it will take in seconds for the border to shrink when it is midnight in-game.
* `/twe bordercenter`
* `/twe borderreset`

### Teams
* `/twe teammode <true/false>` - Zet Team modus aan of uit. Uit betekent dat elke speler alleen speelt.
* `/twe teamshow` - Bekijk bij welk team je op dit moment hoort.
* `/twe teamadd <player> <team>` - Voegt een speler toe aan een team.
* `/twe teamremove <player>` - Haalt een speler uit het team waar hij momenteel aan deelneemt.

### Care Packages

## Supported versions
* 1.17.x

## Developement Team
* Marco (Macronair)
  * Project Owner
  * Main Developer
  * Works on: Worldborder, Teams, Chat functions
  
* Loek (daoek)
  * Main Developer
  * Works on: Worldborder, CarePackages, Command Manager
