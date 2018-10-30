using static System.Console;
using System.Collections;
using System.Collections.Generic;

static class Program
{
    public static void Main(string[] args)
    {
        var enumerator = Fibonacci();
        for (int i = 0; i < 10; i++)
        {
            enumerator.MoveNext();
            WriteLine(enumerator.Current);
        }
    }

    private static IEnumerator<int> Fibonacci()
    {
        var n1 = -1;
        var n2 = 1;
        while (true) {
            var tmp = n2;
            n2 += n1;
            n1 = tmp;
            yield return n2;
        }
    }

    //	static void F()
    //	{
    //		yield return 1;
    //	}

    class FibonacciEnumerator : IEnumerator<int>
    {
        private int n1 = -1;
        private int n2 = 1;

        public bool MoveNext()
        {
            var tmp = n2;
            n2 += n1;
            n1 = tmp;
            return true;
        }

        public int Current => n2;

        public void Reset()
        {
            n1 = -1;
            n2 = 1;
        }
        public void Dispose() {}
        object IEnumerator.Current => Current;
    }

}