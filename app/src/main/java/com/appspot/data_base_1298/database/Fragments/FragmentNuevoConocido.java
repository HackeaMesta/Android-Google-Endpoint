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

import com.appspot.data_base_1298.database.Activitys.ActivityNuevoMap;
import com.appspot.data_base_1298.database.R;
import com.appspot.data_base_1298.database.Tools.App;
import com.appspot.data_base_1298.database.Tools.Indicador;
import com.appspot.data_base_1298.proxyEndpointConocidos.ProxyEndpointConocidos;
import com.appspot.data_base_1298.proxyEndpointConocidos.model.Conocido;

import java.io.IOException;

import static com.appspot.data_base_1298.database.Tools.App.getApp;
import static com.appspot.data_base_1298.database.Tools.Constantes.AGREGANDO;
import static com.appspot.data_base_1298.database.Tools.Constantes.ACTIVITY_NEW_MAP;
import static com.appspot.data_base_1298.database.Tools.Utileria.busca;
import static com.appspot.data_base_1298.database.Tools.Utileria.getRating;
import static com.appspot.data_base_1298.database.Tools.Utileria.getTextFromTextInputLayout;
import static com.appspot.data_base_1298.database.Tools.Utileria.getWifiFromSwitch;
import static com.appspot.data_base_1298.database.Tools.Utileria.muestraTexto;
import static com.appspot.data_base_1298.database.Tools.Utileria.procesaError;
import static com.appspot.data_base_1298.database.Tools.Utileria.setIndicadorActivo;
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
        if (terminado) {
            termina();
        } else {
            muestraMensajeError(mensaje);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_agregar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                clicEnGuardar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMap() {
        final Intent intent = new Intent(getActivity(), ACTIVITY_NEW_MAP);
        startActivity(intent);
    }

    private Conocido creaViewModel() {
        muestraMensajeError("");
        final Conocido viewModel = new Conocido();
        viewModel.setNombre(getTextFromTextInputLayout(getView(), R.id.til_lugar));
        viewModel.setTelefono(getTextFromTextInputLayout(getView(), R.id.til_telefono));
        viewModel.setDescripcion(getTextFromTextInputLayout(getView(), R.id.til_descripcion));
        viewModel.setFoto(getTextFromTextInputLayout(getView(), R.id.til_foto));
        viewModel.setRating(getRating(getView(), R.id.rating_new));
        viewModel.setWeb(getTextFromTextInputLayout(getView(), R.id.til_web));
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
