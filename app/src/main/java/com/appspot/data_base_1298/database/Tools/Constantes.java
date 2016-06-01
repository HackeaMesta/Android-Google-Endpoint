package com.appspot.data_base_1298.database.Tools;

import com.appspot.data_base_1298.database.Activitys.ActivityConocido;
import com.appspot.data_base_1298.database.Activitys.ActivityMap;
import com.appspot.data_base_1298.database.Activitys.ActivityNuevoConocido;
import com.appspot.data_base_1298.database.Activitys.ActivityNuevoMap;

/**
 * Created by rk521 on 1/05/16.
 */
public class Constantes {
    public static final boolean SERVIDOR_LOCAL = false;
    public static final String URL_LOCAL = "http://127.0.0.1:8080";
    public static final String URL_REMOTA = "https://data-base-1298.appspot.com";
    public static final String EXTRA_ID = "com.appspot.data_base_1298.database.ID";
    public static final Class<ActivityNuevoConocido> ACTIVITY_AGREGAR = ActivityNuevoConocido.class;
    public static final Class<ActivityMap> ACTIVITY_MAP = ActivityMap.class;
    public static final Class<ActivityNuevoMap> ACTIVITY_NEW_MAP = ActivityNuevoMap.class;
    public static final Class<ActivityConocido> ACTIVITY_EDITAR = ActivityConocido.class;
    public static final String FALTA_EL_EXTRA_ID = "Falta el extra ID";
    public static final String VIEW_NO_ENCONTRADA = "View no encontrada en el archivo xml.";
    public static final String VIEW_NULA = "View nula.";
    public static final String BUSCANDO_MODEL = "Buscando model.";
    public static final String LISTANDO = "Listando.";
    public static final String AGREGANDO = "Agregando.";
    public static final String MODIFICANDO = "Modificando.";
    public static final String ELIMINANDO = "Eliminando.";
    private Constantes() {
    }
}
