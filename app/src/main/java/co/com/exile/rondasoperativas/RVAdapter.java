package co.com.exile.rondasoperativas;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EquipoViewHolder>{

    List<Equipo> equipos;

    RVAdapter(List<Equipo> equipos){
        this.equipos = equipos;
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView equipo;
        TextView equipoSubtitle;
        ImageView checkImage;


        EquipoViewHolder(View itemView) {
            super(itemView);
            equipo = (TextView)itemView.findViewById(R.id.equipo);
            equipoSubtitle = (TextView) itemView.findViewById(R.id.equipo_subtitle);
            checkImage = (ImageView)itemView.findViewById(R.id.check_image);
        }
    }

    @Override
    public int getItemCount() {
        Log.i("equipos count", "" + equipos.size());
        return equipos.size();
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.equipo, viewGroup, false);
        v.setVisibility(View.VISIBLE);
        return new EquipoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EquipoViewHolder equipoViewHolder, int i) {
        Equipo equipo = equipos.get(i);
        equipoViewHolder.equipo.setText(equipo.getNombre());
        String subtitle = equipo.getPlanta() + " - " + equipo.getUnidad();
        equipoViewHolder.equipoSubtitle.setText(subtitle);
        if(equipo.isChecked()){
            equipoViewHolder.checkImage.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}