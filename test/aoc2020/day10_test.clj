(ns aoc2020.day10-test
  (:require [clojure.test :refer :all]
            [aoc2020.day10 :as d10]))

(def example [16 10 15 5 1 11 7 19 6 12 4])


(deftest test-count-diffs
  (testing "returns the count of 1s and 3s"
    (is (= [7 5] (d10/count-diffs (sort example))))))

(deftest test-prev
  (testing "returns n - 1, 2, 3 filtered by availability"
    (is (= '(3 2 1) (d10/prev 4))))

  (testing "returns 0 even though it's not in set"
    (is (= '(2 1 0) (d10/prev 3)))))
