
package com.nabenik;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.helidon.microprofile.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PhraseTest {

    private static Server server;
    private static String serverUrl;
    private static Client client;

    @BeforeAll
    public static void startTheServer() {
        client = ClientBuilder.newClient();
        server = Server.create().start();
        serverUrl = "http://localhost:" + server.port();
    }

    @AfterAll
    static void destroyClass() {
        CDI<Object> current = CDI.current();
        ((SeContainer) current).close();
    }

    @Test
    void testPokemonTypes() {
        JsonArray types = client.target(serverUrl)
                .path("phrases")
                .request()
                .get(JsonArray.class);
        assertThat(types.size(), is(2));
    }


    @Test
    void testHealthMetrics() {
        Response response = client.target(serverUrl)
                .path("health")
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
        response = client.target(serverUrl)
                .path("metrics")
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
    }

}
