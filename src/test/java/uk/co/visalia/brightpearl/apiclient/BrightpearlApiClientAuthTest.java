/*
 * Copyright 2014 David Morrissey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.visalia.brightpearl.apiclient;

import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.Test;
import uk.co.visalia.brightpearl.apiclient.account.Account;
import uk.co.visalia.brightpearl.apiclient.account.Datacenter;
import uk.co.visalia.brightpearl.apiclient.auth.AppAuthorisation;
import uk.co.visalia.brightpearl.apiclient.auth.PrivateAppIdentity;
import uk.co.visalia.brightpearl.apiclient.exception.BrightpearlHttpException;
import uk.co.visalia.brightpearl.apiclient.exception.BrightpearlServiceException;
import uk.co.visalia.brightpearl.apiclient.exception.ClientErrorCode;
import uk.co.visalia.brightpearl.apiclient.http.ClientFactory;
import uk.co.visalia.brightpearl.apiclient.http.httpclient4.HttpClient4ClientFactoryBuilder;

import java.util.concurrent.TimeUnit;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BrightpearlApiClientAuthTest extends ClientDriverTestSupport {

    @Rule
    public ClientDriverRule driver = new ClientDriverRule(CLIENT_DRIVER_PORT);

    @Test
    public void testAuth_Success() {

        driver.addExpectation(
                onRequestTo("/visalia/authorise")
                        .withMethod(Method.POST)
                        .withHeader(AppAuthorisation.APP_HEADER, APP_REFERENCE),
                giveResponse("{response:\"4dc2d23f-2354-54dc-2d23-f2392a4dc2d2\"}", JSON_CONTENT_TYPE)
                        .withStatus(200)
        );

        BrightpearlApiClient client = basicBrightpearlApiClient();
        String staffToken = client.fetchStaffToken(APP_IDENTITY, CREDENTIALS);
        assertThat(staffToken, is("4dc2d23f-2354-54dc-2d23-f2392a4dc2d2"));

    }

    @Test
    public void testAuth_SocketTimeout() {

        ClientFactory clientFactory = HttpClient4ClientFactoryBuilder
                .httpClient4ClientFactory()
                .withSocketTimeoutMs(200)
                .build();

        BrightpearlApiClient client = BrightpearlApiClientFactory.brightpearlApiClient().withClientFactory(clientFactory).build();

        driver.addExpectation(
                onRequestTo("/visalia/authorise")
                        .withMethod(Method.POST)
                        .withHeader(AppAuthorisation.APP_HEADER, APP_REFERENCE),
                giveResponse("{response:\"4dc2d23f-2354-54dc-2d23-f2392a4dc2d2\"}", JSON_CONTENT_TYPE)
                        .withStatus(200)
                        .after(2000, TimeUnit.MILLISECONDS)
        );

        try {
            client.fetchStaffToken(APP_IDENTITY, CREDENTIALS);
        } catch (BrightpearlHttpException e) {
            assertThat(e.getClientErrorCode(), is(ClientErrorCode.SOCKET_TIMEOUT));
        }

    }

    @Test
    public void testAuth_UnknownHost() {

        Account account = new Account(new Datacenter("INVALID", "http://notavaliddomainname.ppp"), "visalia");
        PrivateAppIdentity appIdentity = PrivateAppIdentity.create(account, APP_REFERENCE);

        BrightpearlApiClient client = basicBrightpearlApiClient();
        try {
            client.fetchStaffToken(appIdentity, CREDENTIALS);
        } catch (BrightpearlHttpException e) {
            assertThat(e.getClientErrorCode(), is(ClientErrorCode.UNKNOWN_HOST));
        }

    }


    @Test
    public void testAuth_ConnectionError() {

        Account account = new Account(new Datacenter("INVALID", "http://localhost:9999"), "visalia");
        PrivateAppIdentity appIdentity = PrivateAppIdentity.create(account, APP_REFERENCE);

        BrightpearlApiClient client = basicBrightpearlApiClient();
        try {
            client.fetchStaffToken(appIdentity, CREDENTIALS);
        } catch (BrightpearlHttpException e) {
            assertThat(e.getClientErrorCode(), is(ClientErrorCode.SOCKET_ERROR));
        }

    }

    @Test
    public void testAuth_InvalidCredentials() {

        driver.addExpectation(
                onRequestTo("/visalia/authorise")
                        .withMethod(Method.POST)
                        .withHeader(AppAuthorisation.APP_HEADER, APP_REFERENCE),
                giveResponse("{\"errors\":[{\"code\":\"GWYB-006\",\"message\":\"Unable to authenticate user@visalia.co.uk\"}]}", JSON_CONTENT_TYPE)
                        .withStatus(401)
        );

        BrightpearlApiClient client = basicBrightpearlApiClient();
        try {
            client.fetchStaffToken(APP_IDENTITY, CREDENTIALS);
        } catch (BrightpearlServiceException e) {
            assertThat(e.getServiceErrors().get(0).getCode(), is("GWYB-006"));
        }

    }

    @Test
    public void testAuth_InvalidAccountCode() {

        driver.addExpectation(
                onRequestTo("/visalia/authorise")
                        .withMethod(Method.POST)
                        .withHeader(AppAuthorisation.APP_HEADER, APP_REFERENCE),
                giveResponse("{\"errors\":[{\"code\":\"GWYB-003\",\"message\":\"Account \\u0027visalia\\u0027 does not have API access\"}]}", JSON_CONTENT_TYPE)
                        .withStatus(401)
        );

        BrightpearlApiClient client = basicBrightpearlApiClient();
        try {
            client.fetchStaffToken(APP_IDENTITY, CREDENTIALS);
        } catch (BrightpearlServiceException e) {
            assertThat(e.getServiceErrors().get(0).getCode(), is("GWYB-003"));
        }
    }

}
