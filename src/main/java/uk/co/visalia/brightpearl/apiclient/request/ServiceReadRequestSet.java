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

package uk.co.visalia.brightpearl.apiclient.request;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * An iterable set of read requests returned from {@link ServiceReadRequestBuilder}.
 * @param <T> placeholder for the response type, used to avoid casting of responses. This is not
 *           linked to the {@link java.lang.reflect.Type} set on each request so a {@link ClassCastException} is possible if the type is set
 *           incorrectly.
 */
public class ServiceReadRequestSet<T> implements Iterable<ServiceReadRequest<T>> {

    private Set<ServiceReadRequest<T>> requests;

    ServiceReadRequestSet(Set<ServiceReadRequest<T>> requests) {
        this.requests = Collections.unmodifiableSet(requests);
    }

    public Set<ServiceReadRequest<T>> getRequests() {
        return requests;
    }

    @Override
    public Iterator<ServiceReadRequest<T>> iterator() {
        return requests.iterator();
    }
}