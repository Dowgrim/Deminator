# SERVER -> CLIENT

## Settings game

### NEW
Inform others players that a new player joined
Template: `NEW playerName`
Example:  `NEW Toto`

### QUIT
Inform others players that a player has quit
Template: `QUIT playerName`
Example: `QUIT Toto`

### PING
Pinging players to compute the lag time
Template: `PING`
Example: `PING`

### LAG
Inform about the lag of all players
Template: `LAG playerName valueInMs`
Example: `LAG Toto 42`

### RDY - Ready
Inform others players that a player is ready to launch the game
Template: `RDY playerName`
Example: `RDY Toto`

### OPT - Options
Inform players that a game option has changed
Template: `OPT optionKey optionValue`
Example: `OPT GridWidth 200`

### LDD - Launch
Inform players that game has been launched
Template: `LDD`
Example: `LDD`

- - - - - - - - - -

## During Game

### PING
Pinging players to compute the lag time
Template: `PING`
Example: `PING`

### LAG
Inform about the lag of all players
Template: `LAG playerName valueInMs`
Example: `LAG Toto 42`

### MOV - Move
Inform a player that a player moved to a specific location
Template: `MOV playerName x y`
Example: `MOV Toto 42 69`

### EXP - Explode
Inform a player that there is (or not) an explosion on a certain place
Template: `EXP isOrNot x y (x y)*`
Example: `EXP true 42 69 42 70 42 71 41 69 43 69`

### SHD - Shield
Inform about the shield value of a player
Template: `SHD playerName value`
Example: `SHD Toto 3`

### DIS - Discover
Inform a player about the value of a specific location
Template: `DIS x y value`
Example: `DIS 42 69 5`

### STN - Stun
Inform that a player is (or not) stunned
Template: `STN playerName isStunned`
Example: `STN Toto true`

### PTS - Points
Inform that a player earned points (showing total of points)
Template: `PTS playerName playerPointsNumber`
Example: `PTS Toto 42`

### QUIT
Inform others players that a player has leaved
Template: `QUIT playerName`
Example: `QUIT Toto`

### END
Inform that the game is finished
Template: `END`
Example: `END`

### ERR - Error
Inform about an error or that a command previously sent to the server was not allowed
Template: `ERR code why`
Example: `ERR MOV N` (Cannot move north)
Example: `ERR UNK XXX YYY` (Unknown received command (or received when was not allowed): `XXX YYY`)
Example: `ERR UNK NEW Toto` during game (cannot rename a player while game is ongoing)
