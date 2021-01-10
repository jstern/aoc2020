(ns aoc2020.day12-test
  (:require [clojure.test :refer :all]
            [aoc2020.day12 :as d12]))

(deftest test-turn1
  (testing "R"
    (is (= "S" (d12/turn1 "E" "R" 90)))
    (is (= "W" (d12/turn1 "E" "R" 180)))
    (is (= "N" (d12/turn1 "E" "R" 270)))
    (is (= "E" (d12/turn1 "E" "R" 360))))
  (testing "L"
    (is (= "N" (d12/turn1 "E" "L" 90)))
    (is (= "W" (d12/turn1 "E" "L" 180)))
    (is (= "S" (d12/turn1 "E" "L" 270)))
    (is (= "E" (d12/turn1 "E" "L" 360)))))

(deftest test-nav1
  (testing "R"
    (is (= {:heading "S" "N" 0 "E" 0 "S" 0 "W" 0}
           (d12/nav1 d12/ship1 "R90"))))
  (testing "L"
    (is (= {:heading "N" "N" 0 "E" 0 "S" 0 "W" 0}
           (d12/nav1 d12/ship1 "L90"))))
  (testing "F"
    (is (= {:heading "E" "N" 0 "E" 10 "S" 0 "W" 0}
           (d12/nav1 d12/ship1 "F10"))))
  (testing "N"
    (is (= {:heading "E" "N" 10 "E" 0 "S" 0 "W" 0}
           (d12/nav1 d12/ship1 "N10"))))
  (testing "E"
    (is (= {:heading "E" "N" 0 "E" 10 "S" 0 "W" 0}
           (d12/nav1 d12/ship1 "E10"))))
  (testing "S"
    (is (= {:heading "E" "N" 0 "E" 0 "S" 10 "W" 0}
           (d12/nav1 d12/ship1 "S10"))))
  (testing "W"
    (is (= {:heading "E" "N" 0 "E" 0 "S" 0 "W" 10}
           (d12/nav1 d12/ship1 "W10")))))

(deftest test-nav2
  (testing "F"
    (is (= {:p [100 10] :w [10 1]}
           (d12/nav2 d12/ship2 "F10"))))
  (testing "N"
    (is (= {:p [0 0] :w [10 3]}
           (d12/nav2 d12/ship2 "N2"))))
  (testing "E"
    (is (= {:p [0 0] :w [12 1]}
           (d12/nav2 d12/ship2 "E2"))))
  (testing "S"
    (is (= {:p [0 0] :w [10 -1]}
           (d12/nav2 d12/ship2 "S2"))))
  (testing "W"
    (is (= {:p [0 0] :w [8 1]}
           (d12/nav2 d12/ship2 "W2"))))
  (testing "R0"
    (is (= {:p [0 0] :w [10 1]}
           (d12/nav2 d12/ship2 "R0"))))
  (testing "R90"
    (is (= {:p [0 0] :w [1 -10]}
           (d12/nav2 d12/ship2 "R90"))))
  (testing "R180"
    (is (= {:p [0 0] :w [-10 -1]}
           (d12/nav2 d12/ship2 "R180"))))
  (testing "L90"
    (is (= {:p [0 0] :w [-1 10]}
           (d12/nav2 d12/ship2 "L90")))))
