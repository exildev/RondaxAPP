package co.com.exile.rondasoperativas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pico on 1/08/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EquipoViewHolder>{

    List<Equipo> equipos;

    RVAdapter(List<Equipo> equipos){
        this.equipos = equipos;
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lv;
        TextView equipo;
        TextView equipoSubtitle;
        ImageView checkImage;


        EquipoViewHolder(View itemView) {
            super(itemView);
            lv = (LinearLayout)itemView.findViewById(R.id.linear_layout);
            equipo = (TextView)itemView.findViewById(R.id.equipo);
            equipoSubtitle = (TextView)itemView.findViewById(R.id.equipo);
            checkImage = (ImageView)itemView.findViewById(R.id.check_image);
        }
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.equipo, viewGroup, false);
        EquipoViewHolder pvh = new EquipoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EquipoViewHolder equipoViewHolder, int i) {
        Equipo equipo = equipos.get(i);
        equipoViewHolder.equipo.setText(equipo.getNombre());
        equipoViewHolder.equipoSubtitle.setText(equipo.getPlanta() + " - " + equipo.getUnidad());
        if(equipo.isChecked()){
            equipoViewHolder.checkImage.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}