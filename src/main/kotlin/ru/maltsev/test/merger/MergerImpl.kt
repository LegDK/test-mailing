package ru.maltsev.test.merger

class MergerImpl : Merger {

    private val usersMap: MutableMap<String, MutableSet<String>> = mutableMapOf()

    private val emailsMap: MutableMap<String, MutableSet<String>> = mutableMapOf()

    override fun add(name: String, emails: MutableSet<String>) {
        usersMap[name] = emails

        emails.forEach {
            emailsMap[it] = emailsMap.getOrDefault(it, linkedSetOf()).run {
                this.add(name)
                this
            }
        }
    }


    override fun merge(): Map<String, MutableSet<String>> {
        val result: MutableMap<String, MutableSet<String>> = mutableMapOf()

        val usedUsers: MutableSet<String> = linkedSetOf()
        usersMap.keys.forEach {
            if (!usedUsers.contains(it)) {
                val resultEmails: MutableSet<String> = linkedSetOf()
                checkUserNode(it, usedUsers, resultEmails)
                result[it] = resultEmails
            }
        }

        return result
    }

    private fun checkUserNode(user: String, usedUsers: MutableSet<String>, resultEmails: MutableSet<String>) {
        usedUsers.add(user)

        usersMap[user]?.forEach {
            if (!resultEmails.contains(it)) {
                checkEmailNode(it, usedUsers, resultEmails)
            }
        }
    }

    private fun checkEmailNode(email: String, usedUsers: MutableSet<String>, resultEmails: MutableSet<String>) {
        resultEmails.add(email)

        emailsMap[email]?.forEach {
            if (!usedUsers.contains(it)) {
                checkUserNode(it, usedUsers, resultEmails)
            }
        }
    }
}