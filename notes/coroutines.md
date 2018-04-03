
### Videos

Philip Wadler on Reynolds’s ‘Definitional Interpreters for Higher-Order Programming Languages’ - 
https://skillsmatter.com/skillscasts/8261-papers-we-love-meetup
(1:11:00 - continuations are not a hack)

Kotlin Coroutines - 2016 - https://www.youtube.com/watch?v=4W3ruTWUhpw

Kotlin Coroutines Reloaded with Roman Elizarov - 2017 - https://www.youtube.com/watch?v=3xalVUY69Ok

Introduction to Kotlin coroutines - Roman Elizarov - https://vimeo.com/221264980/b3ac7f9001

Introduction to Coroutines (kotlinconf) - Roman Elizarov - https://www.youtube.com/watch?list=PLQ176FUIyIUY6UK1cgVsbdPYA3X5WLam5&v=_hfBv0a09Jc

Deep Dive into Coroutines on JVM - Roman Elizarov - https://www.youtube.com/watch?list=PLQ176FUIyIUY6UK1cgVsbdPYA3X5WLam5&v=YrrUCSi72E8

Kotlin Coroutines Reloaded - Roman Elizarov - https://www.youtube.com/watch?v=3xalVUY69Ok

Roman Elizarov — Lock-Free Algorithms for Kotlin Coroutines (Part 1) - jug.ru - https://www.youtube.com/watch?v=W2dOOBN1OQI

droidconDE 2017: Svetlana Isakova - Kotlin coroutines - DAY 2 - https://www.youtube.com/watch?v=nugOMl29K3k


CppCon 2016: James McNellis “Introduction to C++ Coroutines" - https://www.youtube.com/watch?v=ZTqHjjm86Bw

CppCon 2017: Toby Allsopp "Coroutines: what can't they do?" - https://www.youtube.com/watch?v=mlP1MKP8d_Q 

Standardised Coroutine Concurrency in Python 3 - Robert Smallshire - 
https://www.youtube.com/watch?v=tS08IP5ibTs

👍 Ron Pressler - Pull Push: Please stop polluting our imperative languages with pure concepts-Curry On - 
https://www.youtube.com/watch?v=449j7oKQVkc

Ron Pressler - Project Loom: Fibers and Continuations for the Java Virtual Machine - 
https://www.youtube.com/watch?v=fpyub8fbrVE

👍 !!Con 2014 - Michael Arntzenius: Continuations; or, how to travel through time! - 
https://www.youtube.com/watch?v=cnhb4M8-J5M



### Reading
 - Definitional interpreters for higher-order programming languages - https://surface.syr.edu/cgi/viewcontent.cgi?referer=https://duckduckgo.com/&httpsredir=1&article=1012&context=lcsmith_other 
 - The Discoveries of Continuations - http://www.cs.ru.nl/%7Efreek/courses/tt-2011/papers/cps/histcont.pdf
 - https://www.infoworld.com/article/3180344/application-development/llvm-coroutines-come-to-c-await-swift-and-rust.html

 + 👍 http://matt.might.net/articles/by-example-continuation-passing-style
 -/ http://matt.might.net/articles/programming-with-continuations--exceptions-backtracking-search-threads-generators-coroutines
 - http://okmij.org/ftp/continuations/against-callcc.html
 - http://okmij.org/ftp/continuations/undelimited.html#delim-vs-undelim
 - https://blogs.msdn.microsoft.com/wesdyer/2007/12/22/continuation-passing-style
 
 - http://2ality.com/2016/10/asynchronous-iteration.html
 
 - https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md
 - http://www.boost.org/doc/libs/1_66_0/libs/coroutine2/doc/html/coroutine2/motivation.html
 - http://cr.openjdk.java.net/~rpressler/loom/Loom-Proposal.html#dfref-footnote-1 

 - [Your Mouse is a Database](https://queue.acm.org/detail.cfm?id=2169076) 
 - [From Imperative to Pure-Functional and Back Again: Monads vs. Scoped Continuations](http://blog.paralleluniverse.co/2015/08/07/scoped-continuations/)

 - http://taoofcode.net/promise-anti-patterns

 - https://en.wikipedia.org/wiki/Coroutine
 - https://en.wikipedia.org/wiki/Continuation
 - https://en.wikipedia.org/wiki/Continuation-passing_style
 - https://en.wikipedia.org/wiki/Call-with-current-continuation
 - https://en.wikipedia.org/wiki/Setcontext


### Outline
 - historical note (languages which have coroutines and languages which might get them in the future)
 - terms (?)
    - async vs sync (sequential, blocking) 
 - problems it solves (related to usecases, examples?)
    - no threads in js/lua
    - global lock in python
    - linux: 1M sockets, but only thousands on threads
        - switching to kernel mode and back is expensive
        - CPU cache misses
 - continuations
    - scheme examples
    - diagram
    - delimited (aka scoped) vs ...
 - coroutines
    - as a concept; diagram
		- Flavors of coroutines
		    - stackless (c#, scala, kotlin, ...) VS stackful (lisp, go, ...); are goroutines really coroutines or green threads?
		        Stackless:
	                - only local variables are available (one stack frame preserved)
                    - only top-level coroutine function can be suspended 
                    - no special stack handling is needed
                Stackful:
					- all stack frames above are also preserved
                    - can suspend from helper functions
                    - stack must be allocated on the side
		    - symmetric VS asymmetric
		      (An asymmetric coroutine knows its invoker, using a special operation to implicitly yield control specifically to its invoker. 
		      By contrast, all symmetric coroutines are equivalent; one symmetric coroutine may pass control to any other symmetric coroutine. 
		      Because of this, a symmetric coroutine must specify the coroutine to which it intends to yield control.)
    - concurrency vs parallelism (cooperative vs preemptive multitasking)
    - why use them?
        - single-threaded environment / global lock
        - cheaper than threads (e.g. if you need high level of concurrency), fibers; green-threads distinction
            - cheaper than "continuations" provided by OS
            - program-specific scheduling (unlike general-purpose platform/OS scheduler)
        - simpler code
            - generators
            - no nested callbacks
    - scheme coroutines via continuations
    - Lua and other languages examples
 - try/catch
    - coroutine composition
        - coTry() with nested coThrow() 
	- retriable exceptions (?)
 - ambiguous values (?)    
 - generators
    - basic example (differences between languages)
    - factorial stream example
    - stream from DB example
    - pull vs push (inverting control flow 🤔?)
 - async/await
    - basic example (differences between languages)
    - practical application (real world uses)
 - concurrency
    - thread = continuation (callstack) + scheduler 
	- actors
	- channels (like in Go)
	- dataflow / reactive
	- synchronous reactive programming (https://en.wikipedia.org/wiki/Synchronous_programming_language)
 - delimited continuations vs. monads
 - coroutines caveats
 - terminology and recap
    - CPS aka continuation passing style (don't confuse with "communicating sequential processes" aka CSP)
 - references 



### Look into
 - coroutines composability
    - compose better than monads
    - e.g. generator of ambiguous values


Try coroutines in:
 + Lua
    - https://www.lua.org/manual/5.1/manual.html#pdf-coroutine.create
 + JS
 + C#
 + Python
    - async with/for
        - https://www.python.org/dev/peps/pep-0492/#types-coroutine
    - http://www.dabeaz.com/coroutines/Coroutines.pdf
 -+ Go
 -+ Scheme
    - http://community.schemewiki.org/?call-with-current-continuation
 + Kotlin
 -+ C++ (boost)
    - http://www.boost.org/doc/libs/1_66_0/libs/context/doc/html/context/cc.html
    - http://www.boost.org/doc/libs/1_66_0/libs/coroutine2/doc/html/index.html
	- https://isocpp.org/files/papers/N4134.pdf
	- https://blogs.msdn.microsoft.com/vcblog/2017/05/19/using-c-coroutines-with-boost-c-libraries/
	- http://www.open-std.org/jtc1/sc22/wg21/docs/papers/2013/n3708.pdf
	- https://github.com/tonbit/coroutine
	- http://www.open-std.org/jtc1/sc22/wg21/docs/papers/2018/n4723.pdf
 - Haskell / F#
 - C
    - http://www.cplusplus.com/reference/csetjmp/longjmp/
    - http://www.1024cores.net/home/lock-free-algorithms/tricks/fibers
    - https://www.chiark.greenend.org.uk/~sgtatham/coroutines.html 
 - Scala
 - Ruby
 - Dart
 - Clojure

 
 
### Timeline
 - 1958: According to Donald Knuth, Melvin Conway coined the term coroutine in 1958 when he applied it to construction of an assembly program.
 - 1963: The first published explanation of the coroutine appeared later, in 1963
 - 1972: Scheme (https://en.wikipedia.org/wiki/History_of_the_Scheme_programming_language)
 - 2001: yield in Python 2.2 (https://www.python.org/download/releases/2.2) 
	- 2006: In Python 2.5, generators picked up some new features to allow "coroutines" (PEP-342). Most notably: a new send() method
 - 2005: yield in C# 2.0 (https://en.wikipedia.org/wiki/C_Sharp_(programming_language)#Versions)
 - 2012: async/await in C# 5.0 (https://en.wikipedia.org/wiki/C_Sharp_(programming_language)#Versions)
 - 2015: async/await in Python 3.5 (https://www.python.org/dev/peps/pep-0492/)
 - 2015: ECMAScript 2015 (https://en.wikipedia.org/wiki/ECMAScript#Versions)
 
 - One of the large new features in PHP 5.5 will be support for generators and coroutines.
    - https://nikic.github.io/2012/12/22/Cooperative-multitasking-using-coroutines-in-PHP.html
 - ES2018 http://2ality.com/2016/10/asynchronous-iteration.html

