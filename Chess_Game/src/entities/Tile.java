package entities;

import java.awt.Color;
import java.util.LinkedList;

public class Tile {
	private int x;
	private int y;
	private Piece placed_on_top;
	private LinkedList<Piece> threats = new LinkedList<Piece>();
	private boolean isSelected = false;
	private Color defaultColor;
	private Color selectedColor;
	private Color displayedColor;
	private Color displayThreat = Color.RED;
	
	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
	public void setDisplayedColor(Color displayedColor) {
		this.displayedColor = displayedColor;
	}
	
	public void displayThreat(boolean displayThreat)
	{
		if (displayThreat == true)
		{
			displayedColor = this.displayThreat;
		}
		else if (displayThreat == false)
		{
			displayedColor = defaultColor;
		}
	}
	
	
	public LinkedList<Piece> getThreats() {
		return threats;
	}
	public Color getDisplayedColor() {
		return displayedColor;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (this.isSelected == true)
		{
			displayedColor = selectedColor;
		}
		else if (this.isSelected == false)
		{
			displayedColor = defaultColor;
		}
	}
	public boolean isThreat(Piece piece) {
		for (int index = 0; index < this.threats.size(); index++)
		{
			if (threats.get(index) != null && piece.equals(threats.get(index)))
			{
				return true;
			}
		}
		return false;
	}
	public void addThreat(Piece threat) {
		for (int index = 0; index < threats.size(); index++)
		{
			if (threat.equals(threats.get(index)))
			{
				return;
			}
		}
		threats.addFirst(threat);
	}
	public boolean removeThreat(Piece threat, boolean debugging) {
		
		for (int index = 0; index < threats.size(); index++)
		{
			if (threat.equals(threats.get(index)))
			{
				threats.remove(index);
				return true;
			}
		}
		return false;
	}
	static int debuggingIndex = 0;
	public void setPlaced_on_top(Piece placed_on_top) {
		this.placed_on_top = placed_on_top;
	}
	public Piece getPlaced_on_top() {
		return placed_on_top;
	}
	public int getX() {
		return x;
	}
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getY() {
		return y;
	}
	
	
}
