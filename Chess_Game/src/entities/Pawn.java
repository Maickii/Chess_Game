package entities;


public class Pawn extends Piece{

	public Pawn(FACTION_COLOR color,int xLoc, int yLoc)
	{
		super(Piece.PIECE.PAWN, color);
		moveTo(xLoc, yLoc);
		setImage(getName(this));
	}
	
	//false when it fails to move. you are answering the question, did it move?
	@Override
	public boolean set_location(int x, int y)
	{
		
		//would moving cause the king to be on threat?
		
		if (isValidForwardMove(x, y) == true)
		{
			//extra condition when your king is on threat, return false when so, unless this move blocks the threat, if so, move and recalculate threat
			moveTo(x,y);
			return true;
		}
		else if (isValidEat(x, y) == true)
		{
			Board.KILL(Board.isTherePieceAt(x, y));
			moveTo(x,y);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void threatControl(boolean debugging)
	{
		removeTreaths(debugging);
		if (this.getFaction_color() == Piece.FACTION_COLOR.WHITE && this.getY_location() - 1 >= 0)
		{
			if (this.getX_location() - 1 >= 0)
			{
				setTreaths(this.getX_location() - 1, this.getY_location() - 1);
			}
			if (this.getX_location() + 1 <= 7)
			{
				setTreaths(this.getX_location() + 1, this.getY_location() - 1);
			}
		}
		if (this.getFaction_color() == Piece.FACTION_COLOR.BLACK && this.getY_location() + 1 <= 7)
		{
			if (this.getX_location() - 1 >= 0)
			{
				setTreaths(this.getX_location() - 1, this.getY_location() + 1);
			}
			if (this.getX_location() + 1 <= 7)
			{
				setTreaths(this.getX_location() + 1, this.getY_location() + 1);
			}
		}
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return returns true when the following conditions are true
	 * 		1) user clicked only "forward" by one or two and there are no obstacles in that direction and,
	 */
	private boolean isValidForwardMove(int x, int y)
	{
		int relative_Distance_X = this.getX_location()-x;
		int relative_Distance_Y = this.getY_location()-y;
		
		// FAILURE BOX -------------------------------------------------------------------------------------------------------------------------------------------
		// would moving cause the your your faction's king to be on threat?
		if (this.getFaction_color() == Piece.FACTION_COLOR.WHITE &&  relative_Distance_Y <= 0) // is it not forward for a white piece?
		{
			return false;
		}
		if (this.getFaction_color() == Piece.FACTION_COLOR.BLACK &&  relative_Distance_Y >= 0) // is it not forward for a black piece?
		{
			return false;
		}
		// FAILURE BOX -------------------------------------------------------------------------------------------------------------------------------------------
		
		
		
		// SUCCESS BOX -------------------------------------------------------------------------------------------------------------------------------------------
		if (relative_Distance_X == 0 && Math.abs(relative_Distance_Y) == 1 && Board.isTherePieceAt(x,y) == null) // are you moving up/down by one and is path not clear?
		{
			//extra condition when your king is on threat, return false when so, unless this move blocks the threat, if so, move and recalculate threat
			return true;
		}
		
		if (relative_Distance_X == 0 && Math.abs(relative_Distance_Y) == 2 && Board.isTherePieceAt(x,y) == null && 
				((this.getFaction_color() == Piece.FACTION_COLOR.WHITE && this.getY_location() == 6)	||
				(this.getFaction_color() == Piece.FACTION_COLOR.BLACK && this.getY_location()== 1)) ) // are you moving up/down by two, are you at starting position and is path clear?
		{
			//extra condition when your king is on threat, return false when so, unless this move blocks the threat, if so, move and recalculate threat
			return true;
		}
		// SUCCESS BOX -------------------------------------------------------------------------------------------------------------------------------------------
		
		return false;
	}
	private boolean isValidEat(int x, int y)
	{
		int relative_Distance_X = this.getX_location()-x;
		int relative_Distance_Y = this.getY_location()-y;
		if (Board.isTherePieceAt(x, y) == null)
		{
			return false;
		}
		// SUCCESS BOX -------------------------------------------------------------------------------------------------------------------------------------------
		if (this.getFaction_color() == Piece.FACTION_COLOR.WHITE && relative_Distance_Y == 1 && Math.abs(relative_Distance_X) == 1 && Board.isTherePieceAt(x, y).getFaction_color() != this.getFaction_color())
		{
			return true;
		}
		if (this.getFaction_color() == Piece.FACTION_COLOR.BLACK && relative_Distance_Y == -1 && Math.abs(relative_Distance_X) == 1 && Board.isTherePieceAt(x, y).getFaction_color() != this.getFaction_color())
		{
			return true;
		}
		// SUCCESS BOX -------------------------------------------------------------------------------------------------------------------------------------------
		return false;
	}
}
