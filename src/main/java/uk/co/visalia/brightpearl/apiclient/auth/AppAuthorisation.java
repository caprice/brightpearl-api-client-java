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

package uk.co.visalia.brightpearl.apiclient.auth;

import uk.co.visalia.brightpearl.apiclient.account.Account;

import java.util.Map;

/**
 * <p>
 * Interface for classes that combine details of an an app with details of an account, providing account details
 * and authentication headers for {@link uk.co.visalia.brightpearl.apiclient.BrightpearlApiClient}.
 * </p><p>
 * This interface is generic enough to support all four types of app, which each require different headers, together with
 * legacy auth tokens.
 * </p>
 */
public interface AppAuthorisation {

    static final String DEV_HEADER = "brightpearl-dev-ref";
    static final String APP_HEADER = "brightpearl-app-ref";
    static final String ACCOUNT_TOKEN_HEADER = "brightpearl-account-token";
    static final String STAFF_TOKEN_HEADER = "brightpearl-staff-token";

    static final String LEGACY_AUTH_HEADER = "brightpearl-auth";

    /**
     * Returns the Brightpearl customer's account.
     */
    Account getAccount();

    /**
     * Returns authentication headers required for requests made to the customer's account.
     */
    Map<String, String> getHeaders();

}
