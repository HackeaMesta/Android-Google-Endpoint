package com.appspot.data_base_1298.database.Tools;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.squareup.picasso.Picasso;

import static com.appspot.data_base_1298.database.Tools.Constantes.VIEW_NO_ENCONTRADA;
import static com.appspot.data_base_1298.database.Tools.Constantes.VIEW_NULA;
/**
 * Created by rk521 on 1/05/16.
 */
public final class Utileria {
    @NonNull
    public static View busca(@NonNull View view, @IdRes int idView) {
        final View v = view.findViewById(idView);
        if (v == null) {
            throw new NullPointerException(VIEW_NO_ENCONTRADA);
        } else {
            return v;
        }
    }

    @NonNull
    public static String texto(String s) {
        return s == null ? "" : s;
    }

    @NonNull
    public static String getTextFromTextInputLayout(@Nullable View view, @IdRes int idEditText) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final TextInputLayout editText = (TextInputLayout) busca(view, idEditText);
            String str = editText.getEditText().getText().toString();
            if (str.isEmpty()) {
                editText.setError("Ingresa un valor");
                return "";
            } else {
                return str;
            }
        }
    }

    @NonNull
    public static Float getRating(@Nullable View view, @IdRes int idEditText) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final RatingBar editText = (RatingBar) busca(view, idEditText);
            try {
                return editText.getRating();
            } catch (NumberFormatException ex) {
                throw ex;
            }
        }
    }

    @NonNull
    public static boolean getWifiFromSwitch(@Nullable View view, @IdRes int idEditText) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final Switch editText = (Switch) busca(view, idEditText);
            if (editText.isChecked()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @NonNull
    public static String recuperaTexto(@Nullable View view, @IdRes int idEditText) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final EditText editText = (EditText) busca(view, idEditText);
            return editText.getText().toString();
        }
    }

    public static void setTextOnEditText(@Nullable View view, @IdRes int idTextView, @NonNull String s) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final TextView lblSalida = (TextView) busca(view, idTextView);
            lblSalida.setText(texto(s));
        }
    }

    public static void muestraTexto(@Nullable View view, @IdRes int idTextView, @NonNull String s) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final TextView lblSalida = (TextView) busca(view, idTextView);
            lblSalida.setText(texto(s));
        }
    }

    public static void setTextOnWidget(@Nullable View view, @IdRes int idTextView, @NonNull String s) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final TextView lblSalida = (TextView) busca(view, idTextView);
            lblSalida.setText(texto(s));
        }
    }

    public static void setLink(@Nullable View view, @IdRes int idTextView, @NonNull String s) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final TextView lblSalida = (TextView) busca(view, idTextView);
            if (s.length() > 15) {
                if (s.substring(0, 7).equals("http://")) {
                    lblSalida.setText(Html.fromHtml("<a href='"+texto(s)+"'>"+texto(s.substring(7, s.length()))+"</a>"));
                } else {
                    lblSalida.setText(Html.fromHtml("<a href='"+texto(s)+"'>"+texto(s.substring(8, s.length()))+"</a>"));
                }
            } else {
                lblSalida.setText(Html.fromHtml("<a href='" + texto(s) + "'>" + texto(s) + "</a>"));
            }
            lblSalida.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static void setImageResource(@Nullable View view, @IdRes int idTextView, @NonNull String s, Context ctx) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            ImageView img = (ImageView) busca(view, idTextView);
            if (!s.isEmpty()) {
                Picasso.with(ctx).load(s).into(img);
            }
        }
    }

    public static void setRating(@Nullable View view, @IdRes int idTextView, @NonNull Float rating_value) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final RatingBar rating = (RatingBar) busca(view, idTextView);
            rating.setRating(rating_value);
        }
    }

    public static void setSwitch(@Nullable View view, @IdRes int idTextView, @NonNull Boolean status) {
        if (view == null) {
            throw new NullPointerException(VIEW_NULA);
        } else {
            final Switch rating = (Switch) busca(view, idTextView);
            rating.setClickable(false);
            rating.setChecked(status);
        }
    }

    public static void setIndicadorActivo(@Nullable Indicador indicador,
                                          boolean activo) {
        if (indicador != null) {
            indicador.setActivo(activo);
        }
    }

    @NonNull
    public static String procesaError(@NonNull String tag, @NonNull String contexto, @NonNull Exception exception) {
        try {
            throw exception;
        } catch (GoogleJsonResponseException e) {
            return procesaError(tag, contexto, e);
        } catch (Exception e) {
            Log.e(tag, contexto, e);
            return getMessage(e);
        }
    }

    @NonNull
    public static String procesaError(@NonNull String tag,
                                      @NonNull String contexto, @NonNull GoogleJsonResponseException e) {
        final String mensaje = getMessage(e);
        Log.e(tag, contexto, e);
        return mensaje;
    }

    /**
     * Obtiene el mensaje de error para una excepción del tipo
     * GoogleJsonResponseException.
     *
     * @param e excepción que se procesa.
     * @return mensaje asociado con la excepción.
     */
    @NonNull
    public static String getMessage(
            @NonNull GoogleJsonResponseException e) {
        final GoogleJsonError details = e.getDetails();
        return details == null || details.isEmpty() ? getMessage((Exception) e) :
                details.getMessage();
    }

    /**
     * Obtiene el mensaje de error para una excepción.
     *
     * @param e excepción que se procesa.
     * @return mensaje asociado con la excepción.
     */
    @NonNull
    public static String getMessage(@NonNull Exception e) {
        final String localizedMessage = e.getLocalizedMessage();
        return localizedMessage == null || localizedMessage.isEmpty() ?
                e.toString() : localizedMessage;
    }

    @Nullable
    public static String catchMensaje(@NonNull String nombreException,
                                      String texto) {
        final String busqueda = nombreException + ": ";
        if (texto.contains(busqueda)) {
            return texto.substring(busqueda.length());
        } else {
            return null;
        }
    }

    private Utileria() {
    }
}