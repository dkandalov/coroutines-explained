




				(define (factorial n)
					(if (= n 0) 1
					(* n (factorial (- n 1))))
				)

				(define (main args)
					(display (factorial 5))
				)

