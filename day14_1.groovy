def inputs = [
    "OFSVVSFOCBNONHKFHNPK",  "",  "HN -> C",  "VB -> K",  "PF -> C",  "BO -> F",  "PB -> F",  "OH -> H",  "OB -> N",  "PN -> O",  "KO -> V",  "CK -> V",  "FP -> H",  "PC -> V",  "PP -> N",  "FN -> N",  "CC -> F",  "FC -> N",  "BP -> N",  "SH -> F",  "NS -> V",  "KK -> B",  "HS -> C",  "NV -> N",  "FO -> B",  "VO -> S",  "KN -> F",  "SC -> V",  "NB -> H",  "CH -> B",  "SF -> V",  "NP -> V",  "FB -> P",  "CV -> B",  "PO -> P",  "SV -> P",  "OO -> V",  "PS -> C",  "CO -> N",  "SP -> B",  "KP -> H",  "KH -> S",  "KS -> S",  "NH -> K",  "SS -> P",  "PV -> P",  "KV -> V",  "ON -> N",  "BS -> C",  "HP -> K",  "SB -> P",  "VC -> B",  "HB -> N",  "FS -> V",  "VP -> K",  "BB -> N",  "FK -> S",  "CS -> P",  "SO -> F",  "HF -> F",  "VV -> C",  "BC -> S",  "SN -> K",  "KB -> H",  "BN -> H",  "HO -> S",  "KC -> F",  "CP -> S",  "HC -> S",  "OS -> K",  "NK -> N",  "BF -> S",  "VN -> B",  "SK -> K",  "HV -> B",  "KF -> H",  "FV -> B",  "VF -> H",  "BH -> S",  "NN -> O",  "HH -> K",  "CN -> H",  "PH -> V",  "NF -> S",  "OV -> P",  "OC -> V",  "OK -> H",  "OF -> H",  "HK -> N",  "FH -> P",  "BK -> N",  "VS -> H",  "NO -> V",  "VK -> K",  "CF -> N",  "CB -> N",  "NC -> K",  "PK -> B",  "VH -> F",  "FF -> C",  "BV -> P",  "OP -> K",
]

rules = inputs[2..inputs.size()-1].collectEntries { it -> [(it.split(' -> ')[0]): it.split(' -> ')[1]] }
template = inputs[0].split('')

counts = template.groupBy { it }.collectEntries { [(it.key): it.value.size()] }

10.times {
    def toAddList = []
    template.eachWithIndex { t, i ->
        if (i == 0) {
            return
        }
        def toAdd = rules["${template[i-1]}${template[i]}"]
        toAddList << toAdd
        counts[toAdd] = ((counts[toAdd] ? counts[toAdd]+1 : 1) as Long)
    }
    template = merge(template, toAddList)
}

println(counts)
counts.values().max()-counts.values().min()

def merge(template, toAdd) {
    def list = []
    (toAdd.size()).times { it ->
        list << template[it] << toAdd[it]
    }
    list << template[template.size()-1]
}

