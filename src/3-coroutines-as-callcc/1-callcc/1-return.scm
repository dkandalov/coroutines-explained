


    (define (log message)
     (display message)
     (newline)
    )

    (define (main args)
     (log 1)
     (log (call/cc (lambda (return)
        (log 2)
        (return 3)
        (log "💥")
     )))
     (log 4)
    )
