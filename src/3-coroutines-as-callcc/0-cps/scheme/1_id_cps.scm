




				(define (identity value continuation)
					(continuation value)
				)

				(define (main args)
					(identity 42 (lambda (value)
						(display value)
					))
				)

