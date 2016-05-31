package de.htw.chatserver.persistence

import groovy.transform.ToString

/**
 * Created by vera on 31.05.16.
 */
@ToString(includeNames = true)
class User {
    String name
    String mail
    String password
}
