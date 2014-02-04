/*
   Copyright (c) 2013 LinkedIn Corp.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.linkedin.restli.client.uribuilders;


import com.linkedin.restli.client.ActionRequest;
import com.linkedin.restli.client.BatchCreateRequest;
import com.linkedin.restli.client.BatchDeleteRequest;
import com.linkedin.restli.client.BatchGetKVRequest;
import com.linkedin.restli.client.BatchGetRequest;
import com.linkedin.restli.client.BatchPartialUpdateRequest;
import com.linkedin.restli.client.BatchUpdateRequest;
import com.linkedin.restli.client.CreateRequest;
import com.linkedin.restli.client.DeleteRequest;
import com.linkedin.restli.client.FindRequest;
import com.linkedin.restli.client.GetAllRequest;
import com.linkedin.restli.client.GetRequest;
import com.linkedin.restli.client.OptionsRequest;
import com.linkedin.restli.client.PartialUpdateRequest;
import com.linkedin.restli.client.Request;
import com.linkedin.restli.client.UpdateRequest;
import com.linkedin.restli.common.ProtocolVersion;
import com.linkedin.restli.internal.common.AllProtocolVersions;


/**
 * @author kparikh
 */
public class RestliUriBuilderUtil
{
  public static RestliUriBuilder createUriBuilder(Request request)
  {
    return createUriBuilder(request, "", AllProtocolVersions.BASELINE_PROTOCOL_VERSION);
  }

  public static RestliUriBuilder createUriBuilder(Request request, String uriPrefix)
  {
    return createUriBuilder(request, uriPrefix, AllProtocolVersions.BASELINE_PROTOCOL_VERSION);
  }

  /**
   * Create a {@link RestliUriBuilder} based on {@code request}
   * @param request the {@link Request} we are building the {@link RestliUriBuilder} for
   * @param uriPrefix prefix for the uri. E.g. "d2://", "http://localhost:9000/" etc.
   * @param version which version of the Rest.li protocol to use when building the URI
   * @return a {@link RestliUriBuilder} for the input {@code request}
   */
  public static RestliUriBuilder createUriBuilder(Request request, String uriPrefix, ProtocolVersion version)
  {
    switch (request.getMethod())
    {
      case ACTION:
        return new ActionRequestUriBuilder((ActionRequest)request, uriPrefix, version);
      case GET:
        return new GetRequestUriBuilder((GetRequest)request, uriPrefix, version);
      case BATCH_GET:
        if (request instanceof BatchGetRequest)
        {
          return new BatchGetRequestUriBuilder((BatchGetRequest)request, uriPrefix, version);
        }
        else
        {
          return new BatchGetKVRequestUriBuilder((BatchGetKVRequest)request, uriPrefix, version);
        }
      case FINDER:
        return new FindRequestUriBuilder((FindRequest)request, uriPrefix, version);
      case CREATE:
        return new CreateRequestUriBuilder((CreateRequest)request, uriPrefix, version);
      case BATCH_CREATE:
        return new BatchCreateRequestUriBuilder((BatchCreateRequest)request, uriPrefix, version);
      case PARTIAL_UPDATE:
        return new PartialUpdateRequestUriBuilder((PartialUpdateRequest)request, uriPrefix, version);
      case UPDATE:
        return new UpdateRequestUriBuilder((UpdateRequest)request, uriPrefix, version);
      case BATCH_UPDATE:
        return new BatchUpdateRequestUriBuilder((BatchUpdateRequest)request, uriPrefix, version);
      case DELETE:
        return new DeleteRequestUriBuilder((DeleteRequest)request, uriPrefix, version);
      case BATCH_PARTIAL_UPDATE:
        return new BatchPartialUpdateRequestUriBuilder((BatchPartialUpdateRequest)request, uriPrefix, version);
      case BATCH_DELETE:
        return new BatchDeleteRequestUriBuilder((BatchDeleteRequest)request, uriPrefix, version);
      case GET_ALL:
        return new GetAllRequestUriBuilder((GetAllRequest)request, uriPrefix, version);
      case OPTIONS:
        return new OptionsRequestUriBuilder((OptionsRequest)request, uriPrefix, version);
      default:
        throw new IllegalArgumentException("Unknown Request method " + request.getMethod());
    }
  }
}
