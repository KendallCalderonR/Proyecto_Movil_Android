package org.example.final_project_android_mobile.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.example.final_project_android_mobile.MantenimientoProductosFragment;
import org.example.final_project_android_mobile.R;
import org.example.final_project_android_mobile.Model.producto;

import java.util.ArrayList;

public class productosAdapter extends RecyclerView.Adapter<productosAdapter.ProductosViewHolder>{

    ArrayList<producto> datos;

    public productosAdapter(ArrayList<producto> productos){
        this.datos = productos;
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_restaurante,null,false);
        return new ProductosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder,int position){
        holder.ID.setText(datos.get(position).getID());
        holder.imagen.setImageResource(datos.get(position).getImagen());
        holder.descripcion.setText(datos.get(position).getDescripcion());
        holder.precio.setText(datos.get(position).getPrecio());
    }

    @Override
    public int getItemCount(){return this.datos.size();}

    public class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion,precio,ID;
        ImageView imagen;

        public ProductosViewHolder(final View itemView) {

            super(itemView);
            ID = itemView.findViewById(R.id.tvId);
            imagen =  itemView.findViewById(R.id.ivImagen);
            descripcion =  itemView.findViewById(R.id.tvDescripcion);
            precio = itemView.findViewById(R.id.tvPrecio);
            itemView.findViewById(R.id.cv_producto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String id = ID.getText().toString();
                    Bundle bundle = new Bundle();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment MantenimientoFragment = new MantenimientoProductosFragment();
                    bundle.putString("idProducto",id);
                    MantenimientoFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, MantenimientoFragment).addToBackStack(null).commit();
                }
            });
        }
    }

}
