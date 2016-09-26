package at.aictopic1.webserver.rest;

import at.aictopic1.webserver.service.ITwitterService;
import at.aictopic1.webserver.service.impl.TwitterService;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * 
 */
@Path("/twitter")
public class TwitterApi {

    private ITwitterService twitterService = TwitterService.getInstance();

    @POST
    public Response start(){
        twitterService.startCollecting();

        String output = "Start collecting";
        return Response.status(200).entity(output).build();
    }

    @DELETE
    public Response stop(){
        twitterService.stopCollecting();

        String output = "Stop collecting";
        return Response.status(200).entity(output).build();
    }

    @GET
    public Response status(){
        String output = twitterService.status();
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("stream")
    public Response stream(){
        String output = new JSONArray(twitterService.getLastTweets()).toString();
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("analyze/{name}")
    public Response analyze(@PathParam("name") String name){
        String output = new JSONObject(twitterService.analyze(name)).toString();
        return Response.status(200).entity(output).build();
    }
}
