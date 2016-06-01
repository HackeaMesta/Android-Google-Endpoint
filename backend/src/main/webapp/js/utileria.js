"use strict";
define(function () {
  function busca(id) {
    var element = document.getElementById(id);
    if (element) {
      return element;
    } else {
      throw new Error("El elemento con id " + id
          + " no se encuentra en la página HTML.");
    }
  }
  function buscaClass(element, className) {
    var hijos = element.getElementsByClassName(className);
    if (hijos.length > 0) {
      return hijos[0];
    } else {
      throw new Error("El elemento con class " + className
          + " no se encuentra en la página HTML.");
    }
  }
  function texto(s) {
    return s ? s : "";
  }
  function recuperaValue(idInputText) {
    var inputText = busca(idInputText);
    return inputText.value;
  }
  function muestraValue(idInputText, s) {
    var inputText = busca(idInputText);
    inputText.value = texto(s);
  }
  function muestraTexto(idElement, s) {
    var element = busca(idElement);
    element.textContent = texto(s);
  }
  function procesaError(error) {
    console.error(error);
    return error["message"];
  }
  return {
    "busca": busca,
    "buscaClass": buscaClass,
    "muestraTexto": muestraTexto,
    "muestraValue": muestraValue,
    "procesaError": procesaError,
    "recuperaValue": recuperaValue,
    "texto": texto
  };
});