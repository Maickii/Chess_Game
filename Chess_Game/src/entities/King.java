package entities;

import java.awt.Color;
import java.util.LinkedList;

public class King extends Piece{

	private LinkedList<Piece> threatsOnKing = new LinkedList<Piece>();
	public King(FACTION_COLOR color, int xLoc, int yLoc)
	{
		super(Piece.PIECE.KING, color);
		moveTo(xLoc, yLoc);
		setImage(getName(this));
	}
	
	@Override
	public boolean set_location(int x, int y)
	{
		Piece holder;
		int checkX = Math.abs(this.getX_location() -x);
		int checkY = Math.abs(this.getY_location() -y);
		if ((x == this.getX_location() && y == this.getY_location()) || checkX > 1 || checkY > 1){
			return false;
		}
		holder = Board.isTherePieceAt(x, y);
		if(holder != null && holder.getFaction_color() != this.getFaction_color() && isKingValidMove(x, y))
		{
			Board.KILL(holder);
			moveTo(x,y);
			return true;
		}
		if(holder == null && isKingValidMove(x, y))
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
		
		for (int relativeX = -1; relativeX <=1; relativeX++)
		{
			for(int relativeY = -1; relativeY <=1; relativeY++)
			{
				checkX = this.getX_location() - relativeX;
				checkY = this.getY_location() - relativeY;
				if ((checkX != this.getX_location() || checkY != this.getY_location()) && 
						Math.abs(relativeX) <= 1 && Math.abs(relativeY) <= 1 && 
						(checkX >= 0 && checkX <= 7) && (checkY >= 0 && checkY <= 7))
				{
					setTreaths(checkX, checkY);
				}
			}
		}
		
		
	}
	
	public boolean isKingValidMove(int x, int y)
	{
		int size = Board.getTiles()[x][y].getThreats().size();
		for (int index = 0; index < size; index++)
		{
			if (Board.getTiles()[x][y].getThreats().get(index).getFaction_color() != this.getFaction_color())
			{
				return false;
			}
		}
		return true;
	}
	
	public LinkedList<Piece> getThreats() {
		return threatsOnKing;
	}
	/**
	 * @return returns 0 when the king is not being threatened, 
	 * otherwise it returns the number of threats on the king.
	 */
	public int isKingThreatened() {
		return threatsOnKing.size();
	}
	public void addThreat(Piece threat) {
		for (int index = 0; index < threatsOnKing.size(); index++)
		{
			if (threat.equals(threatsOnKing.get(index)))
			{
				return;
			}
		}
//		System.out.println(this + "is threaten by " + threat);
		threatsOnKing.addFirst(threat);
	}
	public boolean removeThreat(Piece threat, boolean debugging) {
		
//		System.out.println("removing threat of " +threat);
		for (int index = 0; index < threatsOnKing.size(); index++)
		{
			if (threat.equals(threatsOnKing.get(index)))
			{
				threatsOnKing.remove(index);
				return true;
			}
		}
		return false;
	}
	
}
