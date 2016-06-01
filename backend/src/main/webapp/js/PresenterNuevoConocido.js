"use strict";
requirejs(["constantes", "Indicador", "utileria", "load_client!"], function (
    constantes, Indicador, util) {
  var /** @const{string} */ NOMBRE = constantes["NOMBRE"];
  var /** @const{string} */ TELEFONO = constantes["TELEFONO"];
  var client = window["gapi"]["client"];
  var busca = util["busca"];
  var recuperaValue = util["recuperaValue"];
  /** @constructor */
  function PresenterNuevoConocido() {
    /** @type {HTMLElement} */
    this.indicador = new Indicador(busca("indicador"));
    this.detalle = busca("detalle");
    /** @type {HTMLElement} */
    this.guardar = busca("guardar");
  }
  PresenterNuevoConocido.prototype = {
    configura: function () {
      this.guardar.disabled = true;
    },
    inicia: function (endpoint) {
      /** type {Endpoint} */
      this.endpoint = endpoint;
      this.detalle.addEventListener("submit", this.clicEnGuardar.bind(this),
          false);
      this.guardar.disabled = false;
      this.indicador.desactiva();
    },
    clicEnGuardar: function () {
      var viewModel = this.creaModel();
      this.indicador.activa();
      var insert = this.endpoint["insert"](viewModel);
      insert["execute"](this.operacionRealizada.bind(this));
    },
    creaModel: function () {
      this.muestraMensajeError("");
      var model = {};
      model[NOMBRE] = recuperaValue(NOMBRE);
      model[TELEFONO] = recuperaValue(TELEFONO);
      return model;
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
      util["muestraTexto"](constantes.ERROR, mensaje);
    },
    regresa: function () {
      location.href = constantes.URL_REGRESO;
    }
  };
  function apisDescargadas() {
    presenter.inicia(client[constantes.ENDPOINT]);
  }
  var presenter = new PresenterNuevoConocido();
  presenter.configura();
  client["load"](constantes.ENDPOINT, constantes.VERSION, apisDescargadas,
      constantes.API_ROOT);
});