package entities;

public class Knight extends Piece{

	public Knight(FACTION_COLOR color, int xLoc, int yLoc)
	{
		super(Piece.PIECE.KNIGHT, color);
		moveTo(xLoc, yLoc);
		setImage(getName(this));
	}
	
	@Override
	public boolean set_location(int x, int y)
	{
		Piece holder;
		int checkX = Math.abs(this.getX_location() -x);
		int checkY = Math.abs(this.getY_location() -y);
		if (checkX*checkY != 2){
			return false;
		}
		holder = Board.isTherePieceAt(x, y);
		if(holder != null && holder.getFaction_color() != this.getFaction_color())
		{
			Board.KILL(holder);
			moveTo(x,y);
			return true;
		}
		if(holder == null)
		{
			moveTo(x,y);
			return true;
		}
		return false;
	}
	@Override
	public void threatControl(boolean debugging)
	{
		removeTreaths(debugging);
		int checkX;
		int checkY;
		
		for (int relativeX = -2; relativeX <=2; relativeX++)
		{
			for(int relativeY = -2; relativeY <=2; relativeY++)
			{
				checkX = this.getX_location() - relativeX;
				checkY = this.getY_location() - relativeY;
				if (Math.abs(relativeX*relativeY) == 2 && (checkX >= 0 && checkX <= 7) && (checkY >= 0 && checkY <= 7))
				{
					setTreaths(checkX, checkY);
				}
			}
		}
		
		
	}
}
