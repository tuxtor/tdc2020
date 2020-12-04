package com.nabenik.controller;

import com.nabenik.repository.PhraseRemoteRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;

@RequestScoped
@Path("/mp")
public class MPResource {

    @Inject
    @ConfigProperty(name = "tdc", defaultValue = "O maior evento de desenvolvedores no Brasil")
    String tdc;

    @Inject
    @RestClient
    PhraseRemoteRepository phraseRepository;

    @Inject
    @Metric
    Counter failedQueries;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Fallback(fallbackMethod = "generateTextFallback")
    @Timeout( value=2000L )
    @Operation( description = "Endpoint criado para testar alguns dos Microservice Patterns com MicroProfile")
    public String generateText() throws IllegalStateException {
        var test = "Seja bem vindo ao consumer service \n";
        test += "\n A minha configuração diz " + tdc;

        var totalPhrases = phraseRepository.getPhrases().size();

        test += "\n Atualmente tem " + totalPhrases + " phrases";

        return test;
    }


    public String generateTextFallback(){
        var test = "Seja bem vindo ao consumer service \n";
        test += "\n A minha configuração diz " + tdc;
        failedQueries.inc();
        return test;
    }
}
