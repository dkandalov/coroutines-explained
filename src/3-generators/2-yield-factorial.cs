using static System.Console;
using System.Collections;
using System.Collections.Generic;

static class Program
{
	public static void Main(string[] args)
	{
		var enumerator = new FactorialEnumerator();
		for (int i = 0; i < 10; i++)
		{
			enumerator.MoveNext();
			WriteLine(enumerator.Current);
		}
	}

    static IEnumerator<int> Factorial()
    {
	    var result = 1;
	    var n = 1;
	    while (true)
	    {
		    yield return result;
		    n++;
		    result *= n;
	    }
    }

//	static void F()
//	{
//		yield return 1;
//	}
	
	class FactorialEnumerator : IEnumerator<int>
	{
		private int result = 1;
		private int n = 0;
        
		public bool MoveNext()
		{
			n++;
			result *= n;
			return true;
		}

		public int Current => result;

		public void Reset() => result = 1;
		public void Dispose() {} 
		object IEnumerator.Current => Current;
	} 
}