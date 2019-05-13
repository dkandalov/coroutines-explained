


            (define (main args)
                (define count 0)

                (print (+ 100 (call/cc (lambda (continuation)
                    (set! saved-continuation continuation)
                    (saved-continuation 100)
                    (print "🙈")
                ))))

                (if (< count 3) (begin
                    (set! count (+ 1 count))
                    (print "🚀")
                    (saved-continuation count)
                ))
            )

			(define (print message)
			 (display message)
			 (newline)
			)
