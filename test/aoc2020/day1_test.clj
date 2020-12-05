(ns aoc2020.day1-test
  (:require [clojure.test :refer :all]
            [aoc2020.day1 :refer :all]))

(deftest test-find-value
  (testing "finds a value that sums to n with another value"
    ;; guess it can find them in either order...
    (is (or (= 2 (find-value 5 (set [1 2 3])))
            (= 3 (find-value 5 (set [1 2 3])))))))
