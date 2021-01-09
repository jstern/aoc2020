(ns aoc2020.day12-test
  (:require [clojure.test :refer :all]
            [aoc2020.day12 :as d12]))

(deftest test-turn
  (testing "R"
    (is (= "S" (d12/turn "E" "R" 90)))
    (is (= "W" (d12/turn "E" "R" 180)))
    (is (= "N" (d12/turn "E" "R" 270)))
    (is (= "E" (d12/turn "E" "R" 360))))
  (testing "L"
    (is (= "N" (d12/turn "E" "L" 90)))
    (is (= "W" (d12/turn "E" "L" 180)))
    (is (= "S" (d12/turn "E" "L" 270)))
    (is (= "E" (d12/turn "E" "L" 360)))))

(deftest test-nav1
  (testing "R"
    (is (= {:heading "S" "N" 0 "E" 0 "S" 0 "W" 0}
           (d12/nav1 d12/ship "R90"))))
  (testing "L"
    (is (= {:heading "N" "N" 0 "E" 0 "S" 0 "W" 0}
           (d12/nav1 d12/ship "L90"))))
  (testing "F"
    (is (= {:heading "E" "N" 0 "E" 10 "S" 0 "W" 0}
           (d12/nav1 d12/ship "F10"))))
  (testing "N"
    (is (= {:heading "E" "N" 10 "E" 0 "S" 0 "W" 0}
           (d12/nav1 d12/ship "N10"))))
  (testing "E"
    (is (= {:heading "E" "N" 0 "E" 10 "S" 0 "W" 0}
           (d12/nav1 d12/ship "E10"))))
  (testing "S"
    (is (= {:heading "E" "N" 0 "E" 0 "S" 10 "W" 0}
           (d12/nav1 d12/ship "S10"))))
  (testing "W"
    (is (= {:heading "E" "N" 0 "E" 0 "S" 0 "W" 10}
           (d12/nav1 d12/ship "W10")))))
