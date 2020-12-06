(ns aoc2020.day1-test
  (:require [clojure.test :refer :all]
            [aoc2020.day1 :refer :all]))

(deftest test-find-values
  (testing "finds 2 values that sum to n with another value"
    (is (= (set (find-values 5 (set [1 2 3])))
           (set [2 3]))))
  (testing "returns nil when n - v is not in the set"
    (is (nil? (find-values 7 (set [1 2 3])))))
  (testing "returns nil when n - v = v (not enough values)"
    (is (nil? (find-values 6 (set [3])))))
  (testing "finds valid pair even if invalid pair exists"
    (is (= (set (find-values 4 (set [1 2 3])))
           (set [1 3])))))

(deftest test-pairs
  (testing "returns {(x1 + x2) #{x1 x2}, ...}"
    (is (= {3 #{1 2} 4 #{1 3} 5 #{2 3}}
           (sum-pairs [1 2 3])))))
