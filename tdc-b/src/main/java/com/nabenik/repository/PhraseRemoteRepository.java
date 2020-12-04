package com.nabenik.repository;

import com.nabenik.dto.Phrase;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("phrases")
@RegisterRestClient
public interface PhraseRemoteRepository {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Phrase> getPhrases();

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Phrase getPhraseById(@PathParam("id") Long id);

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deletePhrase(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPhrase(Phrase phrase);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePhrase(Phrase phrase);
}
