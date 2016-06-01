package com.appspot.data_base_1298.database.Tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
/**
 * Created by rk521 on 1/05/16.
 */
public class Indicador extends ProgressBar {
    /**
     * Nombre con el que se guarda el estado del componente.
     */
    protected static final String ESTADO = "ESTADO";
    /**
     * Nombre con el que se guarda si el componente está activo o no.
     */
    protected static final String ACTIVO = "ACTIVO";
    private boolean activo;

    {
        setIndeterminate(true);
        actualizaView();
    }

    public Indicador(Context context) {
        super(context);
    }

    public Indicador(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Indicador(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Indicador(Context context, AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public final boolean isActivo() {
        return activo;
    }

    public final void setActivo(boolean activo) {
        this.activo = activo;
        actualizaView();
    }

    /**
     * Devuelve un Parcelable con la información necesaria para restaurar el
     * estado del componente.
     *
     * @return un Parcelable con la información necesaria para restaurar el
     * estado
     * del componente.
     */
    @Override
    public Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ESTADO, super.onSaveInstanceState());
        bundle.putBoolean(ACTIVO, activo);
        return bundle;
    }

    /**
     * Restaura el estado del botón.
     *
     * @param state información necesaria para restaurar el estado del botón.
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            activo = bundle.getBoolean(ACTIVO);
            state = bundle.getParcelable(ESTADO);
        }
        super.onRestoreInstanceState(state);
        actualizaView();
    }

    protected void actualizaView() {
        setVisibility(isActivo() ? View.VISIBLE : View.INVISIBLE);
    }
}