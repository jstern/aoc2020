(ns aoc2020.day7-test
  (:require [clojure.test :refer :all]
            [aoc2020.day7 :as d7]))


(deftest test-parse-contain
  (testing "parses no other bags"
    (is (= ["faded blue" {}]
           (d7/parse-raw-rule "faded blue bags contain no other bags"))))

  (testing "parses 1 type of bag"
    (is (= ["bright white" {"shiny gold" 1}]
           (d7/parse-raw-rule "bright white bags contain 1 shiny gold bag"))))

  (testing "parses several types of bag"
    (is (= ["dark olive" {"faded blue" 3 "dotted black" 4}]
           (d7/parse-raw-rule "dark olive bags contain 3 faded blue bags, 4 dotted black bags"))))

  (testing "parses several types of bag (1 and 2+)"
    (is (= ["shiny gold" {"dark olive" 1 "vibrant plum" 2}]
           (d7/parse-raw-rule "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags")))))


(def example-rules
"light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")


(deftest test-parse-rules
  (let [parsed (d7/parse-rules example-rules)]
    (testing "created expected number of rules"
      (is (= 9 (count parsed))))

    (testing "created expected rule for one example"
      (is (= (first parsed) ["light red" {"bright white" 1 "muted yellow" 2}])))))


(deftest test-all-containers
  (testing "maps contained to containers"
    (let [rules [[:x {:p 1 :q 1}], [:p {:z 1}], [:q {}], [:r {:p 1}]]
          expected {:p #{:x :r}, :q #{:x}, :z #{:p}}
          result (d7/all-containers rules)]
      (is (= expected result)))))


(deftest test-find-containers
  (testing "finds all containers for subject"
    (let [containers {:p #{:x :r}, :x #{:y :a}, :y #{:z}, :z #{:p}}
                     ;; p -> r
                     ;;   -> x -> y -> z -> p
                     ;;        -> a
          expected #{:p :r :x :y :z :a}
          result (d7/find-containers :p containers)]
      (is (= expected result))))

  (testing "returns empty set when no containers"
    (is (= #{} (d7/find-containers :p {}))))

  (testing "works with cycle"
    (is (= #{:p :q} (d7/find-containers :p {:p #{:q}, :q #{:p}}))))

  (testing "works for tight cycle"
    (is (= #{:p} (d7/find-containers :p {:p #{:p}})))))
