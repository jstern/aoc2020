(ns aoc2020.day8
  (:require [aoc2020.util :as util]))


(defn read-instruction
  [line]
  (let [matches (re-matches #"(\w+) (.*)" line)]
    (if matches {:ins (nth matches 1)
                 :val (Integer/valueOf (nth matches 2))})))


(defn instructions
  [inp]
  (->> (clojure.string/split-lines inp)
       (map read-instruction)
       (vec)))


(def update-acc-fn
  {"acc" (fn [acc value] (+ acc value))
   "jmp" (fn [acc value] acc)
   "nop" (fn [acc value] acc)})


;; TODO: worry about wrapping around?
(def next-index-fn
  {"acc" (fn [idx value] (inc idx))
   "jmp" (fn [idx value] (+ idx value))
   "nop" (fn [idx value] (inc idx))})


(defn run
  ([all]
   (run 0 0 #{} all))
  ([acc idx prev all]
   (if (prev idx) ;; we've been here before, return acc
     acc
     (let [cur (nth all idx)
           ins (cur :ins)
           value (cur :val)
           next-acc (update-acc-fn ins)
           next-idx (next-index-fn ins)]
       (run (next-acc acc value)
            (next-idx idx value)
            (conj prev idx)
            all)))))


(defn part1 [] (run (instructions (util/input "day8.txt"))))
(defn part2 [] "")
