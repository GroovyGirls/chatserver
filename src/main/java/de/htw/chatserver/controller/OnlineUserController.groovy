package de.htw.chatserver.controller

import de.htw.chatserver.service.UserService
import net.sf.json.JSONObject

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

/**
 * @author vera on 21.06.16.
 */
@Path('onlineUser')
@Provider
class OnlineUserController {

    UserService userService;

    /**
     * Dient zum Abruf der eingeloggten User.
     * @return Eine Map die die Email und die IP-Adresse der eingeloggten User enthält.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    def getOnlineUser() {
        println("start get onlineUsers")

        userService = UserService.getInstance();

        def users = userService.getOnlineUsers()

        return Response
                .ok(new JSONObject(users))
                .build()

    }

}
