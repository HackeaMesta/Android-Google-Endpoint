/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-05-27 16:00:31 UTC)
 * on 2016-06-04 at 16:12:47 UTC 
 * Modify at your own risk.
 */

package com.appspot.data_base_1298.proxyEndpointConocidos.model;

/**
 * Model definition for ConocidoCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the proxyEndpointConocidos. For a detailed explanation
 * see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ConocidoCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Conocido> items;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Conocido> getItems() {
    return items;
  }

  /**
   * @param items items or {@code null} for none
   */
  public ConocidoCollection setItems(java.util.List<Conocido> items) {
    this.items = items;
    return this;
  }

  @Override
  public ConocidoCollection set(String fieldName, Object value) {
    return (ConocidoCollection) super.set(fieldName, value);
  }

  @Override
  public ConocidoCollection clone() {
    return (ConocidoCollection) super.clone();
  }

}
