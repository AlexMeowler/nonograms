import java.awt.event.*;

import javax.swing.*;

public class NonoAskFrame extends JFrame 
{
	public NonoAskFrame(NonoFrame creator)
	{
		this.creator = creator;
		setBounds(200, 200, 300, 150);
		AskPanel p = new AskPanel(this);
		add(p);
		setVisible(true);
	}
	private void returnS(JTextField en, JTextField em)
	{
		creator.getPanel().setF(en, em);
	}
	private NonoFrame creator = null;
	private class AskPanel extends JPanel implements ActionListener
	{
		public AskPanel(NonoAskFrame owner)
		{
			setLayout(null);
			this.owner = owner;
			setBounds(owner.getX(), owner.getY()+40, owner.getWidth(), owner.getHeight());
			en = new JTextField();
			en.setBounds(10, 30, 50, 20);
			add(en);
			em = new JTextField();
			em.setBounds(100, 30, 50, 20);
			add(em);
			add(end);
			end.setBounds(20, 60, 100, 30);
			end.addActionListener(this);
			end.setVisible(true);
			JLabel tN = new JLabel("Строки");
			tN.setBounds(10, 10, 50, 20);
			add(tN);
			JLabel tM = new JLabel("Столбцы");
			tM.setBounds(100, 10, 50, 20);
			add(tM);
			setVisible(true);
		}
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			if(e.getSource() == end)
			{
				if(en.getText() != "" && em.getText() != "")
				{
					owner.returnS(en, em);
					owner.setVisible(false);
					owner.dispose();
				}
			}
		}
		private NonoAskFrame owner = null;
		private JTextField en, em;
		private JButton end = new JButton("OK");
	}
}
