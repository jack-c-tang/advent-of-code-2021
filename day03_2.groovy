a=cal(list, true, 0)
b=cal(list, false, 0)

print(calInt(a[0]) * calInt(b[0]))

def calInt(list) {
    def r=0
    list.each { it ->
        r<<=1
        r+=(it=='1')?1:0
    }
    return r
}

def cal(list, more, index) {
    if (list.size() == 1 || index > list[0].size() - 1) return list
    def llist
    def ones = list.findAll {it -> it[index] == '1'}
    def zeros = list.findAll {it -> it[index] == '0'}
    if (more) {
        if (ones.size() >= zeros.size()) {
            llist = ones
        } else {
            llist = zeros
        }
    } else {
        if (ones.size() < zeros.size()) {
            llist = ones
        } else {
            llist = zeros
        }
    }
    return cal(llist, more, index+1)
}
