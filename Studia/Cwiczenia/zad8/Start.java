import java.util.*;

class Start
{
	
	public static void main(String[] args)
	{
		final Supplier x1 = new Supplier();
		
		HashMap<Integer, Integer> map = new HashMap<>();
		HashMap<Integer, Integer> map1 = new HashMap<>();
		HashMap<Integer, Integer> map2 = new HashMap<>();

		map.put(0, 2);
		map.put(1, 2);

		map1.put(0, 2);
		map1.put(1, 2);

		map2.put(0, 3);
		map2.put(1, 3);

		Runnable runA = new Runnable()
		{
			public void run()
			{
				x1.add(0, 5);
				x1.add(1, 4);
				System.out.println("Dodane");
				//x1.add(2, 3);
			}
		};

		Runnable runB = new Runnable()
		{
			public void run()
			{
				//try{
				x1.request(map);
				//} catch(InterruptedException e){}
			}
		};

		Runnable runC = new Runnable()
		{
			public void run()
			{
				//try{
				x1.request(map1);
				System.out.println("UsunC");
				//} catch(InterruptedException e){}
			}
		};

		Runnable runD = new Runnable()
		{
			public void run()
			{
				//try{
				x1.request(map2);
				System.out.println("UsunD");
				//} catch(InterruptedException e){}
			}
		};

		Thread th1 = new Thread(runA);
		Thread th2 = new Thread(runB);
		Thread th3 = new Thread(runC);
		Thread th4 = new Thread(runD);
		
		try{
		th4.start();
		th3.start();
		th2.start();
		th1.start();
		Thread.sleep(4000);
		System.out.println(x1.getInventory());
		} catch(InterruptedException e){}

		th1.interrupt();
		th2.interrupt();
		th3.interrupt();
		th4.interrupt();

		try{ Thread.sleep(1000); } catch(InterruptedException e) {}

		if(th1.isInterrupted())
		System.out.println("Jest1");
		if(th2.isInterrupted())
		System.out.println("Jest2");
		if(th3.isInterrupted())
		System.out.println("Jest3");
		if(th4.isInterrupted())
		System.out.println("Jest4");

		System.out.println(x1.getInventory());
		/*

		x1.add(0, 5);
		x1.add(1, 7);	
		x1.add(2, 3);
		System.out.println(x1.getInventory());
		x1.add(0, 3);
		System.out.println(x1.getInventory());
		x1.request(map);
		System.out.println(x1.getInventory());*/
	}
}
