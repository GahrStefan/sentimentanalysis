package at.aictopic1.webserver.rest;

import at.aictopic1.webserver.Helper;
import at.aictopic1.webserver.service.ITwitterService;
import at.aictopic1.webserver.service.impl.TwitterService;
import twitter4j.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 
 */
@Path("/keywords/{name}")
public class KeywordsApi {

    private ITwitterService twitterService = TwitterService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("name") String name){
        String output = new JSONArray(twitterService.getAllKeywords(name)).toString();
        return Response.status(200).entity(output).build();
    }

    @PUT
    public Response add(@PathParam("name") String name, String keywords){
        keywords = Helper.getParameter(keywords, "keywords");
        List<String> oldKeywords = twitterService.getAllKeywords(name);

        for(String keyword : keywords.split("\\s?,\\s?")){
            keyword = Helper.capitalize(keyword);
            twitterService.addKeyword(name, keyword);
            oldKeywords.remove(keyword);
        }

        // remove old left keywords
        for(String keyword : oldKeywords){
            twitterService.removeKeyword(name, keyword);
        }

        String output = "Keywords added";
        return Response.status(200).entity(output).build();
    }

    @DELETE
    @Path("{keyword}")
    public Response delete(@PathParam("name") String name,@PathParam("keyword") String keyword){
        twitterService.removeKeyword(name, keyword);

        String output = "Keyword removed";
        return Response.status(200).entity(output).build();
    }
}
