/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.appspot.data_base_1298.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Key;

import java.util.List;

import javax.inject.Named;

import static com.appspot.data_base_1298.backend.Constantes.NOMBRE;
import static com.googlecode.objectify.ObjectifyService.ofy;

/** An endpoint class we are exposing */
@Api(
  name = "proxyEndpointConocidos",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "data-base-1298.appspot.com",
    ownerName = "data-base-1298.appspot.com",
    packagePath=""
  )
)
public class EndpointConocido {
    /**
     * Devuelve una lista con todos los conocidos registrados. Usa el método GET
     * de HTTP.
     * @return una lista con todos los conocidos registrados.
     */
    @ApiMethod(name = "list") public List<Conocido> list() {
        return ofy().load().type(Conocido.class).order(NOMBRE).list();
    }
    /**
     * Devuelve el conocido que corresponde al id. Usa el método GET de HTTP.
     * @param id llave primaria .
     * @return el conocido asociado con el valor de id, o null si no lo encuentra.
     */
    @ApiMethod(name = "get") public Conocido get(@Named("id") String id) {
        return id == null || id.isEmpty() ? null :
                ofy().load().key(Key.<Conocido>create(id)).now();
    }
    /**
     * Agrega un nuevo conocido. Usa el método POST de HTTP.
     * @param model datos capturados en la interfaz gráfica.
     */
    @ApiMethod(name = "insert") public void insert(Conocido model) {
        preparaModel(model);
        ofy().save().entity(model).now();
    }
    /**
     * Actualiza un conocido ya registrado. Usa el método PUT de HTTP.
     * @param model datos capturados en la interfaz gráfica.
     */
    @ApiMethod(name = "update") public void update(Conocido model) {
        preparaModel(model);
        ofy().save().entity(model).now();
    }
    /**
     * Elimina el conocido con la llave primaria id. Usa el método DELETE de
     * HTTP.
     * @param id la llave primaria del conocido que se elimina.
     */
    @ApiMethod(name = "remove") public void remove(@Named("id") String id) {
        ofy().delete().key(Key.create(id)).now();
    }

    private void preparaModel(Conocido viewModel) {
        viewModel.setNombre(viewModel.getNombre().trim());
        viewModel.setTelefono(viewModel.getTelefono().trim());
        viewModel.setDescripcion(viewModel.getDescripcion());
        viewModel.setLatitude(viewModel.getLatitude());
        viewModel.setLongitude(viewModel.getLongitude());
        viewModel.setFoto(viewModel.getFoto());
        viewModel.setRating(viewModel.getRating());
        viewModel.setWeb(viewModel.getWeb());
        viewModel.setWifi(viewModel.getWifi());
    }
}
