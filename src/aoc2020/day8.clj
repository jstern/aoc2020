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


(def next-index-fn
  {"acc" (fn [idx value] (inc idx))
   "jmp" (fn [idx value] (+ idx value))
   "nop" (fn [idx value] (inc idx))})


(defn alt-ins
  [ins]
  (get {"acc" "acc", "jmp" "nop", "nop" "jmp"} ins))


(defn run
  "Returns [acc-value terminated-normally]"
  ([all]
   (run 0 0 #{} all (count all) nil))
  ([all switch]
   (run 0 0 #{} all (count all) switch))
  ([acc idx prev all term switch]
   (cond
     (prev idx)   [acc false] ;; we've been here before, return acc
     (= idx term) [acc true]  ;; we're at the end, return acc
     :else (let [cur (nth all idx)
                 ins (cur :ins)
                 value (cur :val)
                 ;; if we're told to switch instructions for this index, do it
                 effective-ins (if (= idx switch) (alt-ins ins) ins)
                 ;; use effective instruction
                 next-acc ((update-acc-fn effective-ins) acc value)
                 next-idx ((next-index-fn effective-ins) idx value)
                 next-prev (conj prev idx)]
             ;; run next index with updated accumulator
             (run next-acc next-idx next-prev all term switch)))))


(defn part1 [] (run (instructions (util/input "day8.txt"))))

(defn non-accs
  [all]
  (->> (map-indexed (fn [idx itm] (if (= (get itm :ins) "acc") nil idx)) all)
       (filter some?)))


(defn find-corrupted
  [all]
  (loop [candidates (non-accs all)]
    (if (empty? candidates)
      nil ;; we tried them all and found nothing, return nil
      (let [candidate (first candidates)
            [acc finished?] (run all candidate)]
        (if finished?
          [acc candidate] ;; return accumulator value and corrupted index
          (recur (rest candidates)))))))


(defn part2 [] (find-corrupted (instructions (util/input "day8.txt"))))
