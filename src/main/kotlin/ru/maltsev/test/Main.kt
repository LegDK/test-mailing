package ru.maltsev.test

import ru.maltsev.test.merger.Merger
import ru.maltsev.test.merger.MergerImpl

class Main {
    companion object {
        private const val USER_EMAIL_DELIMITER = "->"
        @JvmStatic
        fun main(args: Array<String>) {

            val merger : Merger = MergerImpl()

            val inputLines = generateSequence(::readLine).takeWhile(String::isNotEmpty)
            inputLines.filter { it.contains(USER_EMAIL_DELIMITER) }.forEach {
                val (name, emails) = it.split(USER_EMAIL_DELIMITER).map(String::trim)
                merger.add(name, emails.split(",").map(String::trim).toMutableSet())
            }

            merger.merge().forEach {
                println("${it.key} -> ${it.value.joinToString()}")
            }
        }
    }
}