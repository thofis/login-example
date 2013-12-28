/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.jsf;

import javax.ejb.Local;


@Local
public interface PlayerFacadeLocal {
    
    public Player findByLogin(String login);
    
    public void registerPlayer(String login, String password);
}
