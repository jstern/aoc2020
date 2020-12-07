(ns aoc2020.day2
  (:require [aoc2020.util :as util]))


(defn char-freqs
  [cs]
  (frequencies (seq (char-array cs))))


(defn num-occurs
  [c cs]
  (get (char-freqs cs) c 0))

;; generically define a policy as having
;; a version and 3 positional attributes
(defrecord Policy [version p1 p2 p3])

;; and an Entry is a password and a policy
(defrecord Entry [password policy])

(defmulti valid? (fn [e] (:version (:policy e))))

(defmethod valid? :v1 [entry]
  (let [text   (:password entry)
        policy (:policy entry)
        c-min  (:p1 policy)
        c-max  (:p2 policy)
        c-freq (num-occurs (:p3 policy) text)]
    (and (>= c-freq c-min) (<= c-freq c-max))))

(defmethod valid? :v2 [entry] false)

(def entry-re #"(\d+)-(\d+) (.): (.+)")

(defn parse-entry
  [version raw]
  (let [match (re-matches entry-re raw)
        [_ p1 p2 p3 password] match]
    (->Entry
      password
      (->Policy version
                (Integer/parseInt p1)
                (Integer/parseInt p2)
                (first (seq (char-array p3)))))))

(defn entries
  [version]
  (->> (util/input "day2.txt")
       (clojure.string/split-lines)
       (map #(parse-entry version %))))

(defn part1
  []
  (count (for [entry (entries :v1) :when (valid? entry)] 1)))
