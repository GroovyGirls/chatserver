package de.htw.chatserver.controller

import de.htw.chatserver.persistence.User
import de.htw.chatserver.service.UserService

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

/**
 * Created by Ju on 06.06.2016.
 *
 * Controller für den Login.
 */
@Path('login')
@Provider
class LoginController {

    UserService userService;

    @Context
    org.glassfish.grizzly.http.server.Request request

    /**
     * User loggt sich mit Email und Passwort ein. Es können sich nur regestrierte Nutzer einloggen.
     * @param user die Felder Email und Passwort müssen gesetzt sein. Und die Daten werden mit den regestrierten Nutzern abgeglichen.
     * @return Gibt 200 Ok zurück, wenn Nutzer erfoglreich eingeloggt ist, sonst wird als HTTP-Status 404 NOT_FOUND zurückgegeben
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def loginPost(User user) {
        println("start login mail: ${user.mail} und password: ${user.password}")

        userService = UserService.getInstance();
        String remoteAddr = request.getRemoteAddr()

        println(remoteAddr)

        boolean isValidLogin = userService.isUserAndPwdValid(user.mail, user.password, remoteAddr)

        if (isValidLogin) {
            return Response
                    .ok()
                    .build()
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build()
            // TODO auswertbare Fehlermeldungen anstelle von NOT_FOUND
        }
    }

}
