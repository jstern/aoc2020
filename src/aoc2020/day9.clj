(ns aoc2020.day9
  (:require [aoc2020.util :as util]))


(defn valid?
  [n addends]
  (->> (map #(- n %) addends)
       (filter #(addends %))
       (filter #(not (= n (* 2 %))))
       (first)
       (boolean)))


(defn find-invalid
  [preamble data]
  (loop [i preamble]
    (let [n (nth data i)
          a (set (subvec data (- i preamble) i))]
      (if
        (not (valid? n a)) n  ;; if invalid, return n
        (recur (inc i))))))   ;; slide to next index


(defn part1
  []
  (->> (util/input "day9.txt")
       (clojure.string/split-lines)
       (map #(Long/valueOf %))
       (vec)
       (find-invalid 25)))


(defn test-summing-sequences
  [target data start]
  (loop [end (inc start)]
    (let [run (subvec data start end)
          sum (reduce + run)]
      (cond
        (> sum target) nil ;; no summing sequence at start
        (= sum target) run ;; this run summed to the target
        :else (recur (inc end))))))


(defn find-summing-sequence
  [target data]
  (loop [i 0]
    (let [res (test-summing-sequences target data i)]
      (if (nil? res) (recur (inc i)) res))))


(defn part2
  []
  (let [res (->> (util/input "day9.txt")
                 (clojure.string/split-lines)
                 (map #(Long/valueOf %))
                 (vec)
                 (find-summing-sequence 21806024))]
    (+ (apply min res) (apply max res))))
