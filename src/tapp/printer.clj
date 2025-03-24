(ns tapp.printer
  (:require [puget.color.ansi :as color]
            [puget.printer :as puget]))

(def print-opts
  (merge puget/*options*
         {:print-color    true
          :namespace-maps true
          :color-scheme
          {:nil [:bold :blue]}}))

(defn prnt
  [form metadata]
  (println
   (str (color/sgr "print>" :red)
        (color/sgr (str "[" (:dev/ns metadata) "/" (:dev/fn metadata) ":" (:dev/line metadata) "]") :green)  "\n"
        (puget/pprint-str (:dev/code metadata) print-opts)  "\n"
        "=> "
        (puget/pprint-str form print-opts)))
  form)
