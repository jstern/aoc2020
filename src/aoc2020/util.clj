(ns aoc2020.util
  (:require [clojure.java.io :as io]))

(defn input
  "Read input from file into string"
  [filename]
  (slurp (io/resource filename)))
