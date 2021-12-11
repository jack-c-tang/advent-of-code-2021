def ocs = [
    "6111821767",
    "1763611615",
    "3512683131",
    "8582771473",
    "8214813874",
    "2325823217",
    "2222482823",
    "5471356782",
    "3738671287",
    "8675226574",
]

// ocs = [
//     "5483143223",
//     "2745854711",
//     "5264556173",
//     "6141336146",
//     "6357385478",
//     "4167524645",
//     "2176841721",
//     "6882881134",
//     "4846848554",
//     "5283751526",
// ]

rsize = ocs[0].size()
csize = ocs.size()
ocss = ocs.collect { it.split('').collect { it as Integer } }

flashes = 0
turn = 0
while (true) {
    turn += 1
    ocss.eachWithIndex { row, ri ->
        ocss[ri] = row*.plus(1)
    }
    ocss.eachWithIndex { row, ri ->
        ocss[ri]
            .findIndexValues { it == 10 }
            .each { ci ->
                flashed(ri, ci)
            }
    }
    // println("Round ${it+1}")
    // printOCSS()
    if (allFlashed()) {
        println("All flashed step: ${turn}")
        break
    }
}
flashes

def allFlashed() {
    ocss.every { it == [0]*csize }
}

def flashed(ri, ci) {
    if (ocss[ri][ci] == 0) {
        return 0
    }
    ocss[ri][ci] = 0
    flashes += 1 as Long

    (ri-1..ri+1).each { r ->
        if (r < 0 || r > rsize -1) {
            return
        }
        (ci-1..ci+1).each { c ->
            if (r == ri && c == ci) {
                return
            } else if (c < 0 || c > csize -1) {
                return
            } else if (ocss[r][c] == 0) {
                return
            }
            ocss[r][c] += 1
            if (ocss[r][c] >= 10) {
                flashed(r, c)
            }
        }
    }
}

def printOCSS() {
    ocss.each { row ->
        row.each { print(it) }
        println()
    }
}