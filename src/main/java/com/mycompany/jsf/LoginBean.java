/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.jsf;



import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import javax.inject.Named;


@Named 
@RequestScoped
public class LoginBean implements Serializable {

    private static final Logger log = Logger.getLogger(LoginBean.class.getName());
    
    private String login;
    
    private String password;
    
    @Inject
    PlayerBean playerBean;
    
    @Inject
    PlayerFacadeLocal playerFacade;
    

    public LoginBean() {
        log.fine("loginBean initialized");
    }
    
    public String performLogin() {
        log.fine("performing login...");
    
        Player player = playerFacade.findByLogin(this.login);
        if (player != null) {
            log.log(Level.FINE, "Login successful. Password = {0}", player.getPassword());
            playerBean.setLogin(login);
        } else {
            log.fine("Login failed.");
        }
        return "loginSuccess";
    }
    
    public String performLogout() {
        log.fine("performing logout...");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./login.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
