(ns tapp.core
  (:require [tapp.internals.wrappers :as wrappers]
            [portal.viewer]))
(defmacro p
  [form]
  `(wrappers/wrap ~form wrappers/print*))

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
