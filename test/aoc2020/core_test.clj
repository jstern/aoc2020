(ns aoc2020.core-test
  (:require [clojure.test :refer :all]
            [aoc2020.core :refer :all]))

(deftest test-solution
  (testing "returns day/part fn"
    (let [solution (solution-fn "day0" "part1")]
      (is (= "Hello, aoc2020!" (solution))))))
