(ns tapp.internals.wrappers
  (:require [cats.core :as m]
            [cats.monad.state :as state]
            [clj-stacktrace.core :as stacktrace]
            [puget.color.ansi :as color]
            [puget.printer :as puget]))

(def print-opts
  (merge puget/*options*
         {:print-color    true
          :namespace-maps true
          :color-scheme
          {:nil [:bold :blue]}}))

(defn print*
  [form metadata]
  (println
   (str (color/sgr "print>" :red)
        (color/sgr (str "[" (:dev/ns metadata) "/" (:dev/fn metadata) ":" (:dev/line metadata) "]") :green)  "\n"
        (puget/pprint-str (:dev/code metadata) print-opts)  "\n"
        "=> "
        (puget/pprint-str form print-opts)))
  form)

(defn meta-able?
  [value]
  (and (not (instance? clojure.lang.Range value))
       (or (instance? clojure.lang.IObj value)
           (var? value))))

(defmacro call-information
  []
  (->> (.getStackTrace (RuntimeException. "dummy"))
       stacktrace/parse-trace-elems
       (filterv #(true? (:clojure %)))
       first))

(defmacro tap
  [form metadata]
  `(do
     (if (meta-able? ~form)
       (tap> (with-meta ~form ~metadata))
       (tap> (with-meta [:portal.viewer/pprint ~form] (merge ~metadata {:portal.viewer/default :portal.viewer/hiccup}))))
     ~form))

(defmacro wrap
  [form tap-fn]
  `(let [tap-fn# ~tap-fn
         call-info# (call-information)
         metadata# {:dev/code '~form
                    :dev/fn   (:fn call-info#)
                    :dev/line (:line call-info#)
                    :dev/ns   (:ns call-info#)}]
     (if (state/state? ~form)
       (m/fmap #(tap-fn# % metadata#) ~form)
       (tap-fn# ~form metadata#))))
