package de.htw.chatserver.service

import de.htw.chatserver.persistence.User
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.file.Paths

/**
 * Created by vera on 31.05.16.
 */
class UserService {

    // TODO relativer Pfad
    public static final String path = './src/main/resource/users.json'
    HashMap<String, User> registeredUsers = new HashMap<>()
    HashMap<String, String> onlineUsers = new  HashMap<>()
    JsonSlurper slurper = new JsonSlurper();



// TODO BEAN darf es nur einmal geben
    UserService() {
        Paths.get(path).withReader { reader ->
            def jsonList = slurper.parse(reader)
            for (u in jsonList) {
                def user = u as User
                registeredUsers.put(user.mail, user)
            }

        }
    }

    def register(User user) {
        if (registeredUsers.containsKey(user.mail)) {
            return false
        }

        registeredUsers.put(user.mail, user)
        def json = JsonOutput.toJson(registeredUsers.values())
        Paths.get(path).withWriter { writer ->
            writer.write(JsonOutput.prettyPrint(json))
        }
        return true
    }

    def isUserAndPwdValid(String mail, String pwd, String ip) {

        def user = registeredUsers.get(mail)
        if (user != null){
            if (user.password == pwd) {
                addToOnlineUsers(user, ip)
                true
            }
        }
        false
    }

    def private addToOnlineUsers (User user, String ip){
        onlineUsers.put(user.mail, ip)
    }
}
