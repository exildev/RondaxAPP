package co.com.exile.rondasoperativas;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class FormAdapter extends RecyclerView.Adapter<FormAdapter.CampoViewHolder>{

    List<String> campos;

    FormAdapter(List<String> equipos){
        this.campos = equipos;
    }

    public static class CampoViewHolder extends RecyclerView.ViewHolder {
        TextInputLayout campo;


        CampoViewHolder(View itemView) {
            super(itemView);
            campo = (TextInputLayout)itemView.findViewById(R.id.field_container);
        }
    }

    @Override
    public int getItemCount() {
        Log.i("campos count", "" + campos.size());
        return campos.size();
    }

    @Override
    public CampoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.campo, viewGroup, false);
        return new CampoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CampoViewHolder equipoViewHolder, int i) {
        String campo = campos.get(i);
        equipoViewHolder.campo.setHint(campo);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}