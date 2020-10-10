import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class NonoFrame extends JFrame implements WindowListener
{

	/**
	 * 
	 */
	public NonoFrame(int x, int y, int width, int height) 
	{
		setBounds(x, y, width, height);
		setVisible(true);
		addWindowListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void windowActivated(WindowEvent e) 
	{
		
	}

	@Override
	public void windowClosed(WindowEvent e) 
	{
		
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		BufferedWriter rdr = null;
		try {
			rdr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resources/lvls/crt.lv")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String s;
		try {
			rdr.write(""+panel.getMField().getN()+","+panel.getMField().getM());
			rdr.newLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i = 0; i < panel.getMField().getN(); i++)
		{
			s = "";
			for(int j = 0; j < panel.getMField().getM()-1; j++)
			{
				s+= panel.getMField().getElem(i, j) + ",";
			}
			s+= panel.getMField().getElem(i, panel.getMField().getM()-1);
			try {
				rdr.write(s);
				rdr.newLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			rdr.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rdr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resources/stgs/s.lv")));
			rdr.write(""+panel.getCRT());
			rdr.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void setPanel(NonoPanel p)
	{
		panel = p;
	}
	public NonoPanel getPanel()
	{
		return panel;
	}
	public void deltaResize(int i, int j) 
	{
		setBounds(getX(), getY(), getWidth()+i, getHeight()+j);
		
	}
	private NonoPanel panel;
	private static final long serialVersionUID = -1216102260692122697L;

}
