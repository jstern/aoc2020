(ns aoc2020.uf-test
  (:require [clojure.test :refer :all])
  (:import [aoc2020.uf WQUCP]))

;; too bad this wasn't the right data structure
;; for the problem!

(deftest test-WQUCP
  (testing "initially items are unconnected"
    (let [uf (WQUCP. (to-array ["A" "B" "C"]))]
      (is (= false (.connected uf "A" "B")))))

  (testing "connect connects two items"
    (let [uf (WQUCP. (to-array ["A" "B" "C"]))]
      (.connect uf "A" "B")
      (is (= true (.connected uf "A" "B")))))

  (testing "connect creates transitive connectsions"
    (let [uf (WQUCP. (to-array ["A" "B" "C"]))]
      (.connect uf "A" "B")
      (.connect uf "B" "C")
      (is (= true (.connected uf "A" "C"))))))
