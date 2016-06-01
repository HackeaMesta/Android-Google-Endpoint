"use strict";
requirejs(["constantes", "Indicador", "utileria", "load_client!"], function (
    constantes, Indicador, util) {
  var /** @const{string} */ NOMBRE = constantes["NOMBRE"];
  var /** @const{string} */ TELEFONO = constantes["TELEFONO"];
  var client = window["gapi"]["client"];
  var busca = util["busca"];
  var muestraValue = util["muestraValue"];
  var recuperaValue = util["recuperaValue"];
  /** @constructor */
  function PresenterConocido() {
    this.indicador = new Indicador(busca("indicador"));
    /** @type {HTMLElement} */
    this.detalle = busca("detalle");
    /** @type {HTMLElement} */
    this.guardar = busca("guardar");
    /** @type {HTMLElement} */
    this.eliminar = busca("eliminar");
  }
  PresenterConocido.prototype = {
    configura: function () {
      this.guardar.disabled = true;
      this.eliminar.disabled = true;
    },
    inicia: function (endpoint) {
      /** type {Endpoint} */
      this.endpoint = endpoint;
      this.idViewModel = this.getIdViewModel();
      if (this.idViewModel) {
        this.get();
      } else {
        this.muestraMensajeError(constantes["FALTA_EL_ID"]);
      }
    },
    getIdViewModel: function () {
      var search = location.search;
      if (search.indexOf(constantes["ID_SEARCH"]) < 0) {
        return null;
      } else {
        return search.substr(constantes["ID_SEARCH"].length);
      }
    },
    get: function () {
      var params = {"id": this.idViewModel};
      var get = this.endpoint["get"](params);
      get["execute"](this.recibeModel.bind(this));
    },
    recibeModel: function (respuesta) {
      this.indicador.desactiva();
      var error = respuesta["error"];
      if (error) {
        this.muestraError(error);
      } else {
        this.detalle.addEventListener("submit", this.clicEnGuardar.bind(this),
            false);
        this.eliminar.addEventListener("click", this.clicEnEliminar.bind(this),
            false);
        this.guardar.disabled = false;
        this.eliminar.disabled = false;
        var viewModel = respuesta["result"];
        this.muestraViewModel(viewModel);
      }
    },
    clicEnGuardar: function () {
      var viewModel = this.creaViewModel();
      this.indicador.activa();
      var update = this.endpoint["update"](viewModel);
      update["execute"](this.operacionRealizada.bind(this));
    },
    muestraViewModel: function (viewModel) {
      muestraValue(NOMBRE, viewModel[NOMBRE]);
      muestraValue(TELEFONO, viewModel[TELEFONO]);
    },
    creaViewModel: function () {
      this.muestraMensajeError("");
      return {"id": this.idViewModel, "nombre": recuperaValue(NOMBRE),
        "telefono": recuperaValue(TELEFONO)};
    },
    operacionRealizada: function (respuesta) {
      this.indicador.desactiva();
      var error = respuesta["error"];
      if (error) {
        this.muestraError(error);
      } else {
        this.regresa();
      }
    },
    muestraError: function (error) {
      var mensaje = util["procesaError"](error);
      this.muestraMensajeError(mensaje);
    },
    muestraMensajeError: function (mensaje) {
      util["muestraTexto"](constantes["ERROR"], mensaje);
    },
    regresa: function () {
      location.href = constantes["URL_REGRESO"];
    },
    clicEnEliminar: function () {
      var parametros = {"id": this.idViewModel};
      this.indicador.activa();
      var remove = this.endpoint["remove"](parametros);
      remove["execute"](this.operacionRealizada.bind(this));
    }
  };
  function apisDescargadas() {
    presenter.inicia(client[constantes["ENDPOINT"]]);
  }
  var presenter = new PresenterConocido();
  presenter.configura();
  client["load"](constantes["ENDPOINT"], constantes["VERSION"],
      apisDescargadas, constantes["API_ROOT"]);
});