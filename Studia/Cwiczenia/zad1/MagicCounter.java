
public class MagicCounter {
	private int accumulator;
	private int licznik = 0;
	private int blok = 0;
	private int blok1 = 0;
	private int zapamietana = -1;
	// oczywiście pola i metody można dla wygody dodać.
	
	/**
	 * Metoda modyfikuje zawartość akumulatora. Zmiana wartości zapisanej w
	 * akumulatorze zależy od aktualnie nadesłanego kodu i jego poprzednich
	 * wartości.
	 * 
	 * @param code kod zmieniający wartość akumulatora i tryb pracy. Program zaczyna
	 *             pracę w trybie (nazwijmy go podstawowym), w którym przekazany kod
	 *             (o ile jest różny od 0) oznacza wartość, którą należy dodać do
	 *             akumulatora. Jeśli jednak wartość przekazanego kodu to 0, w
	 *             następnym wywołaniu realizowane jest działanie specjalne i tak:
	 *             <ul>
	 *             <li>Jeśli przekazano 0 oznacza to konieczność wyzerowania
	 *             akumulatora i powrót do trybu podstawowego.
	 *             <li>Jeśli przekazano wartość od zera mniejszą, ale większą do
	 *             -10, to Math.abs(code) kolejnych wywołań metody compute jest
	 *             ignorowanych. Kolejnym analizowanym kodem wg. trybu podstawowego
	 *             jest kod z Math.abs(code)+1 kolejnego wywołania.
	 *             <li>Jeśli przekazano wartość nie większą od -10 wywołania metody
	 *             compute są ignorowane aż do ponownego napotkania dokładnie takiej
	 *             samej wartości ujemnej. Jej przekazanie powoduje powrót do trybu
	 *             podstawowego.
	 *             <li>Jeśli przekazano wartość większą od zera należy ją zapamiętać
	 *             i do akumulatora dodać iloczyn wartości zapamiętanej i code z
	 *             następnego wywołania. Kolejne (trzecie od przekazania do systemu
	 *             code równego 0) analizowane jest wg. trybu podstawowego.
	 *             </ul>
	 */
	public void compute(int code) {
	
		if(blok != 0)
		{
			blok--;
			if(licznik > 0)
				licznik--;
			return;
		}

		if(blok1 != 0)
		{
			if(blok1 != code)
				return;
			else
			{
				blok1 = 0;
				licznik--;
				return;
			}
		}

		if(zapamietana != -1)
		{
			accumulator = accumulator + (code * zapamietana);
			zapamietana = -1;
			return;
		}

		if(licznik != 0)
		{
			if(code == 0)
			{
				accumulator = 0;
				licznik = 0;
				return;
			}
			if((code < 0) && (code >= -10))
			{
				blok = Math.abs(code);
				return;
			}
			if(code > 0)
			{
				zapamietana = code;
				licznik--;
				return;
			}

			if(code < -10)
			{
				blok1 = code;
				return;
			}
		}
		else
		{
			if(code == 0)
			{
				licznik++;
				return;
			}
		}

		accumulator += code;	
				// tu Państwa kod
	}

	/**
	 * Metoda zwraca aktualny stan akumulatora.
	 * 
	 * @return wartość akumulatora.
	 */
	public int get() {
		return accumulator;
	}

	/**
	 * Metoda przywraca stan początkowy tj. akumulator o wartości 0 oraz podstawowy
	 * tryb interpretacji wartości parametru metody compute.
	 */
	public void reset() {
		// tu Państwa kod
		accumulator = 0;
		licznik = 0;
		blok = 0;
		blok1 = 0;
		zapamietana = -1;
	}
}
