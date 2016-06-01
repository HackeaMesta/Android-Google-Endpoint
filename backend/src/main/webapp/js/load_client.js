"use async";
define({
  load: function (url, require, onLoad, config) {
    if (config.isBuild) {
      onLoad(null);
    } else {
      var nombreDeFuncion = "__load_client__" + new Date().getTime();
      window[nombreDeFuncion] = onLoad;
      var s = document.createElement('script');
      s.type = 'text/javascript';
      s.async = true;
      s.src = "https://apis.google.com/js/client.js?onload=" + nombreDeFuncion;
      var t = document.getElementsByTagName('script')[0];
      t.parentNode.insertBefore(s, t);
    }
  }
});