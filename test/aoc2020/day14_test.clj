(ns aoc2020.day14-test
  (:require [clojure.test :refer :all]
            [aoc2020.day14 :as d14]))

(def example [
  "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
  "mem[8] = 11"
  "mem[7] = 101"
  "mem[8] = 0"
])


(deftest test-make-mask
  (testing "X has no effect"
    (is (= 0 ((d14/make-mask "X") 0)))
    (is (= 1 ((d14/make-mask "X") 1))))
  (testing "0 forces bit to 0"
    (is (= 0 ((d14/make-mask "0") 0)))
    (is (= 0 ((d14/make-mask "0") 1))))
  (testing "1 forces bit to 1"
    (is (= 1 ((d14/make-mask "1") 0)))
    (is (= 1 ((d14/make-mask "1") 1)))))


(deftest test-init
  (testing "processes example"
    (let [[mem _] (d14/init example)]
      (is (= {"mem[7]" 101 "mem[8]" 64} mem)))))
