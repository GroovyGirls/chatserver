package de.htw.chatserver.controller

import de.htw.chatserver.service.UserService

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Created by Ju on 06.06.2016.
 */
@Path('login')

class LoginController {

    UserService loginService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    def loginPost (String mail, String pwd){

        loginService = new UserService();
        //TODO IP-ADresse auslesen
        loginService.isUserAndPwdValid(mail, pwd, "DummyIP")
        //TODO Bekommt Userdaten per POST
    }
    //TODO ruft UserService auf und überprüft Daten + (dort wird wenn true): User mit IP-Adresse in OnlineUser-Map speichern
}

