(ns aoc2020.day5-test
  (:require [clojure.test :refer :all]
            [aoc2020.day5 :as d5]))


(deftest test-new-range
  (testing "takes lower for L"
    (is (= (d5/new-range \L [0 2]) [0 1])))
  (testing "takes lower for F"
    (is (= (d5/new-range \F [0 127]) [0 63])))
  (testing "takes upper for R"
    (is (= (d5/new-range \R [0 2]) [1 2])))
  (testing "takes upper for B"
    (is (= (d5/new-range \B [0 2]) [1 2]))))


(deftest find-val
  (testing "rows example"
    (is (= 44 (d5/find-val "FBFBBFF" [0 127]))))
  (testing "seats example"
    (is (= 5 (d5/find-val "RLR" [0 7])))))
