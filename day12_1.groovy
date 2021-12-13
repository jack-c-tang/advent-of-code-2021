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

totalPath = 0

findPaths('start')

totalPath

def findPaths(theCave, path=[]) {
    path << theCave
    caves[theCave].each { cave ->
        if (cave == 'end') {
            println(path)
            totalPath += 1
            return
        }
        if (cave[0] > 'Z' && cave in path) {
            return
        }
        findPaths(cave, path)
    }
    path.remove(path.size()-1)
}
