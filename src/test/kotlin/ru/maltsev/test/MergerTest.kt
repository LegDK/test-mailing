package ru.maltsev.test

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.maltsev.test.merger.Merger
import ru.maltsev.test.merger.MergerImpl

internal class MergerTest {

    private lateinit var merger: Merger

    @BeforeEach
    fun setUp() {
        merger = MergerImpl()
    }

    @Test
    fun mergeEmpty() {
        assert(merger.merge().isEmpty())
    }

    @Test
    fun mergeEmptyEmails() {
        merger.add("", mutableSetOf())
        merger.add("user1", mutableSetOf())

        assertFalse(checkIntersections(merger.merge()))
    }

    @Test
    fun mergeDefault() {
        merger.add("user1", mutableSetOf("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"))
        merger.add("user2", mutableSetOf("foo@gmail.com", "ups@pisem.net"))
        merger.add("user3", mutableSetOf("xyz@pisem.net", "vasya@pupkin.com"))
        merger.add("user4", mutableSetOf("ups@pisem.net", "aaa@bbb.ru"))
        merger.add("user5", mutableSetOf("xyz@pisem.net"))

        val merge = merger.merge()
        assert(merge.size == 2)
        assertFalse(checkIntersections(merge))
    }

    private fun checkIntersections(map: Map<String, Set<String>>): Boolean {
        for ((currentUser, currentEmails) in map) {
            map.filterKeys { it != currentUser }.forEach {
                val anotherEmails = it.value
                if (currentEmails.intersect(anotherEmails).isNotEmpty()) return true
            }
        }

        return false
    }
}