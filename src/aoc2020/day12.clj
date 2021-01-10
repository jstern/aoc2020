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

(def ship1 {:heading "E" "N" 0 "E" 0 "S" 0 "W" 0})

(defn h2d [h] (get {"N" 0 "E" 90 "S" 180 "W" 270} h))
(defn d2h [d] (get {0 "N" 90 "E" 180 "S" 270 "W"} d))

(defn parse
  [instruction]
  [(subs instruction 0 1) (Integer/valueOf (subs instruction 1))])

(defn turn1
  [heading direction amount]
  (let [deg (h2d heading)]
    (-> (get turndirs direction)
        (* amount)
        (+ deg)
        (mod 360)
        (d2h))))

(defn nav1
  [ship instruction]
  (let [[action amount] (parse instruction)]
    (condp clojure.string/includes? action
      "LR"   (update ship :heading turn1 action amount)
      "NSEW" (update ship action + amount)
      "F"    (update ship (:heading ship) + amount))))

(defn part1
  []
  (let [end (->> (util/input "day12.txt")
                 (clojure.string/split-lines)
                 (reduce nav1 ship1))]
    (+ (Math/abs (- (get end "N") (get end "S")))
       (Math/abs (- (get end "E") (get end "W"))))))

(comment
    Action N means to move the waypoint north by the given value.
    Action S means to move the waypoint south by the given value.
    Action E means to move the waypoint east by the given value.
    Action W means to move the waypoint west by the given value.
    Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
    Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
    Action F means to move forward to the waypoint a number of times equal to the given value.)

;; this time lets do position [x y] waypoint [x y]
(def ship2 {:p [0 0] :w [10 1]})

;; 90 degree rotations of waypoint
(defn rgt [[x y]] [y (* -1 x)])
(defn lft [[x y]] [(* -1 y) x])

(defn turn2
  [[dx dy] action amount]
  (let [f (if (= action "L") lft rgt)
        n (mod (/ amount 90) 4)]
    (loop [i 0 r [dx dy]]
      (if (= i n)
        r
        (recur (inc i) (f r))))))

(defn shift2
  [[dx dy] action amount]
  (condp = action
    "N" [dx (+ dy amount)]
    "S" [dx (- dy amount)]
    "E" [(+ dx amount) dy]
    "W" [(- dx amount) dy]))

(defn move2
  [[x y] [dx dy] amt]
  [(+ x (* amt dx)) (+ y (* amt dy))])

(defn nav2
  [ship instruction]
  (let [[action amount] (parse instruction)]
    (condp clojure.string/includes? action
      "LR"   (update ship :w turn2 action amount)
      "NSEW" (update ship :w shift2 action amount)
      "F"    (update ship :p move2 (:w ship) amount))))

(defn part2
  []
  (let [end (->> (util/input "day12.txt")
                 (clojure.string/split-lines)
                 (reduce nav2 ship2))]
    (reduce + (map #(Math/abs %) (:p end)))))
