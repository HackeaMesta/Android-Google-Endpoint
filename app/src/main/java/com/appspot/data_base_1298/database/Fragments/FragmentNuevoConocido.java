package com.appspot.data_base_1298.database.Fragments;

/**
 * Created by rk521 on 1/05/16.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appspot.data_base_1298.database.Activitys.ActivityNuevoConocido;
import com.appspot.data_base_1298.database.Activitys.ActivityNuevoMap;
import com.appspot.data_base_1298.database.R;
import com.appspot.data_base_1298.database.Tools.App;
import com.appspot.data_base_1298.database.Tools.Indicador;
import com.appspot.data_base_1298.proxyEndpointConocidos.ProxyEndpointConocidos;
import com.appspot.data_base_1298.proxyEndpointConocidos.model.Conocido;

import java.io.IOException;

import static com.appspot.data_base_1298.database.Tools.App.getApp;
import static com.appspot.data_base_1298.database.Tools.Constantes.ACTIVITY_NEW_MAP;
import static com.appspot.data_base_1298.database.Tools.Constantes.AGREGANDO;
import static com.appspot.data_base_1298.database.Tools.Constantes.MODIFICANDO;
import static com.appspot.data_base_1298.database.Tools.Utileria.busca;
import static com.appspot.data_base_1298.database.Tools.Utileria.getRating;
import static com.appspot.data_base_1298.database.Tools.Utileria.getWifiFromSwitch;
import static com.appspot.data_base_1298.database.Tools.Utileria.muestraTexto;
import static com.appspot.data_base_1298.database.Tools.Utileria.procesaError;
import static com.appspot.data_base_1298.database.Tools.Utileria.recuperaTexto;
import static com.appspot.data_base_1298.database.Tools.Utileria.setIndicadorActivo;
import static com.appspot.data_base_1298.database.Tools.Utileria.setRating;
import static com.appspot.data_base_1298.database.Tools.Utileria.setSwitch;
import static com.appspot.data_base_1298.database.Tools.Utileria.setTextOnEditText;
import static com.appspot.data_base_1298.database.Tools.Utileria.texto;

public class FragmentNuevoConocido extends Fragment {
    private static final String TAG = FragmentNuevoConocido.class.getName();
    private String mensaje;
    private boolean terminado;
    Button btn;

    @Nullable
    private Indicador indicador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_nuevo, container, false);
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

        btn = (Button) getActivity().findViewById(R.id.btn_new_map);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });

        if (ActivityNuevoConocido.action_editar == true) {
            loadData();
        }

        if (terminado) {
            termina();
        } else {
            muestraMensajeError(mensaje);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_update_existent);
        MenuItem itemSave = menu.findItem(R.id.action_guardar);

        if (ActivityNuevoConocido.action_editar) {
            itemSave.setVisible(false);
            itemSave.setEnabled(false);
        } else {
            item.setEnabled(false);
            item.setVisible(false);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_agregar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update_existent:
                clicEnActualizar();
                return true;

            case R.id.action_guardar:
                clicEnGuardar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clicEnActualizar() {
        final Conocido viewModel = modelUpdate();
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
                        opExec(MODIFICANDO, e);
                    }
                };
        task.execute(viewModel);
    }

    private void opExec(String contexto, Exception e) {
        if (e == null) {
            termina();
        } else {
            showError(contexto, e);
        }
    }

    private void showError(String contexto, Exception exception) {
        final String mensaje = procesaError(TAG, contexto, exception);
        muestraMsjError(mensaje);
    }

    private void muestraMsjError(String mensaje) {
        this.mensaje = mensaje;
        if (isAdded()) {
            muestraTexto(getView(), R.id.error, texto(mensaje));
        }
    }

    private void loadData() {
        setTextOnEditText(getView(), R.id.new_nombre, ActivityNuevoConocido.name);
        setTextOnEditText(getView(), R.id.new_descripcion, ActivityNuevoConocido.descripcion);
        setTextOnEditText(getView(), R.id.new_foto, ActivityNuevoConocido.foto);
        setTextOnEditText(getView(), R.id.new_telefono, ActivityNuevoConocido.telefono);
        setTextOnEditText(getView(), R.id.new_web, ActivityNuevoConocido.web);
        setRating(getView(), R.id.rating_new, ActivityNuevoConocido.rating);
        setSwitch(getView(), R.id.wifi_new, ActivityNuevoConocido.wifi);
        ActivityNuevoMap.latitude = ActivityNuevoConocido.latitude;
        ActivityNuevoMap.longitude = ActivityNuevoConocido.longitude;
    }

    private void showMap() {
        final Intent intent = new Intent(getActivity(), ACTIVITY_NEW_MAP);
        startActivity(intent);
    }

    private Conocido modelUpdate() {
        muestraMensajeError("");
        final Conocido viewModel = new Conocido();
        viewModel.setId(ActivityNuevoConocido.id);
        viewModel.setNombre(recuperaTexto(getView(), R.id.new_nombre));
        viewModel.setTelefono(recuperaTexto(getView(), R.id.new_telefono));
        viewModel.setDescripcion(recuperaTexto(getView(), R.id.new_descripcion));
        viewModel.setFoto(recuperaTexto(getView(), R.id.new_foto));
        viewModel.setRating(getRating(getView(), R.id.rating_new));
        viewModel.setWeb(recuperaTexto(getView(), R.id.new_web));
        viewModel.setWifi(getWifiFromSwitch(getView(), R.id.wifi_new));

        String lat = ActivityNuevoMap.latitude.toString();
        if (lat.toString().isEmpty()) {
            lat = "19.4289712";
        }
        viewModel.setLatitude(lat);

        String lon = ActivityNuevoMap.longitude.toString();
        if (lon.toString().isEmpty()) {
            lon = "-99.1372517";
        }
        viewModel.setLongitude(lon);

        return viewModel;
    }

    private Conocido creaViewModel() {
        muestraMensajeError("");
        final Conocido viewModel = new Conocido();
        viewModel.setNombre(recuperaTexto(getView(), R.id.new_nombre));
        viewModel.setTelefono(recuperaTexto(getView(), R.id.new_telefono));
        viewModel.setDescripcion(recuperaTexto(getView(), R.id.new_descripcion));
        viewModel.setFoto(recuperaTexto(getView(), R.id.new_foto));
        viewModel.setRating(getRating(getView(), R.id.rating_new));
        viewModel.setWeb(recuperaTexto(getView(), R.id.new_web));
        viewModel.setWifi(getWifiFromSwitch(getView(), R.id.wifi_new));

        String lat = ActivityNuevoMap.latitude.toString();
        if (lat.toString().isEmpty()) {
            lat = "19.4289712";
        }
        viewModel.setLatitude(lat);

        String lon = ActivityNuevoMap.longitude.toString();
        if (lon.toString().isEmpty()) {
            lon = "-99.1372517";
        }
        viewModel.setLongitude(lon);

        return viewModel;
    }

    private void clicEnGuardar() {
        final Conocido viewModel = creaViewModel();
        setIndicadorActivo(indicador, true);
        final App app = getApp(getActivity());
        final AsyncTask<Conocido, Void, Exception> task =
                new AsyncTask<Conocido, Void, Exception>() {
                    @Override protected Exception doInBackground(Conocido... params) {
                        try {
                            final ProxyEndpointConocidos.Insert insert = app.getProxyEndpointConocidos().insert(params[0]);
                            insert.execute();
                            return null;
                        } catch (IOException e) {
                            return e;
                        }
                    }
                    @Override protected void onPostExecute(Exception e) {
                        operaciónRealizada(e);
                    }
                };
        task.execute(viewModel);
    }

    private void operaciónRealizada(Exception e) {
        if (e == null) {
            termina();
        } else {
            muestraError(e);
        }
    }
    private void termina() {
        this.terminado = true;
        if (isAdded()) {
            getActivity().finish();
        }
    }
    private void muestraError(Exception exception) {
        final String mensaje = procesaError(TAG, AGREGANDO, exception);
        muestraMensajeError(mensaje);
    }
    private void muestraMensajeError(String mensaje) {
        this.mensaje = mensaje;
        if (isAdded()) {
            muestraTexto(getView(), R.id.error, texto(mensaje));
        }
    }
}
