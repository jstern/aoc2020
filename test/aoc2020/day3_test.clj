(ns aoc2020.day3-test
  (:require [clojure.test :refer :all]
            [aoc2020.day3 :refer :all]))


(deftest test-hit?
  (let [slope ["..#." "#..#"]]
    (testing "returns 0 if no hit"
      (is (= 0 (hit? 0 0 slope))))
    (testing "returns 1 if hit"
      (is (= 1 (hit? 0 1 slope))))))

(deftest test-count-hits
  ;;0 O#..#.
  ;;1 .X..#.
  ;;2 .#O.#.
  ;;3 .#.O#.
  ;;4 .#..X.
  (let [slope (vec (repeat 5 ".#."))]
    (is (= 2 (count-hits 1 1 slope)))))
