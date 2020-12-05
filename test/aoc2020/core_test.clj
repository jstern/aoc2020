(ns aoc2020.core-test
  (:require [clojure.test :refer :all]
            [aoc2020.core :refer :all]))

(deftest test-solution
  (testing "returns result of day/part fn"
    (is (= "Hello, aoc2020!" (solution "day0" "part1")))))
