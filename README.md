<div align="center">  
  <h1>Daniel Machado</h1>
</div>

<p align="center">
 Console Chess Java
</p>

---

## Description

- This is a Java Chess console project developed during the Nélio Alves Complete Java Course > https://www.udemy.com/course/java-curso-completo/ . It has all the chess main rules and works fine.

<a title="Console Chess Java">
  <img src="https://i.imgur.com/UON10pG.png"/>
</a>

## How to start

- In order to be able to open and edit the code you'll need a Java IDE. Personally, in that matter, I'd sugest to use the Eclipse. Once you got the Eclipse IDE installed on your PC, you need to import a project and paste the project's folder address in which you've just made the git clone. Ok, you got the source code in front of your eyes.

- After that, in the project's root folder, look after the bin folder, open it and then open the git terminal again. Then type the following: 'java application/Program' and hit the enter. The game will now appear on the console, 100% ready to be played.

<a title="Console Chess Java">
  <img src="https://i.imgur.com/aH8nPf1.png"/>
</a>

## About the game source code

- On this project, we have a separated folder to each function, which make it possible for us to identify a specif game mechanic easily and correct that without create a mess all around the rest of the code. See the image and look to the subfolders

<a title="Console Chess Java">
  <img src="https://i.imgur.com/wrvgzTV.png"/>
</a>

- As we can see, boardgame folder has a Board, BoardException (to handle the exceptions properly), Piece and Position classes, which means that here we got all the data related to the game's board. Here comes an exemple:

<a title="Console Chess Java">
  <img src="https://i.imgur.com/TEom5sB.png"/>
</a>

- The 'Board', on it's constructor, got the data about the number of rows and columns of the Board, in order to instaciate a Board object outside. 
 
- Meanwhile, the 'ChessPosition' class in the 'chess' folder has the absolutely important task of converting the user inputs to the game matrix coordinates of the chess piece position on the methods we're gonna below

<a title="Console Chess Java">
  <img src="https://i.imgur.com/UbaHWSD.png"/>
</a>

- Lastly, on the application folder, we got two classes, one of them being the code's entry point (class Program) and the other 'UI', which is responsible for the view layer spot

<a title="Console Chess Java">
  <img src="https://i.imgur.com/XgvipD4.png"/>
</a>

- In the red rectangle we got the chess's pieces printer method, in which the memory data of pieces and positions are converted to chars the represent them on the console screen. In the yellow rectangle we got the same process, but now this printer method will make visible the 'possibleMoves'. In the purple rectangle we have the method that paints the pieces according to it's TEAM (white or yellow).

<a title="Console Chess Java">
  <img src="https://i.imgur.com/XgvipD4.png"/>
</a>

- Enjoy the game and feel free to share it with your friends!