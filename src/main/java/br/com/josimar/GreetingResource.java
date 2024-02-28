package br.com.josimar;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.josimar.kafka.genes.ReadGenes;
import br.com.josimar.kafka.nba.ReadStats;

@Path("/hello")
public class GreetingResource {

    @Inject
    ReadStats readStats;

    @Inject
    ReadGenes readGenes;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        // readStats.reader();
        readGenes.reader();
        return "Hello from RESTEasy Reactive";
    }
}