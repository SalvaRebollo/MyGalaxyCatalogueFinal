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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ceviche.sareb.salvisapp.Clases.RandomString;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiPerfilActivity extends AppCompatActivity {

    private static final String TAG = "TaG";
    private static final int GALLERY_INTENT = 1;
    private static final String IMAGE_DIRECTORY = "/myGalaxyCataPhotos";
    ImageView ivImagenPerfil;
    boolean continuar = true;
    Button btnActualizarPerfil;
    TextView txtPerfilTlfContacto, txtPerfilNombre, txtPerfilApellidos, txtPerfilBiografia, txtPerfilPais, txtPerfilCiudad, txtPerfilDireccion;
    String email, nombre, apellidos, biografia, pais, ciudad, direccion, fotoDePerfil, uid, tlfContacto = "";
    RandomString session = new RandomString();
    ValueEventListener valueEventListener = new ValueEventListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Usuarios elemento = dataSnapshot.getValue(Usuarios.class);

            email = elemento.getEmail();
            nombre = elemento.getNombre();
            apellidos = elemento.getApellidos();
            biografia = elemento.getBiografia();
            pais = elemento.getPais();
            ciudad = elemento.getCiudad();
            direccion = elemento.getDireccion();
            fotoDePerfil = elemento.getFotoPerfil();
            uid = elemento.getUsuarioUid();
            tlfContacto = elemento.getTlfContacto();

            txtPerfilNombre.setText(nombre);
            txtPerfilApellidos.setText(apellidos);
            txtPerfilBiografia.setText(biografia);
            txtPerfilPais.setText(pais);
            txtPerfilCiudad.setText(ciudad);
            txtPerfilDireccion.setText(direccion);
            txtPerfilTlfContacto.setText(tlfContacto);

            if (fotoDePerfil.isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.avatardefault)
                        .resize(370, 370)
                        .into(ivImagenPerfil);
                Log.e("ISEMPTY", "SE PUSO LA FOTO DEL ISEMPTY POR DEFECTO");
            } else {
                Picasso.with(getApplicationContext())
                        .load(fotoDePerfil)
                        .placeholder(R.drawable.animacioncargar)
                        //.resize(370, 370)//da problemas con imagenes grandes
                        .into(ivImagenPerfil);
                Log.e("PERFIL", "SE PUSO FOTO PERFIL");
            }


            Log.e("[[[[[[[[[[[[[[nombreUsuario]]]]]]]]]]]]]", nombre);
            Log.e("[[[[[[[[[[[[[[apellidos]]]]]]]]]]]]]", apellidos);
            Log.e("[[[[[[[[[[[[[[pais]]]]]]]]]]]]]", pais);
            Log.e("[[[[[[[[[[[[[[ciudad]]]]]]]]]]]]]", ciudad);
            Log.e("[[[[[[[[[[[[[[fotoperfil]]]]]]]]]]]]]", fotoDePerfil);
            Log.e("[[[[[[[[[[[[[[UID]]]]]]]]]]]]]", uid);
            Log.e("[[[     --->INFO DE USUARIO REALIZADA<---     ]]]", "realizada");


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // FIN EventListener
    private ProgressDialog progressDialog;
    private DatabaseReference firebaseAuth;
    private DatabaseReference usuariosDB;
    private Uri imageUri;
    private StorageReference filePath;
    private StorageReference myFirebaseStorage;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        getSupportActionBar().setTitle("MyGalaxyCatalogue");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        getSupportActionBar().setSubtitle("Mi Perfil");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");

        btnActualizarPerfil = findViewById(R.id.btnPerfilActualizar);
        txtPerfilNombre = findViewById(R.id.txtPerfilNombre);
        txtPerfilApellidos = findViewById(R.id.txtPerfilApellidos);
        txtPerfilBiografia = findViewById(R.id.txtPerfilBiografia);
        txtPerfilPais = findViewById(R.id.txtPerfilPais);
        txtPerfilCiudad = findViewById(R.id.txtPerfilCiudad);
        txtPerfilDireccion = findViewById(R.id.txtPerfilDireccion);
        txtPerfilTlfContacto = findViewById(R.id.txtPerfilTlfContacto);
        progressDialog = new ProgressDialog(this);

        //Declaramos un objeto FirebaseUser llamado user para obtener de el por ejemplo su Uid a posteriori con diferentes métodos. Tambien vamos a configuar el acceso anónimo sin usuario registrado
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseDatabase.getInstance().getReference();
        usuariosDB = FirebaseDatabase.getInstance().getReference("Usuarios");
        //STORAGE2 obtenemos la referencia de storage de firebase e inicializarla.
        myFirebaseStorage = FirebaseStorage.getInstance().getReference();
        /* Consulta Firebase: SELECT * FROM Usuarios/UsuariosRegistrados WHERE ChildUid = user.getUid();Referencia a la base de datos a la tabla de nuestros usuarios*/
        DatabaseReference usuariosFirebase = FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados").child(user.getUid());
        //////// Lo añado al Listener "valueEventListener" creado debajo del OnCreate. Con addListenerForSingleValueEvent lo que hago es obtener los datos del listener 1 VEZ y no estoy continuamente a la escucha para actualizar el dato
        usuariosFirebase.addValueEventListener(valueEventListener);


        String url = "https://i.imgur.com/7uZE61J.png";


        ivImagenPerfil = findViewById(R.id.imagenPerfil);
        //ivImagenPerfil.setImageResource(R.mipmap.imagenperfilpordefecto);
        Picasso.with(getApplicationContext())
                .load(R.drawable.avatardefault)
                //.load(R.mipmap.imagenperfilpordefecto)
                .placeholder(R.drawable.avatardefault)
                .into(ivImagenPerfil);
        Log.e("WE", "SE PUSO LA FOTO POR DEFECTO");

        ejecutarServicioSelectById("https://my-galaxy-catalogue-php.herokuapp.com/index.php?mod=usuario&ope=selectById", "zPz92HOhkqeGH47EWgOJlIy3Gmf1");


        /*On Click Listener BOTON ACTUALIZAR PERFIL */
        btnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (continuar == true) {
                    actualizarPerfil();
                } else {
                    Toast.makeText(MiPerfilActivity.this, "Subiendo fotografía, por favor espere Ó presione 'BOTÓN ATRÁS' para cancelar.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        /*FIN On Click Listener*/

        /*On Click Listener IMAGEN PERFIL */
        ivImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuar = false;
                System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");
                showPictureDialog();
                /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, GALLERY_INTENT);*/ //SUBE IMAGEN4


            }
        });
        /*FIN On Click Listener*/


    } //OnCreate

    public void actualizarPerfil() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String nombre = txtPerfilNombre.getText().toString().trim();
        final String apellidos = txtPerfilApellidos.getText().toString().trim();
        final String biografia = txtPerfilBiografia.getText().toString().trim();
        final String pais = txtPerfilPais.getText().toString().trim();
        final String ciudad = txtPerfilCiudad.getText().toString().trim();
        final String direccion = txtPerfilDireccion.getText().toString().trim();
        final String fotoperfil = fotoDePerfil;
        final String tlfContacto = txtPerfilTlfContacto.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "No puedes dejar el nombre vacío", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(apellidos)) {
            Toast.makeText(this, "No puedes dejar el apellido vacío", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(tlfContacto)) {
            Toast.makeText(this, "Si quieres subir productos, debes introducir un teléfono de contacto, en caso de no necesitarlo, introducir cualquier otro método de contacto.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(tlfContacto)) {

            progressDialog.setMessage("Actualizando tu perfil en linea...");
            progressDialog.show();


            Usuarios usuario = new Usuarios(email, uid, nombre, apellidos, fotoperfil, biografia, pais, ciudad, direccion, tlfContacto);

            // Firebase LOGIC
            usuariosDB.child("UsuariosRegistrados").child(uid).setValue(usuario);
            /**
             * MySQL LOGIC
             */
            System.out.println("usuario a editar:-->\n" + usuario);
            ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com/index.php?mod=usuario&ope=update", uid, nombre, apellidos, direccion, ciudad, pais, email, fotoperfil, biografia, tlfContacto);

            // FIN LO GUARDA EN DATABASE

            progressDialog.dismiss();

            Toast.makeText(MiPerfilActivity.this, "Perfil actualizado correctamente :)", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MiPerfilActivity.this, InterfazPrincipalActivity.class));


        }


    }


    /**
     * @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
     * <p>
     * super.onActivityResult(requestCode, resultCode, data);
     * <p>
     * if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
     * <p>
     * imageUri = data.getData();
     * <p>
     * <p>
     * Picasso.with(getApplicationContext())
     * .load(imageUri)
     * .placeholder(R.drawable.avatardefault)
     * .into(ivImagenPerfil);
     * <p>
     * // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
     * filePath = myFirebaseStorage.child("InfoDePerfilUsuarios").child(uid).child(imageUri.getLastPathSegment() + "avatar");
     * //filePath = myFirebaseStorage.child("FotosDePerfil").child(uid).child(imageUri.getLastPathSegment());
     * <p>
     * <p>
     * //Subimos la foto
     * filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
     * @Override public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
     * <p>
     * Toast.makeText(MiPerfilActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
     * <p>
     * filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
     * @Override public void onSuccess(Uri uri) {
     * fotoDePerfil = uri.toString();
     * continuar = true;
     * System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
     * }
     * });
     * <p>
     * <p>
     * }
     * });
     * <p>
     * <p>
     * } else {
     * continuar = true;
     * System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
     * }
     * <p>
     * }
     */


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        progressDialog.setMessage("Subiendo fotografía... Espero unos segundos");
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
                    Toast.makeText(MiPerfilActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    Picasso.with(getApplicationContext())
                            .load(imageUri)
                            .placeholder(R.drawable.avatardefault)
                            .into(ivImagenPerfil);

                    ////////////////////////////////////////////////////////
                    // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
                    filePath = myFirebaseStorage.child("InfoDePerfilUsuarios").child(uid).child(imageUri.getLastPathSegment() + "avatar");
                    //Subimos la foto
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(MiPerfilActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    fotoDePerfil = uri.toString();
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
                    Toast.makeText(MiPerfilActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                continuar = true;
                System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
            }

        } else if (requestCode == CAMERA) {
            if (data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                //ivImagenPerfil.setImageBitmap(thumbnail);
                Glide.with(getApplicationContext())
                        .load(thumbnail)
                        .placeholder(R.drawable.avatardefault)
                        .into(ivImagenPerfil);
                saveImage(thumbnail);
                Toast.makeText(MiPerfilActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                ByteArrayOutputStream baos = new ByteArrayOutputStream(64);
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data1 = baos.toByteArray();

                ////////////////////////////////////////////////////////
                // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
                String randomString = session.nextString();
                System.out.println("\n\n****RANDOM STRING:[" + randomString + "]*****\n\n");
                filePath = myFirebaseStorage.child("InfoDePerfilUsuarios").child(uid).child(randomString + "avatar");
                //Subimos la foto
                filePath.putBytes(data1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(MiPerfilActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fotoDePerfil = uri.toString();
                                continuar = true;
                                progressDialog.dismiss();
                                System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                            }
                        });


                    }
                });
                ///////////////////////////////////////////////
            } else {
                continuar = true;
                System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
            }


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


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(MiPerfilActivity.this, MenuNavegacion.class);
        startActivity(intent);
        finish();


    }//onBackPressed

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }//onStart

    /**
     * MySQL LOGIC
     */
    private void ejecutarServicio(String URL, String uidUser, String nomUser, String apeUser, String dirUser, String ciuUser, String paisUser, String emailUser, String fotoPerfilUser, String bioUser, String metContactoUser) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UidUser", uidUser);
                parametros.put("NomUser", nomUser);
                parametros.put("ApeUser", apeUser);
                parametros.put("DirUser", dirUser);
                parametros.put("CiuUser", ciuUser);
                parametros.put("PaisUser", paisUser);
                parametros.put("EmailUser", emailUser);
                parametros.put("FotoPerfilUser", fotoPerfilUser);
                parametros.put("BioUser", bioUser);
                parametros.put("MetContactoUser", metContactoUser);

                System.out.println("%&%&%&%&%&%&%&%&%&%&%&%&&\n" + parametros);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void ejecutarServicioSelectById(String URL, String uidUser) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("%&%&%&%&%&%&%&%&%&%&%&%&&\n" + "Response: " + response.toString());
                        //textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UidUser", uidUser);
                System.out.println("%&%&%&%&%&%&%&%&%&%&%&%&&\n" + parametros);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }

}
