package starter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class LoadImages extends JPanel{

	private int tile_Width = 100;
	private int tile_Height = 100;


	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//			    RenderingHints.VALUE_ANTIALIAS_ON);
		drawTiles(g2);

		// draw Line2D.Double
		for (int index = 0; index < 32; index++)
		{

			if (entities.Board.getPieces()[index] != null && entities.Board.getPieces()[index].getImg() != null)
			{
				g2.drawImage(entities.Board.getPieces()[index].getImg(), entities.Board.getPieces()[index].getX_location()*100, entities.Board.getPieces()[index].getY_location()* 100,100,100, null);
			}
		}

	}

	public void drawTiles(Graphics2D g2)
	{
		for (int tile_Loc_Y = 0; tile_Loc_Y < 8; tile_Loc_Y++)
		{
			for (int tile_Loc_X = 0; tile_Loc_X < 8; tile_Loc_X++)
			{
				g2.setColor(entities.Board.getTiles()[tile_Loc_X][tile_Loc_Y].getDisplayedColor());
				g2.fill(new Rectangle(tile_Loc_X*100, tile_Loc_Y*100, tile_Width, tile_Height));
			}
		}


	}

}
