using static System.Console;
using System.Collections.Generic;

static class Program
{
	public static void Main(string[] args)
	{
		WriteLine(1);

		var enumerator = CreateEnumerator();
		enumerator.MoveNext();
		WriteLine(enumerator.Current);
		enumerator.MoveNext();
		WriteLine(enumerator.Current);

		WriteLine(3);
	}

    private static IEnumerator<int> CreateEnumerator()
    {
        WriteLine("started");
        yield return 2;
        WriteLine("finished");
    }
}