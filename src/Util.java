import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javalib.worldimages.Posn;

// a Utils class
class Util {
  Random rand = new Random();

  //creates the game board for LightEmAll based on the given height and width
  //restrictions, additionally places the PowerStation in the given column/row
  ArrayList<ArrayList<GamePiece>> makeBoard(int height, int width,
      int powerCol, int powerRow) {
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    boolean top = false;
    boolean bottom = false;
    boolean left = false;
    boolean right = false;
    boolean powerStation = false;

    for (int row = 0; row < height; row++) {
      ArrayList<GamePiece> currRow = new ArrayList<GamePiece>();
      for (int c = 0; c < width; c++) {
        if (row == powerRow && c == powerCol) {
          powerStation = true;
        } else {
          powerStation = false; }
        if (row == 0) {
          top = false;
          bottom = true;
          left = false;
          right = false;
        }
        if (row == (height - 1)) {
          top = true;
          bottom = false;
          left = false;
          right = false;
        }
        if (row == (height / 2) && c == (width - 1)) {
          top = true;
          bottom = true;
          left = true;
          right = false;
        }
        if (row == (height / 2) && c == 0) {
          top = true;
          bottom = true;
          left = false;
          right = true;
        }
        if (row == (height / 2) && c != 0 && c != (width - 1)) {
          top = true;
          bottom = true;
          left = true;
          right = true;
        }
        if ((row != (height / 2) && (row != 0) && (row != (height - 1)))) {
          top = true;
          bottom = true;
          left = false;
          right = false;
        }
        GamePiece gp = new GamePiece(row, c, left, right, top, bottom, powerStation);
        currRow.add(gp);
      }
      board.add(currRow);
    }
    return board;
  }

  //generates a fractal board
  ArrayList<ArrayList<GamePiece>> makeBoardPart2(int height, int width) {
    ArrayList<ArrayList<GamePiece>> ans =  this.makeBoardPart2acc(height, width, new Posn(0, 0));
    boolean possibleCycleFlag = true;
    if (width >= height) {
      for (int row = height - 1; row >= 0; row--) {
        for (int col = 0; col < width ; col++) {
          if (row == height - 1) { // bottom row
            ans.get(row).get(col).bottom = false;
            if (col == 0) { //botom left corner
              ans.get(row).get(col).right = true;
            }
            else if (col == width - 1) { //bottom right corner
              ans.get(row).get(col).left = true;
            }
            else {
              ans.get(row).get(col).left = true;
              ans.get(row).get(col).right = true;
            }
          }
          if (col == width - 1) { //right column
            ans.get(row).get(col).right = false;

            if (row == 0) { //top right corner
              ans.get(row).get(col).top = false;
              ans.get(row).get(col).bottom = true;
            }

            else if (row == height - 1) { //bottom right corner
              ans.get(row).get(col).bottom = false;
              ans.get(row).get(col).top = true;
            }
            else if (!ans.get(row).get(col).left) {
              ans.get(row).get(col).top = true;
              ans.get(row).get(col).bottom = true;
            }
            else if (possibleCycleFlag) {
              if (! (ans.get(row).get(col).top && ans.get(row).get(col).bottom)) {
                possibleCycleFlag = false;                  
              }
              ans.get(row).get(col).top = true;
              ans.get(row).get(col).bottom = true;

            }
          }
        }
      }
    }

    else {
      for (int row = height - 1; row >= 0; row--) {
        for (int col = 0; col < width ; col++) {
          if (row == height - 1) { // bottom row
            ans.get(row).get(col).bottom = false;
            if (col == 0) { //botom left corner
              ans.get(row).get(col).right = true;
            }
            else if (col == width - 1) { //bottom right corner
              ans.get(row).get(col).left = true;
            }
            else {
              ans.get(row).get(col).left = true;
              ans.get(row).get(col).right = true;
            }
          }
          if (col == width - 1) { //right column
            ans.get(row).get(col).right = false;

            if (row == 0) { //top right corner
              ans.get(row).get(col).top = false;
              ans.get(row).get(col).bottom = true;
            }

            else if (row == height - 1) { //bottom right corner
              ans.get(row).get(col).bottom = false;
              ans.get(row).get(col).top = true;
            }
            else if (!ans.get(row).get(col).left) {
              ans.get(row).get(col).top = true;
              ans.get(row).get(col).bottom = true;
            }
            else if (possibleCycleFlag) {
              ans.get(row).get(col).top = true;
              ans.get(row).get(col).bottom = true;
              possibleCycleFlag = false;
            }
          }
        }
      }
    }

    return ans;

  }

  //generates a fractal board, with correct positioning for columns and rows
  ArrayList<ArrayList<GamePiece>> makeBoardPart2acc(int height, int width, Posn position) {

    ArrayList<ArrayList<GamePiece>> square = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<ArrayList<GamePiece>> ans = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> currRow = new ArrayList<GamePiece>();



    int baseNum = (int)Math.pow(2, Math.max((Math.ceil(Math.log10(height) / Math.log10(2))), 
        Math.ceil(Math.log10(width) / Math.log10(2))));

    square = this.makeBoardPart2FactorOfTwo(baseNum, baseNum, position);
    if (baseNum == height && baseNum == width) { 
      return square; }

    for (int row = 0; row < height ; row++) {
      currRow = new ArrayList<GamePiece>();
      for (int col = 0; col < width; col++) {
        currRow.add(square.get(row).get(col));
      }
      ans.add(currRow);
    }


    return ans;
  }    




  // appends two boards with the same number of columns. The first gets placed on top of the second.
  ArrayList<ArrayList<GamePiece>> appendTopAndBottom(ArrayList<ArrayList<GamePiece>> top, 
      ArrayList<ArrayList<GamePiece>> bottom) {
    //int originalTopSize = top.size();
    for (int row = 0 ; row < bottom.size(); row ++) {
      top.add(bottom.get(row));
    }

    return top;
  }

  // appends two boards withthe same number of rows. The first gets placed to the left of the second
  ArrayList<ArrayList<GamePiece>> appendLeftAndRight(ArrayList<ArrayList<GamePiece>> left, 
      ArrayList<ArrayList<GamePiece>> right) {
    ArrayList<ArrayList<GamePiece>> ans = new  ArrayList<ArrayList<GamePiece>>();
    //   int originalLeftWidth = left.get(0).size();
    for (int row = 0; row < left.size() ; row++) {
      ans.add(left.get(row));
      for (int c = 0 ; c < right.get(row).size() ; c++) {
        ans.get(row).add(right.get(row).get(c));
      }
    }

    for (int row = 0 ; row < ans.size() ; row++) {
      for (int col = 0; col < ans.get(row).size(); col++) {
        if (col == 0 || col == ans.get(row).size() - 1) { // left and right column
          if (row == 0) {
            ans.get(row).get(col).bottom = true;
          }
          else if (row == ans.size() - 1) {
            ans.get(row).get(col).top = true;
          }
          else {
            ans.get(row).get(col).bottom = true;
            ans.get(row).get(col).top = true;
          }
        }

        if (row == ans.size() - 1) { //bottom row
          if (col == 0) {
            ans.get(row).get(col).right = true;
          }
          else if (col == ans.get(row).size() - 1) {
            ans.get(row).get(col).left = true;
          }
          else {
            ans.get(row).get(col).left = true;
            ans.get(row).get(col).right = true;
          }
        }
      }


    }

    return ans;
  }

  //makes a fractal board where the width and height are equal and a power of two
  ArrayList<ArrayList<GamePiece>> makeBoardPart2FactorOfTwo(int height, int width, Posn position) {

    ArrayList<ArrayList<GamePiece>> square = new  ArrayList<ArrayList<GamePiece>>();

    if ((height == 2 && width == 2) || height <= 2 || width <= 2) {
      square = new ArrayList<ArrayList<GamePiece>>(Arrays.asList(
          new ArrayList<GamePiece>(Arrays.asList(
              new GamePiece(position.y, position.x, false, false, false, true, false), 
              new GamePiece(position.y, position.x + 1, false, false, false, true, false))), 
          new ArrayList<GamePiece>(Arrays.asList(new GamePiece(position.y + 1, 
              position.x, false, true, true, false, false), 
              new GamePiece(position.y + 1, position.x + 1, true, false, true, false, false)))));
    }
    else {

      ArrayList<ArrayList<GamePiece>> top = appendQuadsSideTop(this.makeBoardPart2FactorOfTwo(
          (int) Math.ceil(height / 2), 
          (int) Math.ceil(width / 2),  position), 
          this.makeBoardPart2FactorOfTwo((int) Math.ceil(height / 2), 
              (int) Math.ceil(width / 2), new Posn(position.x + (int) 
                  Math.ceil(width / 2), position.y)),
          (int) Math.ceil(height / 2), (int) Math.ceil(width / 2));

      ArrayList<ArrayList<GamePiece>> bottom = appendQuadsSideBottom(makeBoardPart2FactorOfTwo(
          (int) Math.ceil(height / 2), (int) Math.ceil(height / 2), 
          new Posn(position.x, position.y + (int) Math.ceil(height / 2))), 
          makeBoardPart2FactorOfTwo((int) Math.ceil(height / 2), 
              (int) Math.ceil(width / 2),  new Posn(position.x + (int) Math.ceil(width / 2), 
                  position.y + (int) Math.ceil(height / 2))),
          (int) Math.ceil(height / 2), (int) Math.ceil(width / 2));

      square = appendHalves(top, bottom, height, width);

    }
    return square;
  }

  //appends two square boards next to each other
  //EFFECT: changes the outer GamePiece in each quad to be pointing down as well
  ArrayList<ArrayList<GamePiece>> appendQuadsSideTop(ArrayList<ArrayList<GamePiece>> q1, 
      ArrayList<ArrayList<GamePiece>> q2, int height, int width) {

    ArrayList<ArrayList<GamePiece>> ans = new ArrayList<ArrayList<GamePiece>>();
    int rows = q2.size();
    int cols = q2.get(0).size();
    for (int r = 0; r < height ; r ++) {
      ans.add(q1.get(r));
      for (int c = 0 ; c < width ; c++) {
        ans.get(r).add(q2.get(r).get(c));
      }
    }
    ans.get(rows - 1).get(0).bottom = true;
    ans.get(rows - 1).get(cols * 2 - 1).bottom = true;
    return ans;
  }

  //appends two square boards next to each other
  //EFFECT: changes the middle lower gamepieces in each quad to connect to each other
  ArrayList<ArrayList<GamePiece>> appendQuadsSideBottom(ArrayList<ArrayList<GamePiece>> q1,
      ArrayList<ArrayList<GamePiece>> q2, int height, int width) {
    ArrayList<ArrayList<GamePiece>> ans = new ArrayList<ArrayList<GamePiece>>();
    int rows = q2.size();
    int cols = q2.get(0).size();
    for (int r = 0; r < width ; r ++) {
      ans.add(q1.get(r));
      for (int c = 0 ; c < height ; c++) {
        ans.get(r).add(q2.get(r).get(c));
      }
    }

    ans.get(rows - 1).get(cols  - 1).right = true;
    ans.get(rows - 1).get(cols).left = true;


    return ans;
  }

  //appends two boards on top of each other.
  //EFFECT: changes the outher upper gamepieces in the lower one to be pointing upward.
  ArrayList<ArrayList<GamePiece>> appendHalves(ArrayList<ArrayList<GamePiece>> top, 
      ArrayList<ArrayList<GamePiece>> bottom, int height, int width) {
    ArrayList<ArrayList<GamePiece>> ans = new ArrayList<ArrayList<GamePiece>>();
    int rows = top.size();
    int cols = top.get(0).size();
    for (ArrayList<GamePiece> row : top) {
      ans.add(row);
    }
    for (ArrayList<GamePiece> row2 : bottom) {
      ans.add(row2);
    }

    ans.get(rows).get(0).top = true;
    ans.get(rows).get(cols - 1).top = true;
    return ans;
  }



  //returns an updated-version of the given game board by adding all of the connections
  //for each GamePiece within it
  ArrayList<ArrayList<GamePiece>> addConnections(ArrayList<ArrayList<GamePiece>> board,
      int height, int width) {
    ArrayList<ArrayList<GamePiece>> ans = new ArrayList<ArrayList<GamePiece>>();
    for (int row = 0 ; row < board.size() ; row++) {
      ArrayList<GamePiece> ansRow = new ArrayList<GamePiece>();
      ArrayList<GamePiece> currRow = board.get(row);
      for (int col = 0 ; col < currRow.size() ; col++) {
        boolean ttop = false;
        boolean tbottom = false;
        boolean tright = false;
        boolean tleft = false;

        //top left corner
        if (row == 0 && col == 0) {
          tbottom = currRow.get(col).bottom && board.get(row + 1).get(col).top;
          tright = currRow.get(col).right && currRow.get(col + 1).left;
        }
        //top right corner
        else if (row == 0 && col == (width - 1)) {
          tbottom = currRow.get(col).bottom && board.get(row + 1).get(col).top;
          tleft = currRow.get(col).left && currRow.get(col - 1).right;
        }
        //top row not corner
        else if (row == 0 && col != 0 && col != (width - 1)) {
          tbottom = currRow.get(col).bottom && board.get(row + 1).get(col).top;
          tright = currRow.get(col).right && currRow.get(col + 1).left;
          tleft = currRow.get(col).left && currRow.get(col - 1).right;
        }
        //bottom left corner
        else if (row == (height - 1) && col == 0) {
          ttop = currRow.get(col).top && board.get(row - 1).get(col).bottom;
          tright = currRow.get(col).right && currRow.get(col + 1).left;
        }
        //bottom right corner
        else if (row == (height - 1) && col == (width - 1)) {
          ttop = currRow.get(col).top && board.get(row - 1).get(col).bottom;
          tleft = currRow.get(col).left && currRow.get(col - 1).right;
        }
        //bottom row not corner
        else if (row == (height - 1) && col != (width - 1) && col != 0) {
          ttop = currRow.get(col).top && board.get(row - 1).get(col).bottom;
          tleft = currRow.get(col).left && currRow.get(col - 1).right;
          tright = currRow.get(col).right && currRow.get(col + 1).left;
        }
        // left column not corner
        else if (col == 0 && row != 0 && row != (height - 1)) {
          ttop = currRow.get(col).top && board.get(row - 1).get(col).bottom;
          tbottom = currRow.get(col).bottom && board.get(row + 1).get(col).top;
          tright = currRow.get(col).right && currRow.get(col + 1).left;
        }
        // right column not corner
        else if (col == (width - 1) && row != 0 && row != (height - 1)) {
          ttop = currRow.get(col).top && board.get(row - 1).get(col).bottom;
          tbottom = currRow.get(col).bottom && board.get(row + 1).get(col).top;
          tleft = currRow.get(col).left && currRow.get(col - 1).right;
        }
        // not on any edge
        else if (col != (width - 1) && col != 0
            && row != 0 && row != (height - 1)) {
          ttop = currRow.get(col).top && board.get(row - 1).get(col).bottom;
          tbottom = currRow.get(col).bottom && board.get(row + 1).get(col).top;
          tleft = currRow.get(col).left && currRow.get(col - 1).right;
          tright = currRow.get(col).right && currRow.get(col + 1).left;
        }
        GamePiece gp = currRow.get(col).duplicateWithConnections(tleft, tright,
            ttop, tbottom);
        ansRow.add(gp);
      }
      ans.add(ansRow);
    }
    return ans;
  }


  //collapses the given list of lists of GamePieces into a single list of all the
  //GamePieces on the board
  <T> ArrayList<T> collapse(ArrayList<ArrayList<T>> board) {
    ArrayList<T> nodes = new ArrayList<T>();
    for (int col = 0; col < board.size() ; col++) {
      for (T gp : board.get(col)) {
        nodes.add(gp);
      }
    }
    return nodes;
  }

  //EFFECT: shuffles the GamePieces by rotating each a random
  //number of times
  void shuffle(ArrayList<ArrayList<GamePiece>> board) {
    for (ArrayList<GamePiece> row : board) {
      for (GamePiece gp : row) {
        int numRotations = rand.nextInt(3);
        for (int i = 0; i <= numRotations; i++) {
          gp.rotateCounter();
        }
      }
    }
  }

  //same as shuffle but with a seeded rand
  void shuffleTest(ArrayList<ArrayList<GamePiece>> board, 
      Random fakeRand) {
    for (ArrayList<GamePiece> row : board) {
      for (GamePiece gp : row) {
        int numRotations = fakeRand.nextInt(3);
        for (int i = 0; i <= numRotations; i++) {
          gp.rotateCounter();
        }
      }
    }
  }

  //EFFECT: changes all of the gampeices lit field to true if it 
  // is connected to the power station
  void lightUp(ArrayList<ArrayList<GamePiece>> board, 
      int powRow, int powCol, int rad) {
    for (ArrayList<GamePiece> row : board) {
      for (GamePiece gp : row) {
        boolean ans = false;
        if (gp.isConnectedToPS(board, new ArrayList<GamePiece>(), 
            powRow, powCol)) {
          ans = this.findLongestPath(board, gp, 
              board.get(powRow).get(powCol)) <= rad;
        }
        gp.lit = ans;

      }
    }
  }

  // finds the radius of the given board
  // distance from farthest from powerStation to farthest from that one / 2 + 1
  int findRad(ArrayList<ArrayList<GamePiece>> board, int powRow, int powCol) {
    GamePiece furthestFromStation = this.findFurthestFrom(board, powRow, powCol);
    GamePiece furthestFromTheOther = this.findFurthestFrom(board, 
        furthestFromStation.row, furthestFromStation.col);
    return this.findLongestPath(board, furthestFromStation, 
        furthestFromTheOther) / 2 + 1;
  }

  // finds the GamePiece on the baord that is the farthest from the
  // gamepiece at the given row and column
  GamePiece findFurthestFrom(ArrayList<ArrayList<GamePiece>> board, int startRow, int startCol) {
    ArrayList<GamePiece> loConnected = new ArrayList<GamePiece>();
    LinkedList<GamePiece> workList = new LinkedList<GamePiece>();

    workList.add(board.get(startRow).get(startCol));
    loConnected.add(board.get(startRow).get(startCol));

    while (! workList.isEmpty()) {
      GamePiece curr = workList.remove();
      if (curr.toTheLeft) {
        if (! loConnected.contains(board.get(curr.row).get(curr.col - 1))) {
          workList.add(board.get(curr.row).get(curr.col - 1));
          loConnected.add(0, board.get(curr.row).get(curr.col - 1));
        }
      }
      if (curr.toTheRight) {
        if (! loConnected.contains(board.get(curr.row).get(curr.col + 1))) {
          workList.add(board.get(curr.row).get(curr.col + 1));
          loConnected.add(0, board.get(curr.row).get(curr.col + 1));
        }
      }
      if (curr.toTheTop) {
        if (! loConnected.contains(board.get(curr.row - 1).get(curr.col))) {
          workList.add(board.get(curr.row - 1).get(curr.col));
          loConnected.add(0, board.get(curr.row - 1).get(curr.col));
        }
      }
      if (curr.toTheBottom) {
        if (! loConnected.contains(board.get(curr.row + 1).get(curr.col))) {
          workList.add(board.get(curr.row + 1).get(curr.col));
          loConnected.add(0, board.get(curr.row + 1).get(curr.col));
        }
      }
    }

    return loConnected.get(0);
  }

  // finds the length of the path from the start GamePiece to the target GamePiece
  int findLongestPath(ArrayList<ArrayList<GamePiece>> board, 
      GamePiece start, GamePiece target) {
    HashMap<GamePiece, GamePiece> cameFromEdge = 
        new HashMap<GamePiece, GamePiece>();
    LinkedList<GamePiece> workList = new LinkedList<GamePiece>();
    ArrayList<GamePiece> alreadySeen = new ArrayList<GamePiece>();

    workList.add(start);

    while (! workList.isEmpty()) {
      GamePiece next = workList.remove();
      if (alreadySeen.contains(next)) {
        // next is already discarded by .remove()
      }
      else if (next.equalGP(target)) {
        return reconstruct(cameFromEdge, next, start, 0);
      }
      else {
        alreadySeen.add(next);
        if (next.toTheLeft) {
          if (! alreadySeen.contains(board.get(next.row).get(next.col - 1))) {
            workList.add(board.get(next.row).get(next.col - 1));
            cameFromEdge.put(board.get(next.row).get(next.col - 1),next);

          }
        }
        if (next.toTheRight) {
          if (! alreadySeen.contains(board.get(next.row).get(next.col + 1))) {
            workList.add(board.get(next.row).get(next.col + 1));
            cameFromEdge.put(board.get(next.row).get(next.col + 1),next);

          }
        }
        if (next.toTheTop) {
          if (! alreadySeen.contains(board.get(next.row - 1).get(next.col))) {
            workList.add(board.get(next.row - 1).get(next.col));
            cameFromEdge.put(board.get(next.row - 1).get(next.col),next);

          }
        }
        if (next.toTheBottom) {
          if (! alreadySeen.contains(board.get(next.row + 1).get(next.col))) {
            workList.add(board.get(next.row + 1).get(next.col));
            cameFromEdge.put(board.get(next.row + 1).get(next.col),next);

          }
        }
      }
    }
    return 0;

  }

  // reconstructs the path, using the HashMap from the start GamePiece to the target 
  // and returns the length of that path
  // we don't really care about the actually path so it only keeps track of/ returns
  // the length of it
  int reconstruct(HashMap<GamePiece, GamePiece> hash, 
      GamePiece start, GamePiece target, int acc) {
    //System.out.println("goin through reconstruct");
    GamePiece next = hash.get(start);
    acc++;
    if (next == null   || next.equalGP(target)) {
      return acc;
    }
    else {
      return reconstruct(hash, next, target, acc);
    }

  }

  // makes a board of the given width and heigth with "blank" gamePieces
  // (all directions, powerstation set to false)
  ArrayList<ArrayList<GamePiece>> makeBlankBoard(int width, int height) {
    ArrayList<ArrayList<GamePiece>> board = 
        new ArrayList<ArrayList<GamePiece>>();

    for (int row = 0; row < height; row++) {
      ArrayList<GamePiece> currRow = new ArrayList<GamePiece>();
      for (int col = 0; col < width ; col++) {
        currRow.add(new GamePiece(row, col));         
      }
      board.add(currRow);
    }
    return board;
  }

  // makes a list of every possible edge in the given board
  ArrayList<Edge> makeAllEdges(int width, int height, 
      ArrayList<ArrayList<GamePiece>> board) {
    ArrayList<Edge> ans = new ArrayList<Edge>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width ; col++) {
        if (row == height - 1) {
          if (col != width - 1) {
            ans.add(new Edge(board.get(row).get(col), 
                board.get(row).get(col + 1), rand.nextInt(width * height)));
          }
        }
        else if (col == width - 1) {
          if (row != height - 1) {
            ans.add(new Edge(board.get(row).get(col), 
                board.get(row + 1).get(col), rand.nextInt(width * height)));
          }
        }
        else {
          ans.add(new Edge(board.get(row).get(col), 
              board.get(row).get(col + 1), rand.nextInt(width * height)));
          ans.add(new Edge(board.get(row).get(col), 
              board.get(row + 1).get(col), rand.nextInt(width * height)));
        }
      }

    }
    return ans;
  }

  // same as makeAlEdges but with given random for testing
  ArrayList<Edge> makeAllEdgesTester(int width, int height, 
      ArrayList<ArrayList<GamePiece>> board, Random givenRand) {
    ArrayList<Edge> ans = new ArrayList<Edge>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width ; col++) {
        if (row == height - 1) {
          if (col != width - 1) {
            ans.add(new Edge(board.get(row).get(col),
                board.get(row).get(col + 1),
                givenRand.nextInt(width * height)));
          }
        }
        else if (col == width - 1) {
          if (row != height - 1) {
            ans.add(new Edge(board.get(row).get(col), 
                board.get(row + 1).get(col), 
                givenRand.nextInt(width * height)));
          }
        }
        else {
          ans.add(new Edge(board.get(row).get(col),
              board.get(row).get(col + 1), 
              givenRand.nextInt(width * height)));
          ans.add(new Edge(board.get(row).get(col), 
              board.get(row + 1).get(col),
              givenRand.nextInt(width * height)));
        }
      }

    }
    return ans;
  }

  //Makes a HashMap where every GamePiece in the board is set to itself
  HashMap<GamePiece, GamePiece> makeEachRep(ArrayList<ArrayList<GamePiece>> board) {
    HashMap<GamePiece, GamePiece> ans = new HashMap<GamePiece, GamePiece>();
    for (int row = 0 ; row < board.size() ; row++) {
      for (int col = 0 ; col < board.get(0).size() ; col++) {
        ans.put(board.get(row).get(col), board.get(row).get(col));
      }
    }
    return ans;
  }

  // sorts the given list by weight of the edge
  // least weight at the beginning of the list
  LinkedList<Edge> sortByWeight(ArrayList<Edge> edges) {
    LinkedList<Edge> ans = new LinkedList<Edge>();
    for (Edge e : edges) {
      this.insertEdge(e, ans);
    }
    this.insertEdge(ans.removeLast(), ans);
    return ans;
  }

  // inserts the edge at the correct pace in the list of edges
  void insertEdge(Edge e, LinkedList<Edge> list) {
    if (list.size() == 0) {
      list.add(e);
    }
    else {
      int i = 0;
      while (list.get(i).weight < e.weight && i < list.size() - 1) {
        i ++;
      }
      list.add(i, e);
    }
  }

  // does the given hashmap contain only one "tree"
  // i.e. does every GamePiece in the hashmap have the same representitive
  boolean oneTree(HashMap<GamePiece, GamePiece> hash, 
      ArrayList<ArrayList<GamePiece>> board) {
    boolean ans = true;
    GamePiece initialrep = this.findRep(board.get(0).get(0), hash);
    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(0).size() ; col++) {
        if (this.findRep(board.get(row).get(col),
            hash).equalGP(initialrep)) {
          ans = true;
        }
        else {
          return false; 
        }
      }
    }
    return ans;
  }


  // finds the representative of the given GamePiece in the hash map
  GamePiece findRep(GamePiece gp, HashMap<GamePiece, GamePiece> hash) {
    GamePiece ans = hash.get(gp);
    if (gp.equalGP(ans)) {
      return ans;
    }
    return findRep(ans, hash);
  }

  // makes the representatives of the given gamepieces the same in the hashmap
  void union(HashMap<GamePiece, GamePiece> hash,
      GamePiece toNode, GamePiece fromNode) {
    hash.remove(fromNode);
    hash.put(fromNode, toNode);

  }

  // makes a random minimum spanning tree of edges for a board of the given width and height
  // by first creating a list of all possible edges of random wieghts
  ArrayList<Edge> makeMST(int width, int height) {
    ArrayList<ArrayList<GamePiece>> board = this.makeBlankBoard(width, height);
    ArrayList<Edge> allEdges = this.makeAllEdges(width, height, board);
    HashMap<GamePiece, GamePiece> reps = this.makeEachRep(board);
    LinkedList<Edge> workList = this.sortByWeight(allEdges);
    ArrayList<Edge> ans = new ArrayList<Edge>();

    while (! this.oneTree(reps, board)) {
      Edge curr = workList.remove();
      if (! this.findRep(curr.toNode, reps).equalGP(this.findRep(curr.fromNode, reps))) {
        ans.add(curr);
        this.union(reps, this.findRep(curr.toNode, reps), 
            this.findRep(curr.fromNode, reps));
      }
    }
    return ans;    
  }

  // same as makeMST but with given random for testing
  ArrayList<Edge> makeMSTTest(int width, int height, Random rand) {
    ArrayList<ArrayList<GamePiece>> board = this.makeBlankBoard(width, height);
    ArrayList<Edge> allEdges = this.makeAllEdgesTester(width, height, board, rand);
    HashMap<GamePiece, GamePiece> reps = this.makeEachRep(board);
    LinkedList<Edge> workList = this.sortByWeight(allEdges);
    ArrayList<Edge> ans = new ArrayList<Edge>();

    while (! this.oneTree(reps, board)) {
      Edge curr = workList.remove();
      if (! this.findRep(curr.toNode, reps)
          .equalGP(this.findRep(curr.fromNode, reps))) {
        ans.add(curr);
        this.union(reps, this.findRep(curr.toNode, reps), 
            this.findRep(curr.fromNode, reps));
      }
    }
    return ans;    
  }

  // makes the appropriate board for the given list of edges
  ArrayList<ArrayList<GamePiece>> makeMSTBoard(ArrayList<Edge> edges,
      int width, int height) {
    ArrayList<ArrayList<GamePiece>> ans = this.makeBlankBoard(width, height);
    for (Edge e : edges) {
      if (e.fromNode.row == e.toNode.row) { // fromNode is to the left of toNode
        ans.get(e.fromNode.row).get(e.fromNode.col).right = true;
        ans.get(e.toNode.row).get(e.toNode.col).left = true;
      }
      else { //fromNode is on top of toNode
        ans.get(e.fromNode.row).get(e.fromNode.col).bottom = true;
        ans.get(e.toNode.row).get(e.toNode.col).top = true;
      }
    }
    return ans;
  }

}