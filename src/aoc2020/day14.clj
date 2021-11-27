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

;; part2

(defn nth-bit
  [addr n]
  (if (bit-test addr n) \1 \0))

;; 0+? = ?, 1+? = 1, X+? = X
(defn mask-bit
  [mbit abit]
  (if (= mbit \0) abit mbit))

(defn addrs
  "apply mask to addr to return an 'address space'"
  [mask addr]
  (let [m (char-array mask)
        v (Long/valueOf (re-find #"\d+" addr))
        c (count m)]
    (->> (range c)
         (map #(mask-bit (nth m %) (nth-bit v (- c % 1))))
         (apply str))))  ;; turn back into string for my poor eyes

(defn disjoint?
  [b1 b2]
  (and
    (not (= b1 b2)) ;; they're different
    (not (or (= b1 \X) (= b2 \X))))) ;; and neither is X


(def subtract-bit
  ;; what's left if you take away b2 from b1
  {
   [\0 \0] \0
   [\0 \1] \0
   [\0 \X] \0

   [\1 \0] \1
   [\1 \1] \0
   [\1 \X] \0

   [\X \0] \X
   [\X \1] \0
   [\X \X] \0

})

(defn minval
  [addrs]
  (Long/valueOf (clojure.string/replace addrs #"X" "0") 2))

(defn maxval
  [addrs]
  (Long/valueOf (clojure.string/replace addrs #"X" "1") 2))

(defn nth2 [n] (long (Math/pow 2 n)))

(defn subtract
  "return a version of a1 that does not include any addr in a2"
  [a1 a2]
  (let [c (count a1)]
    
    (loop [n c acc '()]
      (if (= n 0)
        (let [i (- n 1) b1 (nth a1 i) b2 (nth a2 i)]
          (if (disjoint? b1 b2)
            (do (println "disjoint!") a1)
            (recur (dec n) (conj acc (subtract-bit [b1 b2])))))))))

(defn write2
  [memory addrs v]
  (let [updated (->> (for [[a av] memory] [(subtract addrs a) av])
                     (into []))]
    (conj updated [addrs v])))


(defn execute2
  [[memory mask] instr]
  (let [[lhs rhs] (clojure.string/split instr #" = ")]
    (if (= lhs "mask")
      [memory rhs] ;; save mask and move on
      (let [masked (addrs mask lhs)]
        (println masked)
        (println " " (minval masked))
        (println " " (maxval masked))
        [(write2 memory masked (Long/valueOf rhs)) mask]))))

(defn init2
  [instructions]
  (let [state [[] nil]]
    (reduce execute2 state instructions)))


;; how many actual bits is a "bit" worth?
(def bitval {\0 1 \1 1 \X 2 \- 0})

(defn size
  [bs]
  (reduce * (map bitval bs)))

(defn part2
  []
  (let [[memory _] (->> (util/input "day14.txt")
                        (clojure.string/split-lines)
                        (init2))]
    (println (first memory))
    (println (first (map #(* (size (first %)) (second %)) memory)))))

;; wrong answer
;; 2757059467008 too low
