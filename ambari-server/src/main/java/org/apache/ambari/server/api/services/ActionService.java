/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ambari.server.api.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.ambari.server.api.resources.ActionResourceDefinition;
import org.apache.ambari.server.api.resources.ResourceDefinition;

public class ActionService extends BaseService {
  /**
   * Parent cluster name.
   */
  private String m_clusterName;
  
  private String m_serviceName;

  /**
   * Constructor.
   *
   * @param clusterName cluster id
   * @param serviceName service
   */
  public ActionService(String clusterName, String serviceName) {
    m_clusterName = clusterName;
    m_serviceName = serviceName;
  }

  /**
   * Handles URL: /clusters/{clusterId}/services/{serviceName}/actions
   * Get all actions for a service in a cluster.
   *
   * @param headers http headers
   * @param ui      uri info
   * @return service collection resource representation
   */
  @GET
  @Produces("text/plain")
  public Response getActions(@Context HttpHeaders headers, @Context UriInfo ui) {
    return handleRequest(headers, null, ui, Request.Type.GET,
        createResourceDefinition(null, m_clusterName, m_serviceName));
  }

  /**
   * Handles URL: /clusters/{clusterId}/services/{serviceName}/actions.  
   * The body should contain:
   * <pre>
   * {
   *     "actionName":"name_string",
   *     "parameters":
   *     {
   *         "key1":"value1",
   *         // ...
   *         "keyN":"valueN"
   *     }
   * }
   * </pre>
   * Get all services for a cluster.
   *
   * @param headers http headers
   * @param ui      uri info
   * @return service collection resource representation
   */
  @POST
  @Produces("text/plain")
  public Response createActions(String body,@Context HttpHeaders headers, @Context UriInfo ui) {
    return handleRequest(headers, body, ui, Request.Type.POST,
        createResourceDefinition(null, m_clusterName, m_serviceName));
  }
  
  /**
   * Handles: POST /clusters/{clusterId}/services/{serviceId}/{actionName}
   * Create a specific service.
   *
   * @param body        http body
   * @param headers     http headers
   * @param ui          uri info
   * @param serviceName service id
   * @return information regarding the created service
   */
  @POST
  @Path("{actionName}")
  @Produces("text/plain")
  public Response createService(String body, @Context HttpHeaders headers, @Context UriInfo ui,
                                @PathParam("actionName") String actionName) {
    return handleRequest(headers, body, ui, Request.Type.POST,
        createResourceDefinition(actionName, m_clusterName, m_serviceName));
  }

  /**
   * Create a service resource definition.
   *
   * @param serviceName host name
   * @param clusterName cluster name
   * @return a service resource definition
   */
  ResourceDefinition createResourceDefinition(String actionName,
      String clusterName, String serviceName) {
    return new ActionResourceDefinition(actionName, clusterName, serviceName);
  }
}
