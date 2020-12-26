(ns aoc2020.day8-test
  (:require [clojure.test :refer :all]
            [aoc2020.day8 :as d8]))


(def example
"nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
")

(deftest test-instructions
  (testing "creates expected vector"
    (is (= (d8/instructions example)
           [{:ins "nop" :val 0}
            {:ins "acc" :val 1}
            {:ins "jmp" :val 4}
            {:ins "acc" :val 3}
            {:ins "jmp" :val -3}
            {:ins "acc" :val -99}
            {:ins "acc" :val 1}
            {:ins "jmp" :val -4}
            {:ins "acc" :val 6}]))))


(deftest test-run
  (testing "returns acc value if index already visited"
    (is (= 7 (d8/run 7 2 #{2} []))))
  (testing "runs example as expected"
    (is (= 5 (d8/run (d8/instructions example))))))
