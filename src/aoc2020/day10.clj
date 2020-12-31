(ns aoc2020.day10
  (:require [aoc2020.util :as util]))


(defn count-diff
  [acc cur]
  (let [[prev d1 d3] acc]
    (if
      (= (- cur prev) 1) ;; assumption: always 1 or 3
      [cur (inc d1) d3]
      [cur d1 (inc d3)])))


(defn count-diffs
  [adapters]
  (let [res (reduce count-diff [0 0 0] adapters)]
    ;; res is [last d1 d3]
    ;; we're interested in d1 and d3 + 1
    ;; (the diff of 3 between last adapter and device)
    [(second res) (inc (last res))]))


(defn adapters
  []
  (->> (util/input "day10.txt")
       (clojure.string/split-lines)
       (map #(Integer/valueOf %))))


(defn part1
  []
  (->> (adapters)
       (sort)
       (count-diffs)
       (reduce *)))

;; lots of failed ideas deleted here!

;; bah, preloading instead of trying to figure out
;; memoizing without shared state :(
(def available (set (adapters)))


(defn prev
  [n]
  (->> (range 1 4)
       (map #(- n %))
       (filter #(or (available %) (= 0 %)))))


(def paths
  (memoize
    (fn [n]
      (println "paths" n)
      (if
        (= 0 n)
        1
        (reduce + (map #(paths %) (prev n)))))))


(defn part2
  []
  (paths (apply max available)))
