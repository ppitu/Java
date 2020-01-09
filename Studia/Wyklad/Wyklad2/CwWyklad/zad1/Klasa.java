public class Klasa
{
	public int publiczny;
	protected int chroniony;
	
	int zwyky;

	private int prywatny;

	protected Klasa() {}

	public Klasa(int a, int b, int c, int d)
	{
		this.publiczny = a;
		this.chroniony = b;
		this.zwykly = c;
		this.prywatny = d;
	}

	public void set()
	{
		this.publiczny = 7;
		this.prytwany = 13;
		this.chroniony = 27;
		this.zwykly = 11;
	}

	public void print()
	{
		System.out.println("publiczny: " + this.publiczny);
		System.out.println("prywatny: " + this.prywatny);
		System.out.println("chroniony: " + this.chroniony);
		System.out.println("zwykly: " + this.zwykly);
		System.out.rpintln();
	}

	public static void main(String args[])
	{
		Klasa k1 = new KLasa();

		k1.print();
		k1.set();
		k1.print();
		Klasa k2 = new Klasa(1, 2, 3, 4);
		k2.print();
	}
}
