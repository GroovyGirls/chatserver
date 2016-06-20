package de.htw.chatserver.controller

import de.htw.chatserver.persistence.User
import de.htw.chatserver.service.UserService

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider
//import org.vertx.groovy.core.http.HttpServerRequest
/**
 * Created by Ju on 06.06.2016.
 */
@Path('login')
@Provider
class LoginController {

    UserService loginService;

   @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    def loginPost(User user, @Context HttpServletRequest request) {
        println("start login mail: ${user.mail} und password: ${user.password}")

//        println( request )

        loginService = new UserService();
        //TODO IP-ADresse auslesen
        String remoteAddr
        if (request == null) {
            remoteAddr = "dummyIp" // TODO dummy-Wert muss entfernt werden sobald ip ausgelesen werden kann
        } else {
            remoteAddr = request.getHeader("X-Forwarded-For")
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("Proxy-Client-IP")
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("WL-Proxy-Client-IP")
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("HTTP_CLIENT_IP")
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR")
            }
            if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
                remoteAddr = request.getRemoteAddr()
            }
//            return remoteAddr

//            remoteAddr = request.getRemoteAddr()
        }
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

