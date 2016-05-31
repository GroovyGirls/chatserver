package de.htw.chatserver.controller

import de.htw.chatserver.persistence.User
import de.htw.chatserver.service.UserService

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Created by vera on 31.05.16.
 */
@Path('register')
class RegisterController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def registerPost(User user) {
        println("start post")
        UserService registerService = new UserService();
        boolean validUser = registerService.register(user);
        //prüfen, ob mail schon vorhanden, dann ablehnen Antworten mit ErrorCode
        //sonst benutzer speichern (json-file)
        //benutzer OK zurückgeben dabei Benutzernamen und Mail zurückgeben
        println(user)

        if (validUser) {
            return Response
                    .ok(user)
                    .build()
        }
        return Response
                .status(Response.Status.CONFLICT)
                .entity(user)
                .build()

    }
}
