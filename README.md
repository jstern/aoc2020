# aoc2020

Solutions for [advent of code 2020](https://adventofcode.com/2020/).

![Clojure CI](https://github.com/jstern/aoc2020/workflows/Clojure%20CI/badge.svg)

Run tests with `lein test`

Print solution for dayN partN with `lein run dayN partN`:

```
$ lein run day0 part1
Hello, aoc2020!
"Elapsed time: 9.165858 msecs"
```

Create stub files for day N with start_day.py:

```
$ ./start_day.py 8
$ cat src/aoc2020/day8.clj 
(ns aoc2020.day8
  (:require [aoc2020.util :as util]))

  (defn part1 [] "")
  (defn part2 [] "")

$ cat test/aoc2020/day8_test.clj 
(ns aoc2020.day8-test
  (:require [clojure.test :refer :all]
              [aoc2020.day8 :as d8]))


(deftest test-nothing
  (testing "quaecumque sunt vera"
      (is true)))

```
