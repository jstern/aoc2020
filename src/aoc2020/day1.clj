(ns aoc2020.day1
  (:require [aoc2020.util :as util]))

(defn int-set
  []
  (->> (util/input "day1.txt")
       (clojure.string/split-lines)
       (map #(Integer/parseInt %))
       (set)))

(defn find-values
  "return the vec [v y] such that y = n - v and y is in the set of values"
  [n values]
  (let [v (first (filter #(not (nil? (get values (- n %)))) values))
        y (if (nil? v) nil (- n v))]
    (cond
      (nil? v) nil  ;; we didn't find our values
      (= v y)  nil  ;; we only found v | 2v = n
      :else    [v y])))

(defn part1
  []
  (->> (int-set)
       (find-values 2020)
       (reduce *)))

(defn sum-pairs
  "map of x + y to #{x y}"
  [xs]
  (into {}
        (for [x1 xs
              x2 xs
              :when (not (= x1 x2))]
          [(+ x1 x2) #{x1 x2}])))

(defn part2
  "Get a map of x+y to x, y values
   Find 2020 - (x+y) in the original set of values
   Use that to look up x and y, return x * y * (2020 - (x+y))"
  []
  (let [zs   (int-set)
        xys  (sum-pairs zs)
        x+y  (first (filter #(contains? zs (- 2020 %)) (keys xys)))
        xy   (get xys x+y)]
    (* (reduce * xy) (- 2020 x+y))))
