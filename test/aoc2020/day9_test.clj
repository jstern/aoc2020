(ns aoc2020.day9-test
  (:require [clojure.test :refer :all]
            [aoc2020.day9 :as d9]))


(deftest test-valid?
  (testing "not valid if no addends in window"
    (is (not (d9/valid? 5 #{6 1 3 7}))))

  (testing "valid if addends in window"
    (is (d9/valid? 5 #{6 1 3 2})))

  (testing "not valid if not two addends"
    (is (not (d9/valid? 6 #{6 1 3 2})))))


(def example [35 2 15 25 47 40 62 55 65 95 102 117
              150 182 127 219 299 277 309 576])


(deftest test-find-invalid
  (testing "finds first invalid in example"
    (is (= 127 (d9/find-invalid 5 example)))))


(deftest test-find-summing-sequence
  (testing "finds sequence that sums to target"
    (let [res (d9/find-summing-sequence 127 example)]
      (is (= res [15 25 47 40])))))
