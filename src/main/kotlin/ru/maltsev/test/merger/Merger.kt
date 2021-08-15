package ru.maltsev.test.merger

interface Merger {

    fun add(name: String, emails: MutableSet<String>)

    fun merge(): Map<String, MutableSet<String>>
}