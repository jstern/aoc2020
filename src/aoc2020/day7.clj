(ns aoc2020.day7
  (:require [aoc2020.util :as util]
            [clojure.set]))


(defn parse-single-contained
  "Interpret one clause of the right side of a rule"
  [raw]
  (let [matches (re-matches #"(\d+) (\w+ \w+).*" raw)]
    (if matches [(nth matches 2) (Integer/valueOf (nth matches 1))])))


(defn parse-contained
  "Interpret the entire right side of a rule"
  [raw]
  (->> (clojure.string/split raw #", ")
       (filter #(not (= % "no other bags")))
       (map parse-single-contained)
       (into {})))


(defn parse-raw-rule
  [raw]
  (let [[outer raw-contained] (clojure.string/split raw #" bags contain ")]
    [outer (parse-contained raw-contained)]))


(defn parse-rules
  [raw]
  (map parse-raw-rule
       (clojure.string/split raw #"\.\n")))


(defn invert-rel
  [rule]
  (let [vset #{(first rule)}]
    (into {} (for [k (keys (second rule))] [k vset]))))


(defn all-containers
  [rules]
  (apply merge-with into (map invert-rel rules)))


(defn find-containers
  ([subject containers]
   (find-containers subject containers #{}))
  ([subject containers res]
   (loop [found res
          subjects [subject]]
     (if (empty? subjects)
       found
       (let [subject-containers (->> (map #(get containers %) subjects)
                                     (filter some?)
                                     (reduce clojure.set/union))
             new-subjects (filter #(not (found %)) subject-containers)]
         (recur (clojure.set/union found subject-containers) new-subjects))))))


(defn part1
  []
  (->> (util/input "day7.txt")
       (parse-rules)
       (all-containers)
       (find-containers "shiny gold")
       (count)))


(defn count-bags
  [rules bag n]
  (let [bags  (get rules bag)]
    (+ n (* n (reduce + (map #(apply count-bags rules %) (seq bags)))))))


(defn part2
  []
  (let [rules (->> (util/input "day7.txt")
                   (parse-rules)
                   (into {}))]
    (- (count-bags rules "shiny gold" 1) 1)))
