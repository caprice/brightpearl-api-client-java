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

package uk.co.visalia.brightpearl.apiclient.config;

/**
 * A setting for the {@link uk.co.visalia.brightpearl.apiclient.BrightpearlLegacyApiSession}, this determines whether the session
 * should attempt to reauthenticate with configured credentials when a 401 Unauthorized response is received from the
 * Brightpearl API, which indicates the auth token has expired.
 */
public enum ExpiredAuthTokenStrategy {

    /**
     * Attempt reauthentication when a 401 response is received and credentials are configured.
     */
    REAUTHENTICATE,

    /**
     * Throw {@link uk.co.visalia.brightpearl.apiclient.exception.BrightpearlAuthException} when 401 responses are received.
     */
    FAIL

}
