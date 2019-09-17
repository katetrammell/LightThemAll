import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javalib.worldimages.*;

// a class for GamePieces
class GamePiece {
  // in logical coordinates, with the origin
  // at the top-left corner of the screen
  int row;
  int col;
  // whether this GamePiece is connected to the
  // adjacent left, right, top, or bottom pieces
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  // whether the power station is on this piece
  boolean powerStation;
  boolean lit;
  //neighbors
  boolean toTheLeft;
  boolean toTheRight;
  boolean toTheTop;
  boolean toTheBottom;
  // ArrayList<Edge> outEdges;

  GamePiece(int row, int col) {
    this.row = row;
    this.col = col;
    this.left = false;
    this.right = false;
    this.top = false;
    this.bottom = false;
    this.powerStation = false;
    this.lit = true;
    this.toTheLeft = false;
    this.toTheRight = false;
    this.toTheTop = false;
    this.toTheBottom = false;
  }

  GamePiece(int row, int col, boolean left, boolean right,
      boolean top, boolean bottom, boolean powerStation) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.powerStation = powerStation;
    this.lit = true;
    this.toTheLeft = false;
    this.toTheRight = false;
    this.toTheTop = false;
    this.toTheBottom = false;
  }

  GamePiece(int row, int col, boolean left, boolean right,
      boolean top, boolean bottom, boolean powerStation,
      boolean toTheLeft, boolean toTheRight, boolean toTheTop, boolean toTheBottom) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.powerStation = powerStation;
    this.lit = true;
    this.toTheLeft = toTheLeft;
    this.toTheRight = toTheRight;
    this.toTheTop = toTheTop;
    this.toTheBottom = toTheBottom;
  }

  //draws the board of the game and renders and image of it
  WorldImage draw(int gamePieceSize, ArrayList<ArrayList<GamePiece>> board, 
      int powRow, int powCol, int radius) {
    Color edgeColor = Color.white;
    ArrayList<Color> gradient = new ArrayList<Color>(Arrays.asList(
        new Color(247, 255, 0), new Color(239, 216, 13), 
        new Color(232, 187, 10), new Color(228, 147, 8), new  Color(242, 123, 4)));
    int distance = new Util().findLongestPath(board, this, board.get(powRow).get(powCol));

    if (this.lit) {
      double len = Math.ceil(radius / 5.0);
      int colorIndex = (int) Math.floor(distance / len);
      if (colorIndex == 5) {
        colorIndex = 4;
      }
      edgeColor = gradient.get(colorIndex); 
    }

    WorldImage base = new RectangleImage(gamePieceSize,
        gamePieceSize, OutlineMode.SOLID, Color.LIGHT_GRAY);
    if (this.left) {
      WorldImage rect = new RectangleImage(gamePieceSize / 2, gamePieceSize / 10,
          OutlineMode.SOLID, edgeColor).movePinhole(10 , 0);
      base = new OverlayImage(rect, base);
    }
    if (this.right) {
      WorldImage rect = new RectangleImage(gamePieceSize / 2, gamePieceSize / 10,
          OutlineMode.SOLID, edgeColor).movePinhole(-10, 0);
      base = new OverlayImage(rect, base);
    }
    if (this.top) {
      WorldImage rect = new RectangleImage(gamePieceSize / 10, gamePieceSize / 2,
          OutlineMode.SOLID, edgeColor).movePinhole(0,10);
      base = new OverlayImage(rect, base);
    }
    if (this.bottom) {
      WorldImage rect = new RectangleImage(gamePieceSize / 10, gamePieceSize / 2,
          OutlineMode.SOLID, edgeColor).movePinhole(0, -10);
      base = new OverlayImage(rect, base);
    }
    if (this.powerStation) {
      WorldImage star = new StarImage(gamePieceSize / 2, 5, OutlineMode.SOLID,
          Color.blue);
      base = new OverlayImage(star, base);
    }
    return base;
  }

  //EFFECT: updates the direction of the electric lines on this GamePiece
  //by rotating it counter clockwise
  void rotateCounter() {
    boolean oldTop = top;
    boolean oldBottom = bottom;
    boolean oldRight = right;
    boolean oldLeft = left;
    left = false;
    right = false;
    top = false;
    bottom = false;
    if (oldLeft) {
      bottom = true;
    }
    if (oldRight) {
      top = true;
    }
    if (oldTop) {
      left = true;
    }
    if (oldBottom) {
      right = true;
    }
  }

  //creates a new game piece with the give connections, signifying it is linked
  //with its respective neighboring GamePiece
  GamePiece duplicateWithConnections(boolean tleft, boolean tright,
      boolean ttop, boolean tbottom) {
    return new GamePiece(this.row, this.col, this.left, this.right, this.top,
        this.bottom, this.powerStation, tleft, tright, ttop, tbottom);
  }

  //determines if this GamePiece is connected to the GamePiece that has the powerStation
  boolean isConnectedToPS(ArrayList<ArrayList<GamePiece>> board, 
      ArrayList<GamePiece> alreadyVisited, 
      int powRow, int powCol) {
    boolean connectedLeft = false;
    boolean connectedRight = false;
    boolean connectedTop = false;
    boolean connectedBottom = false;

    if (this.powerStation) {
      return true; }
    if (this.toTheLeft) {
      if (! alreadyVisited.contains(board.get(this.row).get(this.col - 1))) {
        if (board.get(this.row).get(this.col - 1).powerStation) {
          return true;
        } else {
          alreadyVisited.add(board.get(this.row).get(this.col - 1));
          connectedLeft = board.get(this.row).get(this.col - 1)
              .isConnectedToPS(board, alreadyVisited, powRow, powCol);
        }
      }
    }
    if (this.toTheRight) {
      if (! alreadyVisited.contains(board.get(this.row).get(this.col + 1))) {
        if (board.get(this.row).get(this.col + 1).powerStation) {
          return true;
        } else {
          alreadyVisited.add(board.get(this.row).get(this.col + 1));
          connectedRight = board.get(this.row).get(this.col + 1)
              .isConnectedToPS(board, alreadyVisited, powRow, powCol);
        }
      }
    }
    if (this.toTheTop) {
      if (! alreadyVisited.contains(board.get(this.row - 1).get(this.col))) {
        if (board.get(this.row - 1).get(this.col).powerStation) {
          return true;
        } else {
          alreadyVisited.add(board.get(this.row - 1).get(this.col));
          connectedTop = board.get(this.row - 1).get(this.col)
              .isConnectedToPS(board, alreadyVisited, powRow, powCol);
        }
      }
    }
    if (this.toTheBottom) {
      if (! alreadyVisited.contains(board.get(this.row + 1).get(this.col))) {
        if (board.get(this.row + 1).get(this.col).powerStation) {
          return true;
        } else {
          alreadyVisited.add(board.get(this.row + 1).get(this.col));
          connectedBottom = board.get(this.row + 1)
              .get(this.col).isConnectedToPS(board, alreadyVisited, powRow, powCol);
        }
      }
    }
    return (connectedLeft || connectedRight || connectedTop || connectedBottom);


  }

  // is this GamePiece equal to that one?
  // test equality using only row and col because this function
  // is only tested on the same board and if the row and col are equal
  // and the GamePieces are on the same board then they shoudl be the same
  boolean equalGP(GamePiece that) {
    return this.row == that.row && this.col == that.col;
  }

}