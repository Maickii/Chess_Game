package entities;

public class Rook extends Piece{

	public Rook(FACTION_COLOR color, int xLoc, int yLoc)
	{
		super(Piece.PIECE.ROOK, color);
		moveTo(xLoc, yLoc);
		setImage(getName(this));
		
	}

	@Override
	public boolean set_location(int x, int y)
	{
		if (y < this.getY_location() && isValidVerticalMove(x, y, true) == true)
		{
			return true;
		}
		if (y > this.getY_location() && isValidVerticalMove(x, y, false) == true)
		{
			return true;
		}
		if (x < this.getX_location() && isValidHorizontalMove(x, y, true) == true)
		{
			return true;
		}
		if (x > this.getX_location() && isValidHorizontalMove(x, y, false) == true)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void threatControl(boolean debugging)
	{
		this.debugging = 0;
		
		
		removeTreaths(debugging);
		
		
		isVerticalPathClear(0, true);
		isVerticalPathClear(7, false);
//		System.out.println(this.debugging + "----------------");
		isHorizontalPathClear(0, true);
//		System.out.println(this.debugging+ "----------------");
		isHorizontalPathClear(7, false);
//		System.out.println(this.debugging+ "----------------");
		
	}

	/**
	 * forward as in vertical up.
	 * this method assumes the given y is always in a forward direction
	 * this method uses this piece's x value
	 * when its just one tile forward returns null
	 * NOTE: this checks tiles between the given piece up to but not including the given point(x,y)
	 * whether that point is a valid move is delegated to some other method.
	 * @return null when path is clear, or the first piece blocking the path
	 */
	private Piece isVerticalPathClear(int y, boolean direction)// true for vertical up, false for vertical down
	{
		int incDec = 0;
		int checkY;
		if (direction == false)
		{
			incDec = 1;
		}
		if (direction == true)
		{
			incDec = -1;
		}
		checkY = this.getY_location() + incDec;
		while (checkY != y && (checkY >= 0 && checkY <= 7))
		{
			setTreaths(this.getX_location(), checkY);
			if (Board.isTherePieceAt(this.getX_location(), checkY) != null)
			{
				return Board.isTherePieceAt(this.getX_location(), checkY);
			}
			if (direction == false)
			{
				checkY++;
			}
			else if (direction == true)
			{
				checkY--;
			}
		}
		if ((checkY >= 0 && checkY <= 7))
		{
			setTreaths(this.getX_location(), checkY);
		}
		return null;
	}
	
	int debugging = 0;
	private Piece isHorizontalPathClear(int x, boolean direction)// true for vertical up, false for vertical down
	{
//		System.out.println("here");
		int incDec = 0;
		int checkX;
		if (direction == false)
		{
			incDec = 1;
		}
		if (direction == true)
		{
			incDec = -1;
		}
		checkX = this.getX_location() + incDec;
		while (checkX != x && (checkX >= 0 && checkX <= 7))
		{
			if (Board.isTherePieceAt(checkX, this.getY_location()) != null && Board.isTherePieceAt(checkX, this.getY_location()).getPiece() == Piece.PIECE.KING)
			{
				System.out.println(debugging + " there is a "+Board.isTherePieceAt(checkX, this.getY_location()));	
				debugging++;
			}
			setTreaths(checkX, this.getY_location());
			if (Board.isTherePieceAt(checkX, this.getY_location()) != null)
			{
				return Board.isTherePieceAt(checkX, this.getY_location());
			}
			if (direction == false)
			{
				checkX++;
			}
			else if (direction == true)
			{
				checkX--;
			}
		}
		if ((checkX >= 0 && checkX <= 7))
		{
			setTreaths(checkX, this.getY_location());
		}
		return null;
	}

	// returns true when succesffuly moved forward
	private boolean isValidVerticalMove(int x, int y, boolean direction)
	{
		if (x == this.getX_location() && takeAction(isVerticalPathClear(y, direction), x, y) == true)
		{
			return true;
		}
		return false;
	}
	private boolean isValidHorizontalMove(int x, int y, boolean direction)
	{
		if (y == this.getY_location() && takeAction(isHorizontalPathClear(x, direction), x, y) == true)
		{
			return true;
		}
		return false;
	}
	
	private boolean takeAction(Piece isPathClear, int x, int y)
	{
		if (isPathClear == null) // is forward path is clear?
		{
			if (Board.isTherePieceAt(this.getX_location(), y) == null)
			{
				moveTo(x,y);
				return true;
			}
			else if (Board.isTherePieceAt(this.getX_location(), y) != null && Board.isTherePieceAt(this.getX_location(), y).getFaction_color() != this.getFaction_color())
			{
				Board.KILL(Board.isTherePieceAt(this.getX_location(), y));
				moveTo(x,y);
				return true;
			}
			else if (Board.isTherePieceAt(x, this.getY_location()) == null)
			{
				moveTo(x,y);
				return true;
			}
			else if (Board.isTherePieceAt(x, this.getY_location()) != null && Board.isTherePieceAt(x, this.getY_location()).getFaction_color() != this.getFaction_color())
			{
				Board.KILL(Board.isTherePieceAt(x, this.getY_location()));
				moveTo(x,y);
				return true;
			}
		}
		return false;
	}
}
