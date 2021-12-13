def theMap = [
    "fw-ll",
    "end-dy",
    "tx-fw",
    "tx-tr",
    "dy-jb",
    "ZD-dy",
    "dy-BL",
    "dy-tr",
    "dy-KX",
    "KX-start",
    "KX-tx",
    "fw-ZD",
    "tr-end",
    "fw-jb",
    "fw-yi",
    "ZD-nr",
    "start-fw",
    "tx-ll",
    "ll-jb",
    "yi-jb",
    "yi-ll",
    "yi-start",
    "ZD-end",
    "ZD-jb",
    "tx-ZD",
]

// theMap = [
//     "dc-end",
//     "HN-start",
//     "start-kj",
//     "dc-start",
//     "dc-HN",
//     "LN-dc",
//     "HN-end",
//     "kj-sa",
//     "kj-HN",
//     "kj-dc",
// ]

caves = [:]
theMap.each { connection ->
    def a = connection.split('-')[0]
    def b = connection.split('-')[1]
    if (!caves[a]) {
        caves[a] = [b]
    } else {
        caves[a] << b
    }
    if (!caves[b]) {
        caves[b] = [a]
    } else {
        caves[b] << a
    }
}

totalPath = 0 as Long

findPaths('start')

totalPath

def findPaths(theCave, path=[], twoSmall=false) {
    path << theCave
    caves[theCave].each { cave ->
        if (cave == 'end') {
            totalPath += 1
            return
        } else if (cave == 'start') {
            return
        }
        if (cave[0] > 'Z' && cave in path) {
            if (twoSmall) {
                return
            } else {
                findPaths(cave, path, cave)
            }
        } else {
            findPaths(cave, path, twoSmall)
        }
    }
    path.remove(path.size()-1)
}
