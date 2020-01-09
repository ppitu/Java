import java.io.IOException;

/**
* Klasa umozliwiajaca zgadywanie liczby, ktora wylosowal komputer
* @author Kubuc Puchatek
*/
public class TryAndCheck
{
	private int number;

	/**
	* konstruktor, w nim odbywa sie losowanie liczby
	*/
	public TryAndCheck()
	{
		this.number = (int)(Math.random()*10);
	}

	/**
	* sprawdza, czy podana wartosc jest wieksza, mniejsza badz rowna
	* wylosowanej liczbie
	* @param iv
	* @return -1 gdy iv jest mniejsza, 1 gdy wieksza, 0 gdy rowna
	*/
	public byte check(int iv)
	{
		if (iv < this.number) return -1;
		if (iv> this.number) return 1;
		return 0;
	}

	/**
	*metoda main
	*/
	public static void main(String [] args) throws IOException
	{
		TryAndCheck play = new TryAndCheck();
		int res;
		char c;

		do
		{
			c = (char)System.in.read();
			res = play.check(Integer.valueOf(Character.toString(c)));
			c = (char)System.in.read();
			if(res < 0 )
				System.out.println("Za malo");
			if(res > 0 )
				System.out.println("Za duzo");
		} while(res != 0);
		System.out.println("Win");
	}
}
