package org.example.final_project_android_mobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MantenimientoProductosFragment extends Fragment {

    private StorageReference mStorageRef;
    public EditText etIdProductoModificar;
    public ImageView ivImagenProductoModificar;
    public EditText etNombreProductoModificar;
    public EditText etPrecioProductoModificar;
    public EditText etDescripcionProductoModificar;
    public Button btBuscarProducto;
    public Button btSeleccionarImagenModificar;
    public Button btGuardarProducto;
    public Button btDesactivarProducto;
    private String nombreImagen;
    private Uri filePath;
    private static final int SELECT_FILE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mantenimiento_productos, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        etIdProductoModificar = view.findViewById(R.id.etIdProductoModificar);
        ivImagenProductoModificar = view.findViewById(R.id.ivImagenProdModificar);
        etNombreProductoModificar = view.findViewById(R.id.etNombreProdModificar);
        etPrecioProductoModificar = view.findViewById(R.id.etPrecioProductoModificar);
        etDescripcionProductoModificar = view.findViewById(R.id.etDescripcionProductoModificar);
        btBuscarProducto = view.findViewById(R.id.btBuscar);
        btSeleccionarImagenModificar = view.findViewById(R.id.btCargarImagenModificar);
        btGuardarProducto = view.findViewById(R.id.btGuardar);
        btDesactivarProducto = view.findViewById(R.id.btDesactivar);

        btBuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarProductoID();
            }
        });

        btSeleccionarImagenModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria(view);
            }
        });

        btGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar();
            }
        });

        btDesactivarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desactivar();
            }
        });

        return view;
    }

    public void buscarProductoID(){

        SQLiteDatabase conn = Conexion.getLectura();
        int id = Integer.parseInt(etIdProductoModificar.getText().toString());

        Cursor fila = conn.rawQuery("select * from producto where id ="+id,null);


        if (fila.moveToFirst()){
            String ImagenName = fila.getString(1);
            descargarImagen(ImagenName);
            etNombreProductoModificar.setText(fila.getString(2));
            etPrecioProductoModificar.setText(fila.getString(3));
            etDescripcionProductoModificar.setText(fila.getString(4));

        }else {
          //  ivImagenProductoModificar.setImageIcon();
            etNombreProductoModificar.setText("");
            etPrecioProductoModificar.setText("");
            etDescripcionProductoModificar.setText("");
            Toast.makeText(getContext(),"No se encuentra",Toast.LENGTH_LONG).show();
        }
        conn.close();

    }


    public void modificar(){
        SQLiteDatabase conn = Conexion.getEscritura();
        int idProducto = Integer.parseInt(etIdProductoModificar.getText().toString());
        String nombreImagen = String.valueOf(ivImagenProductoModificar.getTag());
        String nombreProducto = etNombreProductoModificar.getText().toString();
        double precioProducto  = Double.parseDouble(etPrecioProductoModificar.getText().toString());
        String descripcionProducto = etNombreProductoModificar.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("id",idProducto);
        registro.put("imagen",nombreImagen);
        registro.put("nombre",nombreProducto);
        registro.put("precio",precioProducto);
        registro.put("descripcion",descripcionProducto);
        registro.put("estado",true);
        SubirImagenFirebase();

        long n = conn.update("producto",registro,"id="+idProducto,null);
        if (n==-1){
            Toast.makeText(getContext(),"No se modifico",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"Se modifico",Toast.LENGTH_SHORT).show();
        }
        conn.close();

    }

    public void desactivar(){
        SQLiteDatabase conn = Conexion.getEscritura();
        int idProducto = Integer.parseInt(etIdProductoModificar.getText().toString());

        ContentValues registro = new ContentValues();
        registro.put("estado",false);

        long n = conn.update("producto",registro,"id="+idProducto,null);
        if (n==-1){
            Toast.makeText(getContext(),"Error al desactivar",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"Producto desactivado",Toast.LENGTH_SHORT).show();
        }
        conn.close();
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getActivity().getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            ivImagenProductoModificar.setImageBitmap(bmp);
                            nombreImagen = String.valueOf(ivImagenProductoModificar.getTag());
                            filePath = selectedImage.getPath();

                        }
                    }
                }
                break;
        }
    }

    public void SubirImagenFirebase(){
        //Uri file = Uri.fromFile(new File(filePath));

        StorageReference riversRef = mStorageRef.child("ImagenesProductos/"+nombreImagen);

        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getActivity(),"La imagen se subio exitosamente",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

    public void descargarImagen(String nombreImagen){
        File localFile = null;
        try{
            localFile = File.createTempFile("images", "jpg");
        }catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference riversRef = mStorageRef.child("ImagenesProductos/"+nombreImagen+".jpg");

        final File finalLocalFile = localFile;
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        ivImagenProductoModificar.setImageBitmap(BitmapFactory.decodeFile(finalLocalFile.getPath()));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }


}
