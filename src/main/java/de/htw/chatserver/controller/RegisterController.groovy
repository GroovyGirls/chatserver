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

    /**
     * Regestrieren eines neuen Nutzers.
     * @param user Die Daten des Users müssen Emailadresse, Name und Passwort enthalten. Pro Emailadresse kann
     * nur ein Account angelegt werden.
     * @return 200 OK bei erfolgreicher Regestrierung, ansonsten 200 OK
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def registerPost(User user) {
        println("start post")
        UserService registerService = UserService.getInstance();
        boolean validUser = registerService.register(user);
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
        // TODO auswertbare Fehlermeldung zurückgeben.
    }
}
