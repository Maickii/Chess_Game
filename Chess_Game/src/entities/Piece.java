package entities;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Piece {
	
	public enum PIECE {PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING}
	public enum FACTION_COLOR {BLACK, WHITE}
	
	private PIECE piece;
	private FACTION_COLOR faction_color;
	private int x_location;
	private int y_location;
	private LinkedList<Point> threats = new LinkedList<Point>();
	
	private BufferedImage img = null;
	
	public BufferedImage getImg() {
		return img;
	}
	public Piece(PIECE piece, FACTION_COLOR color)
	{
		this.piece = piece;
		this.faction_color = color;
	}
	protected void moveTo(int x, int y)
	{
		this.x_location = x;
		this.y_location = y;
	}
	
	public void setImage(String name)
	{
		try {
		    img = ImageIO.read(new File("Images/"+ name+ ".png"));
		} catch (IOException e) {
		}
		if (img == null)
		{
			System.out.println("Sorry but the image was not opened correctly");
		}
	}
	
	public String getName(Piece piece)
	{
		if (piece.faction_color == FACTION_COLOR.WHITE)
		{
			if (piece.piece == PIECE.KING)
			{
				return "white_King";
			}
			else if(piece.piece == PIECE.QUEEN)
			{
				return "white_Queen";
			}
			else if(piece.piece == PIECE.ROOK)
			{
				return "white_Rook";
			}
			else if(piece.piece == PIECE.PAWN)
			{
				return "white_Pawn";
			}
			else if(piece.piece == PIECE.BISHOP)
			{
				return "white_Bishop";
			}
			else if(piece.piece == PIECE.KNIGHT)
			{
				return "white_Knight";
			}
		}
		else if (piece.faction_color == FACTION_COLOR.BLACK)
		{
			if (piece.piece == PIECE.KING)
			{
				return "black_King";
			}
			else if(piece.piece == PIECE.QUEEN)
			{
				return "black_Queen";
			}
			else if(piece.piece == PIECE.ROOK)
			{
				return "black_Rook";
			}
			else if(piece.piece == PIECE.PAWN)
			{
				return "black_Pawn";
			}
			else if(piece.piece == PIECE.BISHOP)
			{
				return "black_Bishop";
			}
			else if(piece.piece == PIECE.KNIGHT)
			{
				return "black_Knight";
			}
		}
		return null;
	}
	public PIECE getPiece() {
		return piece;
	}
	public FACTION_COLOR getFaction_color() {
		return faction_color;
	}
	public int getX_location() {
		return x_location;
	}
	public int getY_location() {
		return y_location;
	}
	public boolean set_location(int x, int y)
	{
		return false;
	}
	
	public void setTreaths(int threat_x, int threat_y)
	{
		if (Board.isTherePieceAt(x_location, y_location).getPiece() == Piece.PIECE.KING)
		{
//			System.out.println("trying to add threat to white king");
//			System.out.println(Board.isTherePieceAt(x_location, y_location));
//			System.out.println(Board.isTherePieceAt(x_location, y_location).getPiece() == Piece.PIECE.KING);
//			System.out.println(this);
		}
		if (Board.isTherePieceAt(x_location, y_location).getPiece() == Piece.PIECE.KING && 
			this.getFaction_color() != Board.isTherePieceAt(x_location, y_location).getFaction_color())
		{
				System.out.println(this + " is threatening king");
			Board.addKingThreat(this);
		}
		threats.add(new Point(threat_x,threat_y));
		Board.setThreat(threat_x, threat_y, Board.isTherePieceAt(x_location, y_location));
	}
	
	public void threatControl(boolean debugging)
	{
		
	}
	public void removeTreaths(boolean debugging)
	{
		
		for (int index = 0; index < threats.size(); index++)
		{
			Board.removeThreat(threats.get(index).x, threats.get(index).y, this, debugging);
		}
		for (int index = 0; index < threats.size();)
		{
			if (Board.getTiles()[threats.get(index).x][threats.get(index).y].getPlaced_on_top() != null &&
				Board.getTiles()[threats.get(index).x][threats.get(index).y].getPlaced_on_top().piece == Piece.PIECE.KING &&
				Board.getTiles()[threats.get(index).x][threats.get(index).y].getPlaced_on_top().getFaction_color() != this.getFaction_color())
			{
				Board.removeKingThreat(this);
			}
			threats.remove(index);
		}
		
		
		
	}
	int debuggingIndex = 0;
	public boolean equals(Piece piece)
	{
		if (piece == null)
		{
			return false;
		}
		if (this.faction_color == piece.faction_color &&
			this.piece == piece.piece &&
			this.x_location == piece.x_location &&
			this.y_location == piece.y_location)
		{
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
	    int hashCode = 1;

	    hashCode = hashCode * 37 + this.faction_color.hashCode();
	    hashCode = hashCode * 37 + this.piece.hashCode();
	    hashCode = hashCode * 37 + this.getX_location();
	    hashCode = hashCode * 37 + this.getY_location();

	    return hashCode;
	}
	@Override
	public String toString()
	{
		return faction_color +" "+ piece + " at (" + x_location + ", " + y_location + ")";
	}
	
	public void viewThreats()
	{
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (Board.getTiles()[x][y].isThreat(this))
				{
					System.out.print("X");
				}
				else 
				{
					System.out.print("O");
				}
			}
			System.out.println();
		}
	}
	
}
