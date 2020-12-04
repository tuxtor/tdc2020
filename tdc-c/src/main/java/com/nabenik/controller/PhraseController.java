package com.nabenik.controller;

import com.nabenik.model.Phrase;
import com.nabenik.repository.PhraseRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("phrases")
@RequestScoped
public class PhraseController {
    
    @Inject
    PhraseRepository phraseRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Phrase> getPhrases() {
        return phraseRepository.findAll();
        
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Phrase getPhraseById(@PathParam("id") Long id) {
        
        var phrase = phraseRepository.findBy(id);
        
        if (phrase == null) {
            throw new NotFoundException("Unable to find phrase with ID " + id);
        }
        return phrase;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deletePhrase(@PathParam("id") Long id) {
        var phrase = phraseRepository.findBy(id);
        phraseRepository.remove(phrase);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public Response createPhrase(Phrase phrase) {
        phrase = phraseRepository.saveAndFlush(phrase);
        return Response.status(Response.Status.CREATED).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public Response updatePhrase(Phrase phrase) {
        Phrase oldPhrase = this.phraseRepository.findBy(phrase.getPhraseId());
        if(oldPhrase == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        phrase = phraseRepository.saveAndFlush(phrase);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
