public class SquareRoot
{
	public static final double precision = 1.0e-5;

	public static double calculateSquareRoot(double x)
	{
		double guess = 1.0;

		do
		{
			guess = (guess + x/guess)/2.0;
		}while((guess*guess/x < 1.0 - precision) || (guess*guess/x > 1.0 + precision));
		
		return guess;
	}

	public static void main(String[] args)
	{
		if(args.length < 1)
			System.out.println("Brak argumentow");
		else
			System.out.println(calculateSquareRoot(Double.parseDouble(args[0])));
	}
}
