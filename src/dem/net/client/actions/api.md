# CLIENT -> SERVER

## Settings game

### NEW
Ask to be added to the game as a specific name (or rename the player)
Template: `NEW playerName`
Example: `NEW Toto`

### PONG
Returned after a server PING to compute lag
Template: `PONG`
Example: `PONG`

### RDY - Ready
Inform the server that the player is (or not) ready to launch the game
Template: `RDY isReady`
Example: `RDY true`

- - - - - - - - - -

## During Game

### PONG
Returned after a server PING to compute lag
Template: `PONG`
Example: `PONG`

### MOV - Move
Ask to move the player's character in a specific direction
Template: `MOV direction`
Example: `MOV N`
