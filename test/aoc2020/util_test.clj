(ns aoc2020.util-test
  (:require [clojure.test :refer :all]
            [aoc2020.util :as util]))

(deftest test-input
  (testing "finds file and reads input"
    (is (= "input 0\n" (util/input "0.txt")))))
