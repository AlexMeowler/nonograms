import java.io.*;

public final class NField 
{
	public NField(int n, int m)
	{
		field = new int[n][m];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				field[i][j] = 0;
			}
		}
		this.n = n;
		this.m = m;
	}
	public NField(String name) throws IOException
	{
		generate(name);
		vBar = generateVertBar();
		gBar = generateGorizBar();
		vFBar = generateFlagVBar();
		gFBar = generateFlagGBar();
	}
	public NField(NField f) 
	{
		n = f.getN();
		m = f.getM();
		field = new int[n][m];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				field[i][j] = f.getElem(i, j);
			}
		}
	}
	public int getN()
	{
		return n;
	}
	public int getM()
	{
		return m;
	}
	public void setElem(int y, int x, int k)
	{
		field[y][x] = k;
	}
	public int getElem(int y, int x)
	{
		return field[y][x];
	}
	public boolean getVFElem(int y, int x)
	{
		return vFBar[y][x];
	}
	public boolean getGFElem(int y, int x)
	{
		return gFBar[y][x];
	}
	public void setVFElem(int y, int x, boolean z)
	{
		vFBar[y][x] = z;
	}
	public void setGFElem(int y, int x, boolean z)
	{
		gFBar[y][x] = z;
	}
	// 0 - �����, 1000 - ������, 1001 - ����� ����� �����
	private int[][] generateVertBar()
	{
		int[][] bar;
		int n, m, k, l;
		n = this.n;
		m = 0;
		for(int i = 0; i < this.n; i++)
		{
			k = 0;
			for (int j = 1; j < this.m; j++)
			{
				if(getElem(i, j) != 1000)
				{
					if (getElem(i, j-1) == 1000)
					{
						k++;
					}
				}
			}
			if ((getElem(i, this.m-1) == 1000) && (getElem(i, this.m-2) != 1000))
			{
				k++;
			}
			if (k > m)
			{
				m = k;
			}
		}
		if (m == 0)
		{
			m++;
		}
		bar = new int[n][m];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < m; j++)
			{
				bar[i][j] = 0;
			}
		}
		for(int i = 0; i < this.n; i++)
		{
			l = 0;
			k = m-1;
			if(getElem(i, this.m-1) == 1000)
			{
				l++;
			}
			for(int j = this.m-2; j>-1; j--)
			{
				if(getElem(i, j) == 1000)
				{
					l++;
				}
				if(getElem(i, j) != 1000)
				{
					if(getElem(i, j+1) == 1000)
					{
						bar[i][k] = l;
						l = 0;
						k--;
					}
				}
			}
			if (l != 0)
			{
				bar[i][k] = l;
			}
			
		}
		vm = m;
		vn = n;
		return bar;
	}
	private int[][] generateGorizBar()
	{
		int[][] bar;
		int n, m, k, l;
		n = 0;
		m = this.m;
		for(int j = 0; j < this.m; j++)
		{
			k = 0;
			for (int i = 1; i < this.n; i++)
			{
				if(getElem(i, j) != 1000)
				{
					if (getElem(i-1, j) == 1000)
					{
						k++;
					}
				}
			}
			if ((getElem(j, this.n-1) == 1000) && (getElem(j, this.n-2) != 1000))
			{
				k++;
			}
			if (k > n)
			{
				n = k;
			}
		}
		if(n == 0)
		{
			n++;
		}
		bar = new int[n][m];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < m; j++)
			{
				bar[i][j] = 0;
			}
		}
		for(int j = 0; j < this.m; j++)
		{
			l = 0;
			k = n-1;
			if(getElem(this.n-1, j) == 1000)
			{
				l++;
			}
			for(int i = this.n-2; i>-1; i--)
			{
				if(getElem(i, j) == 1000)
				{
					l++;
				}
				if(getElem(i, j) != 1000)
				{
					if(getElem(i+1, j) == 1000)
					{
						bar[k][j] = l;
						l = 0;
						k--;
					}
				}
			}
			if (l != 0)
			{
				bar[k][j] = l;
			}
		}
		gm = m;
		gn = n;
		return bar;
	}
	private boolean[][] generateFlagVBar()
	{
		boolean[][] bar = new boolean[vn][vm];
		for(int i = 0; i < vn; i++)
		{
			for(int j = 0; j < vm; j++)
			{
				bar[i][j] = false;
			}
		}
		return bar;
	}
	private boolean[][] generateFlagGBar()
	{
		boolean[][] bar = new boolean[gn][gm];
		for(int i = 0; i < gn; i++)
		{
			for(int j = 0; j < gm; j++)
			{
				bar[i][j] = false;
			}
		}
		return bar;
	}
	private void generate(String name) throws IOException
	{
		 BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("resources/lvls/"+name+".lv")));
		 String line = "";
		 line = rdr.readLine();
		 this.n = Integer.parseInt(line.substring(0, line.indexOf(",")));
		 this.m = Integer.parseInt(line.substring(line.indexOf(",")+1, line.length()));
		 this.field = new int[n][m];
		 int k = 0;
		 for(int i = 0; i < this.n; i++)
		 {
			k = 0;
			line = rdr.readLine();
			for(int j = 0; j < this.m-1; j++)
			{
				this.field[i][j] = Integer.parseInt(line.substring(k, line.indexOf(",", k+1)));
				k = line.indexOf(",", k+1)+1;
			}
				this.field[i][m-1] = Integer.parseInt(line.substring(k, line.length()));
		}
		 rdr.close();
	}
	public void setVBar(int[][] b, int n, int m)
	{
		vBar = b;
		this.vn = n;
		this.vm = m;
	}
	public void setGBar(int[][] b, int n, int m)
	{
		gBar = b;
		this.gn = n;
		this.gm = m;
	}
	public void setVFBar(boolean[][] b)
	{
		vFBar = b;
	}
	public void setGFBar(boolean[][] b)
	{
		gFBar = b;
	}
	public int getVElem(int i, int j)
	{
		return vBar[i][j]; 
	}
	public int getGElem(int i, int j)
	{
		return gBar[i][j];
	}
	public int getVN()
	{
		return vn;
	}
	public int getVM()
	{
		return vm;
	}
	public int getGN()
	{
		return gn;
	}
	public int getGM()
	{
		return gm;
	}
	public boolean compareTo(NField field2) 
	{
		boolean x = true;
		NField t = new NField(field2);
		for(int i = 0; i < t.getN(); i++)
		{
			for (int j = 0; j < t.getM(); j++)
			{
				if (t.getElem(i, j) == 1001)
				{
					t.setElem(i, j, 0);
				}
			}
		}
		for(int i = 0; i < this.getN(); i++)
		{
			for (int j = 0; j < this.getM(); j++)
			{
				if(x)
				{
					x = this.getElem(i, j) == t.getElem(i, j); 
				}
			}
		}
		return x;
	} 
	public void forceEqual(NField fieldH)
	{
		int[][] b = new int[fieldH.getVN()][fieldH.getVM()];
		for(int i = 0; i < fieldH.getVN(); i++)
		{
			for(int j = 0; j < fieldH.getVM(); j++)
			{
				b[i][j] = fieldH.getVElem(i, j);
			}
		}
		
		setVBar(b, fieldH.getVN(), fieldH.getVM());
		b = new int[fieldH.getGN()][fieldH.getGM()];
		for(int i = 0; i < fieldH.getGN(); i++)
		{
			for(int j = 0; j < fieldH.getGM(); j++)
			{
				b[i][j] = fieldH.getGElem(i, j);
			}
		}
		setGBar(b, fieldH.getGN(), fieldH.getGM());
		boolean[][] b2 = new boolean[fieldH.getVN()][fieldH.getVM()];
		for(int i = 0; i < fieldH.getVN(); i++)
		{
			for(int j = 0; j < fieldH.getVM(); j++)
			{
				b2[i][j] = fieldH.getVFElem(i, j);
			}
		}
		setVFBar(b2);
		b2 = new boolean[fieldH.getGN()][fieldH.getGM()];
		for(int i = 0; i < fieldH.getGN(); i++)
		{
			for(int j = 0; j < fieldH.getGM(); j++)
			{
				b2[i][j] = fieldH.getGFElem(i, j);
			}
		}
		setGFBar(b2);
	}
	private int[][] field;
	private int n, m;
	private int[][] vBar, gBar;
	private int vn = 0;
	private int vm = 0;
	private int gm = 0;
	private int gn = 0;
	private boolean[][] vFBar, gFBar;


}
