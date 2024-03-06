#lang racket
;D = 3
; number of bins = 2 ^(D*3)

(define (compare h1 h2)
  ;histogram values are stored in lists.
  ;h1 is the first histogram list of a picture and h2 is the second to compare.
  ;number of bins is equal for both so the lenght of the lists are the same.
  (cond ((null? h1) 0)
        ((equal? (car h1) (car h2))
         (+ (car h2) (compare (cdr h1) (cdr h2))))
        ((< (car h1) (car h2))
         (+ (car h1) (compare (cdr h1) (cdr h2))))
        ((< (car h2) (car h1))
         (+ (car h2) (compare (cdr h1) (cdr h2)))))
  )