#lang racket
;CSI2120 Project - Part 2
;Arin Barak - 300280812
;Ben Miller - 300297574


;Getting the file names in the directory.
(define (list-files-in-directory directory-path)
  (map path->string (directory-list directory-path)))


;Reading the file from the directory and putting it into a list.
(define (put-file-in-list dir filename)
  (let((path (string-append (string-append dir "/") filename)))
    (cond ((equal? (substring path (- (string-length path) 3) (string-length path)) "txt")
           (cons filename (file->list path)))
          (else '())
           )))


;Compares the lists of two histograms.
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
(define (go-through-datasets dataset dir)
  (cond ((null? dataset)
         '())
        (else
         ;putting the histogram lists in another list.
         (cons (put-file-in-list dir (car dataset)) (go-through-datasets (cdr dataset) dir)))))


;Comparing all dataset histograms with the query histogram.
(define (comparing-all query data-list)
  (cond ((null? data-list)
         '())
        ((null? (car data-list))
         (comparing-all query (cdr data-list)))
        (else 
         (let ((result-num (compare query (cdar data-list))))
           (cons (cons (caar data-list) result-num) (comparing-all query (cdr data-list)))))))
               


;To sort nested list by the second value (aka comparaison result).
(define (sort-nested-by-second lst)
  (sort lst
        (lambda (x y)
          (let ((x-second (if (pair? (cdr x)) (cadr x) 0))
                (y-second (if (pair? (cdr y)) (cadr y) 0)))
            (> x-second y-second)))))

;Prints the 5 closest pictures.
(define (print-results res i)
  (if (= i 5)
      '()
      (cons (caar res) (print-results (cdr res) (+ i 1)))))

;Creates the list for the query image histogram from its txt file.
(define (query-txt-to-list filename)
    (file->list filename))

;Sorting by the comparaison result.
(define (sort-by-number lst)
  (define (compare x y)
    (> (cdr x) (cdr y)))
  (sort lst compare))



;Main program
(define (similaritySearch queryHistogramFilename imageDatasetDirectory)
  ;when running the program: the query image's histogram txt file and dataset directory must be in the same directory as this code.
  ; (similaritySearch "q00.txt" "imageDataset2_15_20")

  (let ((querylist (query-txt-to-list queryHistogramFilename)))
   (let ((res-list (sort-by-number
       (comparing-all querylist (go-through-datasets (list-files-in-directory imageDatasetDirectory) imageDatasetDirectory)))))
       (print-results res-list 0))))


  
