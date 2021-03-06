/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jcrapi;

import jcrapi.model.Constants;
import jcrapi.model.DetailedClan;
import jcrapi.model.Profile;
import jcrapi.model.TopClans;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Michael Lieshoff
 */
public class ApiTest {

    private ClientFactory clientFactory;

    private Client client;

    private Api api;

    @Before
    public void setUp() {
        clientFactory = Mockito.mock(ClientFactory.class);
        client = Mockito.mock(Client.class);
        when(clientFactory.createClient("lala")).thenReturn(client);
        api = new Api("lala", clientFactory);
    }

    @Test(expected = NullPointerException.class)
    public void failCreateBecauseNullUrl() {
        new Api(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateBecauseEmptyUrl() {
        new Api("");
    }

    @Test
    public void shouldGetVersion() throws Exception {
        when(client.getVersion()).thenReturn("1.0");
        assertEquals("1.0", api.getVersion());
    }

    @Test
    public void failGetVersion() throws Exception {
        when(client.getVersion()).thenThrow(new IOException("crapi: 400"));
        try {
            api.getVersion();
        } catch(ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test(expected = NullPointerException.class)
    public void failGetProfileBecauseNullTag() throws Exception {
        api.getProfile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failGetProfileBecauseEmptyTag() throws Exception {
        api.getProfile("");
    }

    @Test
    public void shouldGetProfile() throws Exception {
        Profile profile = new Profile();
        when(client.getProfile("abc")).thenReturn(profile);
        assertEquals(profile, api.getProfile("abc"));
    }

    @Test
    public void failGetProfile() throws Exception {
        when(client.getProfile("abc")).thenThrow(new IOException("crapi: 400"));
        try {
            api.getProfile("abc");
        } catch(ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test
    public void shouldGetTopClans() throws Exception {
        TopClans topClans = new TopClans();
        when(client.getTopClans()).thenReturn(topClans);
        assertEquals(topClans, api.getTopClans());
    }

    @Test
    public void failGetTopClans() throws Exception {
        when(client.getTopClans()).thenThrow(new IOException("crapi: 400"));
        try {
            api.getTopClans();
        } catch(ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test(expected = NullPointerException.class)
    public void failGetClanBecauseNullTag() throws Exception {
        api.getClan(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failGetClanBecauseEmptyTag() throws Exception {
        api.getClan("");
    }

    @Test
    public void shouldGetClan() throws Exception {
        DetailedClan detailedClan = new DetailedClan();
        when(client.getClan("abc")).thenReturn(detailedClan);
        assertEquals(detailedClan, api.getClan("abc"));
    }

    @Test
    public void failGetClan() throws Exception {
        when(client.getClan("abc")).thenThrow(new IOException("crapi: 400"));
        try {
            api.getClan("abc");
        } catch(ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void failGetClansBecauseNullTags() throws Exception {
        api.getClans(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failGetClansBecauseEmptyTags() throws Exception {
        api.getClans(new ArrayList<String>());
    }

    @Test
    public void shouldGetClans() throws Exception {
        List<DetailedClan> detailedClans = new ArrayList<DetailedClan>();
        List<String> tags = new ArrayList<String>();
        tags.add("abc");
        when(client.getClans(tags)).thenReturn(detailedClans);
        assertEquals(detailedClans, api.getClans(tags));
    }

    @Test
    public void failGetClans() throws Exception {
        List<String> tags = new ArrayList<String>();
        tags.add("abc");
        when(client.getClans(tags)).thenThrow(new IOException("crapi: 400"));
        try {
            api.getClans(tags);
        } catch(ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

    @Test
    public void shouldGetConstants() throws Exception {
        Constants constants = new Constants();
        when(client.getConstants()).thenReturn(constants);
        assertEquals(constants, api.getConstants());
    }

    @Test
    public void failGetConstants() throws Exception {
        when(client.getConstants()).thenThrow(new IOException("crapi: 400"));
        try {
            api.getConstants();
        } catch(ApiException e) {
            assertEquals(400, e.getCode());
        }
    }

}
