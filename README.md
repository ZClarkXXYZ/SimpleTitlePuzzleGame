(Ignore the obvious typo of the repository)

This project is a simple puzzle game where you click on tiles to flip it and the 8 sorrounding tiles, with the goal of matching the top 6x6 grid in the set amount of moves.

Run puzzleAppication.java to start the application.

Game instructions:
Click on a tile on the bottom 6x6 grid to swap the colors of a tile and the 8 surrounding tiles. 
Once once the bottom grid matches the top, it will move on to the next round, generating the next puzzle.

If the moves hit 0, the next time a tile is clicked, another puzzle generates.

The Disable Moves Button will prevent the next puzzle from generating after clicking when out of moves (and can also be re-enabled by hitting the same button. It still tracks the moves in the background, so clicking when at negative moves will work as if you had 0 moves)

The reset button takes you back to round 1, generates a random puzzle.

Certain tiles have added symbols, giving a different way of how it swap tiles color.

[!] tile: works as normal, except does not swap itself when clicked. Can be in addition to other modifiers.

[=] tile: the whole row of tiles is swaped

[||] tile: the whole column is swapped

[+] tile: the left, right, top, bottom tiles are swapped

[X] tile: the corner tiles are swapped

[P] tile: swaps every prime tile, that is, if the grid was labeled from right top to left bottom 0,1,2...35, it swaps the odd tiles (and itself, unless it is also a [!] tile)

_________________________________________________________________________________________
Project notes:

The puzzles are mostly pre-determined, but with random tile placement, and random order of how the puzzle was clicked (in case that matters, although it doesn't always matter)
So, there are 10 puzzles that are pooled from (but shifted down and right randomly, depending on the puzzle)
There is 1/11 chance that it would select random tiles instead of using a pre-determined puzzle, which depends on the round.

A major oversight is how some of the puzzle take a lot of moves (up to 11), and how difficult these puzzles are normally.
So the tiles to click, and in what order, are printed in the console, allowing one to cheat.

_________________________________________________________________________________________
Code notes:

In the code, the puzzle tile object, puzzleTileObject.java, holds the information of what tile it is.
Contains methods that are used by the puzzleModel.java.

puzzleModel contains the majority of the written code (which some might have needed to to split into different files, but since the project is already done, it is too late to change)
It creates a "board" (ArrayList) of 36 puzzleTileObjects, and methods and variables to interact with those puzzleTileObjects.


puzzleControl contains the parts that use FXML, which interacts with the board ArrayList.
Contains the code of clicking the imageView objects in the application, and code for the buttons and the labels.

_________________________________________________________________________________________
Added code notes:
there is "scrapped code" which were potential additional tile types and modifiers, in the case where I felt I had enough time to add them.
They are left in, but are not directly used.

With the testing I have done, there doesn't appear to be any major bugs.

I did not really know where to use Inheritance, but it is used by initialize(URL url, ResourceBundle resourceBundle), which is requried for fxml.inheritance.
_________________________________________________________________________________________
Final note:
I did not use any LLM models in the process of the making of this project.
I did however use a video tuturial "JavaFX Memory Game" as a refence for figuring out how to do certain things like:
-finding that I could use the imageView objects to do what I wanted.
-how to do what I needed with the scene builder (like adding the flowPane to the scene)
-Using initialize

So there is going to be some code that is copied from the tutorial, whether directly or indirectly.
_________________________________________________________________________________________

