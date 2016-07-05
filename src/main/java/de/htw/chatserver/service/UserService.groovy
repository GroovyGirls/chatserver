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
    HashMap<String, String> onlineUsers = new HashMap<>()
    JsonSlurper slurper = new JsonSlurper();

    //darf es nur einmal geben, da die Maps registeredUsers und onlineUsers eindeutig sein müssen.
    private UserService() {
        Paths.get(path).withReader { reader ->
            def jsonList = slurper.parse(reader)
            for (u in jsonList) {
                def user = u as User
                registeredUsers.put(user.mail, user)
            }
        }
    }

    /**
     *
     * @return Instanz des Userservice
     */
    public static UserService getInstance() {
        return userService
    }

    /**
     * Regestrieren eines Nutzers
     * @param user
     * @return true , wenn Emailadresse noch nicht verhanden und false, wenn bereits ein Nutzer mit der angegebenen
     * Mailadresse exestiert.
     */
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

    /**
     * Prüft, ob es sich bei den übergebenen Daten, um Daten eines regestrierten Nutzers handelt
     * @param mail Emailadresse
     * @param pwd Passwort
     * @param ip aktuelle IP-Adresse des Nutzers
     * @return true , wenn Benutzername und Passwort für den Nutzer hinterlegt sind, false, wenn es sich nicht um
     * die Daten eines regestrierten Nutzers handelt
     */
    def isUserAndPwdValid(String mail, String pwd, String ip) {
        def user = registeredUsers.get(mail)
        if (user != null) {
            if (user.password.equals(pwd)) {
                addToOnlineUsers(user.mail, ip)
                return true
            }
        }
        false
    }

    /**
     * Logout eines Nutzers.
     * @param ip
     * @return true , wenn es einen OnlineUser mit der übergebenen  IP gibt und dieser entfernt werden konnte,
     * ansonsten false
     */
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
