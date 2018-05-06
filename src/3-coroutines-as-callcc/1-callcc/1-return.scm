


            (define (log message)
                (display message)
                (newline)
            )

            (define (main args)
                (log 1)
                (log (call/cc (lambda (continuation)
                    (log 2)
                    (continuation 3)
                    (log "🙈")
                )))
                (log 4)
            )
