package sorting
import java.io.File
import java.util.Scanner

fun main(args: Array<String>) {
    val regExp = Regex("[-](\\w+)\\s(\\w+\\.\\w+|\\w+)?")
    val input = args.joinToString(" ")
    val types = regExp.findAll(input).toList()
    var dataType: String? = null
    var sortType: String? = null
    var inputFileName: String? = null
    var outputFileName: String? = null
    for(type in types) {
        val arg = type.groups[1]?.value
        if(type.groups.isNotEmpty()) {
            if (arg == "dataType") {
                dataType = type.groups[2]?.value
                if(dataType == null) {
                    println("No data type defined!")
                    return
                }
            } else if (arg == "sortingType") {
                sortType = type.groups[2]?.value
                if(sortType == null) {
                    println("No sorting type defined!")
                    return
                }
            } else if(arg == "inputFile") {
                inputFileName = type.groups[2]?.value
            } else if(arg == "outputFile") {
                outputFileName = type.groups[2]?.value
            }
            else if (arg != null) {
                println("\"-$arg\" is not a valid parameter. It will be skipped")
            }
        }
    }

//     if (outputFileName != null) {
//        if(!File(outputFileName).exists()) {
//            println("There is no output file $outputFileName")
//            return
//        }
//    } else
        if(dataType == null) {
            dataType = "word"
        }
        when (dataType) {
            "long" -> {
                sortInts(sortType, inputFileName, outputFileName)
            }
            "line" -> {
                sortLines(sortType, inputFileName, outputFileName)
            }
            "word" -> {
                sortWords(sortType, inputFileName, outputFileName)
            }
        }
    }

fun sortInts(sortType: String?, inPath: String?, outPath: String?) {
    val inputs: MutableList<String> = if (inPath != null) {
        getIntsFromFile(inPath)
    } else {
        getIntInputs()
    }

    val intInputs = mutableListOf<Int>()
    for(i in inputs.indices) {
        val intInput = inputs[i].toIntOrNull()
        if(intInput == null) {
            println("${inputs[i]} is not a long. It will be skipped")
        } else {
            intInputs.add(intInput)
        }
    }
    intInputs.sort()
    var output = ""
    if(sortType == null || sortType == "natural") {
        output += "Total numbers: ${intInputs.size}.\n"
        output += "Sorted Data: ${intInputs.joinToString(" ")}\n"
    } else {
        val map: MutableMap<Int, Int> = mutableMapOf()
        for(input in intInputs) {
            if(map[input] == null) {
                map[input] = 1
            } else {
                map[input] = map[input]?.plus(1) ?: 1
            }
        }
        val pairs  = mutableListOf<Pair<Int, Int>>()
        for((k, v) in map) {
            pairs.add(Pair(k, v))
        }
        pairs.sortWith { o1, o2 ->
            if (o1.second > o2.second) 1
            else if (o1.second < o2.second) -1
            else if (o1.first <= o2.first) -1 else 0
        }
        output += "Total numbers: ${inputs.size}.\n"
        for(pair in pairs) {
            val k = pair.first
            val v = pair.second
            val percentage = ((v / inputs.size.toDouble()) * 100).toInt()
            output += "$k: $v ${getPlural(v)}, $percentage%\n"
        }
    }
    if(outPath != null) {
        val file = File(outPath)
        file.writeText(output)
    } else {
        println(output)
    }
}

fun getPlural(number: Int) : String {
    return if (number > 1) "times" else "time"
}

fun getIntInputs() : MutableList<String> {
    val inputs = mutableListOf<String>()
    val scanner = Scanner(System.`in`)
    var flag = true
    while(flag) {
        val hasNext = scanner.hasNext()
        if(hasNext) {
            inputs.add(scanner.next())
        } else {
            flag = false
        }
    }
    return inputs
}

fun getIntsFromFile(path: String): MutableList<String> {
    val file = File(path)
    val ints = mutableListOf<String>()
    file.forEachLine { ints.add(it) }
    return ints
}

fun getWordInputs() : MutableList<String> {
    val inputs = mutableListOf<String>()
    val scanner = Scanner(System.`in`)
    var flag = true
    while(flag) {
        val hasNext = scanner.hasNext()
        if(hasNext) {
            inputs.add(scanner.next())
        } else {
            flag = false
        }
    }
    return inputs
}

fun sortWords(sortType: String?, inPath: String?, outPath: String?) {
    val inputs: MutableList<String> = if (inPath != null) {
        getIntsFromFile(inPath)
    } else {
        getWordInputs()
    }
    inputs.sort()
    var output = ""
    if(sortType == null || sortType == "natural") {
        output += "Total words: ${inputs.size}.\n"
        output += "Sorted Data: ${inputs.joinToString(" ")}\n"
    } else {
        val map: MutableMap<String, Int> = mutableMapOf()
        for(input in inputs) {
            if(map[input] == null) {
                map[input] = 1
            } else {
                map[input] = map[input]?.plus(1) ?: 1
            }
        }
        val pairs  = mutableListOf<Pair<String, Int>>()
        for((k, v) in map) {
            pairs.add(Pair(k, v))
        }
        pairs.sortWith { o1, o2 ->
            if (o1.second > o2.second) 1
            else if (o1.second < o2.second) -1
            else o1.first.compareTo(o2.first)
        }
        output += "Total words: ${inputs.size}.\n"
        for(pair in pairs) {
            val k = pair.first
            val v = pair.second
            val percentage = ((v / inputs.size.toDouble()) * 100).toInt()
            output += "$k: $v ${getPlural(v)}, $percentage%\n"
        }
    }
    if(outPath != null) {
        val file = File(outPath)
        file.writeText(output)
    } else {
        println(output)
    }
}

fun getLineInputs() : MutableList<String> {
    val inputs = mutableListOf<String>()
    val scanner = Scanner(System.`in`)
    var flag = true
    while(flag) {
        val hasNext = scanner.hasNext()
        if(hasNext) {
            inputs.add(scanner.nextLine())
        } else {
            flag = false
        }
    }
    return inputs
}

fun sortLines(sortType: String?, inPath: String?, outPath: String?) {
    val inputs: MutableList<String> = if (inPath != null) {
        getIntsFromFile(inPath)
    } else {
        getLineInputs()
    }
    inputs.sort()
    var output = ""
    if (sortType == null || sortType == "natural") {
        output += "Total lines: ${inputs.size}.\n"
        output += "Sorted Data: \n"
        for (input in inputs) {
            output += input
        }
    } else {
        val map: MutableMap<String, Int> = mutableMapOf()
        for (input in inputs) {
            if (map[input] == null) {
                map[input] = 1
            } else {
                map[input] = map[input]?.plus(1) ?: 1
            }
        }
        val pairs  = mutableListOf<Pair<String, Int>>()
        for((k, v) in map) {
            pairs.add(Pair(k, v))
        }
        pairs.sortWith { o1, o2 ->
            if (o1.second > o2.second) 1
            else if (o1.second < o2.second) -1
            else o1.first.compareTo(o2.first)
        }
        output += "Total lines: ${inputs.size}.\n"
        for(pair in pairs) {
            val k = pair.first
            val v = pair.second
            val percentage = ((v / inputs.size.toDouble()) * 100).toInt()
            output += "$k: $v ${getPlural(v)}, $percentage%\n"
        }
    }
    if(outPath != null) {
        val file = File(outPath)
        file.writeText(output)
    } else {
        println(output)
    }
}
