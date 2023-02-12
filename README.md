# MinesweeperAtHome

A Minesweeper clone written in Java.

Use the arrow keys to move.
`D` to dig, `F` to flag, `R` to reset the game, `Q` to quit.

Roadmap:
* Refactoring:
    * The responsibility split between Board and MineMap is very unclear.
    * Introduce interfaces between classes. (Dependency Inversion Principle)
* Clean up the code.
* Make the UI nice.

Fun ideas for future development:
* Infinite chunk-wise map-generation, think `Minecraft`.
* Fake 3D rendering making it look like a table-top game. Think `Don't Starve`.
