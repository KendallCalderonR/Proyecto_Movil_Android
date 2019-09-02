package org.example.final_project_android_mobile;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.example.final_project_android_mobile.Model.producto;
import org.example.final_project_android_mobile.Adapter.productosAdapter;


public class MenuFragment extends Fragment {

    RecyclerView recycleUsuarios;
    private StorageReference mStorageRef;
   // private FragmentActivity myContext;


    private static ArrayList<producto> Productos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Productos = new ArrayList<>();
        recycleUsuarios = view.findViewById(R.id.recyclerMenu);
        recycleUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        CargarUsuarios();
        productosAdapter adapter = new productosAdapter(Productos);
        recycleUsuarios.setAdapter(adapter);
        return view;
    }

   // @Override
  //  public void onAttach(Activity activity) {
   //     myContext=(FragmentActivity) activity;
    //    super.onAttach(activity);
   // }

    public  void CargarUsuarios() {

            //SQLiteDatabase conn = Conexion.getLectura();
            AdminDB conn = new AdminDB(getActivity(),"databaseFood",null,1);
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from producto" ,null);


        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                int activado = fila.getInt(5);
                if(activado == 1){
                    String idProducto = fila.getString(0);
                    String nombreProducto = fila.getString(2);
                    String precioProducto  = fila.getString(3);
                    Productos.add(new producto(idProducto,R.drawable.pizza2,nombreProducto,precioProducto));
                }
            } while(fila.moveToNext());
        }
            conn.close();


        Productos.add(new producto("1",R.drawable.pizza,"Pizza Jamón","5500"));
        //Productos.add(new producto("2",R.drawable.pizza2,"Pizza Suprema","7500"));
        Productos.add(new producto("3",R.drawable.arepa,"Arepa Rellena","3500"));
        Productos.add(new producto("4",R.drawable.combo1,"Combo 1: Hog dog + Papas ","4000"));
        Productos.add(new producto("5",R.drawable.combo2,"Combo 2: Hamburguesa + Papas","4000"));
        Productos.add(new producto("6",R.drawable.pollo,"Pollo frito","2500"));
        Productos.add(new producto("7",R.drawable.hotdogs,"Hot Dogs","2000"));
        Productos.add(new producto("8",R.drawable.papas,"Papas Fritas","1500"));
    }








}
