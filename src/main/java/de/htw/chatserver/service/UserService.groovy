package de.htw.chatserver.service

import de.htw.chatserver.persistence.User
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.file.Paths

/**
 * Created by vera on 31.05.16.
 */
class UserService {
    private static final UserService userService = new UserService()

    public static final String path = './src/main/resource/users.json'
    HashMap<String, User> registeredUsers = new HashMap<>()
    HashMap<String, String> onlineUsers = new  HashMap<>()
    JsonSlurper slurper = new JsonSlurper();

    // TODO BEAN darf es nur einmal geben
    private UserService() {
        Paths.get(path).withReader { reader ->
            def jsonList = slurper.parse(reader)
            for (u in jsonList) {
                def user = u as User
                registeredUsers.put(user.mail, user)
            }

        }
    }

    public static UserService getInstance() {
        return userService
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
            if (user.password.equals(pwd)) {
                addToOnlineUsers(user.mail, ip)
                return true
            }
        }
        false
    }

    boolean logout(String ip) {
        // TODO zwei user mit gleicher IP ???
        try {
            onlineUsers.remove(onlineUsers.find { it.value == ip }?.key)
            true
        }
        catch (Exception e) {
            false
        }
    }

    def private addToOnlineUsers(String mail, String ip) {
        onlineUsers.put(mail, ip)
    }


}
