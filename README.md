# Tapp

This is a spin-off from the already excellent [weavejester/hashp](https://github.com/weavejester/hashp/tree/master), including some improvements for my own personal needs.

Like Hashp, it serves as a more intuitive printer for development, allowing to more easily print results in Clojure code without breaking structure.

## Usage

Once installed, you can add `#p` in front of any form you wish to
print:

```clojure
(ns example.core)

(defn mean [xs]
  (/ (double #p (reduce + xs)) #p (count xs)))
```

## Tapp vs. Hashp

At its core, its the same idea. The main differences are mainly the following:

1. Support for State monads
To facilitate usage of tapp in state-flow tests, it detects if the form associated to the `#p` returns a State monad and, if it does, it will correctly envelop the printing to the monadic structures.

2. Exception printing
If the `#p` is associated to a form that throws an exception, in Hashp it will not print anything. In Tapp, it will print the exception itself and then return the exception to the program flow as expected.

## Installation

### Leiningen

To make `#p` globally available to all Leiningen projects, add the
following to `~/.lein/profiles.clj`:

```edn
{:user
 {:dependencies [[dev.weavejester/hashp "0.4.0"]]
  :injections [(require 'hashp.preload)]}}
```
