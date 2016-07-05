package de.htw.chatserver.controller

import de.htw.chatserver.service.UserService

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

/**
 * @author vera on 20.06.16.
 */
@Path('logout')
@Provider
class LogoutController {


    @Context
    org.glassfish.grizzly.http.server.Request request

    /**
     * Ausloggen des Nutzers.
     * @return 200 Ok wenn ausloggen erfolgreich, ansosnten 404 NOT_FOUND
     */
    @POST
    def logout() {
        UserService userService = UserService.getInstance()
        String remoteAddr = request.getRemoteAddr()
        // TODO ausloggen funktioniert noch nicht
        if (userService.logout(remoteAddr)) {
            return Response
                    .ok()
                    .build()
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build()
        }
    }
}
