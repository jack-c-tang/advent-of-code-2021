def gamma = 0
def ep = 0
def len = list[0].length()
def ones = []
len.times { it -> ones << 0}
list.each { l ->
  l.eachWithIndex { bit, index ->
    ones[index] += (bit=='1') ? 1 : 0
  }
}

ones.each { one ->
  gamma <<= 1
  ep <<= 1
  if (one > list.size()/2) {
    gamma += 1
  } else {
    ep += 1
  }
}

gamma * ep​