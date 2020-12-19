(ns aoc2020.day6
  (:require [aoc2020.util :as util]
            [clojure.set]))


(defn grouper
  [agg]
  (fn [individuals](reduce agg (map set individuals))))


(defn groups
  [agg input]
  (->> (clojure.string/split input #"\n\n")
       (map clojure.string/split-lines)
       (map (grouper agg))))


(defn count-all
  [agg-method]
  (reduce + (map count (groups agg-method (util/input "day6.txt")))))


(defn part1 [] (count-all clojure.set/union))
(defn part2 [] (count-all clojure.set/intersection))
