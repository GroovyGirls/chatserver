package de.htw.chatserver

import de.htw.chatserver.controller.LoginController
import de.htw.chatserver.controller.LogoutController
import de.htw.chatserver.controller.OnlineUserController
import de.htw.chatserver.controller.RegisterController
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

/**
 * Created by vera on 31.05.16.
 */

class Main {

    static void main(String[] args) {
        println('Application started')

        GrizzlyHttpServerFactory.createHttpServer(
                // TODO ifconfig ip con wlan0
                "http://141.45.206.103:8081".toURI(),
                new ResourceConfig(RegisterController.class, LoginController.class, LogoutController.class, OnlineUserController.class));

        synchronized (this) {
            wait() // Hack damit der Server nicht wieder runtergefahren wird
        }
        println("server closed")
    }
}
