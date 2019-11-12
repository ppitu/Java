class Start
{
	public static void main(String args[])
	{
		//int [][] tab = {{1,2,1},{2,1,2},{1,2,1}};
		int [][] tab = {{5,3,3,3,5},{3,2,1,2,3},{3,1,0,1,3},{3,2,1,2,3},{5,3,3,3,5}};
		boolean [][]tab1 = new boolean[7][12];
		

		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				tab1[i][j] = false;
			}
		}	

		//tab1[0][0] = true;
		//tab1[0][1] = true;
		//tab1[0][2] = true;
		//tab1[1][0] = true;
		//tab1[1][1] = true;
		//tab1[1][2] = true;
		//tab1[2][0] = true;
		
		tab1[1][3] = true;
		tab1[1][4] = true;
		tab1[2][2] = true;
		tab1[2][6] = true;
		tab1[3][6] = true;
		tab1[5][11] = true;
		tab1[0][11] = true;

		/*tab1[0][0] = true;
		tab1[0][1] = true;
		tab1[1][1] = true;*/

		int [] tab2;
	
		HistogramBase x1 = new Histogram();
		x1.setNeighboursTable(tab);
		x1.setOccupancyTable(tab1);
		tab2 = x1.getHistogram();

		for(int i = 0; i < tab2.length; i++)
			System.out.println(tab2[i]);

		System.out.println("Test");
		System.out.println(x1.noNeighbours());
	}
}
