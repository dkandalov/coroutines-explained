




				(define (factorial-cps n continuation)
					(if (= n 0) (continuation 1)
					(factorial-cps (- n 1) (lambda (result)
						(continuation (* n result))
					)))
				)

				(define (main args)
					(factorial-cps 5 (lambda (result)
						(display result)
					))
				)

