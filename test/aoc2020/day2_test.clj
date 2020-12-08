(ns aoc2020.day2-test
  (:require [clojure.test :refer :all]
            [aoc2020.day2 :refer :all]))

(deftest test-parse-entry
  (testing "creates record with versioned policy"
    (let [expected (->Entry "abbot" (->Policy :v1 1 2 \b))
          actual (parse-entry :v1 "1-2 b: abbot")]
      (is (= expected actual)))))


(deftest test-num-occurs
  (testing "returns count found"
    (is (= 2 (num-occurs \b "abbot"))))
  (testing "return 0 if not found"
    (is (= 0 (num-occurs \c "abbot")))))

(deftest test-valid?-v1
  (testing "detects valid"
    (is (valid? (parse-entry :v1 "1-2 b: abbot"))))
  (testing "detects invalid: too many"
    (is (not (valid? (parse-entry :v1 "1-2 b: abbbbot")))))
  (testing "detects invalid: not enough"
    (is (not (valid? (parse-entry :v1 "3-4 b: abbot"))))))

(deftest test-valid?-v2
  (testing "ch is not in either position"
    (is (not (valid? (parse-entry :v2 "1-2 c: abc")))))
  (testing "ch is in first position only"
    (is (valid? (parse-entry :v2 "1-2 a: abc"))))
  (testing "ch is in second position only"
    (is (valid? (parse-entry :v2 "1-2 b: abc"))))
  (testing "ch is in both positions"
    (is (not (valid? (parse-entry :v2 "1-2 b: bbc"))))))
