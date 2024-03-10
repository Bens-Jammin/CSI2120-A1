#lang racket

;Getting the file names in the directory.
;+
(define (list-files-in-directory directory-path)
  (map path->string (directory-list directory-path)))

;Reading the file from the directory and putting it into a list.
;+
(define (put-file-in-list dir filename)
  (let((path (string-append (string-append dir "/") filename)))
    (cond ((equal? (substring path (- (string-length path) 3) (string-length path)) "txt")
           (file->list path) ; if created it compare it)
           ))))

;+
(define (compare h1 h2)
  ;Histogram values are stored in lists.
  ;h1 is the first histogram list of a picture and h2 is the second to compare.
  (cond ((null? h1) 0)
        ((null? h2) 0)
        ((equal? (car h1) (car h2))
         (+ (car h2) (compare (cdr h1) (cdr h2))))
        ((< (car h1) (car h2))
         (+ (car h1) (compare (cdr h1) (cdr h2))))
        ((< (car h2) (car h1))
         (+ (car h2) (compare (cdr h1) (cdr h2)))))
  )


;Constructs list for all dataset txt files.
;+
(define (go-through-datasets dataset dir)
  (cond ((null? dataset)
         '())
        (else
         ;putting the histogram lists in another list.
         (cons (put-file-in-list dir (car dataset)) (go-through-datasets (cdr dataset) dir)))))


;Comparing all dataset histograms with the query histogram.
;+
(define (comparing-all query data-list)
  (cond ((null? data-list)
         '())
        (else 
         (let ((result-num (compare query (cdar data-list))))
           (cons (cons (caar data-list) result-num) (comparing-all query (cdr data-list)))))))
               


;To sort nested list by the second value (aka comparaison result).
;+
(define (sort-nested-by-second lst)
  (sort lst
        (lambda (x y)
          (let ((x-second (if (pair? (cdr x)) (cadr x) 0))
                (y-second (if (pair? (cdr y)) (cadr y) 0)))
            (> x-second y-second)))))

;Prints the 5 closest pictures.
(define (print-results res i)
  (cond ((equal? i 5)
         '())
        (display (car res)) (print-results (cdr res (+ i 1)))))



;Main program
(define (similaritySearch queryHistogramFilename imageDatasetDirectory)
  ;get the query points txt file and read it into a list
  ;get the file names from the directory
    ;read the txt files and get their histograms in a list
    ;compare the query and dataset hist list
    ;put it into the closest pics pair list
      ;compare the result with the min result, if greater than it put it it and sort the results list

  ;only problem is to create histogram list for the query point and call the below function with that list
   (let ((res-list (sort-nested-by-second
       (comparing-all queryHistogramFilename (go-through-datasets (list-files-in-directory imageDatasetDirectory) imageDatasetDirectory)))))
       (print-results res-list 0)))

  
