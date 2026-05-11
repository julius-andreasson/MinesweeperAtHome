# MinesweeperAtHome

A Minesweeper clone written in Java.

Use the arrow keys to move.
`D` to dig, `F` to flag, `R` to reset the game, `Q` to quit. Hold `shift` to move faster.

Did a major refactor after doing a course on OOP patterns, hence the sometimes over-zealous application of design patterns.\
Uses Model-View-Controller (MVC) pattern. (Smalltalk-80-style)
- `Game` is the model. It holds and updates game state.
- `View` is the view. It provides a GUI, and reads game state to display it.
- `Controller` is the controller. It handles user input and connects `Game` and `View`.
