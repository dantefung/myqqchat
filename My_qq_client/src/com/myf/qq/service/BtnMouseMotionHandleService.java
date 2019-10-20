package com.myf.qq.service;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolTip;

import com.myf.qq.sys.domain.Config;
import com.myf.qq.view.LoginUI;
/**
 * 
 * @author myf
 *
 */
public class BtnMouseMotionHandleService implements MouseListener
{
	private LoginUI loginUI;
	
	private JButton JB;
	
	private String command;
	
	private Config config;
	
	
	public BtnMouseMotionHandleService()
    {
	    // TODO Auto-generated constructor stub
    }
	
	
	
	public BtnMouseMotionHandleService(LoginUI loginUI, JButton JB, String command, Config config)
    {
	    this.loginUI = loginUI;
	    this.JB = JB;
	    this.command = command;
	    this.config = config;
    }



	@Override
	public void mouseClicked(MouseEvent event)
	{
		if (loginUI != null) 
		{
			switch (command) 
			{
				case "close":
					System.exit(0);
					break;
				case "minimization":
					loginUI.setExtendedState(JFrame.ICONIFIED);
					break;
				default:
					break;
			}
		}
		else{
			
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		if("close".equals(command))
		{
			JB.setIcon(new ImageIcon(config.getCloseHoverURL()));
			JB.setToolTipText("关闭");
		}
		else
		{
			JB.setIcon(new ImageIcon(config.getShrinkHoverURL()));
			JB.setToolTipText("最小化");
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		JB.setContentAreaFilled(false);
		if("close".equals(command))
		{
			JB.setIcon(new ImageIcon(config.getCloseURL()));
		}
		else
		{
			JB.setIcon(new ImageIcon(config.getShrinkURL()));
		}
	}
	
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if("close".equals(command))
		{
			JB.setIcon(new ImageIcon(config.getClosePressURL()));
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
}
