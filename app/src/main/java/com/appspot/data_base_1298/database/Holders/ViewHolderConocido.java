package com.appspot.data_base_1298.database.Holders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appspot.data_base_1298.proxyEndpointConocidos.model.Conocido;

import static com.appspot.data_base_1298.database.Tools.Constantes.ACTIVITY_EDITAR;
import static com.appspot.data_base_1298.database.Tools.Constantes.EXTRA_ID;

/**
 * Created by rk521 on 1/05/16.
 */
public class ViewHolderConocido extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static ViewHolderConocido creaViewHolder(ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolderConocido(view);
    }

    @NonNull
    private final TextView text1;
    @Nullable
    private Conocido model;

    public ViewHolderConocido(@NonNull View view) {
        super(view);
        text1 = (TextView) itemView.findViewById(android.R.id.text1);
        text1.setOnClickListener(this);
    }

    public void setModel(@Nullable Conocido model) {
        this.model = model;
        text1.setText(model == null ? "" : model.getNombre());
    }

    @Override
    public void onClick(View v) {
        System.out.println(model);
        if (model != null) {
            muestraDetalle(model.getId());
        }
    }

    private void muestraDetalle(String id) {
        final Context context = text1.getContext();
        final Intent intent = new Intent(context, ACTIVITY_EDITAR);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }
}