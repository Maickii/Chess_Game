package starter;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class MouseInput extends JComponent implements MouseListener {

	public Point getTileClicked()
	{
		return null;
	}
	private Point mouseClicked;
    @Override
    public void mouseClicked(MouseEvent arg0) {
//        System.out.println(arg0.getX()+ "," + arg0.getY());
        mouseClicked = new Point((arg0.getX()-8)/100,(arg0.getY()-30)/100);
//        System.out.println(mouseClicked);
        entities.Board.performAction((int)mouseClicked.getX(),(int)mouseClicked.getY());
        //System.out.println(Board.getTiles()[mouseClicked.x][mouseClicked.y].getPlaced_on_top().getName(Board.getTiles()[mouseClicked.x][mouseClicked.y].getPlaced_on_top()));
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}