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

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @author Michael Lieshoff
 */
public class CrawlerTest {

    private HttpClientFactory httpClientFactory;

    private HttpClient httpClient;

    @Before
    public void setUp() {
        httpClientFactory = Mockito.mock(HttpClientFactory.class);
        httpClient = Mockito.mock(HttpClient.class);
        when(httpClientFactory.create()).thenReturn(httpClient);
    }

    @Test(expected = NullPointerException.class)
    public void failGetBecauseNullUrl() throws IOException {
        new Crawler(httpClientFactory).get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failGetBecauseEmptyUrl() throws IOException {
        new Crawler(httpClientFactory).get("");
    }

    @Test
    public void shouldGetAbc() throws IOException {
        String expectedResult = "break-out-prison";
        when(httpClientFactory.create()).thenReturn(httpClient);
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http", 100, 1), 200, ""));
        httpResponse.setEntity(new StringEntity(expectedResult));
        when(httpClient.execute((HttpUriRequest) anyObject())).thenReturn(httpResponse);
        assertEquals(expectedResult, new Crawler(httpClientFactory).get("the-url"));
    }

    @Test
    public void shouldGetErrorStatus() throws IOException {
        String expectedResult = "break-out-prison";
        when(httpClientFactory.create()).thenReturn(httpClient);
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http", 100, 1), 200, ""));
        httpResponse.setEntity(new StringEntity(expectedResult));
        httpResponse.setStatusCode(400);
        when(httpClient.execute((HttpUriRequest) anyObject())).thenReturn(httpResponse);
        try {
            new Crawler(httpClientFactory).get("the-url");
        } catch (IOException e) {
            assertEquals("crapi: 400", e.getMessage());
        }
    }

}