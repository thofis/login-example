/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("add")
public class AddResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNumbers(@QueryParam("zahl1") String zahl1, @QueryParam("zahl2") String zahl2) {
        int z1 = Integer.parseInt(zahl1);
        int z2 = Integer.parseInt(zahl2);
        int ergebnis = z1 + z2;
        Addition addition = new Addition(z1, z2, ergebnis);
        return Response.ok(addition).build();
    }
    
    
    
}
