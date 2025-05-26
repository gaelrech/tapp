(ns tapp.core
  (:require [tapp.internals.wrappers :as wrappers]))

(defmacro p
  ([form]
   `(wrappers/wrap ~form wrappers/print*))
  ([x y] ;handle for thread-first and thread-last
   (cond
     (meta x) `(wrappers/wrap ~x wrappers/print*)
     (meta y) `(wrappers/wrap ~y wrappers/print*))))

(defmacro t
  [form]
  `(wrappers/wrap ~form wrappers/tap))

;; Wrappers for data_readers

(defn p*
  [form]
  `(p ~form))

(defn t*
  [form]
  `(t ~form))
