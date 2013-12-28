/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jsf;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PlayerFacade implements PlayerFacadeLocal {

    @PersistenceContext(unitName = "login-example-pu")
    private EntityManager em;

    public PlayerFacade() {
    }

    @Override
    public Player findByLogin(String login) {
        Query query = em.createQuery("from Player p where p.login = :login");
        query.setParameter("login", login);
        List<Player> players = query.getResultList();
        if (players != null && !players.isEmpty()) {
            return players.get(0); 
        } else {
            return null;
        }
    }

    @Override
    public void registerPlayer(String login, String password) {
        Player u = new Player(login, password);
        em.persist(u);
    }

}
