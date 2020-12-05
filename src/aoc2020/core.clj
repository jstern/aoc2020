(ns aoc2020.core
  (:gen-class))

(defn solution
  "return solution from day/part
    where day is a namespace within aoc2020 and part is a function"
  [day part]
  (let [dayns (symbol (str "aoc2020." day))
            _ (require dayns)
       partfn (get (ns-publics dayns) (symbol part))]
    (apply partfn [])))

(defn -main
  [day part]
  (time
    (println (solution day part))))
