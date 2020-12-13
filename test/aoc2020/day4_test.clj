(ns aoc2020.day4-test
  (:require [clojure.test :refer :all]
            [aoc2020.day4 :as d4]))

(deftest test-between
  (testing "< lo"
    (is (not ((d4/between 4 5) "3"))))
  (testing "lo"
    (is ((d4/between 4 5) "4")))
  (testing "hi"
    (is ((d4/between 4 5) "5")))
  (testing "> hi"
    (is (not ((d4/between 4 5) "6"))))
  (testing "not parsed"
    (is (not ((d4/between 4 5) "ham")))))


(deftest test-valid-hgt
  (testing "no unit"
    (is (not (d4/valid-hgt "80"))))
  (testing "invalid unit"
    (is (not (d4/valid-hgt "80ft"))))
  (testing "< lo in"
    (is (not (d4/valid-hgt "58in"))))
  (testing "> hi in"
    (is (not (d4/valid-hgt "77in"))))
  (testing "valid in"
    (is (d4/valid-hgt "75in")))
  (testing "< lo cm"
    (is (not (d4/valid-hgt "149cm"))))
  (testing "> hi cm"
    (is (not (d4/valid-hgt "194cm"))))
  (testing "valid cm"
    (is (d4/valid-hgt "159cm"))))


(def allbad "eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007
")

(deftest test-allbad
  (testing "finds 0 good"
    (is (= 0
           (count (filter #(d4/valid-spec? %) (d4/passports allbad)))))))

(def allgood "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
")

(deftest test-allgood
  (testing "finds 0 good"
    (is (= 4
           (count (filter #(d4/valid-spec? %) (d4/passports allgood)))))))

(deftest examples
  (testing "byr valid:   2002"
    (is (d4/valid-byr "2002")))
  (testing "byr invalid: 2003"
    (is (not (d4/valid-byr "2003"))))
  (testing "hgt valid:   60in"
    (is (d4/valid-hgt "60in")))
  (testing "hgt valid:   190cm"
    (is (d4/valid-hgt "190cm")))
  (testing "hgt invalid: 190in"
    (is (not (d4/valid-hgt "190in"))))
  (testing "hgt invalid: 190"
    (is (not (d4/valid-hgt "190"))))
  (testing "hcl valid:   #123abc"
    (is (d4/valid-hcl "#123abc")))
  (testing "hcl invalid: #123abz"
    (is (not (d4/valid-hcl "#123abz"))))
  (testing "hcl invalid: 123abc"
    (is (not (d4/valid-hcl "123abc"))))
  (testing "ecl valid:   brn"
    (is (d4/valid-ecl "brn")))
  (testing "ecl invalid: wat"
    (is (not (d4/valid-ecl "wat"))))
  (testing "pid valid:   000000001"
    (is (d4/valid-pid "000000001")))
  (testing "pid invalid: 0123456789"
    (is (not (d4/valid-pid "0123456789")))))
