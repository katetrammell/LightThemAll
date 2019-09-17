import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javalib.impworld.*;
import javalib.worldimages.*;

// NOTE about Extra Credit:
// we have implemented 4 "whistles"
// 1) gradient coloring
// 2) allowing the player to start a new puzzle
// 3) keeping score and displaying it
// 4) keeping time and displaying it

//a class for the LightEmAll game
class LightEmAll extends World {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // the width and height of the board
  int width;
  int height;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;
  int gamePieceSize = 40;
  int score;
  int tickCount;
  int seconds;
  String type;
  boolean allLit;

  // the type signifies which type of board generation 
  LightEmAll(int width, int height, String type) {
    if (type.equals("part two")) {
      this.width = width;
      this.height = height;
      this.powerRow = 0;
      this.powerCol = (int)Math.floor(width / 2.0) ;
      this.mst = new ArrayList<Edge>();
      ArrayList<ArrayList<GamePiece>> boardTemp = new Util(). makeBoardPart2(height, width);
      boardTemp.get(powerRow).get(powerCol).powerStation = true;
      this.board = new Util().addConnections(boardTemp, height, width);
      this.radius = new Util().findRad(board, this.powerRow, this.powerCol);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
    if (type.equals("part two shuffled")) {
      this.width = width;
      this.height = height;
      this.powerRow = 0;
      this.powerCol = (int)Math.floor(width / 2.0) ;
      this.radius = 20;
      this.mst = new ArrayList<Edge>();
      ArrayList<ArrayList<GamePiece>> boardTemp = new Util().makeBoardPart2(height, width);
      new Util().shuffle(boardTemp);
      boardTemp.get(powerRow).get(powerCol).powerStation = true;
      this.board = new Util().addConnections(boardTemp, height, width);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
    if (type.equals("part one shuffled")) {
      this.width = width;
      this.height = height;
      this.powerRow = (int)Math.floor(height / 2.0);
      this.powerCol = (int)Math.floor(width / 2.0);
      this.radius = 20;
      this.mst = new ArrayList<Edge>();
      ArrayList<ArrayList<GamePiece>> boardTemp = new Util().makeBoard(height, width, 
          powerCol, powerRow);
      new Util().shuffle(boardTemp);
      this.board = new Util().addConnections(boardTemp, this.height, this.width);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
    if (type.equals("part one")) {
      this.width = width;
      this.height = height;
      this.powerRow = (int)Math.floor(height / 2.0);
      this.powerCol = (int)Math.floor(width / 2.0);
      this.radius = 20;
      this.mst = new ArrayList<Edge>();
      ArrayList<ArrayList<GamePiece>> boardTemp = new Util().makeBoard(height, width, 
          powerCol, powerRow);
      this.board = new Util().addConnections(boardTemp, this.height, this.width);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
    if (type.equals("part three")) {
      this.width = width;
      this.height = height;
      this.powerRow = 0;
      this.powerCol = 0;
      this.mst =  new Util().makeMST(this.width, this.height);
      this.board = new Util().makeMSTBoard(this.mst, this.width, this.height);
      this.board.get(powerRow).get(powerCol).powerStation = true;
      this.board =  new Util().addConnections(this.board, this.height, this.width);
      this.radius =  new Util().findRad(this.board, this.powerRow, this.powerCol);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
    if (type.equals("part three shuffled")) {
      this.width = width;
      this.height = height;
      this.powerRow = 0;
      this.powerCol = 0;
      this.mst =  new Util().makeMST(this.width, this.height);
      this.board = new Util().makeMSTBoard(this.mst, this.width, this.height);
      this.board.get(powerRow).get(powerCol).powerStation = true;
      this.board =  new Util().addConnections(this.board, this.height, this.width);
      this.radius =  new Util().findRad(this.board, this.powerRow, this.powerCol);
      new Util().shuffle(board);
      this.board =  new Util().addConnections(this.board, this.height, this.width);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
  }

  LightEmAll(int width, int height, Random rand,  String type) {
    if (type.equals("part three")) {
      this.width = width;
      this.height = height;
      this.powerRow = 0;
      this.powerCol = 0;
      this.mst =  new Util().makeMSTTest(this.width, this.height, rand);
      this.board = new Util().makeMSTBoard(this.mst, this.width, this.height);
      this.board.get(powerRow).get(powerCol).powerStation = true;
      this.board =  new Util().addConnections(this.board, this.height, this.width);
      this.radius =  new Util().findRad(this.board, this.powerRow, this.powerCol);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
    if (type.equals("part three shuffled")) {
      this.width = width;
      this.height = height;
      this.powerRow = 0;
      this.powerCol = 0;
      this.mst =  new Util().makeMSTTest(this.width, this.height, rand);
      this.board = new Util().makeMSTBoard(this.mst, this.width, this.height);
      this.board.get(powerRow).get(powerCol).powerStation = true;
      this.board =  new Util().addConnections(this.board, this.height, this.width);
      this.radius =  new Util().findRad(this.board, this.powerRow, this.powerCol);
      new Util().shuffleTest(board, rand);
      this.board =  new Util().addConnections(this.board, this.height, this.width);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.nodes = new Util().collapse(this.board);
      this.score = 0;
      this.tickCount = 0;
      this.seconds = 0;
      this.type = type;
      this.allLit = false;
    }
  }

  //creates the scene of the LightEmAll game
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    scene.placeImageXY(new RectangleImage(this.width  * (gamePieceSize + 4),
        this.height  * (gamePieceSize + 4), OutlineMode.SOLID, Color.DARK_GRAY),
        this.width  * (gamePieceSize + 4) / 2,
        (this.height  * (gamePieceSize + 4)) / 2);
    int x = gamePieceSize / 2;
    int y = gamePieceSize / 2;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        scene.placeImageXY(board.get(row).get(col).draw(gamePieceSize, this.board, 
            this.powerRow, this.powerCol, this.radius),
            x, y);
        x = x + gamePieceSize + 4;
      }
      y = y + gamePieceSize + 4;
      x = gamePieceSize / 2;
    }
    x = (width / 2 * gamePieceSize);
    scene.placeImageXY(new TextImage("Score: " + Integer.toString(this.score) ,
        Color.black), x , y);
    y = y + gamePieceSize;
    int minutes = Math.floorDiv(this.seconds,  60);

    scene.placeImageXY(new TextImage("Time: " + Integer.toString(minutes) +
        ":" + Integer.toString(this.seconds - (minutes * 60)), Color.black), x , y);
    y = y + gamePieceSize;
    scene.placeImageXY(new RectangleImage(gamePieceSize * 5, 
        gamePieceSize , OutlineMode.SOLID, Color.pink), x , y);
    scene.placeImageXY(new TextImage("Restart", Color.black), x , y);
    return scene;
  }

  //handles mouse clicks by the user
  //EFFECT: updates the clicked-on GamePiece by rotating it counter clockwise,
  // additionally, it updates the connections between GamePieces base don the
  // rotation
  public void onMouseClicked(Posn location) {
    if (location.x > 0 && location.x < (width * (gamePieceSize + 4))
        && location.y > 0 && location.y < (height * (gamePieceSize + 4))) {
      int row = location.y / (gamePieceSize + 4);
      int col = location.x / (gamePieceSize + 4);
      this.board.get(row).get(col).rotateCounter();
      this.board = new Util().addConnections(this.board, this.height, this.width);
      new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
      this.score ++;
      this.allLit = this.areAllLit();
    }

    if (location.y > (height * (gamePieceSize + 4) + (gamePieceSize * 2 )) 
        && location.y < (height * (gamePieceSize + 4) + (gamePieceSize * 3))
        && location.x > ((width / 2 * gamePieceSize) - (gamePieceSize * 5 / 2))
        && location.x < ((width / 2 * gamePieceSize) + (gamePieceSize * 5 / 2))) {
      LightEmAll g = new LightEmAll(this.width, this.height, this.type);
      g.bigBang(800, 800, .1);

    }
  }


  //handles key events by the user
  //EFFECT: updates the position of the PowerStation in response to the
  // arrow pressed by the user, updates the coordinates of the PowerStation
  public void onKeyEvent(String key) {
    int oldRow = this.powerRow;
    int oldCol = this.powerCol;
    if (key.equals("up") && this.board.get(this.powerRow).get(this.powerCol).toTheTop) {
      this.powerRow = this.powerRow - 1;
    }
    if (key.equals("down") && this.board.get(this.powerRow).get(this.powerCol).toTheBottom) {

      this.powerRow = this.powerRow + 1;
    }
    if (key.equals("left") && this.board.get(this.powerRow).get(this.powerCol).toTheLeft) {

      this.powerCol = this.powerCol - 1;
    }
    if (key.equals("right") && this.board.get(this.powerRow).get(this.powerCol).toTheRight) {
      this.powerCol = this.powerCol + 1;
    }
    this.board.get(oldRow).get(oldCol).powerStation = false;
    this.board.get(powerRow).get(powerCol).powerStation = true;
    new Util().lightUp(this.board, this.powerRow, this.powerCol, this.radius);
    this.score++;
    this.allLit = this.areAllLit();
  }

  // is every GamePiece in the board it?
  boolean areAllLit() {
    for (int row = 0; row < height - 1; row++) {
      for (int col = 0; col < width - 1; col++) {
        if (! this.board.get(row).get(col).lit) {
          return false;
        }
      }
    }
    return true;
  }

  //keeps track of the seconds that have passed
  public void onTick() {
    this.tickCount++;
    if (this.tickCount % 10 == 0) {
      this.seconds++;
      this.tickCount = 0;
    }
  }

  // ends the world with a win if all GamePieces are lit up
  public WorldEnd worldEnds() {
    if (this.allLit) {
      return new WorldEnd(true, this.drawWin());
    }
    else {
      return new WorldEnd(false, this.getEmptyScene());
    }
  }

  // draws the scene in the event of win
  WorldScene drawWin() {
    WorldScene scene = this.getEmptyScene();
    scene.placeImageXY(new TextImage("You Won!" ,
        Color.black), 50, 
        40);
    scene.placeImageXY(new TextImage("Time: " + 
        Integer.toString(Math.floorDiv(this.seconds, 60)) + ":" +
        Integer.toString(this.seconds % 60),
        Color.black), 50, 
        60);
    scene.placeImageXY(new TextImage("Score: " + 
        Integer.toString(this.score) ,
        Color.black), 50, 
        80);
    return scene;
  }

}
