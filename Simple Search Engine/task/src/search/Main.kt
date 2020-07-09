package search

import java.io.File

enum class States {
    MENU, FIND, LIST, EXIT
}

class Database {
    private val db = mutableListOf<String>()
    private val idx = mutableMapOf<String, MutableList<Int>>()
    fun add(content: String) = db.add(content)

    fun construct() {
        val set = mutableSetOf<String>()
        db.flatMap { it.split(" ") }.forEach { set.add(it) }
        set.forEach {
            for (i in db.indices) {
                if (db[i].contains(it)) {
                    if (idx.containsKey(it.toLowerCase())) {
                        idx[it.toLowerCase()]?.add(i)
                    } else {
                        idx[it.toLowerCase()] = mutableListOf(i)
                    }
                }
            }
        }
    }

    fun retrieve(pattern: String) = idx[pattern.toLowerCase()]
    fun get(id: Int) = db[id]
    fun retrieveAll() = db
}

class Program(val db: Database) {
    var state: States = States.MENU

    fun process() {
        when (state) {
            States.MENU -> {
                try {
                    println("""
                    === Menu ===
                    1. Find a person
                    2. Print all people
                    0. Exit
                """.trimIndent())
                    state = when (readLine()!!.toInt()) {
                        0 -> States.EXIT
                        1 -> States.FIND
                        2 -> States.LIST
                        else -> throw IllegalArgumentException()
                    }
                } catch (e: IllegalArgumentException) {
                    println("Incorrect option! Try again.")
                }
            }
            States.FIND -> {
                println("Enter a name or email to search all suitable people.")
                val pattern = readLine()!!
                val result = db.retrieve(pattern)
                if (result.isNullOrEmpty())
                    println("No matching people found.")
                else
                    result.forEach { println(db.get(it)) }
                state = States.MENU
            }
            States.LIST -> {
                println("=== List of people ===")
                db.retrieveAll().forEach { println(it) }
                state = States.MENU
            }
            States.EXIT -> {
                println("Bye!")
            }
        }
    }
}


fun main(args: Array<String>) {
//    println("Enter the number of people:")
//    val size = readLine()!!.toInt()
//    println("Enter all people:")
//    val db = Database()
//    for (i in 1 .. size) {
//        db.add(readLine()!!)
//    }
    val people = File(args[1]).readLines()
    val db = Database()
    for (p in people) {
        db.add(p)
    }
    db.construct()
    val program = Program(db)
    while (program.state != States.EXIT) {
        program.process()
        if (program.state == States.EXIT)
            println("Bye!")
    }
}
