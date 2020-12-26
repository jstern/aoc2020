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
    (is (= [7 false] (d8/run 7 0 #{0} [{:ins "nop" :val 0}] 1 nil))))

  (testing "updates acc and returns if next index is term"
    (is (= [1 true] (d8/run [{:ins "acc" :val 1}]))))

  (testing "runs example as expected"
    (is (= [5 false] (d8/run (d8/instructions example))))))


(deftest test-find-corrupted
  (testing "finds corrupted index in example"
    (is (= [8 7] (d8/find-corrupted (d8/instructions example))))))
