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
 */
@Path('login')
@Provider
class LoginController {

    UserService loginService;

    @Context
    org.glassfish.grizzly.http.server.Request request

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def loginPost(User user) {
        println("start login mail: ${user.mail} und password: ${user.password}")

        loginService = new UserService();
        //TODO IP-ADresse auslesen
        String remoteAddr = request.getRemoteAddr()

        println(remoteAddr)

        boolean isValidLogin = loginService.isUserAndPwdValid(user.mail, user.password, remoteAddr)

        if (isValidLogin) {
            return Response
                    .ok()
                    .build()
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build()
            // TODO auswertbare Fehlermeldung mitgeben
        }
    }

}

