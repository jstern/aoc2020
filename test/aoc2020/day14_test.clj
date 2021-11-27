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

(deftest test-addrs
  (testing "applies mask to addr"
    (is (= "000000000000000000000000000000X1101X"
           (d14/addrs "000000000000000000000000000000X1001X" "mem[42]")))
    (is (= "00000000000000000000000000000001X0XX"
           (d14/addrs "00000000000000000000000000000000X0XX" "mem[26]")))))

(deftest test-subtract
  (testing "obvious stuff"
    (is (= "0" (d14/subtract "0" "1")))
    (is (= "1" (d14/subtract "1" "0")))
    (is (= "-" (d14/subtract "0" "0")))
    (is (= "-" (d14/subtract "X" "X")))
    (is (= "0" (d14/subtract "X" "1")))
    (is (= "1" (d14/subtract "X" "0"))))
  (testing "returns minuend if any bit is disjoint"
    (is (= "01" (d14/subtract "01" "11"))))
  (testing "longer spaces"
    (is (= "01-" (d14/subtract "XXX" "10X")))))


(deftest test-size
  (testing "counts possible addresses in space"
    (is (= 4 (d14/size "000000000000000000000000000000X1101X"))))
  (testing "recognizes my - notation"
    (is (= 0 (d14/size "000000000000000000000000000000X1-01X")))))


(def example2
  [
   "mask = X1001X"
   "mem[42] = 100"
   "mask = 00X0XX"
   "mem[26] = 1"
   ])


(deftest test-init2
  (testing "example data"
    (let [[memory _] (d14/init2 example2)]
      (println (map #(str % "\n") memory))
      (is (= 208
             (reduce
               +
               (map #(* (d14/size (first %)) (second %)) memory)))))))

;; [XX0011X0X0010X0X01001X11111011111111 186083]
;; 23818624
