(ns aoc2020.day13
  (:require [aoc2020.util :as util]))

(defn parse
  [input]
  (let [[l1 l2] (clojure.string/split-lines input)
        ts (Integer/valueOf l1)
        frq (->> (clojure.string/split l2 #",")
                 (filter #(not (= % "x")))
                 (map #(Integer/valueOf %)))]
    [ts frq]))

(defn next-departure
  [ts freq]
  (let [t (Math/ceil (/ ts freq))
        n (* freq t)]
    (int n)))

(defn part1 []
  (let [[ts frqs] (parse (util/input "day13.txt"))
        [wait id] (->> (for [f frqs] [(- (next-departure ts f) ts) f])
                       (sort-by #(first %))
                       (first))]
    (* wait id)))


(defn parse2
  "get [freq offset] pairs in descending order of freq"
  [input]
  (let [[_ l2] (clojure.string/split-lines input)
        df (->> (clojure.string/split l2 #",")
                (map-indexed (fn [i v] [v i]))
                (filter #(not (= "x" (first %))))
                (map (fn [[v i]] [(Integer/valueOf v) i]))
                (sort-by #(* -1 (first %))))]
    df))

(defn find-t
  ;; assume constraints [freq offset] are in descending order of frequency
  ;; first constraint [f1 o1] will always match at (f1 - o1) + (n * f1),
  ;; so take (f1 - o1) as our initial time t0 and f1 as our step size
  ;; then, look for the first t = t0 + (n * f1) where the next constraint [f2 o2]
  ;; matches; when we find it, our step becomes (n * f1 * f2)
  ;; so each time we find another match, our step becomes larger and we have
  ;; fewer intervening candidate times to check
  [constraints]
  (let [[f1 o1] (first constraints)]
    (println "starting with" [f1 o1] "t =" (- f1 o1) "step =" f1)
    (loop [t    (- f1 o1)
           step f1
           cs   (rest constraints)]
      (if (empty? cs)
        t
        (let [[f2 o2] (first cs)]
          (if (= 0 (rem (+ t o2) f2))
            ;; works for next constraint
            ;; start looping from t with bigger step over remaining cs
            (do
              (println "matched" [f2 o2] "at" t "; step " step "->" (* step f2))
              (recur t (* step f2) (rest cs)))
            ;; doesn't work, keep looping with current step and cs
            (recur (+ t step) step cs)))))))


(defn part2
  []
  (let [constraints (parse2 (util/input "day13.txt"))]
    (find-t constraints)))
