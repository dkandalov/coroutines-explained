
    (define (task-1 do-other-tasks)
        (for-each (lambda (item)
            (display "🐶 -- ")
            (display item)
            (newline)
            (set! do-other-tasks (call/cc do-other-tasks))
            (display "🐶 working\n")
            (set! do-other-tasks (call/cc do-other-tasks))
            (display "🐶 working\n")
            (set! do-other-tasks (call/cc do-other-tasks))
        ) '(1 2 3))
    )

    (define (task-2 do-other-tasks)
        (let loop ()
          (for-each (lambda (item)
              (display "🐷 ")
              (display item)
              (newline)
              (set! do-other-tasks (call/cc do-other-tasks))
          ) '(1 2 3 4 5))
         (loop))
    )

    (define (main args)
        (task-1 task-2)
    )
