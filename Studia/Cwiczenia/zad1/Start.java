public class Start
{

	public static void main(String[] args)
	{
		MagicCounter c = new MagicCounter();

		/*System.out.println("Wartosc code: 5");
		c.compute(5);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: -2");
		c.compute(-2);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 5");
		c.compute(5);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 5");
		c.compute(5);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 2");
		c.compute(2);
		System.out.println("Wartosc get: " + c.get() + "\n");
		
		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: -2");
		c.compute(-2);
		System.out.println("Wartosc get: " + c.get() + "\n");
		
		System.out.println("Wartosc code: 3");
		c.compute(3);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 2");
		c.compute(2);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");
		
		System.out.println("Wartosc code: -111");
		c.compute(-111);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");
	
		System.out.println("Wartosc code: 0");
		c.compute(0);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 2");
		c.compute(2);
		System.out.println("Wartosc get: " + c.get() + "\n");
	
		System.out.println("Wartosc code: -111");
		c.compute(-111);
		System.out.println("Wartosc get: " + c.get() + "\n");

		System.out.println("Wartosc code: 2");
		c.compute(2);
		System.out.println("Wartosc get: " + c.get() + "\n");
		*/
		c.compute(5);
		System.out.println(c.get());
		c.compute(-10);
		System.out.println(c.get());
		c.compute(0);
		System.out.println(c.get());
		c.compute(5);
		c.compute(2);
		System.out.println(c.get());
		c.compute(0);
		c.compute(-11);
		System.out.println(c.get());
		c.compute(2);
		System.out.println(c.get());
		c.reset();
		System.out.println(c.get());
		c.compute(1);
		c.compute(1);
		System.out.println(c.get());
	}
}
