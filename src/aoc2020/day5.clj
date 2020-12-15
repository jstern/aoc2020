(ns aoc2020.day5
  (:require [aoc2020.util :as util]
            [clojure.set]))


(defn new-range
  [c start-range]
  (let [[lo hi] start-range]
    (cond
      (#{\F \L} c) [lo (+ lo (int (/ (- hi lo) 2)))]
      (#{\B \R} c) [(- hi (int (/ (- hi lo) 2))) hi])))


(defn find-val
  [instructions full-range]
  (loop [instrs instructions
         [lo hi] full-range]
    ;; (println instrs lo hi)
    (if (= lo hi)
      lo
      (recur
        (rest instrs)
        (new-range (first instrs) [lo hi])))))


(defn seat-id [r s] (+ (* 8 r) s))


(defn bp-seat-id
  [bp]
  (seat-id (find-val (subs bp 0 7) [0 127])
           (find-val (subs bp 7) [0 7])))


(defn observed-seat-ids
  []
  (->> (util/input "day5.txt")
       (clojure.string/split-lines)
       (map bp-seat-id)))


(defn part1
  []
  (apply max (observed-seat-ids)))


(def possible-seat-ids
  (into #{}
        (for [row (range 128)
              seat (range 8)]
          (seat-id row seat))))


(defn part2
  []
  (let [all-observed (set (observed-seat-ids))
        all-missing (clojure.set/difference possible-seat-ids all-observed)
        adjacent (filter #(and (all-observed (- % 1)) (all-observed (+ % 1))) all-missing)]
    (set adjacent)))

