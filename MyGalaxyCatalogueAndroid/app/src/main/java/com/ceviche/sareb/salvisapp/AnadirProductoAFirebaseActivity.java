package com.ceviche.sareb.salvisapp;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceviche.sareb.salvisapp.Clases.Productos;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnadirProductoAFirebaseActivity extends AppCompatActivity {

    private static final String TAG = "TAGDEBUG";
    private static final String IMAGE_DIRECTORY = "/myGalaxyCataPhotos";
    private static final int GALLERY_INTENT = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    String fotoProducto = "SINFOTO";
    String encodedImage = "";
    boolean continuar = true;
    String nombre, fotoperfil;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String usuarioCreadorUid = user.getUid();
    private EditText txtTitulo, txtDesc, txtPrecio;
    private Spinner spinCateg, spinEstado;
    private Button btnRegistrar, btnElegirFotoSubir;
    private Intent intent;
    private Uri imageUri;
    private StorageReference filePath;
    private ImageView imgproducto;
    private int GALLERY = 1, CAMERA = 2;
    private ProgressDialog progressDialog;
    //Primero: Referencia a la base de datos
    private StorageReference myFirebaseStorage; //STORAGE1:Referencia para usar el Storage, el cual va a ser usado para subir la foto del producto
    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    // Segundo: Obtenemos la instancia, en el parentesis va la clase Java "Productos.java" que tenemos creada con los mismos atributos como variables
    private DatabaseReference productosDB = FirebaseDatabase.getInstance().getReference("Productos").child("ProductosRegistrados");
    String productoid = productosDB.push().getKey(); //Genera la key y la guarda en la id
    private DatabaseReference usuariosDB = FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados").child(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_productos_a_firebase_activity);

        getSupportActionBar().setTitle("MyGalaxyCatalogue");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        getSupportActionBar().setSubtitle("Añadir producto");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        requestMultiplePermissions();
        System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");
        //STORAGE2 obtenemos la referencia de storage de firebase e inicializarla.
        myFirebaseStorage = FirebaseStorage.getInstance().getReference();

        Intent outputInterfazPrincipalDatosUsu = getIntent();
        Bundle datos = outputInterfazPrincipalDatosUsu.getExtras();

        if (datos != null) {
            nombre = (String) datos.get("nombre");
            fotoperfil = (String) datos.get("fotoperfil");
        }

        System.out.println("*\n*\n*\n*\n*NOMBRE = " + nombre + "*\nFOTOPERFIL:" + fotoperfil + "*\n*\n*\n*");


        /*Vinculamos los items de la vista en xml con las variables Java*/
        /*---> Cajas de texto*/
        txtTitulo = findViewById(R.id.txttitulo);
        txtDesc = findViewById(R.id.txtdesc);
        txtPrecio = findViewById(R.id.txtprecio);
        /*---> Spinners o selects*/
        spinCateg = findViewById(R.id.spincateg);
        spinEstado = findViewById(R.id.spinestado);
        /*Buttons/Botones*/
        btnRegistrar = findViewById(R.id.btnregistrar);
        btnElegirFotoSubir = findViewById(R.id.btneleg);
        /*Imagen de producto*/
        imgproducto = findViewById(R.id.imgproducto);
        Bitmap productoSinFotoDecoded;
        /*progressDialog*/
        progressDialog = new ProgressDialog(this);
        // Imagen de producto sin foto que saldrá por defecto al iniciar la actividad de añadir productos
        imgproducto.setImageBitmap(decodeStringBase64toByte(getResources().getString(R.string.img_defecto)));


        /*ESTO ES PARA VER SI SE CONSIGUE LEER LOS DATOS DE FIREBASE Y SE IMPRIMEN EN CONSOLA*/
        /*
        productosDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Productos elemento = snapshot.getValue(Productos.class);
                    String productoid = elemento.getProductoid();
                    String titulo = elemento.getTitulo();
                    String descripcion = elemento.getDescripcion();
                    String precio = elemento.getPrecio();
                    String categoria = elemento.getCategoria();
                    String estado = elemento.getEstado();
                    String imagen = elemento.getImagen();


                    Log.e("=============== Datos"+ i + " ===============",""+snapshot.getValue());
                    Log.e("[TITULOOOO"+ i + "]=", titulo);
                    Log.e("[DESCRIPCION"+ i + "]=", descripcion);
                    Log.e("[PRECIO"+ i + "]=", precio);
                    Log.e("[CATEGORIA"+ i + "]=", categoria);
                    Log.e("[ESTADO"+ i + "]=", estado);
                    Log.e("[IMAGEN_BASE64"+ i + "]=", imagen);
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
        /*FIN LEER LOS DATOS DE FIREBASE Y SE IMPRIMEN EN CONSOLA*/

        /*On Click Listener para que al pulsar el boton haga funcionar el método registrarProducto()*/
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continuar == true) {
                    registrarProducto();
                } else {
                    Toast.makeText(AnadirProductoAFirebaseActivity.this, "Subiendo fotografía, por favor espere Ó presione 'BOTÓN ATRÁS' para cancelar.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //STORAGE3 On Click Listener para que al pulsar el boton O LA FOTO elijamos una foto de la Galeria
        btnElegirFotoSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuar = false;
                System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");

               /* intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);*/ //SUBE IMAGEN4


                showPictureDialog();
                /*Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);*/

            }
        });

        imgproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuar = false;
                System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");

                showPictureDialog();

                /*intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT); //SUBE IMAGEN4*/


            }
        });
        // FIN STORAGE3  ///////////

    }


/*

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

       */
/* if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(AnadirProductoAFirebaseActivity.this, new String[] {Manifest.permission.CAMERA}, requestCode);
        }*//*


        if (requestCode == GALLERY) {
            if (data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    String path = saveImage(bitmap);
                    Toast.makeText(AnadirProductoAFirebaseActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imgproducto.setImageBitmap(bitmap);

                    // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
                    filePath = myFirebaseStorage.child("FotosDeProductos").child(productoid + "_FOTO");

                    //Subimos la foto
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(AnadirProductoAFirebaseActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    fotoProducto = uri.toString();

                                    continuar = true;
                                    System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                                }
                            });
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AnadirProductoAFirebaseActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");



            imageUri = getImageUri(getApplicationContext(), thumbnail);


            imgproducto.setImageBitmap(thumbnail);

            saveImage(thumbnail);
            Toast.makeText(AnadirProductoAFirebaseActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();


            System.out.println("\ncontext=" + getApplicationContext() + "\nthumnanil=" + thumbnail.toString());


            // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
            filePath = myFirebaseStorage.child("FotosDeProductos").child(productoid + "_FOTO");

            //Subimos la foto
            if (imageUri != null) {

                filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(AnadirProductoAFirebaseActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                fotoProducto = uri.toString();

                                continuar = true;
                                System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(AnadirProductoAFirebaseActivity.this, "IMAGEN URI NULA", Toast.LENGTH_SHORT).show();
                System.out.println("*\n*\n*\n*\n*IMAGEN URI NULA" + "*\n*\n*\n*\n*");
            }
        }

    }



*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        progressDialog.setMessage("Subiendo fotografía... Espere unos segundos");
        progressDialog.show();

        if (resultCode == RESULT_CANCELED) {
            progressDialog.dismiss();
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    String path = saveImage(bitmap);
                    Toast.makeText(AnadirProductoAFirebaseActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imgproducto.setImageBitmap(bitmap);

                    ////////////////////////////////////////////////////////
                    // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
                    filePath = myFirebaseStorage.child("FotosDeProductos").child(productoid + "_FOTO");
                    //Subimos la foto
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(AnadirProductoAFirebaseActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    fotoProducto = uri.toString();
                                    continuar = true;
                                    progressDialog.dismiss();
                                    System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                                }
                            });
                        }
                    });
                    ///////////////////////////////////////////////

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AnadirProductoAFirebaseActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgproducto.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(AnadirProductoAFirebaseActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

            ByteArrayOutputStream baos = new ByteArrayOutputStream(64);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();

            ////////////////////////////////////////////////////////
            // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
            filePath = myFirebaseStorage.child("FotosDeProductos").child(productoid + "_FOTO");
            //Subimos la foto
            filePath.putBytes(data1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(AnadirProductoAFirebaseActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            fotoProducto = uri.toString();

                            continuar = true;
                            progressDialog.dismiss();
                            System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                        }
                    });
                }
            });
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
                            //Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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


    /*Vamos a crear un método para registrar los productosDB así que haremos variables para vincular con las de la clase*/
    public void registrarProducto() {

        String categoria = spinCateg.getSelectedItem().toString();
        // Para pasar los items que son spinners a cadena String
        String estado = spinEstado.getSelectedItem().toString();

        String titulo = txtTitulo.getText().toString(); // Para pasar los items que son EdiText a cadena String
        String descripcion = txtDesc.getText().toString();
        String precio = txtPrecio.getText().toString();
        //precio = String.format("%.2f", precio); //Redondear a 2 decimales
        String imagen = encodedImage;


        if (!TextUtils.isEmpty(titulo) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(precio)) {


            Productos producto = new Productos(productoid, usuarioCreadorUid, titulo, descripcion, precio, categoria, estado, fotoProducto, nombre, fotoperfil);

            // Firebase upload
            productosDB.child(productoid).setValue(producto);

            /**
             * MySQL LOGIC
             */
            System.out.println("productoID-->" + productoid + "\nUsuarioCreadorUID -->" + usuarioCreadorUid);
            ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com/", productoid, usuarioCreadorUid, titulo, descripcion, precio, fotoProducto, categoria, estado);

            /********/

            //productosDB.child("ProductosRegistrados").child(id).setValue(producto);
            //Mensaje en pantalla
            Toast.makeText(this, "Producto registrado correctamente", Toast.LENGTH_LONG).show();
            // Reiniciamos actividad para vaciar los campos
            startActivity(new Intent(AnadirProductoAFirebaseActivity.this, InterfazPrincipalActivity.class));
        } else {
            //Mensaje en pantalla
            Toast.makeText(this, "Debe escribir en todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * MySQL LOGIC
     */
    private void ejecutarServicio(String URL, String idProduct, String uidUser, String tituloProduct, String descProduct, String precioProduct, String imgProduct, String categProduct, String estadoProduct) {


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
                parametros.put("UidUser", uidUser);
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


    /********/


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String encodeImageBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);

        return encImage;
    }

    private Bitmap decodeStringBase64toByte(String encodedImage) {
        byte[] decodeString = Base64.decode(encodedImage, Base64.NO_WRAP);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);


        return decoded;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(AnadirProductoAFirebaseActivity.this, InterfazPrincipalActivity.class);
        startActivity(intent);
        finish();


    }


}
