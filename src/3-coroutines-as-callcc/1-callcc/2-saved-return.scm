


            (define (log message)
              (display message)
              (newline)
            )

            (define (main args)
              (define count 0)
              (log 1)

              (log (+ 100 (call/cc (lambda (continuation)
                 (set! saved-continuation continuation)
                 (log 2)
                 (continuation 3)
                 (log "🙈")
              ))))
              (log 4)

              (if (< count 13) (begin
                 (set! count (+ 1 count))
                 (log "🚀")
                 (saved-continuation count)
              ))
            )
