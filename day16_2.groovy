def input = "00537390040124EB240B3EDD36B68014D4C9ECCCE7BDA54E62522A300525813003560004223BC3F834200CC108710E98031C94C8B4BFFF42398309DDD30EEE00BCE63F03499D665AE57B698F9802F800824DB0CE1CC23100323610069D8010ECD4A5CE5B326098419C319AA2FCC44C0004B79DADB1EB48CE5EB7B2F4A42D9DF0AA74E66468C0139341F005A7BBEA5CA65F3976200D4BC01091A7E155991A7E155B9B4830056C01593829CC1FCD16C5C2011A340129496A7EFB3CA4B53F7D92675A947AB8A016CD631BE15CD5A17CB3CEF236DBAC93C4F4A735385E401804AA86802D291ED19A523DA310006832F07C97F57BC4C9BBD0764EE88800A54D5FB3E60267B8ED1C26AB4AAC0009D8400854138450C4C018855056109803D11E224112004DE4DB616C493005E461BBDC8A80350000432204248EA200F4148FD06C804EE1006618419896200FC1884F0A00010A8B315A129009256009CFE61DBE48A7F30EDF24F31FCE677A9FB018F6005E500163E600508012404A72801A4040688010A00418012002D51009FAA0051801CC01959801AC00F520027A20074EC1CE6400802A9A004A67C3E5EA0D3D5FAD3801118E75C0C00A97663004F0017B9BD8CCA4E2A7030C0179C6799555005E5CEA55BC8025F8352A4B2EC92ADF244128C44014649F52BC01793499EA4CBD402697BEBD18D713D35C9344E92CB67D7DFF05A60086001610E21A4DD67EED60A8402415802400087C108DB068001088670CA0DCC2E10056B282D6009CFC719DB0CD3980026F3EEF07A29900957801AB8803310A0943200042E3646789F37E33700BE7C527EECD13266505C95A50F0C017B004272DCE573FBB9CE5B9CAE7F77097EC830401382B105C0189C1D92E9CCE7F758B91802560084D06CC7DD679BC8048AF00400010884F18209080310FE0D47C94AA00"

// input = '9C0141080250320F1802104A08'

bInput = input.inject('') { str, item ->
    def b = Long.toBinaryString(Long.parseLong(item, 16 ))
    str + '0'*(4-b.size()) + b
}

parse(bInput)[1][0]

def parse(packets, max=null) {
    println(packets)
    if (packets.every { it == '0' }) {
        println('ignore all zeros')
        return
    }

    def li = 0
    def count = 0
    def values = []
    while (true) {
        if (packets[li..(packets.size()-1)].every { it == '0' }) {
            println('ignore all zeros')
            return [packets.size(), values]
        }
        def ver = packets[li..li+2]
        def type = packets[li+3..li+5]

        li += 6
        if (type == '100') {
            def literal = ''
            while (packets[li] == '1') {
                literal += packets[(li+1)..(li+4)]
                li += 5
            }
            literal += packets[(li+1)..(li+4)]
            li += 5
            values << Long.parseLong(literal, 2)
        } else {
            def lID = packets[li]
            li += 1
            if (lID == '0') {
                def length = Integer.parseInt(packets[(li)..(li+14)], 2)
                println("length: ${length}")
                li += 15
                def result = parse(packets[(li)..(li+length-1)])
                values << cal(type, result[1])
                li += length
            } else {
                def subCount = Integer.parseInt(packets[(li)..(li+10)], 2)
                println("subCount: ${subCount}")
                li += 11
                def result = parse(packets[li..(packets.size()-1)], subCount)
                values << cal(type, result[1])
                li += result[0]
            }
        }
        count++
        if (li >= packets.size()-1) {
            break
        } else if (max && count >= max) {
            break
        }
    }
    println("return: ${values}")
    return [li, values]
}

def cal(type, values) {
    def iType = Integer.parseInt(type, 2)
    println("${iType}: ${values}")
    switch(iType) {
        case 0:
            return values.sum()
        case 1:
            return values.inject(1) { n, b -> n = n * b}
        case 2:
            return values.min()
        case 3:
            return values.max()
        case 5:
            return (values[0] > values[1] ? 1 : 0)
        case 6:
            return (values[0] < values[1] ? 1 : 0)
        case 7:
            return (values[0] == values[1] ? 1 : 0)
        default:
            println('!!default!!')
            return 0
    }
}
