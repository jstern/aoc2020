(ns aoc2020.day12
  (:require [aoc2020.util :as util]))

(comment
    Action N means to move north by the given value.
    Action S means to move south by the given value.
    Action E means to move east by the given value.
    Action W means to move west by the given value.
    Action L means to turn left the given number of degrees.
    Action R means to turn right the given number of degrees.
    Action F means to move forward by the given value in the
             direction the ship is currently facing.)

(def turndirs {"L" -1 "R" 1})

(def ship {:heading "E" "N" 0 "E" 0 "S" 0 "W" 0})

(defn h2d [h] (get {"N" 0 "E" 90 "S" 180 "W" 270} h))
(defn d2h [d] (get {0 "N" 90 "E" 180 "S" 270 "W"} d))

(defn turn
  [heading direction amount]
  (let [deg (h2d heading)]
    (-> (get turndirs direction)
        (* amount)
        (+ deg)
        (mod 360)
        (d2h))))

(defn nav1
  [ship instruction]
  (let [action (subs instruction 0 1)
        amount (Integer/valueOf (subs instruction 1))]
    (println ship instruction)
    (condp clojure.string/includes? action
      "LR"   (update ship :heading turn action amount)
      "NSEW" (update ship action + amount)
      "F"    (update ship (:heading ship) + amount))))

(defn part1
  []
  (let [end (->> (util/input "day12.txt")
                 (clojure.string/split-lines)
                 (reduce nav1 ship))]
    (+ (Math/abs (- (get end "N") (get end "S")))
       (Math/abs (- (get end "E") (get end "W"))))))
(defn part2 [] "")
