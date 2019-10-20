package model.biz;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * 鼠标拖动事件响应
 * 
 * @author KumaHime
 * 
 */
public class dragHandleBiz extends MouseAdapter {
	private JFrame mainFrame;
	
	private Point pressPoint = new Point();
	
	public dragHandleBiz(JFrame jf){
		mainFrame = jf;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressPoint = new Point();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mainFrame.setLocation(mainFrame.getLocation().x + (e.getX() - pressPoint.x),
				mainFrame.getLocation().y + (e.getY() - pressPoint.y));

	}
}
