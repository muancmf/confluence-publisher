package com.github.muancmf.confluence.api;

import com.github.muancmf.confluence.publisher.ConfluencePublisher;
import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BaseAPITest {

    private BaseAPI api;
    @Mock
    private ConfluencePublisher publisher;
    @Mock
    HttpRequest request;

    @BeforeEach
    void setUp(){
        this.api = new BaseAPI(publisher);
    }

    @Test
    void authorizedRequest() {
        Mockito.when(publisher.getAuthentication()).thenReturn("auth");
        assertEquals(api.authorizedRequest(Unirest.get("")).getHeaders().get("Authorization").get(0), "Basic auth");
        Mockito.verify(publisher, Mockito.times(1)).getAuthentication();
    }

    @Test
    void getURI() {
        Mockito.when(publisher.getDomainName()).thenReturn("domain");
        assertEquals(api.getURI(), "https://domain/wiki/rest/api");
        Mockito.verify(publisher, Mockito.times(1)).getDomainName();
    }
}