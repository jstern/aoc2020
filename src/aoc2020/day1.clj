(ns aoc2020.day1
  (:require [aoc2020.util :as util]))

(defn find-value
  [n values]
  ;; return the value such that n - value is in the set of values
  (first (filter #(not (nil? (get values (- n %)))) values)))

(defn part1
  []
  (let [input  (util/input "day1.txt")
        values (set (map #(Integer/parseInt %) (clojure.string/split-lines input)))
        value  (find-value 2020 values)]
    (* value (- 2020 value))))
