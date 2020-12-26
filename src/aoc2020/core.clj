(ns aoc2020.core
  (:gen-class))

(defn solution-fn
  "return solution function from day/part
   where day is a namespace within aoc2020
   and part is a function in that ns"
  [day part]
  (let [dayns (symbol (str "aoc2020." day))
            _ (require dayns)]
    (get (ns-publics dayns) (symbol part))))

(defn -main
  [day part]
  (let [solution (solution-fn day part)]
    (time
      (println (apply solution [])))))
