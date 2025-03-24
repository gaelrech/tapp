(ns tapp.core
  (:require [cats.core :as m]
            [cats.monad.state :as state]
            [clj-stacktrace.core :as stacktrace]
            [tapp.printer :as printer]
            [portal.viewer]))

(defn meta-able?
  [value]
  (and (not (instance? clojure.lang.Range value))
       (or (instance? clojure.lang.IObj value)
           (var? value))))

(defmacro call-information
  []
  `(->> (.getStackTrace (Thread/currentThread))
        stacktrace/parse-trace-elems
        (filterv #(true? (:clojure %)))
        first))

(defmacro p
  [form]
  `(let [call-info# (call-information)
         metadata# {:dev/code (portal.viewer/pprint '~form)
                    :dev/fn   (:fn call-info#)
                    :dev/line (:line call-info#)
                    :dev/ns   (:ns call-info#)}]
     (if (state/state? ~form)
       (m/fmap #(printer/prnt % metadata#) ~form)
       (printer/prnt ~form metadata#))))

(defmacro t
  [form]
  `(let [{:keys [line# ns# fn#]} (call-information)
         metadata# {:dev/code (portal.viewer/pprint '~form)
                    :dev/fn   fn#
                    :dev/line line#
                    :dev/ns   ns#}]
     (if (state/state? ~form)
       (m/fmap #(tap> (with-meta % metadata#)) ~form)
       (do (if (meta-able? ~form)
             (tap> (with-meta ~form metadata#))
             (tap> (with-meta [:portal.viewer/pprint ~form] (merge metadata# {:portal.viewer/default :portal.viewer/hiccup}))))
           ~form))))

;; Wrappers for data_readers

(defn p*
  [form]
  `(p ~form))

(defn t*
  [form]
  `(t ~form))
