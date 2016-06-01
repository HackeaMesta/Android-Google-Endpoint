"use strict";
requirejs(["constantes", "Indicador", "utileria", "load_client!"], function (
    constantes, Indicador, util) {
  var client = window["gapi"]["client"];
  var busca = util["busca"];
  var buscaClass = util["buscaClass"];
  /** @constructor */
  function PresenterConocidos() {
    this.indicador = new Indicador(busca("indicador"));
    /** @type {HTMLElement} */
    this.listado = busca("listado");
    /** @type {HTMLElement} */
    this.renglon = buscaClass(this.listado, "renglon");
  }
  PresenterConocidos.prototype = {
    borraListado: function () {
      var listado = this.listado;
      while (listado.firstChild) {
        listado.removeChild(listado.firstChild);
      }
    },
    inicia: function (endpoint) {
      this.endpoint = endpoint;
      this.list();
    },
    list: function () {
      this.indicador.activa();
      var list = this.endpoint["list"]();
      list["execute"](this.recibeItems.bind(this));
    },
    recibeItems: function (respuesta) {
      this.indicador.desactiva();
      var error = respuesta["error"];
      if (error) {
        this.muestraError(error);
      } else {
        var items = respuesta["result"]["items"] || [];
        this.setLista(items);
      }
    },
    setLista: function (lista) {
      this.borraListado();
      for (var i = 0, longitud = lista.length; i < longitud; i++) {
        var model = lista[i];
        var tr = this.renglon.cloneNode(true);
        var nombre = buscaClass(tr, "nombre");
        nombre.href = constantes["ID_SEACH_URL"] + model["id"];
        nombre.textContent = model["nombre"];
        this.listado.appendChild(tr);
      }
    },
    muestraError: function (error) {
      var mensaje = util["procesaError"](error);
      this.muestraMensajeError(mensaje);
    },
    muestraMensajeError: function (mensaje) {
      util["muestraTexto"](constantes.ERROR, mensaje);
    }
  };
  function apisDescargadas() {
    presenterListado.inicia(client[constantes.ENDPOINT]);
  }
  var presenterListado = new PresenterConocidos();
  presenterListado.borraListado();
  client["load"](constantes["ENDPOINT"], constantes["VERSION"],
      apisDescargadas, constantes["API_ROOT"]);
});