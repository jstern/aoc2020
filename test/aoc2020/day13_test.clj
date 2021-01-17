(ns aoc2020.day13-test
  (:require [clojure.test :refer :all]
            [aoc2020.day13 :as d13]))


(def example "939
7,13,x,x,59,x,31,19
")


(deftest test-parse
  (testing "returns timestamp and frequencies"
    (is (= [939 [7 13 59 31 19]]
           (d13/parse example)))))

(deftest test-next-departure
  (testing "returns next departure after timestamp"
    (is (= 944
           (d13/next-departure 939 59)))
    (is (= 945
           (d13/next-departure 939 7)))
    (is (= 949
           (d13/next-departure 939 13)))))


(deftest test-find-t
  (testing "finds t for provided example"
    (is (= 1068781 (d13/find-t (d13/parse2 example))))))
