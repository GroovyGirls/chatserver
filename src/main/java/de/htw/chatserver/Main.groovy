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



        String uri = "http://localhost:" + (System.getenv("PORT") != null ? System.getenv("PORT") : "8081") + "/"
        GrizzlyHttpServerFactory.createHttpServer(
                uri.toURI(),
                new ResourceConfig(RegisterController.class, LoginController.class, LogoutController.class, OnlineUserController.class));

        synchronized (this) {
            wait() // Hack damit der Server nicht wieder runtergefahren wird
        }
        println("server closed")
    }
}
