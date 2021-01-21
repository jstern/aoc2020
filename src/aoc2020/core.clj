(ns aoc2020.core
  (:gen-class))

(defn solution-fn
  "return solution function from day/part
   where day is a namespace within aoc2020
   and part is a function in that ns"
  [day part]
  (let [dayns (symbol (str "aoc2020." day))]
    (try
      (require dayns)
      (try
        (get (ns-publics dayns) (symbol part))
        (catch Exception e
          ;; (println "No solution function" part "in ns" dayns)
          nil))
      (catch Exception e
        ;; (println "No ns" dayns)
        nil))))

(defn run-one
  [args]
  (let [[day part] args
        solution (solution-fn day part)]
    (println)
    (println day part)
    (if (nil? solution)
      (println "> solution missing")
      (time
        (println ">" (solution))))))

(defn run-all
  []
  (doseq [day (range 1 26)
          part '(1 2)]
    (run-one [(str "day" day) (str "part" part)])))

(defn -main
  [& args]
  (if (= args ["all"]) (run-all) (run-one args)))
