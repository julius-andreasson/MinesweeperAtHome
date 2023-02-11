# MinesweeperAtHome

A Minesweeper clone written in Java. It works, but some parts of the code are rather messy.

Use the arrow keys to move.
`D` to dig, `F` to flag, `R` to reset the game, `Q` to quit.

Roadmap:
* Fix counting of cleared tiles.
* Major refactoring:
    * The responsibility split between Board and MineMap is very unclear.
    * Introduce interfaces between classes. (Dependency Inversion Principle)
* Clean up the code.
* Movement:
    * Ctrl-key to snap to nearest mine/wall in a direction. (Wall done, mine not)
* Make the UI nice.
* Map generation that never creates situations where the player has to rely on chance.

Fun ideas for future development:
* Infinite chunk-wise map-generation, think `Minecraft`.
* Fake 3D rendering making it look like a table-top game. Think `Don't Starve`.
