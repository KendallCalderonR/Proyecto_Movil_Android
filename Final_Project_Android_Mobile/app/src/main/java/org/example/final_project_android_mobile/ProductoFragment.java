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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class ProductoFragment extends Fragment {



    private static final int SELECT_FILE = 0;
    public EditText etIdProducto;
    public ImageView ivImagenProducto;
    public EditText etNombreProducto;
    public EditText etPrecioProducto;
    public EditText etDescripcionProducto;
    public Button btCrearProducto;
    public Button btSeleccionarImagen;
    public Button btModificar;
    public Button btEliminar;
    public Button btConsultar;
    private String nombreImagen;
    private Uri filePath;
    private StorageReference mStorageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        etIdProducto = view.findViewById(R.id.etIdProducto);
        ivImagenProducto = view.findViewById(R.id.ivImagen);
        etNombreProducto = view.findViewById(R.id.etNombreProducto);
        etPrecioProducto = view.findViewById(R.id.etPrecioProducto);
        etDescripcionProducto = view.findViewById(R.id.etDescripcionProducto);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        btCrearProducto = view.findViewById(R.id.btCrearProducto);
        btSeleccionarImagen = view.findViewById(R.id.btSeleccionarImagen);

        btCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });

        btSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria(view);
            }
        });


        return view;
    }

    private void insertar() {
        //SQLiteDatabase conn = Conexion.getEscritura();
        AdminDB conn = new AdminDB(getActivity(),"databaseFood",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();

        int idProducto = Integer.parseInt(etIdProducto.getText().toString());
        String nombreImagen =  String.valueOf(ivImagenProducto.getTag());
        String nombreProducto = "test";//etNombreProducto.getText().toString();
        double precioProducto  = Double.parseDouble(etPrecioProducto.getText().toString());
        String descripcionProducto = etDescripcionProducto.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("id",idProducto);
        registro.put("imagen","ham");//nombreImagen);
        registro.put("nombre",nombreProducto);
        registro.put("precio",precioProducto);
        registro.put("descripcion",descripcionProducto);
        registro.put("estado",true);

        long n = db.insert("producto",
                "id",registro);
        //SubirImagenFirebase();
        if (n==-1) {
            Toast.makeText(getContext(),
                    "No se insertó",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(),
                    "Se insertó",
                    Toast.LENGTH_SHORT).show();
        }
       // conn.close();
    }


    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
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
                    filePath = selectedPath;
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
                            ivImagenProducto.setImageBitmap(bmp);
                            nombreImagen = String.valueOf(ivImagenProducto.getTag());
                            //filePath = selectedImage.getPath();

                        }
                    }
                }
                break;
        }
    }

    public void SubirImagenFirebase(){
        //Uri file = Uri.fromFile(new File(filePath.getPath()));

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




}
