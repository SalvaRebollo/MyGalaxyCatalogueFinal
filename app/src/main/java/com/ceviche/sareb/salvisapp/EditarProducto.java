package com.ceviche.sareb.salvisapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceviche.sareb.salvisapp.Clases.Productos;
import com.ceviche.sareb.salvisapp.Clases.Usuarios;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarProducto extends AppCompatActivity {

    private static final int GALLERY_INTENT = 1;
    private static final String IMAGE_DIRECTORY = "/myGalaxyCataPhotos";
    String idProductoAEditar;
    String nuevaFotoProducto = "SINFOTO";

    ///////////////////////
    DatabaseReference usuariosFirebase;
    DatabaseReference productosFirebase;
    boolean continuar = true;
    Button btnEditarProducto, btnEleg;
    TextView editTxtTitulo, editTxtDescripcion, editTxtPrecio;
    Spinner editSpinEstado, editSpinCategoria;
    ImageView editIvImagenProducto;
    String productoid, usuarioCreadorUid, titulo, descripcion, precio, categoria, estado, imagen, nombreUsuarioCreador, fotoUsuarioCreador;
    String email, nombre, apellidos, biografia, pais, ciudad, direccion, fotoDePerfil, uid, tlfContacto = "";
    ValueEventListener valueEventListenerUsuarios = new ValueEventListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Usuarios elemento = dataSnapshot.getValue(Usuarios.class);

            nombre = elemento.getNombre();
            fotoDePerfil = elemento.getFotoPerfil();


            Log.e("[[[[[[[[[[[[[[nombreUsuario]]]]]]]]]]]]]", nombre);
            Log.e("[[[[[[[[[[[[[[fotoperfil]]]]]]]]]]]]]", fotoDePerfil);
            Log.e("[[[     --->INFO DE USUARIO PARA ACTUALIZAR REALIZADA<---     ]]]", "realizada");


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // FIN EventListenerUsuarios
    ValueEventListener valueEventListener = new ValueEventListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Productos elemento = dataSnapshot.getValue(Productos.class);

            productoid = elemento.getProductoid();
            usuarioCreadorUid = elemento.getUsuarioCreadorUid();
            titulo = elemento.getTitulo();
            descripcion = elemento.getDescripcion();
            precio = elemento.getPrecio();
            categoria = elemento.getCategoria();
            estado = elemento.getEstado();
            imagen = elemento.getImagen();
            nombreUsuarioCreador = elemento.getNombreUsuarioCreador();
            fotoUsuarioCreador = elemento.getFotoUsuarioCreador();

            editTxtTitulo.setText(titulo);
            editTxtDescripcion.setText(descripcion);
            editTxtPrecio.setText(precio);


            if (estado.equals("Bien")) {
                editSpinEstado.setSelection(1);
            } else if (estado.equals("Regular/Bastante usado")) {
                editSpinEstado.setSelection(0);
            } else if (estado.equals("Mal estado")) {
                editSpinEstado.setSelection(2);
            } else {
                editSpinEstado.setSelection(0);
            }

            if (categoria.equals("Objetos")) {
                editSpinCategoria.setSelection(0);
            } else if (categoria.equals("Servicios")) {
                editSpinCategoria.setSelection(1);
            } else if (categoria.equals("Otros")) {
                editSpinCategoria.setSelection(2);
            } else {
                editSpinCategoria.setSelection(0);
            }


            //editSpinCategoria.setAdapter(categoria);
            //editSpinEstado.setText(estado);


            if (imagen.isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.productosinfoto)
                        .resize(370, 370)
                        .into(editIvImagenProducto);
                Log.e("ISEMPTY", "SE PUSO LA FOTO DEL ISEMPTY POR DEFECTO");
            } else {
                Picasso.with(getApplicationContext())
                        .load(imagen)
                        .placeholder(R.drawable.productosinfoto)
                        //.resize(370, 370)//da problemas con imagenes grandes
                        .into(editIvImagenProducto);
                Log.e("PRODUCTO", "SE PUSO FOTO DEL PRODUCTO");
            }


            Log.e("[[[[[[[[[[[[[[productoid]]]]]]]]]]]]]", productoid);
            Log.e("[[[[[[[[[[[[[[usuarioCreadorUid]]]]]]]]]]]]]", usuarioCreadorUid);
            Log.e("[[[[[[[[[[[[[[titulo]]]]]]]]]]]]]", titulo);
            Log.e("[[[[[[[[[[[[[[DESCRIPCION]]]]]]]]]]]]]", descripcion);
            Log.e("[[[[[[[[[[[[[[precio]]]]]]]]]]]]]", precio);
            Log.e("[[[[[[[[[[[[[[categoria]]]]]]]]]]]]]", categoria);
            Log.e("[[[[[[[[[[[[[[estado]]]]]]]]]]]]]", estado);
            Log.e("[[[[[[[[[[[[[[imagenproducto]]]]]]]]]]]]]", imagen);
            Log.e("[[[[[[[[[[[[[[imagenusuariocreador]]]]]]]]]]]]]", fotoUsuarioCreador);
            Log.e("[[[[[[[[[[[[[[nombreusuariocreador]]]]]]]]]]]]]", nombreUsuarioCreador);
            Log.e("[[[     --->INFO DE PRODUCTO REALIZADA<---     ]]]", "realizada");


            usuariosFirebase = FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados").child(elemento.getUsuarioCreadorUid());
            usuariosFirebase.addListenerForSingleValueEvent(valueEventListenerUsuarios);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // FIN EventListener
    private ProgressDialog progressDialog;
    private DatabaseReference firebaseAuth;
    private DatabaseReference productosDB;
    private Uri imageUri;
    private StorageReference filePath;
    /////////////////////////////////////////////////////////////////
    private StorageReference myFirebaseStorage;


    /////////////////////////////////////////////////////////////////////////////////////////////
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);


        Intent outputEditarProductoId = getIntent();
        Bundle datos = outputEditarProductoId.getExtras();


        if (datos != null) {
            idProductoAEditar = (String) datos.get("idProductoAEditar");

            System.out.println("*\n*\n*\n*\n***idProductoAEditar: " + idProductoAEditar + "*\n*\n*\n*\n***");

        } else {
            idProductoAEditar = "ERROR, NO SE PUDO OBTENER EL ID DEL PRODUCTO ELEGIDO";
            System.out.println("*\n*\n*\n*\n***idProductoAEditar: " + idProductoAEditar + "*\n*\n*\n*\n***");
        }


        //////////////////////////////////////////////////////////////////////////////////////////////////

        getSupportActionBar().setTitle("MyGalaxyCatalogue");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        getSupportActionBar().setSubtitle("Mi Perfil");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");

        editTxtTitulo = findViewById(R.id.edittxttitulo);
        editTxtDescripcion = findViewById(R.id.edittxtdesc);
        editTxtPrecio = findViewById(R.id.edittxtprecio);
        editSpinEstado = findViewById(R.id.editspinestado);
        editSpinCategoria = findViewById(R.id.editspincateg);
        editIvImagenProducto = findViewById(R.id.editimgproducto);

        btnEditarProducto = findViewById(R.id.editbtneditarproducto);
        btnEleg = findViewById(R.id.editbtneleg);
        progressDialog = new ProgressDialog(this);

        //Declaramos un objeto FirebaseUser llamado user para obtener de el por ejemplo su Uid a posteriori con diferentes métodos. Tambien vamos a configuar el acceso anónimo sin usuario registrado
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseDatabase.getInstance().getReference();

        //STORAGE2 obtenemos la referencia de storage de firebase e inicializarla.
        myFirebaseStorage = FirebaseStorage.getInstance().getReference();
        /* Consulta Firebase: SELECT * FROM Usuarios/UsuariosRegistrados WHERE ChildUid = user.getUid();Referencia a la base de datos a la tabla de nuestros usuarios*/
        productosFirebase = FirebaseDatabase.getInstance().getReference("Productos").child("ProductosRegistrados").child(idProductoAEditar);
        //////// Lo añado al Listener "valueEventListener" creado debajo del OnCreate. Con addListenerForSingleValueEvent lo que hago es obtener los datos del listener 1 VEZ y no estoy continuamente a la escucha para actualizar el dato
        productosFirebase.addListenerForSingleValueEvent(valueEventListener);

        //FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados");


        //ivImagenPerfil.setImageResource(R.mipmap.imagenperfilpordefecto);
        Picasso.with(getApplicationContext())
                .load(R.drawable.productosinfoto)
                //.load(R.mipmap.imagenperfilpordefecto)
                .placeholder(R.drawable.avatardefault)
                .into(editIvImagenProducto);
        Log.e("WE", "SE PUSO LA FOTO POR DEFECTO");




        /*On Click Listener BOTON ACTUALIZAR PERFIL */
        btnEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (continuar == true) {
                    editarProducto();
                } else {
                    Toast.makeText(EditarProducto.this, "Subiendo fotografía, por favor espere Ó presione 'BOTÓN ATRÁS' para cancelar.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        /*FIN On Click Listener*/

        //STORAGE3 On Click Listener para que al pulsar el boton O LA FOTO elijamos una foto de la Galeria
        /*On Click Listener IMAGEN PERFIL */
        editIvImagenProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuar = false;
                System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");
                showPictureDialog();
                /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT); //SUBE IMAGEN4*/
            }
        });
        /*FIN On Click Listener*/


        btnEleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuar = false;
                System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT); //SUBE IMAGEN4
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////


    } //OnCreate

    public void editarProducto() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String titulO = editTxtTitulo.getText().toString().trim();
        final String descripcioN = editTxtDescripcion.getText().toString().trim();
        final String preciO = editTxtPrecio.getText().toString().trim();
        final String imageN;

        if (nuevaFotoProducto.equals("SINFOTO")) {
            imageN = imagen;
        } else {
            imageN = nuevaFotoProducto;
        }


        final String categoriA = editSpinCategoria.getSelectedItem().toString();
        final String estadO = editSpinEstado.getSelectedItem().toString();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(titulO)) {
            Toast.makeText(this, "No puedes dejar el título vacío", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(descripcioN)) {
            Toast.makeText(this, "No puedes dejar la descripción vacía", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(preciO)) {
            Toast.makeText(this, "No puedes dejar el precio vacío", Toast.LENGTH_LONG).show();
            return;
        }

        if (!TextUtils.isEmpty(titulO) && !TextUtils.isEmpty(descripcioN) && !TextUtils.isEmpty(preciO)) {

            progressDialog.setMessage("Actualizando tu producto en linea...");
            progressDialog.show();

            Productos producto = new Productos(idProductoAEditar, usuarioCreadorUid, titulO, descripcioN, preciO, categoriA, estadO, imageN, nombre, fotoDePerfil);

            // Firebase Logic
            productosFirebase.setValue(producto);

            ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com?ope=update", idProductoAEditar, titulO, descripcioN, preciO, imageN, categoriA, estadO);


            //productosDB.child("ProductosRegistrados").child(id).setValue(producto);
            //Mensaje en pantalla
            progressDialog.dismiss();
            Toast.makeText(this, "Producto editado correctamente", Toast.LENGTH_LONG).show();
            // Reiniciamos actividad para vaciar los campos
            startActivity(new Intent(EditarProducto.this, MenuNavegacion.class));


        }

    }

    /**
     * MySQL LOGIC
     */
    private void ejecutarServicio(String URL, String idProduct, String tituloProduct, String descProduct, String precioProduct, String imgProduct, String categProduct, String estadoProduct) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("IdProduct", idProduct);
                parametros.put("TituloProduct", tituloProduct);
                parametros.put("DescProduct", descProduct);
                parametros.put("PrecioProduct", precioProduct);
                parametros.put("ImgProduct", imgProduct);
                parametros.put("CategProduct", categProduct);
                parametros.put("EstadoProduct", estadoProduct);


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estás seguro de cancelar la edición del elemento seleccionado?")
                .setMessage("Se perderán los datos no guardados.")
                .setCancelable(false)
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(EditarProducto.this, MenuNavegacion.class);

                        startActivity(intent);

                    }
                })
                .setNegativeButton("Continuar editando", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    } //OnBackPressed*/


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            imageUri = data.getData();

            Picasso.with(getApplicationContext())
                    .load(imageUri)
                    .placeholder(R.drawable.productosinfoto)
                    .into(editIvImagenProducto);

            // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
            SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
            String format = s.format(new Date());
            filePath = myFirebaseStorage.child("FotosDeProductos").child(idProductoAEditar + format + "_FOTO");

            //Subimos la foto


            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(EditarProducto.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            nuevaFotoProducto = uri.toString();

                            continuar = true;
                            System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                        }
                    });// getDownloadUrl()
                }
            }); //putFile()


        } else {
            continuar = true;
            System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
        }

    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        progressDialog.setMessage("Subiendo fotografía... Espere unos segundos");
        progressDialog.show();

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    String path = saveImage(bitmap);
                    Toast.makeText(EditarProducto.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    Picasso.with(getApplicationContext())
                            .load(imageUri)
                            .placeholder(R.drawable.productosinfoto)
                            .into(editIvImagenProducto);

                    ////////////////////////////////////////////////////////
                    // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
                    String format = s.format(new Date());
                    filePath = myFirebaseStorage.child("FotosDeProductos").child(idProductoAEditar + format + "_FOTO");
                    //Subimos la foto
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(EditarProducto.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    nuevaFotoProducto = uri.toString();
                                    continuar = true;
                                    progressDialog.dismiss();
                                    System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                                }
                            });// getDownloadUrl()
                        }
                    }); //putFile()
                    ///////////////////////////////////////////////

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditarProducto.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            editIvImagenProducto.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(EditarProducto.this, "Image Saved!", Toast.LENGTH_SHORT).show();

            ByteArrayOutputStream baos = new ByteArrayOutputStream(64);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();

            ////////////////////////////////////////////////////////
            // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
            SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
            String format = s.format(new Date());
            filePath = myFirebaseStorage.child("FotosDeProductos").child(idProductoAEditar + format + "_FOTO");
            //Subimos la foto
            filePath.putBytes(data1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(EditarProducto.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            nuevaFotoProducto = uri.toString();
                            continuar = true;
                            progressDialog.dismiss();
                            System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                        }
                    });// getDownloadUrl()
                }
            }); //putFile()
            ///////////////////////////////////////////////

        }
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


}
