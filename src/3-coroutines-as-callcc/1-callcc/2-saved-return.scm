


    (define (log message)
     (display message)
     (newline)
    )

    (define (main args)
     (define count 0)
     (log 1)
     (log (+ 100 (call/cc (lambda (return)
        (set! saved-return return)
        (log 2)
        (return 3)
        (log "💥")
     ))))
     (log 4)
     (if (< count 3) (begin
        (set! count (+ 1 count))
        (log "🚀")
        (saved-return count)
     ))
    )
