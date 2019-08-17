package org.example.final_project_android_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import org.example.final_project_android_mobile.Model.producto;
import org.example.final_project_android_mobile.Adapter.productosAdapter;


public class MenuFragment extends Fragment {

    RecyclerView recycleUsuarios;

    private static ArrayList<producto> Productos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Productos = new ArrayList<>();
        recycleUsuarios = view.findViewById(R.id.recyclerMenu);
        recycleUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        CargarUsuarios();
        productosAdapter adapter = new productosAdapter(Productos);
        recycleUsuarios.setAdapter(adapter);
        return view;
    }

    public static void CargarUsuarios() {
        Productos.add(new producto("1",R.drawable.pizza,"Pizza Jamón","5500"));
        Productos.add(new producto("2",R.drawable.pizza2,"Pizza Suprema","7500"));
        Productos.add(new producto("3",R.drawable.arepa,"Arepa Rellena","3500"));
        Productos.add(new producto("4",R.drawable.combo1,"Combo 1: Hog dog + Papas ","4000"));
        Productos.add(new producto("5",R.drawable.combo2,"Combo 2: Hamburguesa + Papas","4000"));
        Productos.add(new producto("6",R.drawable.pollo,"Pollo frito","2500"));
        Productos.add(new producto("7",R.drawable.hotdogs,"Hot Dogs","2000"));
        Productos.add(new producto("8",R.drawable.papas,"Papas Fritas","1500"));
    }


}
