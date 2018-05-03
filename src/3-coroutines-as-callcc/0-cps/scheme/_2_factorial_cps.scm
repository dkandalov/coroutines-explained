




				(define (factorial n continuation)
					(if (= n 0) (continuation 1)
					(factorial (- n 1) (lambda (result)
						(continuation (* n result))
					)))
				)

				(define (main args)
					(factorial 5 (lambda (result)
						(display result)
					))
				)

