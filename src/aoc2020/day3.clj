(ns aoc2020.day3
  (:require [aoc2020.util :as util]))


(defn hit? [x y slope]
  (if (= \# (nth (nth slope y) x)) 1 0))


(defn count-hits
  [dx dy slope]
  (let [ymax (count slope)
        xmax (count (nth slope 0))]
    (loop [hits 0
           x 0
           y 0]
      (if (>= y ymax)
        hits
        (recur (+ hits (hit? x y slope))
               (mod (+ x dx) xmax)
               (+ y dy))))))


(defn part1
  []
  (->>
    (util/input "day3.txt")
    (clojure.string/split-lines)
    (count-hits 3 1)))


(defn part2
  []
  (let [slope (clojure.string/split-lines (util/input "day3.txt"))]
    (reduce
      *
      ;; (count-hits [dx dy slope]) for each [dx dy]
      (map
        #(apply count-hits (conj % slope))
        [[1 1] [3 1] [5 1] [7 1] [1 2]]))))
