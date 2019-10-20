package model.biz;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.html.MinimalHTMLWriter;

import comondata.comondata;

public class BtnMouseMotionHandleBiz implements MouseListener {
	private String command;
	private JButton mainbtn;
	private JFrame mainjf = null;

	public BtnMouseMotionHandleBiz(JFrame jf, JButton jb, String cmd) {
		mainjf = jf;
		mainbtn = jb;
		command = cmd;
	}

	public BtnMouseMotionHandleBiz(JButton jb, String cmd) {
		command = cmd;
		mainbtn = jb;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (mainjf != null) {
			switch (command) {
			case "close":
				System.exit(0);
				break;
			case "minimization":
				mainjf.setExtendedState(JFrame.ICONIFIED);
				break;
			default:
				break;
			}
		}
		else{
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mainbtn.setContentAreaFilled(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mainbtn.setContentAreaFilled(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
