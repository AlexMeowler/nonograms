import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.TimerTask;
import java.util.Timer;
// 0 - пусто, 1000 - залито, 1001 - крест иначе цифра
public class NonoPanel extends JPanel implements MouseListener, ActionListener
{
	/**
	 * 
	 */
	
	public NonoPanel(NonoFrame owner) throws IOException 
	{
		setBounds(owner.getX(), owner.getY()+40, owner.getWidth(), owner.getHeight());
		this.owner = owner;
		BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("resources/stgs/s.lv")));
		crt = Integer.parseInt(rdr.readLine().substring(0));
		field = new NField("crt");
		if(crt != -1)
		{
			fieldH = new NField(""+crt);
		}
		else
		{
			fieldH = new NField(field.getN(), field.getM());
		}
		mb = -1;
		field.forceEqual(fieldH);
		setLayout(null);
		fin.setBounds(500, 200, 100, 50);
		add(fin);
		fin.setVisible(true);
		load = new JButton("Load level");
		load.setBounds(500, 250, 100, 25);
		add(load);
		load.setVisible(true);
		save = new JButton("Save level");
		save.setBounds(500, 300, 100, 25);
		add(save);
		save.setVisible(true);
		clear = new JButton("Clear");
		clear.setBounds(500, 350, 100, 25);
		add(clear);
		clear.setVisible(true);
		newF = new JButton("New...");
		newF.setBounds(620, 300, 100, 25);
		add(newF);
		newF.setVisible(true);
		addMouseListener(this);
		load.addActionListener(this);
		save.addActionListener(this);
		clear.addActionListener(this);
		newF.addActionListener(this);
		rdr.close();
		checkFin();
		checkCross();
		this.validate();
		timer.schedule(new RepC(), 1);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(UIManager.getColor ("Panel.background"));
		g2d.fillRect(0,  0, getWidth(), getHeight());
		g2d.setColor(new Color(0,0,0));
		drawMField(g2d);
		drawVField(g2d);
		drawGField(g2d);
		drawVCross(g2d);
		drawGCross(g2d);
		g2d.setColor(new Color(205, 106, 0));
		
	}
	private void drawGField(Graphics2D g2d) {
		// TODO Auto-generated method stub
		for(int i = field.getGN()-1; i > -1; i--)
		{
			for(int j = 0; j < field.getGM(); j++)
			{
				switch(field.getGElem(i, j))
				{
				case 0:
				{
					g2d.setColor(new Color(205, 106, 0));
					g2d.fillRect(sx+j*bsize, sy-(i+1)*bsize, bsize, bsize);
					g2d.setColor(new Color(0, 0, 0));
					g2d.drawRect(sx+j*bsize, sy-(i+1)*bsize, bsize, bsize);
					break;
				}
				default:
				{
					g2d.setColor(new Color(205, 106, 0));
					g2d.fillRect(sx+j*bsize, sy-(i+1)*bsize, bsize, bsize);
					g2d.setColor(new Color(0, 0, 0));
					g2d.drawRect(sx+j*bsize, sy-(i+1)*bsize, bsize, bsize);
					g2d.drawString(""+field.getGElem(i, j),sx+j*bsize+(int)(bsize/2), sy-(i+1)*bsize+(int)(bsize/2));
				}
				}
			}
		}
	}
	private void drawVField(Graphics2D g2d) {
		// TODO Auto-generated method stub
		for(int i = 0; i<field.getVN(); i++)
		{
			for(int j = field.getVM()-1; j>-1; j--)
			{
				switch(field.getVElem(i, j))
				{
				case 0:
				{
					g2d.setColor(new Color(205, 106, 0));
					g2d.fillRect(sx-(field.getVM()-j)*bsize, sy+i*bsize, bsize, bsize);
					g2d.setColor(new Color(0, 0, 0));
					g2d.drawRect(sx-(field.getVM()-j)*bsize, sy+i*bsize, bsize, bsize);
					break;
				}
				default:
				{
					g2d.setColor(new Color(205, 106, 0));
					g2d.fillRect(sx-(field.getVM()-j)*bsize, sy+i*bsize, bsize, bsize);
					g2d.setColor(new Color(0, 0, 0));
					g2d.drawRect(sx-(field.getVM()-j)*bsize, sy+i*bsize, bsize, bsize);
					g2d.drawString(""+field.getVElem(i, j),sx-(field.getVM()-j)*bsize+(int)(bsize/2), sy+i*bsize+(int)(bsize/2));
				}
				}
			}
		}
	}
	private void drawMField(Graphics2D g2d) {
		// TODO Auto-generated method stub
		for(int i = 0; i < field.getN(); i++)
		{
			for(int j = 0; j < field.getM(); j++)
			{
				g2d.setColor(new Color(255,255,255));
				g2d.fillRect(sx+j*bsize, sy+i*bsize, bsize, bsize);
				g2d.setColor(new Color(0,0,0));
				g2d.drawRect(sx+j*bsize, sy+i*bsize, bsize, bsize);
				switch(field.getElem(i, j))
				{
				case 1000:
						{
							g2d.fillRect(sx+j*bsize+6, sy+i*bsize+6, bsize-11, bsize-11);
							break;
						}
				case 1001:
						{
							g2d.drawRect(sx+j*bsize, sy+i*bsize, bsize, bsize);
							g2d.drawLine(sx+j*bsize, sy+i*bsize, sx+(j+1)*bsize, sy+(i+1)*bsize);
							g2d.drawLine(sx+j*bsize, sy+(i+1)*bsize, sx+(j+1)*bsize, sy+i*bsize);
						}
				}
			}
		}
	}
	private void drawVCross(Graphics2D g2d)
	{
		for(int i = 0; i<field.getVN(); i++)
		{
			for(int j = field.getVM()-1; j>-1; j--)
			{
				if(field.getVFElem(i, j))
				{
					g2d.drawLine(sx-(field.getVM()-j)*bsize, sy+i*bsize, sx-(field.getVM()-j-1)*bsize, sy+(i+1)*bsize);
					g2d.drawLine(sx-(field.getVM()-j)*bsize, sy+(i+1)*bsize, sx-(field.getVM()-j-1)*bsize, sy+i*bsize);
				}
			}
		}
	}
	private void drawGCross(Graphics2D g2d)
	{
		for(int i = 0; i<field.getGN(); i++)
		{
			for(int j = field.getGM()-1; j>-1; j--)
			{
				if(field.getGFElem(i, j))
				{
					g2d.drawLine(sx+j*bsize, sy-(i+1)*bsize, sx+(j+1)*bsize, sy-i*bsize);
					g2d.drawLine(sx+j*bsize, sy-i*bsize, sx+(j+1)*bsize, sy-(i+1)*bsize);
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) 
	{
		task.e = e;
		timer.scheduleAtFixedRate(task, 0, 10);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		task.cancel();
		task = new MyTask();
		this.mb = -1; 
		checkCross();
		checkFin();
	}
	private void checkFin()
	{
		if (fieldH.compareTo(field))
		{
			fin.setText("Верно!");
		}
		else
		{
			fin.setText("");
		}
	}
	private void checkCross()
	{
		int z, l;
		boolean f;
		int n = field.getN();
		int m = field.getM();
		for(int i = 0; i < n; i++)
		{
			z = field.getVM();
			l = 0;
			f = false;
			for(int j = m-1; j > -1; j--)
			{
				if(f)
				{
					if(field.getElem(i, j) == 1000)
					{
						l++;
					}
					else
					{
						f = !f;
						if(z>-1)
						{
							field.setVFElem(i, z, field.getVElem(i, z) == l);
						}
						l = 0;
					}
				}
				else
				{
					if(field.getElem(i, j) == 1000)
					{
						f = !f;
						l++;
						z--;
					}
				}
			}
			if(l != 0)
			{
				if(z>-1)
				{
					field.setVFElem(i, z, field.getVElem(i, z) == l);
				}
				l = 0;
			}
		}
		for(int j = 0; j < m; j++)
		{
			z = field.getGN();
			l = 0;
			f = false;
			for(int i = n-1; i > -1; i--)
			{
				if(f)
				{
					if(field.getElem(i, j) == 1000)
					{
						l++;
					}
					else
					{
						f = !f;
						if(z>-1)
						{
							field.setGFElem(z, j, field.getGElem(z, j) == l);
						}
						l = 0;
					}
				}
				else
				{
					if(field.getElem(i, j) == 1000)
					{
						f = !f;
						l++;
						z--;
					}
				}
			}
			if(l != 0)
			{
				if(z>-1)
				{
					field.setGFElem(z, j, field.getGElem(z, j) == l);
				}
				l = 0;
			}
		}
		repaint();
	}
	public NField getMField()
	{
		return field;
	}
	public int getCRT()
	{
		return crt;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (e.getSource() == load)
		{
			int code = fd.showOpenDialog(this);
			if (code == JFileChooser.APPROVE_OPTION)
			{
				try {
					fieldH = new NField(fd.getSelectedFile().getName().substring(0, fd.getSelectedFile().getName().indexOf(".")));
					field = new NField(fieldH.getN(), fieldH.getM());
					field.forceEqual(fieldH);
					crt = Integer.parseInt(fd.getSelectedFile().getName().substring( 0,  fd.getSelectedFile().getName().indexOf(".")));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//
			repaint();
		}
		if (e.getSource() == save)
		{
			int code = fd.showSaveDialog(this);
			if (code == JFileChooser.APPROVE_OPTION)
			{
				String s = "";
				try {
					BufferedWriter rdr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fd.getSelectedFile().getAbsolutePath()+".lv")));
					rdr.write(s+field.getN()+","+field.getM());
					rdr.newLine();
					for(int i = 0; i < field.getN(); i++)
					{
						s = "";
						for(int j = 0; j < field.getM()-1; j++)
						{
							s += field.getElem(i, j) + ",";
						}
						s += field.getElem(i, field.getM()-1);
						rdr.write(s);
						rdr.newLine();
					}
					rdr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource() == clear)
		{
			for(int i = 0; i < field.getN(); i++)
			{
				for(int j = 0; j < field.getM(); j++)
				{
					field.setElem(i, j, 0);
				}
			}
			for(int i = 0; i < field.getVN(); i++)
			{
				for(int j = 0; j < field.getVM(); j++)
				{
					field.setVFElem(i, j, false);
				}
			}
			for(int i = 0; i < field.getGN(); i++)
			{
				for(int j = 0; j < field.getGM(); j++)
				{
					field.setGFElem(i, j, false);
				}
			}
			repaint();
		}
		if (e.getSource() == newF)
		{
			NonoAskFrame f = new NonoAskFrame(this.owner);
		}
	}
	public void setF(JTextField f1, JTextField f2)
	{
		dn = Integer.parseInt(f1.getText());
		dm = Integer.parseInt(f2.getText());
		field = new NField(dn, dm);
		fieldH = new NField(dn, dm);
		crt = -1;
		repaint();
	}
	private class MyTask extends TimerTask
	{

		@Override
		public void run() {
			Rectangle rect = new Rectangle(sx, sy, fieldH.getM()*bsize, fieldH.getN()*bsize);
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if (rect.contains(MouseInfo.getPointerInfo().getLocation().getX()-getLocationOnScreen().getX(), MouseInfo.getPointerInfo().getLocation().getY()-getLocationOnScreen().getY()))
				{
					if (mb == -1)
					{
						switch(field.getElem((int)((MouseInfo.getPointerInfo().getLocation().getY()-sy-getLocationOnScreen().getY())/bsize), (int)((MouseInfo.getPointerInfo().getLocation().getX()-sx-getLocationOnScreen().getX())/bsize)))
						{
						case 1001:{}
						case 0:
						{
							field.setElem((int)((MouseInfo.getPointerInfo().getLocation().getY()-sy-getLocationOnScreen().getY())/bsize), (int)((MouseInfo.getPointerInfo().getLocation().getX()-sx-getLocationOnScreen().getX())/bsize), 1000);
							break;
						}
						case 1000:
						{
							field.setElem((int)((MouseInfo.getPointerInfo().getLocation().getY()-sy-getLocationOnScreen().getY())/bsize), (int)((MouseInfo.getPointerInfo().getLocation().getX()-sx-getLocationOnScreen().getX())/bsize), 0);
							break;
						}
						}
						mb = field.getElem((int)((MouseInfo.getPointerInfo().getLocation().getY()-sy-getLocationOnScreen().getY())/bsize), (int)((MouseInfo.getPointerInfo().getLocation().getX()-sx-getLocationOnScreen().getX())/bsize));
					}
					else
					{
						field.setElem((int)((MouseInfo.getPointerInfo().getLocation().getY()-sy-getLocationOnScreen().getY())/bsize), (int)((MouseInfo.getPointerInfo().getLocation().getX()-sx-getLocationOnScreen().getX())/bsize), mb);
					}
					
				}
			}
			else 
			{
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					if (rect.contains(MouseInfo.getPointerInfo().getLocation().getX()-getLocationOnScreen().getX(), MouseInfo.getPointerInfo().getLocation().getY()-getLocationOnScreen().getY()))
					{
						field.setElem((int)((MouseInfo.getPointerInfo().getLocation().getY()-sy-getLocationOnScreen().getY())/bsize), (int)((MouseInfo.getPointerInfo().getLocation().getX()-sx-getLocationOnScreen().getX())/bsize), 1001);
					}
				}
			}
			repaint();
		}
		public MouseEvent e;
	}
	private class RepC extends TimerTask
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			owner.deltaResize(-1,-1);
			setBounds(owner.getX(), owner.getY()+40, owner.getWidth(), owner.getHeight()-40);
			owner.setResizable(false);
		}
		
	}
	private static final int bsize = 30;
	private static final int sx = 100;
	private static final int sy = 100;
	private NField field = null;
	private NField fieldH = null;
	private int mb;
	private JLabel fin = new JLabel();
	private Timer timer = new Timer();
	private MyTask task = new MyTask();
	private JButton load = null;
	private JButton save = null;
	private JButton clear = null;
	private JButton newF = null;
	private final JFileChooser fd = new JFileChooser();
	private NonoFrame owner = null;
	private int crt;
	private int dn, dm;
	private static final long serialVersionUID = 3008903542275518871L;
}
