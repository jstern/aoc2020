#! /usr/bin/env python3

from pathlib import Path
import sys


src = """(ns aoc2020.day{n}
  (:require [aoc2020.util :as util]))

(defn part1 [] "")
(defn part2 [] "")
"""

tst = """(ns aoc2020.day{n}-test
  (:require [clojure.test :refer :all]
            [aoc2020.day{n} :as d{n}]))


(deftest test-nothing
  (testing "quaecumque sunt vera"
    (is true)))
"""


def start_day(n):
    src_path = Path(f"src/aoc2020/day{n}.clj")
    tst_path = Path(f"test/aoc2020/day{n}_test.clj")

    create(src_path, src, n)
    create(tst_path, tst, n)


def create(path, tpl, n):
    if path.is_file():
        return
    path.write_text(tpl.format(n=n))


if __name__ == "__main__":
    start_day(sys.argv[1])
