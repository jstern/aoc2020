import collections
import re


def asdict(doc):
    res = {}
    for pair in doc:
        k, v = pair.split(":")
        res[k] = v
    return res


with open("resources/day4.txt", "r") as f:
    batch = f.read()


docs = [grp.replace("\n", " ") for grp in batch.split("\n\n")]
docs = [d.strip() for d in docs if d.strip()]
docs = [d.split(" ") for d in docs]
docs = [asdict(d) for d in docs]

required = ["byr", "iyr", "eyr", "ecl", "hcl", "hgt", "pid"]


def complete(doc):
    return all(k in doc for k in required)


print(len([d for d in docs if complete(d)]))


def between(v, lo, hi):
    try:
        vv = int(v)
        return vv >= lo and vv <= hi
    except:
        return False


def valid_height(v):
    if v[-2:] not in ("in", "cm"):
        return False
    try:
        vv = int(v[:-2])
        un = v[-2:]
        if un == "in":
            return between(vv, 59, 76)
        if un == "cm":
            return between(vv, 150, 193)
    except:
        pass
    return False


ecl = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"}
validate = {
    "ecl": lambda v: v in ecl,
    "hcl": lambda v: re.match(r'^#[0-9a-f]{6}$', v),
    "pid": lambda v: re.match(r'^\d{9}$', v),
    "byr": lambda v: between(v, 1920, 2002),
    "iyr": lambda v: between(v, 2010, 2020),
    "eyr": lambda v: between(v, 2020, 2030),
    "hgt": lambda v: valid_height(v),
}


def valid(doc):
    if not complete(doc):
        return False
    # print(doc)
    for key, validation in validate.items():
        res = validation(doc[key])
        # print(key, doc[key], res)
        if not res:
            return False
    return True


for i, d in enumerate(docs):
    print(i, valid(d))


docs = [d for d in docs if valid(d)]
print(len(docs))
