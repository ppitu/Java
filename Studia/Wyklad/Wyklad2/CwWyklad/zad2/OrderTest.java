public class OrderTest
{
	static
	{
		System.out.println("static");
	}

	public OrderTest()
	{
		System.out.println("constructor");
	}

	public static void main(String args[])
	{
		System.out.println("main: begin");
		OrderTest o;

		System.out.println("main: middle");

		o = new OrderTest();

		System.out.println("main: end");
	}
}
