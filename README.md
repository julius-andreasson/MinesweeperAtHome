# MinesweeperAtHome

A Minesweeper clone written in Java. It works, but is missing some features and some parts of the code are rather messy.

Use 'D' to dig, 'F' to flag, and 'R' to reset the round.

Roadmap:
* The selection of tiles using the mouse is slightly off - fix this.
* Rethink class structure.
    * There are many oddities due to the current division of responsibility between classes.
    * Introduce interfaces between classes. (Dependency Inversion Principle)
* Clean up the code.
* Movement using arrow keys.
    * Shift-key to sprint: jumps `x` tiles per arrow key press.
* Make the UI nice. 
* Map generation that never creates situations where the player has to rely on chance. 

Fun ideas for future development:
* Infinite chunk-wise map-generation, think `Minecraft`.
* Fake 3D rendering making it look like a table-top game. Think `Don't Starve`.
