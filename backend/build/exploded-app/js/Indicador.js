"use strict";
define(function () {
  /** Muestra u oculta un element.
   * @constructor
   * @param {HTMLElement} element elemento dom que se usa como indicador. */
  function Indicador(element) {
    this.element = element;
  }
  Indicador.prototype = {
    "activa": function () {
      this.element.style.visibility = "visible";
    },
    "desactiva": function () {
      this.element.style.visibility = "hidden";
    }
  };
  return Indicador;
});