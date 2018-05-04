(use http-client)

(define (main args)
	(display (with-input-from-request "http://wiki.call-cc.org/" #f read-string))
)
