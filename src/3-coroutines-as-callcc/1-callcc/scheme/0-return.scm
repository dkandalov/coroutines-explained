


            (define (print message)
                (display message)
                (newline)
            )

            (define (main args)
                (print 1)
                (print (call/cc (lambda (continuation)
                    (print 2)
                    (continuation 3)
                    (print "🙈")
                )))
                (print 4)
            )
