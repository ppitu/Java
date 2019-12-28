import java.util.*;

public class Supplier implements SupplierInterface
{
	Map<Integer, Integer> zbior;

	public Supplier()
	{
		zbior = Collections.synchronizedMap(new HashMap<Integer, Integer>());
	}

	synchronized public void add(int objectID, int quantity)
	{
		synchronized(zbior)
		{
			if(zbior.containsKey(objectID))
			{
				quantity += zbior.get(objectID);
				zbior.replace(objectID, quantity);
			}
			else
			{
				zbior.put(objectID, quantity);
			}

			zbior.notifyAll();
		}
	}

	public void request(Map<Integer, Integer> order) //throws InterruptedException
	{
		synchronized(zbior){
			for(Map.Entry<Integer, Integer> m : order.entrySet())
			{
				while(!zbior.containsKey(m.getKey()) || zbior.get(m.getKey()) < m.getValue())
				{
					try{
					zbior.wait();
					} catch(InterruptedException e){ System.out.println("Wait error"); return ;}
				}
			}

			for(Map.Entry<Integer, Integer> m : order.entrySet())
			{
				int wartosc = m.getValue();

				int wartosc1 = zbior.get(m.getKey());

				zbior.replace(m.getKey(), wartosc1 - wartosc);
			}
		}
	}

	public Map<Integer, Integer> getInventory()
	{
		return zbior;
	}
}
