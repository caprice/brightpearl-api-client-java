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

package uk.co.visalia.brightpearl.apiclient.exception;

/**
 * This exception is thrown when a request cap error is detected, caused by too many requests being made to a single
 * account.
 */
public class BrightpearlRequestCapException extends BrightpearlClientException {

    public BrightpearlRequestCapException(String message) {
        super(message);
    }

    public BrightpearlRequestCapException(String message, Exception cause) {
        super(message, cause);
    }

}
