package entities;

public class Queen extends Piece{

	public Queen(FACTION_COLOR color, int xLoc, int yLoc)
	{
		super(Piece.PIECE.QUEEN, color);
		moveTo(xLoc, yLoc);
		setImage(getName(this));
	}
	
	@Override
	public boolean set_location(int x, int y)
	{
		//ROOK MOVES--------------------------------------------------------------------------------------------------------------------------
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
		//ROOK MOVES--------------------------------------------------------------------------------------------------------------------------
		
		//BISHOP MOVES--------------------------------------------------------------------------------------------------------------------------
		if ((this.getX_location() - x) == 0)
		{
			return false; // to prevent division by zero when calculating slope
		}
		double slope = (double)(this.getY_location() - y)/(double)(this.getX_location() - x);
		if (Math.abs(slope) != 1)
		{
			return false;
		}
		if (slope == -1 && isValidLeftDiagonalMove(x, y) == true)
		{
			return true;
		}
		if (slope == 1 && isValidRightDiagonalMove(x, y) == true)
		{
			return true;
		}
		//BISHOP MOVES--------------------------------------------------------------------------------------------------------------------------
		return false;
	}
	@Override
	public void threatControl(boolean debugging)
	{
		removeTreaths(debugging);
		isVerticalPathClear(0, true); //works fine on its own //rook to rook interaction does not work. and yet rook to any other piece works just fine. why?
		isVerticalPathClear(7, false); //works fine on its own
		isHorizontalPathClear(0, true); //works fine on its own
		isHorizontalPathClear(7, false); //works fine on its own
		
		int xLoc_of_yInt = 0;
		int yLoc_of_yInt = this.getY_location() - this.getX_location();
		if (yLoc_of_yInt < 0)
		{
			xLoc_of_yInt = -1*yLoc_of_yInt;
			yLoc_of_yInt = 0;
		}
		isRightDiagonalPathClear(xLoc_of_yInt, yLoc_of_yInt);

		xLoc_of_yInt = 7 - xLoc_of_yInt;
		yLoc_of_yInt = 7 - yLoc_of_yInt;
		isRightDiagonalPathClear(yLoc_of_yInt, xLoc_of_yInt);

		yLoc_of_yInt = this.getY_location() - this.getX_location();
		xLoc_of_yInt = 2*(this.getY_location() - yLoc_of_yInt) + yLoc_of_yInt;
		yLoc_of_yInt = 0;
		if (xLoc_of_yInt > 7)
		{
			yLoc_of_yInt = xLoc_of_yInt - 7;
			xLoc_of_yInt = 7;
		}
		isLeftDiagonalPathClear(xLoc_of_yInt, yLoc_of_yInt);
		isLeftDiagonalPathClear(yLoc_of_yInt, xLoc_of_yInt);
	}
	
	//ROOK MOVES--------------------------------------------------------------------------------------------------------------------------
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
	
	private Piece isHorizontalPathClear(int x, boolean direction)// true for vertical up, false for vertical down
	{
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
	//ROOK MOVES--------------------------------------------------------------------------------------------------------------------------
	
	
	//BISHOP MOVES--------------------------------------------------------------------------------------------------------------------------
	private boolean isValidLeftDiagonalMove(int x, int y)
	{
		if (isLeftDiagonalPathClear(x,y) == null)
		{
			if (Board.isTherePieceAt(x, y) == null)
			{
				moveTo(x,y);
				return true;
			}
			else if (Board.isTherePieceAt(x, y) != null && Board.isTherePieceAt(x, y).getFaction_color() != this.getFaction_color())
			{
				Board.KILL(Board.isTherePieceAt(x, y));
				moveTo(x,y);
				return true;
			}
		}
		return false;
	}
	private boolean isValidRightDiagonalMove(int x, int y)
	{
		if (isRightDiagonalPathClear(x,y) == null)
		{
			if (Board.isTherePieceAt(x, y) == null)
			{
				moveTo(x,y);
				return true;
			}
			else if (Board.isTherePieceAt(x, y) != null && Board.isTherePieceAt(x, y).getFaction_color() != this.getFaction_color())
			{
				Board.KILL(Board.isTherePieceAt(x, y));
				moveTo(x,y);
				return true;
			}
		}
		return false;
	}


	private Piece isLeftDiagonalPathClear(int x, int y)
	{
		int checkX;
		int checkY;
		if (x < this.getX_location())
		{
			checkX = this.getX_location() -1;
			checkY = this.getY_location() +1;
			while(checkX > x)
			{
				setTreaths(checkX, checkY);
				if (Board.isTherePieceAt(checkX, checkY) != null)
				{
					return Board.isTherePieceAt(checkX, checkY);
				}
				checkX--;
				checkY++;
			}
			if ((checkX >= 0 && checkX <= 7) && (checkY >= 0 && checkY <= 7))
			{
				setTreaths(checkX, checkY);
			}
			return null;
		}
		if (x > this.getX_location())
		{
			checkX = this.getX_location() +1;
			checkY = this.getY_location() -1;
			while(checkX < x)
			{
				setTreaths(checkX, checkY);
				if (Board.isTherePieceAt(checkX, checkY) != null)
				{
					return Board.isTherePieceAt(checkX, checkY);
				}
				checkX++;
				checkY--;
			}
			if ((checkX >= 0 && checkX <= 7) && (checkY >= 0 && checkY <= 7))
			{
				setTreaths(checkX, checkY);
			}
			return null;
		}
		return null;
	}

	private Piece isRightDiagonalPathClear(int x, int y)
	{
		int checkX;
		int checkY;
		if (x < this.getX_location())
		{
			checkX = this.getX_location() -1;
			checkY = this.getY_location() -1;
			while(checkX > x)
			{
				setTreaths(checkX, checkY);
				if (Board.isTherePieceAt(checkX, checkY) != null)
				{
					return Board.isTherePieceAt(checkX, checkY);
				}
				checkX--;
				checkY--;
			}
			if ((checkX >= 0 && checkX <= 7) && (checkY >= 0 && checkY <= 7))
			{
				setTreaths(checkX, checkY);
			}
			return null;
		}
		if (x > this.getX_location())
		{
			checkX = this.getX_location() +1;
			checkY = this.getY_location() +1;
			while(checkX < x)
			{
				setTreaths(checkX, checkY);
				if (Board.isTherePieceAt(checkX, checkY) != null)
				{
					return Board.isTherePieceAt(checkX, checkY);
				}
				checkX++;
				checkY++;
			}
			if ((checkX >= 0 && checkX <= 7) && (checkY >= 0 && checkY <= 7))
			{
				setTreaths(checkX, checkY);
			}
			return null;
		}
		return null;
	}
	//BISHOP MOVES--------------------------------------------------------------------------------------------------------------------------
}
