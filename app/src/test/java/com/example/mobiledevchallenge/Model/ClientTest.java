package com.example.mobiledevchallenge.Model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class ClientTest extends TestCase {

    private Client client;

    @Before
    public void setUp() {
        client = new Client();

        //Data for tests - OK tests!!!
        client.setNames("Katherine Jacinta Eleuteria");
        client.setLastnames("McKilester Choquehuanca");
        client.setAge(28);
        client.setBirthDate("25/05/1996");

        //Data for tests - failed tests!!!
        /*client.setNames("");
        client.setLastnames("null");
        client.setAge(-1);
        client.setBirthDate("25/05/1996");*/
    }

    @Before
    public void setUpNames(){
        client.setNames("juan");
    }

    @Test
    public void testClientNotNull() {
        assertNotNull(client);
    }

    @Test
    public void testNamesNotNull(){
        assertNotNull(client.getNames());
    }

    @Test
    public void testLastNamesNotNull(){
        assertNotNull(client.getLastnames());
    }

    @Test
    public void testAgeNotNull(){
        assertNotNull(client.getAge());
    }

    @Test
    public void testBirthDateNotNull(){
        assertNotNull(client.getBirthDate());
    }

    @Test
    public void testNamesNotEmpty(){
        assertTrue(!client.getNames().isEmpty());
    }

    @Test
    public void testLastNamesNotEmpty(){
        assertTrue(!client.getLastnames().isEmpty());
    }

    @Test
    public void testAgeNotEmpty(){
        assertTrue(client.getAge()>=0);
    }

    @Test
    public void testBirthdateNotEmpty(){
        assertTrue(!client.getBirthDate().isEmpty());
    }
}