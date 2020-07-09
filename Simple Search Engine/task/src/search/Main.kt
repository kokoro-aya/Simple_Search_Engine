package search

fun main() {
    println("Enter the number of people:")
    val size = readLine()!!.toInt()
    println("Enter all people:")
    val database = mutableListOf<String>()
    for (i in 1 .. size) {
        database.add(readLine()!!)
    }
    println("Enter the number of search queries:")
    val queries = readLine()!!.toInt()
    for (i in 1 .. queries) {
        println("Enter data to search people:")
        val data = readLine()!!
        val result = database.filter { it.toLowerCase().contains(data.toLowerCase()) }
        if (result.isEmpty()) {
            println("No matching people found.")
        } else {
            println("Found people:")
            result.forEach { println(it) }
        }
    }
}
