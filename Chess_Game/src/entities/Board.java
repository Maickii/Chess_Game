package entities;

import java.awt.Color;


public class Board {


	private static Piece[] pieces = new Piece[32];
	private static Tile[][] tiles= new Tile[8][8];
	private static Piece selected_piece = null;
	private static int selectedTileX = -1;
	private static int selectedTileY = -1;
	private static enum turns {black, white}// true for white, false for black
	private static turns turn = turns.white;// true for white, false for black
	private static int whiteKingIndex = 30;
	private static int blackKingIndex = 31;

	public Board()
	{
		init_tiles();
		init_pieces();
		set_pieces_on_tiles();
		set_threats();

	}

	private void init_pieces()
	{
		int index = 0;
		for (int blackPawns = 0; blackPawns < 8; blackPawns++,index++)
		{
			pieces[index] = new Pawn(Piece.FACTION_COLOR.BLACK, blackPawns, 1);
		}
		for (int whitePawns = 0; whitePawns < 8; whitePawns++,index++)
		{
			pieces[index] = new Pawn(Piece.FACTION_COLOR.WHITE, whitePawns, 6);
		}


		pieces[index++] = new Queen(Piece.FACTION_COLOR.WHITE,3,7);
		pieces[index++] = new Queen(Piece.FACTION_COLOR.BLACK,3,0);

		pieces[index++] = new Bishop(Piece.FACTION_COLOR.WHITE,2,7);
		pieces[index++] = new Bishop(Piece.FACTION_COLOR.WHITE,5,7);
		pieces[index++] = new Bishop(Piece.FACTION_COLOR.BLACK,2,0);
		pieces[index++] = new Bishop(Piece.FACTION_COLOR.BLACK,5,0);

		pieces[index++] = new Knight(Piece.FACTION_COLOR.WHITE,1,7);
		pieces[index++] = new Knight(Piece.FACTION_COLOR.WHITE,6,7);
		pieces[index++] = new Knight(Piece.FACTION_COLOR.BLACK,1,0);
		pieces[index++] = new Knight(Piece.FACTION_COLOR.BLACK,6,0);

		pieces[index++] = new Rook(Piece.FACTION_COLOR.WHITE,0,7);
		pieces[index++] = new Rook(Piece.FACTION_COLOR.WHITE,7,7);
		pieces[index++] = new Rook(Piece.FACTION_COLOR.BLACK,0,0);
		pieces[index++] = new Rook(Piece.FACTION_COLOR.BLACK,7,0);
		
		pieces[whiteKingIndex] = new King(Piece.FACTION_COLOR.WHITE,4,7);
		pieces[blackKingIndex] = new King(Piece.FACTION_COLOR.BLACK,4,0);
	}
	private void init_tiles()
	{
		Color selected = Color.CYAN;
		boolean toggleColor = true; // true for white
		for (int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				tiles[x][y] = new Tile();
				tiles[x][y].setXY(x, y);
				tiles[x][y].setSelectedColor(selected);
				if (toggleColor == true)
				{
					tiles[x][y].setDefaultColor(Color.LIGHT_GRAY);
					tiles[x][y].setDisplayedColor(Color.LIGHT_GRAY);
				}
				else if (toggleColor == false)
				{
					tiles[x][y].setDefaultColor(Color.DARK_GRAY);
					tiles[x][y].setDisplayedColor(Color.DARK_GRAY);
				}
				toggleColor = !toggleColor;
			}
			toggleColor = !toggleColor;
		}
	}

	private void set_pieces_on_tiles()
	{
		for (int index = 0; index < pieces.length; index++)
		{
			if (pieces[index] != null)
			{
				tiles[pieces[index].getX_location()][pieces[index].getY_location()].setPlaced_on_top(pieces[index]);
			}
		}
	}
	private void set_threats()
	{
		for (int index = 0; index < pieces.length; index++)
		{
			if (pieces[index] != null)
			{
				pieces[index].threatControl(false);
			}
		}
	}



	public static Tile[][] getTiles() {
		return tiles;
	}

	public static Piece[] getPieces() {
		return pieces;
	}

	//returns the piece that is at that location, or
	//returns null if nothing is there.
	public static Piece isTherePieceAt(int x, int y)
	{
		return tiles[x][y].getPlaced_on_top();
	}

	public static void performAction(int x, int y)
	{
		starter.Main.update(true);
		// if you just clicked on a piece, and there is no piece currently selected
		if (isTherePieceAt(x, y) != null && selected_piece == null)
		{
			//if you clicked on a piece that is your color depending on your turn
			if (turn.ordinal() == isTherePieceAt(x, y).getFaction_color().ordinal())
			{
				selected_piece = isTherePieceAt(x, y);
				viewThreats(isTherePieceAt(x, y));
				tiles[x][y].setSelected(true);
				selectedTileX = x;
				selectedTileY = y;
				starter.Main.update(true);
			}

			return;
		}
		if (selected_piece != null)
		{
			int x_temp = selected_piece.getX_location();
			int y_temp = selected_piece.getY_location();
			boolean moved = selected_piece.set_location(x, y);
			if (moved == true)
			{
				tiles[x_temp][y_temp].setPlaced_on_top(null);
				tiles[selected_piece.getX_location()][selected_piece.getY_location()].setPlaced_on_top(findMatchingPiece(selected_piece));
				updateThreatsOf(x_temp, y_temp, false);
				selected_piece.threatControl(true);
				updateThreatsOf(x, y, false);
				toggleTurn();
			}
			if (moved && selected_piece.getFaction_color() == Piece.FACTION_COLOR.WHITE && ((King)pieces[whiteKingIndex]).isKingThreatened() > 0)
			{
				System.out.println("the piece was not moved and the king is still threathed");
				selected_piece.moveTo(x_temp, y_temp);
				tiles[x][y].setPlaced_on_top(null);
				tiles[selected_piece.getX_location()][selected_piece.getY_location()].setPlaced_on_top(findMatchingPiece(selected_piece));
				updateThreatsOf(x, y, false);
				updateThreatsOf(x_temp, y_temp, false);
				selected_piece.threatControl(true);
				toggleTurn();
				selected_piece = null;
				viewThreats(isTherePieceAt(x, y));
			}
		}
		selected_piece = null;
		if (isTherePieceAt(x, y) != null)
		{
			//			isTherePieceAt(x, y).viewThreats();
//			viewThreats(isTherePieceAt(x, y));
		}
		starter.Main.update(true);
	}

	private static Piece findMatchingPiece(Piece piece)
	{
		for (int index = 0; index < pieces.length; index++)
		{
			if (piece == pieces[index])
			{
				return pieces[index];
			}
		}
		return null;
	}
	private static int findIndexOfMatchingPiece(Piece piece)
	{
		for (int index = 0; index < pieces.length; index++)
		{
			if (piece == pieces[index])
			{
				return index;
			}
		}
		return -1;
	}

	public static void KILL(Piece piece)
	{
		tiles[piece.getX_location()][piece.getY_location()].setPlaced_on_top(null);
		piece.removeTreaths(false);
		pieces[findIndexOfMatchingPiece(piece)] = null;
	}

	private static void toggleTurn()
	{
		if (turn == turns.white)
		{
			turn = turns.black;
		}
		else if (turn == turns.black)
		{
			turn = turns.white;
		}
	}
	public static void setThreat(int x, int y, Piece piece)
	{
		tiles[x][y].addThreat(piece);
	}
	public static boolean removeThreat(int x, int y, Piece piece, boolean debugging)
	{

		if (tiles[x][y].removeThreat(piece, debugging))
		{
			return true;
		}
		return false;
		// this fails the 14 time it runs
	}
	static int debuggingIndex = 0;
	public static void viewThreats(Piece piece)
	{
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (tiles[x][y].isThreat(piece))
				{
					tiles[x][y].displayThreat(true);
				}else
				{
					tiles[x][y].displayThreat(false);
				}

			}
		}
	}
	
	/**
	 * this really just updates the tiles and calls the tiles threats to update
	 * @param x
	 * @param y
	 * @param debugging
	 */
	public static void updateThreatsOf(int x, int y, boolean debugging)
	{
		int index =0;
		Piece temp;
		if (debugging)
		{
			printContentOfthreats(x, y);
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		}
		for (index = 0; index < tiles[x][y].getThreats().size(); index++)
		{
			if (debugging)
			{
				System.out.println();
				System.out.println();
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				printContentOfthreats(x, y);
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println();
				System.out.println();
			}
			temp = tiles[x][y].getThreats().get(index);
			tiles[x][y].getThreats().get(index).threatControl(true);
			if (index < tiles[x][y].getThreats().size() && !tiles[x][y].isThreat(temp))
			{
				index--;

			}
		}
		if (debugging)
		{
			printContentOfthreats(x, y);
		}
		starter.Main.update(true);
	}
	public static void printContentOfthreats(int x, int y)
	{
		for (int index = 0; index < tiles[x][y].getThreats().size(); index++)
		{
			System.out.println(tiles[x][y].getThreats().get(index));
		}
	}
	public static void addKingThreat(Piece piece)
	{
		if (piece.getFaction_color() == Piece.FACTION_COLOR.WHITE)
		{
			((King)pieces[blackKingIndex]).addThreat(piece);
		}
		else if (piece.getFaction_color() == Piece.FACTION_COLOR.BLACK)
		{
			((King)pieces[whiteKingIndex]).addThreat(piece);
		}
	}
	public static void removeKingThreat(Piece piece)
	{
		if (piece.getFaction_color() == Piece.FACTION_COLOR.WHITE)
		{
			((King)pieces[blackKingIndex]).removeThreat(piece, false);
		}
		else if (piece.getFaction_color() == Piece.FACTION_COLOR.BLACK)
		{
			((King)pieces[whiteKingIndex]).removeThreat(piece, false);
		}
	}

}
