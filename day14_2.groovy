def inputs = [
    "OFSVVSFOCBNONHKFHNPK",  "",  "HN -> C",  "VB -> K",  "PF -> C",  "BO -> F",  "PB -> F",  "OH -> H",  "OB -> N",  "PN -> O",  "KO -> V",  "CK -> V",  "FP -> H",  "PC -> V",  "PP -> N",  "FN -> N",  "CC -> F",  "FC -> N",  "BP -> N",  "SH -> F",  "NS -> V",  "KK -> B",  "HS -> C",  "NV -> N",  "FO -> B",  "VO -> S",  "KN -> F",  "SC -> V",  "NB -> H",  "CH -> B",  "SF -> V",  "NP -> V",  "FB -> P",  "CV -> B",  "PO -> P",  "SV -> P",  "OO -> V",  "PS -> C",  "CO -> N",  "SP -> B",  "KP -> H",  "KH -> S",  "KS -> S",  "NH -> K",  "SS -> P",  "PV -> P",  "KV -> V",  "ON -> N",  "BS -> C",  "HP -> K",  "SB -> P",  "VC -> B",  "HB -> N",  "FS -> V",  "VP -> K",  "BB -> N",  "FK -> S",  "CS -> P",  "SO -> F",  "HF -> F",  "VV -> C",  "BC -> S",  "SN -> K",  "KB -> H",  "BN -> H",  "HO -> S",  "KC -> F",  "CP -> S",  "HC -> S",  "OS -> K",  "NK -> N",  "BF -> S",  "VN -> B",  "SK -> K",  "HV -> B",  "KF -> H",  "FV -> B",  "VF -> H",  "BH -> S",  "NN -> O",  "HH -> K",  "CN -> H",  "PH -> V",  "NF -> S",  "OV -> P",  "OC -> V",  "OK -> H",  "OF -> H",  "HK -> N",  "FH -> P",  "BK -> N",  "VS -> H",  "NO -> V",  "VK -> K",  "CF -> N",  "CB -> N",  "NC -> K",  "PK -> B",  "VH -> F",  "FF -> C",  "BV -> P",  "OP -> K",
]

rules = inputs[2..inputs.size()-1].collectEntries { it -> [(it.split(' -> ')[0]): it.split(' -> ')[1]] }
template = inputs[0].split('')

counts = template.groupBy { it }.collectEntries { [(it.key): it.value.size() as Long] }
countMap = [:]

times = 40
template.eachWithIndex { t, i ->
    if (i==0) {
        return
    }
    mergeCounts(counts, cal(template[i-1], template[i], times-1))
}

println(counts)
counts.values().max()-counts.values().min()

def cal(a, b, times) {
    if (times < 0) {
        return
    } else if (countMap["${a}${b}${times}"]) {
        return countMap["${a}${b}${times}"]
    }
    def toAdd = rules["${a}${b}"]
    def theCount = [(toAdd): 1]

    mergeCounts(theCount, cal(a, toAdd, times-1))
    mergeCounts(theCount, cal(toAdd, b, times-1))

    countMap["${a}${b}${times}"] = theCount
    return theCount
}

def mergeCounts(theMap, toMerge) {
    toMerge.each { toAdd, count ->
        theMap[toAdd] = (theMap[toAdd] ? theMap[toAdd]+count : (count as Long))
    }
}
