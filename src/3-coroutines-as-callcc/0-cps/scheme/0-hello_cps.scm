

            (define (display-cps message continuation)
                (display message)
                (continuation)
            )

            (define (main args)
                (display-cps "hello " (lambda ()
                    (display-cps "world" (lambda () #f))
                ))
            )

