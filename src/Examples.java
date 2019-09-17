import java.awt.Color;
import javalib.worldimages.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javalib.impworld.WorldScene;
import tester.*;

//a class to hold examples
class Examples {

  // tests the big bang function
  // the string in the constructor for LightEmAll
  // signifies the type of board generation
  // "part one" or "part two" generates
  // the respective boards unshuffled and
  // "part one shuffled" and "part two shuffled"
  // generates the respective board type shuffle

  // also "part three" and "part three shuffled"

  // can use the constructor LightEmALL(width, height, random, string)
  // on "part three" and "part three shuffled" for testing

  void testGame(Tester t) {
    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three shuffled");
    g.bigBang(800, 800, .1);
  }

  ArrayList<Integer> ints1 = new ArrayList<Integer>(
      Arrays.asList(1, 2, 3, 4));

  ArrayList<Integer> ints2 = new ArrayList<Integer>(
      Arrays.asList(4, 5, 6, 7));

  ArrayList<ArrayList<Integer>> ints1And2 = new ArrayList<
      ArrayList<Integer>>(Arrays.asList(ints1, ints2));

  ArrayList<Integer> ints3 = new ArrayList<Integer>(
      Arrays.asList(1, 2, 3, 4, 4, 5, 6, 7));

  ArrayList<Integer> ints4 = new ArrayList<Integer>(
      Arrays.asList(5, 6, 7, 8));

  ArrayList<Integer> ints5 = new ArrayList<Integer>(
      Arrays.asList(8, 9, 10, 11));

  ArrayList<ArrayList<Integer>> ints4And5 = new ArrayList<
      ArrayList<Integer>>(Arrays.asList(ints4, ints5));



  Util u = new Util();
  LightEmAll lea;
  LightEmAll small;
  LightEmAll once = new LightEmAll(5, 5, "part one shuffled");
  WorldScene smallScene;
  GamePiece gp1;
  GamePiece gp2;
  GamePiece gp3;
  GamePiece gp4;
  ArrayList<ArrayList<GamePiece>> twoByTwo;
  GamePiece gp12;
  GamePiece gp22;
  GamePiece gp32;
  GamePiece gp42;
  ArrayList<ArrayList<GamePiece>> twoByTwo2;
  WorldImage BottomLeft;
  WorldImage topLeft;
  WorldImage BottomRight;

  // initializes the data for the beginning state
  void init() {
    lea = new LightEmAll(5,5, "part one");
    small = new LightEmAll(2, 2, "part one shuffled");
    smallScene = small.getEmptyScene();
    topLeft = new RectangleImage(40,
        40, OutlineMode.SOLID, Color.DARK_GRAY);
    topLeft = new OverlayImage(new RectangleImage(4, 20,
        OutlineMode.SOLID, Color.yellow).movePinhole(0, -10), topLeft);
    smallScene.placeImageXY(topLeft, 20, 20);
    smallScene.placeImageXY(topLeft, 65, 20);
    BottomLeft = new RectangleImage(40,
        40, OutlineMode.SOLID, Color.DARK_GRAY);
    BottomLeft = new OverlayImage(new RectangleImage(4, 20,
        OutlineMode.SOLID, Color.yellow).movePinhole(0, -10), BottomLeft);
    BottomLeft = new OverlayImage(new RectangleImage(4, 20,
        OutlineMode.SOLID, Color.yellow).movePinhole(0, 10), BottomLeft);
    BottomLeft = new OverlayImage(new RectangleImage(4, 20,
        OutlineMode.SOLID, Color.yellow).movePinhole(-10, 0), BottomLeft);
    smallScene.placeImageXY(BottomLeft, 20, 65);
    BottomRight = new RectangleImage(40,
        40, OutlineMode.SOLID, Color.DARK_GRAY);
    BottomRight = new OverlayImage(new RectangleImage(20, 4,
        OutlineMode.SOLID, Color.yellow).movePinhole(10, 0), BottomRight);
    BottomRight = new OverlayImage(new RectangleImage(4, 20,
        OutlineMode.SOLID, Color.yellow).movePinhole(0, 10), BottomRight);
    BottomRight = new OverlayImage(new RectangleImage(4, 20,
        OutlineMode.SOLID, Color.yellow).movePinhole(0, -10), BottomRight);
    BottomRight = new OverlayImage(new StarImage(40 / 2, 5, OutlineMode.SOLID,
        Color.blue), BottomRight);
    smallScene.placeImageXY(BottomRight, 65, 65);
    smallScene.placeImageXY(new TextImage("Score: 0"  , Color.black), 40 , 65);
    smallScene.placeImageXY(new TextImage("Time: 0:0"  , Color.black), 40 , 105);
    smallScene.placeImageXY(new RectangleImage(40 * 5, 40 , 
        OutlineMode.SOLID, Color.pink), 40 , 145);
    smallScene.placeImageXY(new TextImage("Restart", Color.black), 40 , 145);

    gp1 = new GamePiece(0, 0, false, false, false, true, false, false, false, false, true);
    gp2 = new GamePiece(0, 1, false, false, false, true, false, false, false, false, true);
    gp3 = new GamePiece(1, 0, false, true, true, true, false, false, true, true, false);
    gp4 = new GamePiece(1, 1, true, false, true, true, true, true, false, true, false);

    ArrayList<GamePiece> row0 = new ArrayList<GamePiece>(Arrays.asList(gp1, gp2));
    ArrayList<GamePiece> row1 = new ArrayList<GamePiece>(Arrays.asList(gp3, gp4));

    twoByTwo = new ArrayList<ArrayList<GamePiece>>(Arrays.asList(row0, row1));

    gp12 = new GamePiece(0, 0, false, false, false, true, false);
    gp22 = new GamePiece(0, 1, false, false, false, true, false);
    gp32 = new GamePiece(1, 0, false, true, true, true, false);
    gp42 = new GamePiece(1, 1, true, false, true, true, true);

    ArrayList<GamePiece> row02 = new ArrayList<GamePiece>(Arrays.asList(gp12, gp22));
    ArrayList<GamePiece> row12 = new ArrayList<GamePiece>(Arrays.asList(gp32, gp42));

    twoByTwo2 = new ArrayList<ArrayList<GamePiece>>(Arrays.asList(row02, row12));

  }

  //tests the collapse method
  void testCollapse(Tester t) {
    t.checkExpect(u.collapse(this.ints1And2), ints3);
    t.checkExpect(Math.ceil(2.32), 3.0);
  }

  //tests for the onKey method
  void testOnKey(Tester t) {
    init();
    t.checkExpect(lea.score, 0);
    lea.onKeyEvent("up");
    t.checkExpect(lea.board.get(4).get(3).toTheBottom, false);
    t.checkExpect(lea.powerRow, 1);
    t.checkExpect(lea.score, 1);
    lea.onKeyEvent("up");
    t.checkExpect(lea.powerRow, 0);
    t.checkExpect(lea.score, 2);
    lea.onKeyEvent("right");
    t.checkExpect(lea.powerRow, 0);
    t.checkExpect(lea.powerCol, 2);
    t.checkExpect(lea.score, 3);
    t.checkExpect(lea.board.get(4).get(2).toTheBottom, false);
    t.checkExpect(lea.board.get(4).get(0).toTheBottom, false);

    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");

    t.checkExpect(g.score, 0);
    t.checkExpect(g.allLit, false);
    g.onKeyEvent("right");
    g.onKeyEvent("right");
    g.onKeyEvent("right");
    g.onKeyEvent("down");
    g.onKeyEvent("down");
    g.onKeyEvent("left");
    g.onKeyEvent("down");
    g.onKeyEvent("right");
    t.checkExpect(g.score, 8);
    t.checkExpect(g.allLit, true);

  }

  //test the onTick method
  void testOnTick(Tester t) {
    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");

    t.checkExpect(g.tickCount, 0);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 1);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 2);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 3);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 4);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 5);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 6);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 7);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 8);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 9);
    t.checkExpect(g.seconds, 0);
    g.onTick();
    t.checkExpect(g.tickCount, 0);
    t.checkExpect(g.seconds, 1);
    g.onTick();
    t.checkExpect(g.tickCount, 1);
    t.checkExpect(g.seconds, 1);
  }

  //test the worldEnds method
  void testWordEnds(Tester t) {
    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");

    t.checkExpect(g.score, 0);
    t.checkExpect(g.allLit, false);
    t.checkExpect(g.worldEnds(), new WorldEnd(false, g.getEmptyScene()));
    g.onKeyEvent("right");
    g.onKeyEvent("right");
    g.onKeyEvent("right");
    g.onKeyEvent("down");
    g.onKeyEvent("down");
    g.onKeyEvent("left");
    g.onKeyEvent("down");
    g.onKeyEvent("right");
    t.checkExpect(g.score, 8);
    t.checkExpect(g.allLit, true);

    WorldScene scene = g.getEmptyScene();
    scene.placeImageXY(new TextImage("You Won!" ,
        Color.black), 50, 
        40);
    scene.placeImageXY(new TextImage("Time: " + 
        Integer.toString(Math.floorDiv(0, 60)) + ":" +
        Integer.toString(0 % 60),
        Color.black), 50, 
        60);
    scene.placeImageXY(new TextImage("Score: " + 
        Integer.toString(8) ,
        Color.black), 50, 
        80);
    t.checkExpect(g.worldEnds(), new WorldEnd(true, scene));
  }

  //tests the drawWin method
  void testDrawWIn(Tester t) {
    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");
    WorldScene scene1 = g.getEmptyScene();
    scene1.placeImageXY(new TextImage("You Won!" ,
        Color.black), 50, 
        40);
    scene1.placeImageXY(new TextImage("Time: " + 
        Integer.toString(Math.floorDiv(0, 60)) + ":" +
        Integer.toString(0 % 60),
        Color.black), 50, 
        60);
    scene1.placeImageXY(new TextImage("Score: " + 
        Integer.toString(0) ,
        Color.black), 50, 
        80);
    t.checkExpect(g.drawWin(), scene1);

    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();
    g.onTick();

    g.onKeyEvent("right");
    WorldScene scene2 = g.getEmptyScene();
    scene2.placeImageXY(new TextImage("You Won!" ,
        Color.black), 50, 
        40);
    scene2.placeImageXY(new TextImage("Time: "  
        + Integer.toString(0) + ":" 
        + Integer.toString(1),
        Color.black), 50, 
        60);
    scene2.placeImageXY(new TextImage("Score: " 
        + Integer.toString(1) ,
        Color.black), 50, 
        80);
    t.checkExpect(g.drawWin(), scene2);
  }

  //test the areAllLit method
  void testAreAllLit(Tester t) {
    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");

    t.checkExpect(g.areAllLit(), false);
    t.checkExpect(g.allLit, false);
    g.onKeyEvent("right");
    g.onKeyEvent("right");
    t.checkExpect(g.areAllLit(), false);
    t.checkExpect(g.allLit, false);
    g.onKeyEvent("right");
    g.onKeyEvent("down");
    g.onKeyEvent("down");
    t.checkExpect(g.areAllLit(), false);
    t.checkExpect(g.allLit, false);
    g.onKeyEvent("left");
    g.onKeyEvent("down");
    g.onKeyEvent("right");
    t.checkExpect(g.areAllLit(), true);
    t.checkExpect(g.allLit, true);
  }

  //tests for the OnMouseClicked method
  void testOnMouseClicked(Tester t) {
    init();
    t.checkExpect(lea.seconds, 0);
    t.checkExpect(lea.score, 0);
    lea.onMouseClicked(new Posn(5, 5));
    t.checkExpect(lea.board.get(0).get(0).bottom, false);
    t.checkExpect(lea.board.get(0).get(0).toTheBottom, false);
    t.checkExpect(lea.board.get(0).get(0).right, true);
    t.checkExpect(lea.board.get(0).get(0).toTheRight, false);
    t.checkExpect(lea.score, 1);
    lea.onMouseClicked(new Posn(60, 100));
    t.checkExpect(lea.board.get(2).get(1).left, true);
    t.checkExpect(lea.board.get(2).get(1).right, true);
    t.checkExpect(lea.board.get(2).get(1).top, true);
    t.checkExpect(lea.board.get(2).get(1).bottom, true);
    t.checkExpect(lea.board.get(2).get(1).toTheLeft, true);
    t.checkExpect(lea.board.get(2).get(1).toTheRight, true);
    t.checkExpect(lea.board.get(2).get(1).toTheTop, true);
    t.checkExpect(lea.board.get(2).get(1).toTheBottom, true);
    t.checkExpect(lea.score, 2);

    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");
    t.checkExpect(g.allLit, false);
    g.onMouseClicked(new Posn(60, 100));
    t.checkExpect(g.allLit, false);

  }

  //tests for the MakeScene method
  void testMakeScene(Tester t) {
    init();
    t.checkExpect(this.small.makeScene(), this.smallScene);
  }

  //tests for the rotateCounter method
  void testRotateCounter(Tester t) {
    init();
    lea.board.get(0).get(0).rotateCounter();
    t.checkExpect(lea.board.get(0).get(0).bottom, false);
    t.checkExpect(lea.board.get(0).get(0).right, true);
    lea.board.get(2).get(1).rotateCounter();
    t.checkExpect(lea.board.get(2).get(1).left, true);
    t.checkExpect(lea.board.get(2).get(1).right, true);
    t.checkExpect(lea.board.get(2).get(1).top, true);
    t.checkExpect(lea.board.get(2).get(1).bottom, true);
  }

  //tests for the duplicateWithConnections method
  void testDuplicateWithConnections(Tester t) {
    t.checkExpect(new GamePiece(3, 3, true, true, true, true,
        true).duplicateWithConnections(false, true,
            false,  true),
        new GamePiece(3, 3, true, true,
            true, true, true, false,
            true, false, true));
    t.checkExpect(new GamePiece(3, 1, true, false, true, true,
        true).duplicateWithConnections(false, true,
            false,  true),
        new GamePiece(3, 1, true, false, true, true,
            true, false, true, false, true));
  }

  //tests for the makeBoard method
  void testMakeBoard(Tester t) {
    init();
    t.checkExpect(u.makeBoard(2, 2, 1, 1), this.twoByTwo2);
  }

  //tests for the addConnections method
  void testAddConections1(Tester t) {
    init();
    t.checkExpect(u.addConnections(this.twoByTwo2, 2, 2), this.twoByTwo);
  }

  //tests for the draw method
  void testDraw(Tester t) {
    init();
    LightEmAll g = new LightEmAll(5, 8, new Random(8), "part three");
    WorldImage cell = new RectangleImage(40, 40, 
        OutlineMode.SOLID, Color.LIGHT_GRAY);
    cell = new OverlayImage(new RectangleImage(20, 4,
        OutlineMode.SOLID, new Color(247, 255, 0)).movePinhole(-10,0), 
        cell);
    cell = new OverlayImage(new StarImage(40 / 2, 5, OutlineMode.SOLID,
        Color.blue), cell);

    t.checkExpect(g.board.get(0).get(0).draw(40, g.board, 
        0, 0, 12), cell);

    WorldImage cell2 = new RectangleImage(40, 40, 
        OutlineMode.SOLID, Color.LIGHT_GRAY);
    cell2 = new OverlayImage(new RectangleImage(20, 4,
        OutlineMode.SOLID, new  Color(242, 123, 4)).movePinhole(-10,0), 
        cell2);
    t.checkExpect(g.board.get(3).get(0).draw(40, g.board, 0, 0, 10), cell2);

  }

  //tests the buildPart2 method
  void testBuildPart2(Tester t) {
    t.checkExpect(new LightEmAll(4, 4, "part two").board.size(), 4);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).size(), 4);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(1).size(), 4);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(2).size(), 4);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(3).size(), 4);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(1).get(0).toTheTop, true);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(1).get(0).toTheLeft, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).get(0).toTheTop, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).get(0).toTheLeft, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(3).get(0).toTheBottom, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(3).get(2).toTheBottom, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(2).get(0).toTheTop, true);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(2).get(0).toTheLeft, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(2).get(0).toTheBottom, true);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(2).get(0).toTheRight, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).get(1).toTheTop, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).get(2).toTheTop, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).get(3).toTheTop, false);
    t.checkExpect(new LightEmAll(4, 4, "part two").board.get(0).get(0).lit, false);
  }

  //tests the addConnections method
  void testAddConections(Tester t) {

    LightEmAll game1 = new LightEmAll(8, 8, "part two");
    LightEmAll game2 = new LightEmAll(6,6, "part two");


    t.checkExpect(game2.board.get(0).get(0).row, 0);
    t.checkExpect(game2.board.get(1).get(0).row, 1);
    t.checkExpect(game2.board.get(2).get(0).row, 2);
    t.checkExpect(game2.board.get(3).get(0).row, 3);
    t.checkExpect(game2.board.get(4).get(0).row, 4);
    t.checkExpect(game2.board.get(5).get(0).row, 5);

    t.checkExpect(game2.board.get(0).get(0).col, 0);
    t.checkExpect(game2.board.get(1).get(0).col, 0);
    t.checkExpect(game2.board.get(2).get(0).col, 0);
    t.checkExpect(game2.board.get(3).get(0).col, 0);
    t.checkExpect(game2.board.get(4).get(0).col, 0);
    t.checkExpect(game2.board.get(5).get(0).col, 0);

    t.checkExpect(game2.board.get(0).get(1).row, 0);
    t.checkExpect(game2.board.get(1).get(1).row, 1);
    t.checkExpect(game2.board.get(2).get(1).row, 2);
    t.checkExpect(game2.board.get(3).get(1).row, 3);
    t.checkExpect(game2.board.get(4).get(1).row, 4);
    t.checkExpect(game2.board.get(5).get(1).row, 5);

    t.checkExpect(game2.board.get(0).get(1).col, 1);
    t.checkExpect(game2.board.get(1).get(1).col, 1);
    t.checkExpect(game2.board.get(2).get(1).col, 1);
    t.checkExpect(game2.board.get(3).get(1).col, 1);
    t.checkExpect(game2.board.get(4).get(1).col, 1);
    t.checkExpect(game2.board.get(5).get(1).col, 1);

    t.checkExpect(game2.board.get(0).get(2).row, 0);
    t.checkExpect(game2.board.get(1).get(2).row, 1);
    t.checkExpect(game2.board.get(2).get(2).row, 2);
    t.checkExpect(game2.board.get(3).get(2).row, 3);
    t.checkExpect(game2.board.get(4).get(2).row, 4);
    t.checkExpect(game2.board.get(5).get(2).row, 5);

    t.checkExpect(game2.board.get(0).get(2).col, 2);
    t.checkExpect(game2.board.get(1).get(2).col, 2);
    t.checkExpect(game2.board.get(2).get(2).col, 2);
    t.checkExpect(game2.board.get(3).get(2).col, 2);
    t.checkExpect(game2.board.get(4).get(2).col, 2);
    t.checkExpect(game2.board.get(5).get(2).col, 2);

    t.checkExpect(game2.board.get(0).get(3).row, 0);
    t.checkExpect(game2.board.get(1).get(3).row, 1);
    t.checkExpect(game2.board.get(2).get(3).row, 2);
    t.checkExpect(game2.board.get(3).get(3).row, 3);
    t.checkExpect(game2.board.get(4).get(3).row, 4);
    t.checkExpect(game2.board.get(5).get(3).row, 5);

    t.checkExpect(game2.board.get(0).get(3).col, 3);
    t.checkExpect(game2.board.get(1).get(3).col, 3);
    t.checkExpect(game2.board.get(2).get(3).col, 3);
    t.checkExpect(game2.board.get(3).get(3).col, 3);
    t.checkExpect(game2.board.get(4).get(3).col, 3);
    t.checkExpect(game2.board.get(5).get(3).col, 3);

    t.checkExpect(game2.board.get(0).get(4).row, 0);
    t.checkExpect(game2.board.get(1).get(4).row, 1);
    t.checkExpect(game2.board.get(2).get(4).row, 2);
    t.checkExpect(game2.board.get(3).get(4).row, 3);
    t.checkExpect(game2.board.get(4).get(4).row, 4);
    t.checkExpect(game2.board.get(5).get(4).row, 5);

    t.checkExpect(game2.board.get(0).get(4).col, 4);
    t.checkExpect(game2.board.get(1).get(4).col, 4);
    t.checkExpect(game2.board.get(2).get(4).col, 4);
    t.checkExpect(game2.board.get(3).get(4).col, 4);
    t.checkExpect(game2.board.get(4).get(4).col, 4);
    t.checkExpect(game2.board.get(5).get(4).col, 4);

    t.checkExpect(game2.board.get(0).get(5).row, 0);
    t.checkExpect(game2.board.get(1).get(5).row, 1);
    t.checkExpect(game2.board.get(2).get(5).row, 2);
    t.checkExpect(game2.board.get(3).get(5).row, 3);
    t.checkExpect(game2.board.get(4).get(5).row, 4);
    t.checkExpect(game2.board.get(5).get(5).row, 5);

    t.checkExpect(game2.board.get(0).get(5).col, 5);
    t.checkExpect(game2.board.get(1).get(5).col, 5);
    t.checkExpect(game2.board.get(2).get(5).col, 5);
    t.checkExpect(game2.board.get(3).get(5).col, 5);
    t.checkExpect(game2.board.get(4).get(5).col, 5);
    t.checkExpect(game2.board.get(5).get(5).col, 5);


    t.checkExpect(game1.board.get(0).get(0).row, 0);
    t.checkExpect(game1.board.get(1).get(0).row, 1);
    t.checkExpect(game1.board.get(2).get(0).row, 2);
    t.checkExpect(game1.board.get(3).get(0).row, 3);
    t.checkExpect(game1.board.get(0).get(1).col, 1);
    t.checkExpect(game1.board.get(0).get(2).col, 2);
    t.checkExpect(game1.board.get(0).get(3).col, 3);
  }

  //tests the findFurthestFrom method
  void testFindFurthestFrom(Tester t) {
    LightEmAll testLEA = new LightEmAll(5, 5, new Random(3), "part three");
    LightEmAll testit = new LightEmAll(3, 3, "part two");
    t.checkExpect(new Util().findFurthestFrom(testLEA.board, 0, 0), 
        testLEA.board.get(3).get(0));
    t.checkExpect(new Util().findFurthestFrom(testLEA.board,3, 0), 
        testLEA.board.get(2).get(0));
    t.checkExpect(new Util().findFurthestFrom(testLEA.board, 1, 2), 
        testLEA.board.get(3).get(0));
    t.checkExpect(new Util().findFurthestFrom(testit.board, 0, 0), 
        testit.board.get(0).get(2));
  }

  // test the findLongestPath method
  void testFindLongestPath(Tester t) {
    LightEmAll testLEA = new LightEmAll(5, 5, new Random(3), "part three");
    LightEmAll testit = new LightEmAll(3, 3, "part two");
    LightEmAll testit2 = new LightEmAll(7, 7, "part two");
    t.checkExpect(new Util().findLongestPath(testLEA.board, 
        testLEA.board.get(0).get(0), 
        testLEA.board.get(3).get(0)), 13);
    t.checkExpect(new Util().findLongestPath(testLEA.board, 
        testLEA.board.get(1).get(3), 
        testLEA.board.get(2).get(2)), 2);
    t.checkExpect(new Util().findLongestPath(testit.board,
        testit.board.get(0).get(0),
        testit.board.get(1).get(1)), 2);
    t.checkExpect(new Util().findLongestPath(testit.board, 
        testit.board.get(0).get(0), 
        testit.board.get(0).get(1)), 3);
    t.checkExpect(new Util().findLongestPath(testit.board, 
        testit.board.get(0).get(0), 
        testit.board.get(0).get(2)), 6);
    t.checkExpect(new Util().findLongestPath(testit2.board, 
        testit2.board.get(5).get(3), 
        testit2.board.get(0).get(3)), 13);
    t.checkExpect(new Util().findLongestPath(testit2.board,
        testit2.board.get(6).get(4),
        testit2.board.get(0).get(3)), 13);
    t.checkExpect(new Util().findLongestPath(testit2.board, 
        testit2.board.get(5).get(2), 
        testit2.board.get(0).get(3)), 14);
    t.checkExpect(new Util().findLongestPath(testit2.board,
        testit2.board.get(3).get(4), testit2.board.get(1).get(6)), 4);
  }

  // test the findRad method
  void testRadius(Tester t) {
    LightEmAll testit2 = new LightEmAll(7, 7, "part two");
    LightEmAll testLEA = new LightEmAll(5, 5, new Random(3), "part three");

    t.checkExpect(testit2.radius, 13);
    t.checkExpect(new Util().findRad(testLEA.board, 0, 0), 8);
    t.checkExpect(testLEA.radius, 8);


  }

  //tests the isConnectedToPS method
  void testIsConnectedToPS(Tester t) {
    LightEmAll testLEA = new LightEmAll(5, 5, new Random(3), "part three");
    LightEmAll testShuffle = new LightEmAll(4, 6, new Random(4), "part three shuffled");

    t.checkExpect(testLEA.board.get(4).get(3).isConnectedToPS(testLEA.board,
        new ArrayList<GamePiece>(), 0, 0), true);
    t.checkExpect(testLEA.board.get(0).get(0).isConnectedToPS(testLEA.board,
        new ArrayList<GamePiece>(), 0, 0), true);
    t.checkExpect(testShuffle.board.get(0).get(0)
        .isConnectedToPS(testShuffle.board, new ArrayList<GamePiece>(), 
            0, 0), true);
    t.checkExpect(testShuffle.board.get(0).get(1)
        .isConnectedToPS(testShuffle.board, new ArrayList<GamePiece>(), 
            0, 0), false);
    t.checkExpect(testShuffle.board.get(3).get(3)
        .isConnectedToPS(testShuffle.board, new ArrayList<GamePiece>(), 
            0, 0), false);
  }

  //tests the equalGP method
  void testEqualGP(Tester t) {
    LightEmAll testLEA = new LightEmAll(5, 5, new Random(3), "part three");

    t.checkExpect(testLEA.board.get(0).get(0)
        .equalGP(testLEA.board.get(0).get(0)), true);
    t.checkExpect(testLEA.board.get(1).get(4)
        .equalGP(testLEA.board.get(0).get(0)), false);
    t.checkExpect(testLEA.board.get(3).get(2)
        .equalGP(testLEA.board.get(3).get(2)), true);
    t.checkExpect(testLEA.board.get(0).get(0)
        .equalGP(new GamePiece(0, 0, false, false, false, false, false)), true);

  }

  //tests the shuffle method
  void testShuffle(Tester t) {
    Random testing = new Random(2);
    LightEmAll testLEA = new LightEmAll(2, 2, testing, "part three");
    ArrayList<GamePiece> r0 = new ArrayList<GamePiece>(Arrays.asList(
        new GamePiece(0, 0, false, true, true, false, true, false, false, false, false),
        new GamePiece(0, 1, false, false, false, true, false, false, false, false, false)));
    ArrayList<GamePiece> r1 = new ArrayList<GamePiece>(Arrays.asList(
        new GamePiece(1, 0, true, false, true, false, false, false, false, false, false),
        new GamePiece(1, 1, false, false, false, true, false, false, false, false, false)));
    ArrayList<ArrayList<GamePiece>> hopefullyTheResult = new ArrayList<ArrayList<GamePiece>>(
        Arrays.asList(r0, r1));
    new Util().lightUp(hopefullyTheResult, 0, 0, 3); 
    new Util().shuffleTest(testLEA.board, testing);
    testLEA.board = new Util().addConnections(testLEA.board, 2, 2);
    new Util().lightUp(testLEA.board, 0, 0, 3);

    t.checkExpect(testLEA.board, hopefullyTheResult);
  }

  //tests the lightUP method
  void testLightUp(Tester t) {
    ArrayList<GamePiece> r0 = new ArrayList<GamePiece>(Arrays.asList(
        new GamePiece(0, 0, false, false, false, true, true, false, false, false, true),
        new GamePiece(0, 1, false, false, false, true, false, false, false, false, true)));
    ArrayList<GamePiece> r1 = new ArrayList<GamePiece>(Arrays.asList(
        new GamePiece(1, 0, false, true, true, false, false, false, true, true, false),
        new GamePiece(1, 1, true, false, true, false, false, true, false, true, false)));
    ArrayList<ArrayList<GamePiece>> preLit = new ArrayList<ArrayList<GamePiece>>(
        Arrays.asList(r0, r1));
    ArrayList<ArrayList<GamePiece>> postLit = new ArrayList<ArrayList<GamePiece>>(
        Arrays.asList(r0, r1));
    postLit.get(0).get(1).lit = false;
    new Util().lightUp(preLit, 0, 0, 3);   
    t.checkExpect(postLit, preLit);
  }

  //tests the findRad method
  void testFindRad(Tester t) {
    LightEmAll lea1 = new LightEmAll(2, 2, "part three");
    t.checkExpect(new Util().findRad(lea1.board, 0, 0), 2);
    t.checkExpect(lea1.radius, 2);
    LightEmAll lea1s = new LightEmAll(2, 2, "part three shuffled");
    t.checkExpect(lea1s.radius, 2);

    LightEmAll lea2 = new LightEmAll(5, 3, new Random(4), "part three");
    t.checkExpect(new Util().findRad(lea2.board, 0, 0), 5);
    t.checkExpect(lea2.radius, 5);
    LightEmAll lea2s = new LightEmAll(5, 3, new Random(4), "part three shuffled");
    t.checkExpect(lea2s.radius, 5);
  }

  //tests the reconstruct method
  void testReconstruct(Tester t) {
    GamePiece gp1 = new GamePiece(0,0);
    GamePiece gp2 = new GamePiece(0,1);
    GamePiece gp3 = new GamePiece(0,2);
    GamePiece gp4 = new GamePiece(1,0);
    GamePiece gp5 = new GamePiece(1,1);
    GamePiece gp6 = new GamePiece(1,2);

    HashMap<GamePiece, GamePiece> hash = new HashMap<GamePiece, GamePiece>();
    hash.put(gp2, gp1);
    hash.put(gp3, gp2);
    hash.put(gp5, gp3);
    hash.put(gp6, gp5);
    hash.put(gp4, gp1);

    t.checkExpect(new Util().reconstruct(hash, gp6, gp1, 0), 4);
    t.checkExpect(new Util().reconstruct(hash, gp5, gp1, 0), 3);
    t.checkExpect(new Util().reconstruct(hash, gp5, gp1, 5), 8);
  }

  //tests the makeBlankBoard method
  void testMakeBlankBoard(Tester t) {
    ArrayList<GamePiece> row0 = new ArrayList<GamePiece>(Arrays.asList(
        new GamePiece(0,0), new GamePiece(0, 1)));
    ArrayList<GamePiece> row1 = new ArrayList<GamePiece>(Arrays.asList(
        new GamePiece(1,0), new GamePiece(1, 1)));
    ArrayList<ArrayList<GamePiece>> testBoard = new ArrayList<ArrayList<GamePiece>>(
        Arrays.asList(row0, row1));

    t.checkExpect(new Util().makeBlankBoard(2, 2), testBoard);
  }

  //tests the makeAllEdges method
  void testMakeAllEdges(Tester t) {
    ArrayList<ArrayList<GamePiece>> testBoard = new Util().makeBlankBoard(2, 2);
    Random testrand = new Random(1);
    Edge e1 = new Edge(testBoard.get(0).get(0),
        testBoard.get(0).get(1),testrand.nextInt(4));
    Edge e2 = new Edge(testBoard.get(0).get(0),
        testBoard.get(1).get(0),testrand.nextInt(4));
    Edge e3 = new Edge(testBoard.get(0).get(1),
        testBoard.get(1).get(1),testrand.nextInt(4));
    Edge e4 = new Edge(testBoard.get(1).get(0),
        testBoard.get(1).get(1),testrand.nextInt(4));

    ArrayList<Edge> edges = new ArrayList<Edge>(Arrays.asList(
        e1, e2, e3, e4));
    t.checkExpect(new Util().makeAllEdges(2, 2, testBoard).size(), 4);
    t.checkExpect(new Util().makeAllEdgesTester(2, 2, testBoard, new Random(1)), 
        edges);
  }

  //tests the makeEachRep method
  void testMakeEachRep(Tester t) {
    ArrayList<ArrayList<GamePiece>> testBoard = new Util().makeBlankBoard(2, 2);
    HashMap<GamePiece, GamePiece> testMap = new HashMap<GamePiece, GamePiece>();
    testMap.put(testBoard.get(0).get(0), testBoard.get(0).get(0));
    testMap.put(testBoard.get(0).get(1), testBoard.get(0).get(1));
    testMap.put(testBoard.get(1).get(0), testBoard.get(1).get(0));
    testMap.put(testBoard.get(1).get(1), testBoard.get(1).get(1));

    t.checkExpect(new Util().makeEachRep(testBoard), testMap);
  }

  //tests the sortByWeight method
  void testSortByWeight(Tester t) {
    ArrayList<Edge> notSorted = new ArrayList<Edge>();
    notSorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 3));
    notSorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 5));
    notSorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 10));
    notSorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 2));
    notSorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 1));

    LinkedList<Edge> sorted = new LinkedList<Edge>();
    sorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 1));
    sorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 2));
    sorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 3));
    sorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 5));
    sorted.add(new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 10));

    t.checkExpect(new Util().sortByWeight(notSorted), sorted);
  }

  //tests the insertEdge method
  void testInsertEdge(Tester t) {
    LinkedList<Edge> testl = new LinkedList<Edge>();
    Edge e1 = new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 3);
    new Util().insertEdge(e1, testl);
    t.checkExpect(testl.size(), 1);
    t.checkExpect(testl.peekFirst(), e1);

    Edge e2 = new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 3);
    new Util().insertEdge(e2, testl);
    t.checkExpect(testl.size(), 2);
    t.checkExpect(testl.peekFirst(), e2);

    Edge e3 = new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 5);
    new Util().insertEdge(e3, testl);
    t.checkExpect(testl.size(), 3);
    t.checkExpect(testl.peekFirst(), e2);

    Edge e4 = new Edge(new GamePiece(0, 0), new GamePiece(1, 1), 10);
    new Util().insertEdge(e4, testl);
    t.checkExpect(testl.size(), 4);
    t.checkExpect(testl.peekFirst(), e2);
  }

  //tests the oneTree method
  void testOneTree(Tester t) {
    ArrayList<ArrayList<GamePiece>> testBoard = new Util().makeBlankBoard(2, 2);

    GamePiece gp1 = testBoard.get(0).get(0);
    GamePiece gp2 = testBoard.get(0).get(1);
    GamePiece gp4 = testBoard.get(1).get(0);
    GamePiece gp5 = testBoard.get(1).get(1);


    HashMap<GamePiece, GamePiece> hash = new HashMap<GamePiece, GamePiece>();
    hash.put(gp2, gp1);
    hash.put(gp5, gp2);
    hash.put(gp4, gp1);
    hash.put(gp1, gp1);

    t.checkExpect(new Util().oneTree(hash, testBoard), true);

    HashMap<GamePiece, GamePiece> hash2 = new HashMap<GamePiece, GamePiece>();
    hash.put(gp2, gp2);
    hash.put(gp5, gp1);
    hash.put(gp4, gp1);
    hash.put(gp1, gp1);

    t.checkExpect(new Util().oneTree(hash, testBoard), false);

  }

  //tests the findRep method
  void testFindRep(Tester t) {
    ArrayList<ArrayList<GamePiece>> testBoard = new Util().makeBlankBoard(2, 2);

    GamePiece gp1 = testBoard.get(0).get(0);
    GamePiece gp2 = testBoard.get(0).get(1);
    GamePiece gp4 = testBoard.get(1).get(0);
    GamePiece gp5 = testBoard.get(1).get(1);


    HashMap<GamePiece, GamePiece> hash = new HashMap<GamePiece, GamePiece>();
    hash.put(gp2, gp1);
    hash.put(gp5, gp2);
    hash.put(gp4, gp1);
    hash.put(gp1, gp1);

    t.checkExpect(new Util().findRep(gp1, hash), gp1);
    t.checkExpect(new Util().findRep(gp2, hash), gp1);
    t.checkExpect(new Util().findRep(gp4, hash), gp1);
    t.checkExpect(new Util().findRep(gp5, hash), gp1);

    HashMap<GamePiece, GamePiece> hash2 = new HashMap<GamePiece, GamePiece>();
    hash2.put(gp2, gp2);
    hash2.put(gp5, gp2);
    hash2.put(gp4, gp1);
    hash2.put(gp1, gp1);

    t.checkExpect(new Util().findRep(gp1, hash2), gp1);
    t.checkExpect(new Util().findRep(gp5, hash2), gp2);
    t.checkExpect(new Util().findRep(gp4, hash2), gp1);
  }

  //tests the union method
  void testUnion(Tester t) {
    ArrayList<ArrayList<GamePiece>> testBoard = new Util().makeBlankBoard(2, 2);

    GamePiece gp1 = testBoard.get(0).get(0);
    GamePiece gp2 = testBoard.get(0).get(1);
    GamePiece gp4 = testBoard.get(1).get(0);
    GamePiece gp5 = testBoard.get(1).get(1);

    HashMap<GamePiece, GamePiece> hash2 = new HashMap<GamePiece, GamePiece>();
    hash2.put(gp2, gp2);
    hash2.put(gp5, gp2);
    hash2.put(gp4, gp1);
    hash2.put(gp1, gp1);

    t.checkExpect(new Util().findRep(gp1, hash2), gp1);
    t.checkExpect(new Util().findRep(gp5, hash2), gp2);
    t.checkExpect(new Util().findRep(gp4, hash2), gp1);

    new Util().union(hash2, gp1, gp5);

    t.checkExpect(new Util().findRep(gp1, hash2), gp1);
    t.checkExpect(new Util().findRep(gp5, hash2), gp1);
    t.checkExpect(new Util().findRep(gp4, hash2), gp1);
    t.checkExpect(new Util().findRep(gp2, hash2), gp2);
    t.checkExpect(new Util().oneTree(hash2, testBoard), false);

    new Util().union(hash2, gp2, gp1);

    t.checkExpect(new Util().findRep(gp1, hash2), gp2);
    t.checkExpect(new Util().findRep(gp5, hash2), gp2);
    t.checkExpect(new Util().findRep(gp4, hash2), gp2);
    t.checkExpect(new Util().findRep(gp2, hash2), gp2);
    t.checkExpect(new Util().oneTree(hash2, testBoard), true);
  }

  //tests the makeMST method
  void testMakeMST(Tester t) {
    ArrayList<Edge> testmst = new Util().makeMSTTest(2, 2, new Random(12));

    ArrayList<ArrayList<GamePiece>> testBoard = new Util().makeBlankBoard(2, 2);
    ArrayList<Edge>  allEdges  = new Util().makeAllEdgesTester(2, 2, 
        testBoard, new Random(12));
    ArrayList<Edge> ans = new ArrayList<Edge>(Arrays.asList(allEdges.get(3), 
        allEdges.get(1), allEdges.get(0)));

    t.checkExpect(testmst, ans);
  }

  //tests the makeMSTBoard method
  void testMakeMSTBoard(Tester t) {
    ArrayList<Edge> testmst = new Util().makeMSTTest(2, 2, new Random(12));

    GamePiece gp1 = new GamePiece(0, 0, false, true, false, true, false);
    GamePiece gp2 = new GamePiece(0, 1, true, false, false, false, false);

    ArrayList<GamePiece> r1 = new ArrayList<GamePiece>(Arrays.asList(gp1, gp2));

    GamePiece gp3 = new GamePiece(1, 0, false, true, true, false, false);
    GamePiece gp4 = new GamePiece(1, 1, true, false, false, false, false);

    ArrayList<GamePiece> r2 = new ArrayList<GamePiece>(Arrays.asList(gp3, gp4));

    ArrayList<ArrayList<GamePiece>> ans = new ArrayList<ArrayList<GamePiece>>(
        Arrays.asList(r1, r2));

    t.checkExpect(new Util().makeMSTBoard(testmst, 2, 2), ans);
  }

}



























