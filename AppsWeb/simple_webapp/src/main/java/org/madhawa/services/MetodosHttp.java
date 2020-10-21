package org.madhawa.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class MetodosHttp {

    ListaAliens aliens = new ListaAliens();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Saludo() {
        return "Bienvenido a la Raiz";
    }

    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("/saludo/{nombre}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello(@PathParam("nombre") String nombre) {
        return "Bienvenido: " + nombre;
    }

    @GET
    @Path("/aliens")
    @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
    public List<Alien> getAliens() {
        return aliens.getAliens();
    }

    @GET
    @Path("/alien/{id}")
    @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
    public Alien getAlien(@PathParam("id") int id){
        return aliens.getAlien(id);
    }

    @POST
    @Path("/insert")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Alien createAlien(Alien alien) {
        aliens.create(alien);
        return alien;
    }

    @DELETE
    @Path("/delete/{id}")
    public Alien deleteAlien(@PathParam("id") int id) {
        Alien alien = aliens.getAlien(id);

        if (alien.getId() != 0) {
            aliens.delete(id);
        }
        return alien;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Alien updateAlien(Alien alien, @PathParam("id") int id) {
        aliens.update(alien, id);
        return alien;
    }
}