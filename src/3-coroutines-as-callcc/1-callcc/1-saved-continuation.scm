


            (define (log message)
                (display message)
                (newline)
            )

            (define (main args)
                (define count 0)

                (log (+ 100 (call/cc (lambda (continuation)
                    (set! saved-continuation continuation)
                    (continuation 100)
                    (log "🙈")
                ))))

                (if (< count 12) (begin
                    (set! count (+ 1 count))
                    (log "🚀")
                    (saved-continuation count)
                ))
            )
