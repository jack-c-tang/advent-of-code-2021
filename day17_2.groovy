def inputs = "target area: x=60..94, y=-171..-136"

xI = [60, 94]
yI = [-171, -136]

count = 0
(0..xI[1]).each { vx ->
    vy = Math.abs(yI[0])+1
    while (true) {
        def found = check(vx, --vy)
        if (found) {
            println("${vx}, ${vy}: ${found}")
            count++
        }
        if (vy < yI[0]) {
            break
        }
    }
}

count

def check(vx, vy) { 
    x=0
    y=0
    found = false
    while(true) {
        x += vx
        y += vy
        if (x >= 60 && x <= 94 && y <= -136 && y >= -171) {
            found = true
            break
        } else if (x > 94 || y < -171) {
            break
        }
        vx = (vx == 0 ? 0 : vx-1)
        vy -= 1
    }
    return found
}
​