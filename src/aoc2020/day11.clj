(ns aoc2020.day11
  (:require [aoc2020.util :as util]))

(defprotocol FerryMethods
  (onboard? [_ [r a]])
  (seat [_ [r a]])
  (seat? [_ [r a]])
  (all-seats [_])
  (occupied? [_ [r a]])
  (count-occupied [_])
  (updated [_ [r a] crit tol])
  (simulate [_ crit tol])
  (settle [_ crit tol]))

(defrecord Ferry [seats rows aisles]
  FerryMethods

  (onboard? [_ [r a]] (and (<= 0 r (dec rows)) (<= 0 a (dec aisles))))

  (seat [_ [r a]]
    (if
      (onboard? _ [r a])
      (nth (nth seats r) a)
      nil))

  (seat? [_ [r a]] (#{\L \#} (seat _ [r a])))

  (all-seats [_]
    (for [r (range 0 rows)
          a (range 0 aisles)
          :when (seat? _ [r a])]
      [r a]))

  (occupied? [_ [r a]] (and(= (seat _ [r a]) \#)))

  (count-occupied
    [_]
    (->> (all-seats _)
         (filter #(occupied? _ %))
         (count)))

  (updated
    [_ [r a] crit tol]
    (let [cur (seat _ [r a])
          occ (count (filter #(occupied? _ %) (crit [r a])))]
      (cond
        (and (= cur \L) (= occ 0))    \#     ;; empty, no neighbors -> occupied
        (and (= cur \#) (>= occ tol)) \L     ;; occupied, too many neighbors -> empty
        :else                         cur))) ;; unchanged

  (simulate
    [_ crit tol]
    (let [updated (->> (for
                         [r (range 0 rows)]
                         (vec (map #(updated _ [r %] crit tol) (range 0 aisles))))
                       (vec))]
      (->Ferry updated rows aisles)))

  (settle
    [_ crit tol]
    (loop [cur _ gen 0]
      (let [nxt (simulate cur crit tol)]
        (if
          (= (:seats nxt) (:seats cur))
          (do (println "settled in" gen "iterations") cur)
          (recur nxt (inc gen)))))))

(defn ferry
  [desc]
  (let [rows (map vec (clojure.string/split-lines desc))
        rct (count rows)
        act (count (first rows))]
      (->Ferry (vec rows) rct act)))

(def dirs
  (for
    [dx (range -1 2) dy (range -1 2) :when (not (= 0 dx dy))]
    [dx dy]))

(defn adj-seats
  [f [r a]]
  (->> (map (fn [[dx dy]] [(+ r dx) (+ a dy)]) dirs)
       (filter #(seat? f %))))

(defn adjacent-map
  [f]
  (->> (for [[r a] (all-seats f)] [[r a] (adj-seats f [r a])])
       (into {})))

(defn vis-seat
  [f [r a] [dx dy]]
  (loop [[x y] [r a]]
    (let [[nx ny] [(+ x dx) (+ y dy)]]
      (cond
        (seat? f [nx ny]) [nx ny]            ;; found
        (onboard? f [nx ny]) (recur [nx ny]) ;; keep going
        :else nil))))                        ;; give up

(defn vis-seats
  [f [r a]]
  (->> (map #(vis-seat f [r a] %) dirs)
       (filter some?)))

(defn visible-map
  [f]
  (->> (for [[r a] (all-seats f)] [[r a] (vis-seats f [r a])])
       (into {})))

(defn part1
  []
  (let [f (ferry (util/input "day11.txt"))
        a (adjacent-map f)
        r (settle f a 4)]
    (count-occupied r)))

(defn part2
  []
  (let [f (ferry (util/input "day11.txt"))
        a (visible-map f)
        r (settle f a 5)]
    (count-occupied r)))
