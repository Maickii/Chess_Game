package entities;
/**
 * extend your arms out to make an X in front of you.
 * your left arm makes the left diagonal and your right
 * arm makes the right diagonal.
 * @return
 */
public class Bishop extends Piece{

	public Bishop(FACTION_COLOR color, int xLoc, int yLoc)
	{
		super(Piece.PIECE.BISHOP, color);
		moveTo(xLoc, yLoc);
		setImage(getName(this));
	}

	@Override
	public boolean set_location(int x, int y)
	{
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
		return false;
	}
	@Override
	public void threatControl(boolean debugging)
	{
		removeTreaths(debugging);
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
}
