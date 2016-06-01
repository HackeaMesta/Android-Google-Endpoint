package com.appspot.data_base_1298.database.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appspot.data_base_1298.database.R;
import com.appspot.data_base_1298.database.Tools.App;
import com.appspot.data_base_1298.database.Tools.Indicador;
import com.appspot.data_base_1298.database.Tools.Respuesta;
import com.appspot.data_base_1298.proxyEndpointConocidos.ProxyEndpointConocidos;
import com.appspot.data_base_1298.proxyEndpointConocidos.model.Conocido;

import java.io.IOException;

import static com.appspot.data_base_1298.database.Tools.App.getApp;
import static com.appspot.data_base_1298.database.Tools.Constantes.ACTIVITY_MAP;
import static com.appspot.data_base_1298.database.Tools.Constantes.BUSCANDO_MODEL;
import static com.appspot.data_base_1298.database.Tools.Constantes.ELIMINANDO;
import static com.appspot.data_base_1298.database.Tools.Constantes.FALTA_EL_EXTRA_ID;
import static com.appspot.data_base_1298.database.Tools.Constantes.MODIFICANDO;
import static com.appspot.data_base_1298.database.Tools.Utileria.busca;
import static com.appspot.data_base_1298.database.Tools.Utileria.muestraTexto;
import static com.appspot.data_base_1298.database.Tools.Utileria.procesaError;
import static com.appspot.data_base_1298.database.Tools.Utileria.recuperaTexto;
import static com.appspot.data_base_1298.database.Tools.Utileria.setImageResource;
import static com.appspot.data_base_1298.database.Tools.Utileria.setIndicadorActivo;
import static com.appspot.data_base_1298.database.Tools.Utileria.setLink;
import static com.appspot.data_base_1298.database.Tools.Utileria.setRating;
import static com.appspot.data_base_1298.database.Tools.Utileria.setSwitch;
import static com.appspot.data_base_1298.database.Tools.Utileria.setTextOnWidget;
import static com.appspot.data_base_1298.database.Tools.Utileria.texto;

/**
 * Created by rk521 on 1/05/16.
 */
public class FragmentConocido extends Fragment
        implements LoaderManager.LoaderCallbacks<Respuesta<Conocido>> {
    private static final String TAG = FragmentConocido.class.getName();

    Context ctx;
    String map_title;
    String map_foto;
    Double map_latitude;
    Double map_longitude;
    Button btn;
    Float map_rating;

    private boolean terminado;
    @Nullable
    private String idViewModel;
    @Nullable
    private String mensaje;
    @Nullable
    private Indicador indicador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.ctx = container.getContext();
        return inflater.inflate(R.layout.view_conocido, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        indicador = (Indicador) busca(view, R.id.indicador);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        btn = (Button) getActivity().findViewById(R.id.btn_map);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showMap();
            }
        });

        if (terminado) {
            termina();
        } else {
            muestraMensajeError(mensaje);
            if (idViewModel == null) {
                muestraMensajeError(FALTA_EL_EXTRA_ID);
            } else {
                getLoaderManager().initLoader(0, null, this);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detalle, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_eliminar:
                clicEnEliminar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Respuesta<Conocido>> onCreateLoader(int idLoader, Bundle args) {
        setIndicadorActivo(indicador, true);
        final App app = getApp(getActivity());
        return idViewModel == null ? null :
                new AsyncTaskLoader<Respuesta<Conocido>>(app) {
                    @Override
                    protected void onStartLoading() {
                        forceLoad();
                    }

                    @Override
                    public Respuesta<Conocido> loadInBackground() {
                        try {
                            final ProxyEndpointConocidos.Get get =
                                    app.getProxyEndpointConocidos().get(idViewModel);
                            final Conocido viewModel = get.execute();
                            return new Respuesta<>(viewModel, null);
                        } catch (IOException e) {
                            return new Respuesta<>(null, e);
                        }
                    }
                };
    }

    @Override
    public void onLoadFinished(Loader<Respuesta<Conocido>> loader,
                               Respuesta<Conocido> respuesta) {
        setIndicadorActivo(indicador, false);
        final Exception exception = respuesta.getException();
        if (exception != null) {
            muestraError(BUSCANDO_MODEL, exception);
        } else {
            final Conocido viewModel = respuesta.getResultado();
            muestraViewModel(viewModel);
        }
    }

    @Override
    public void onLoaderReset(Loader<Respuesta<Conocido>> loader) {
    }

    public final void setIdViewModel(@Nullable String idViewModel) {
        this.idViewModel = idViewModel;
    }

    private void clicEnGuardar() {
        final Conocido viewModel = creaViewModel();
        setIndicadorActivo(indicador, true);
        final App app = getApp(getActivity());
        final AsyncTask<Conocido, Void, Exception> task =
                new AsyncTask<Conocido, Void, Exception>() {
                    @Override
                    protected Exception doInBackground(Conocido... params) {
                        try {
                            final ProxyEndpointConocidos.Update update =
                                    app.getProxyEndpointConocidos().update(params[0]);
                            update.execute();
                            return null;
                        } catch (IOException e) {
                            return e;
                        }
                    }

                    @Override
                    protected void onPostExecute(Exception e) {
                        operaciónRealizada(MODIFICANDO, e);
                    }
                };
        task.execute(viewModel);
    }

    private void muestraViewModel(Conocido model) {
        this.map_title = model.getNombre();
        this.map_latitude = Double.parseDouble(model.getLatitude());
        this.map_longitude = Double.parseDouble(model.getLongitude());
        this.map_foto = model.getFoto();
        this.map_rating = model.getRating();

        setTextOnWidget(getView(), R.id.nombre, model.getNombre());
        setTextOnWidget(getView(), R.id.telefono, model.getTelefono());

        String web = model.getWeb();
        if (!web.substring(0, 6).equals("http://") || !web.substring(0, 7).equals("https://")) {
            web = "http://" + web;
        }

        setLink(getView(), R.id.web, web);
        setTextOnWidget(getView(), R.id.descripcion, model.getDescripcion());
        setSwitch(getView(), R.id.wifi, model.getWifi());
        setImageResource(getView(), R.id.person_photo, model.getFoto(), this.ctx);
        setRating(getView(), R.id.ratingBar, model.getRating());
    }

    private void showMap() {
        final Intent intent = new Intent(getActivity(), ACTIVITY_MAP);
        intent.putExtra("foto", this.map_foto);
        intent.putExtra("latitude", this.map_latitude);
        intent.putExtra("longitude", this.map_longitude);
        intent.putExtra("name", this.map_title);
        intent.putExtra("rating", this.map_rating);
        startActivity(intent);
    }


    //creaViewModel se ejecuta al dart click en Guardar
    private Conocido creaViewModel() {
        muestraMensajeError("");
        final Conocido viewModel = new Conocido();
        viewModel.setId(idViewModel);
        //viewModel.setId(recuperaTexto(getView(), R.id.id_element));
        viewModel.setNombre(recuperaTexto(getView(), R.id.nombre));
        viewModel.setTelefono(recuperaTexto(getView(), R.id.telefono));
        return viewModel;
    }

    private void clicEnEliminar() {
        setIndicadorActivo(indicador, true);
        final App app = getApp(getActivity());
        AsyncTask<String, Void, Exception> taskRemove =
                new AsyncTask<String, Void, Exception>() {
                    @Override
                    protected Exception doInBackground(String... params) {
                        try {
                            final ProxyEndpointConocidos.Remove remove =
                                    app.getProxyEndpointConocidos().remove(params[0]);
                            remove.execute();
                            return null;
                        } catch (IOException e) {
                            return e;
                        }
                    }

                    @Override
                    protected void onPostExecute(Exception e) {
                        operaciónRealizada(ELIMINANDO, e);
                    }
                };
        taskRemove.execute(idViewModel);
    }

    private void operaciónRealizada(String contexto, Exception e) {
        if (e == null) {
            termina();
        } else {
            muestraError(contexto, e);
        }
    }

    private void termina() {
        this.terminado = true;
        if (isAdded()) {
            getActivity().finish();
        }
    }

    private void muestraError(String contexto, Exception exception) {
        final String mensaje = procesaError(TAG, contexto, exception);
        muestraMensajeError(mensaje);
    }

    private void muestraMensajeError(String mensaje) {
        this.mensaje = mensaje;
        if (isAdded()) {
            muestraTexto(getView(), R.id.error, texto(mensaje));
        }
    }
}