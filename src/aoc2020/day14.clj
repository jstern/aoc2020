(ns aoc2020.day14
  (:require [aoc2020.util :as util]))


(defn replace-x [s x] (clojure.string/replace s #"X" x))


(defn make-mask
  "given a string describing a mask, return a function
   that applies the mask to an integer"
  [desc]
  (comp
    (partial bit-and (Long/valueOf (replace-x desc "1") 2))
    (partial bit-or  (Long/valueOf (replace-x desc "0") 2))))

(defn write
  "return memory with updated masked value at register"
  [memory lhs rhs mask]
  (let [register lhs ;; for now, let's not bother transforming mem[xxx] at all
        value (Long/valueOf rhs)]
    (assoc memory register (mask value))))

(defn execute
  [[memory mask] instr]
  (let [[lhs rhs] (clojure.string/split instr #" = ")]
    (if (= lhs "mask")
      [memory (make-mask rhs)]
      [(write memory lhs rhs mask) mask])))

(defn init
  [instructions]
  (let [state [{} nil]]
    (reduce execute state instructions)))

(defn part1
  []
  (let [[memory _] (->> (util/input "day14.txt")
                        (clojure.string/split-lines)
                        (init))]
    (reduce + (vals memory))))

(defn part2 [] "")
