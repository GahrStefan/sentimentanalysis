package at.aictopic1.webserver.rest;

import at.aictopic1.webserver.Helper;
import at.aictopic1.webserver.service.ITwitterService;
import at.aictopic1.webserver.service.impl.TwitterService;
import twitter4j.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
@Path("/customers")
public class CustomersApi {

    private ITwitterService twitterService = TwitterService.getInstance();

    @POST
    public Response add(String name){
        name = Helper.getParameter(name, "name");
        twitterService.addCustomer(name);

        String output = "Customer added";
        return Response.status(200).entity(output).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name){
        twitterService.removeCustomer(name);

        String output = "deleted: "+ name;
        return Response.status(200).entity(output).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        String output = new JSONArray(twitterService.getAllCustomers()).toString();
        return Response.status(200).entity(output).build();
    }
}
