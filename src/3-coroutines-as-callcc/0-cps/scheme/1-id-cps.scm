






				(define (identity-cps value continuation)
					(continuation value)
				)

				(define (main args)
					(identity-cps 42 (lambda (value)
						(display value)
					))
				)

