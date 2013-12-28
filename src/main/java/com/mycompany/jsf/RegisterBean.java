/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.jsf;



import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.inject.Named;


@Named 
@RequestScoped
public class RegisterBean implements Serializable {

    private static final Logger log = Logger.getLogger(RegisterBean.class.getName());
    
    private String login;
    
    private String password;
    
    @Inject
    PlayerFacadeLocal playerFacade;
    

    public RegisterBean() {
        log.fine("registerBean initialized");
    }
    
    public String performRegistration() {
        log.fine("performing register...");
        playerFacade.registerPlayer(login, password);
        return "login";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
