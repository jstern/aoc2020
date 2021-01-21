(ns aoc2020.day4
  (:require [aoc2020.util :as util]
            [clojure.set]
            [clojure.spec.alpha :as s]))


(def required? #{::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid})

(defn complete?
  [passport] (= required?
     (clojure.set/intersection required?
                               (set (keys passport)))))

(s/def ::passport
  (s/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
          :opt [::cid]))


(defn parse-int
  ([v] (parse-int v 10))
  ([v b]
   (try
     (Integer/valueOf v b)
     (catch Exception ex
       ;;(println (type ex) (.getMessage ex))
       nil))))


(defn between
  ([lo hi]
   (fn [v]
     (let [parsed (parse-int v)]
       (and parsed (>= parsed lo) (<= parsed hi))))))


(def valid-byr (between 1920 2002))
(def valid-iyr (between 2010 2020))
(def valid-eyr (between 2020 2030))
(s/def ::byr valid-byr)
(s/def ::iyr valid-iyr)
(s/def ::eyr valid-eyr)


(defn valid-hgt
  [v]
  (let [matches (re-matches #"(\d+)(.*)" (or v ""))]
    (if matches
      (cond
        (= (nth matches 2) "in") ((between 59 76) (nth matches 1))
        (= (nth matches 2) "cm") ((between 150 193) (nth matches 1))
        :else false)
      false)))
(s/def ::hgt valid-hgt)


(defn valid-hcl [v] (re-matches #"#[0-9a-f]{6}" (or v "")))
(s/def ::hcl valid-hcl)

(defn valid-ecl [v] (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} v))
(s/def ::ecl valid-ecl)

(defn valid-pid [v] (re-matches #"\d{9}" (or v "")))
(s/def ::pid valid-pid)


(defn read-field
  [kvstr]
  (let [[k v] (clojure.string/split kvstr #":")]
    [(keyword "aoc2020.day4" k) v]))


(defn read-passport
  [pvec]
  (into {} (map #(read-field %) pvec)))

(defn passports
  [batch]
  (->> (clojure.string/split batch #"\n\n")
       (map #(clojure.string/split % #"\s"))
       (map #(read-passport %))))


(defn part1 []
  (count (filter #(complete? %) (passports (util/input "day4.txt")))))


(defn valid-spec?
  [doc]
  (let [valid (s/valid? ::passport doc)]
    ;; (println doc "-" valid)
    ;; (if (not valid) (println (s/explain ::passport doc)))
    valid))


(defn valid-manual?
  [doc]
  (let [valid (and
    (valid-byr (::byr doc))
    (valid-iyr (::iyr doc))
    (valid-eyr (::eyr doc))
    (valid-hgt (::hgt doc))
    (valid-ecl (::ecl doc))
    (valid-hcl (::hcl doc))
    (valid-pid (::pid doc)))]
    ;; (println doc "-" valid)
    (if (not valid) (println (s/explain ::passport doc)))
    valid))


(defn part2-spec []
  (count (filter #(valid-spec? %) (passports (util/input "day4.txt")))))


(defn part2-manual []
  (count (filter #(valid-manual? %) (passports (util/input "day4.txt")))))


(defn part2 [] (part2-spec))

(defn countall []
  (count (passports (util/input "day4.txt"))))


(defn printall []
  (doseq [[i p] (map-indexed (fn [x y] [x y]) (passports (util/input "day4.txt")))]
    (println i (s/valid? ::passport p))))


(defn examine []
  (doseq [[i p] (map-indexed (fn [x y] [x y]) (passports (util/input "day4.txt")))]
    (if (#{236} i)
      (do
        ;; (println i)
        ;; (println p)
        (s/explain ::passport p)))))
