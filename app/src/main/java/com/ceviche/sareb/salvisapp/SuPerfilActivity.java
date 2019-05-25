package com.ceviche.sareb.salvisapp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceviche.sareb.salvisapp.Clases.RandomString;
import com.ceviche.sareb.salvisapp.Clases.Usuarios;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SuPerfilActivity extends AppCompatActivity {

    private static final String TAG = "TaG";
    private static final int GALLERY_INTENT = 1;
    private static final String IMAGE_DIRECTORY = "/myGalaxyCataPhotos";
    ImageView ivImagenPerfil;
    boolean continuar = true;
    Button btnActualizarPerfil;
    TextView txtPerfilTlfContacto, txtPerfilNombre, txtPerfilNombreCompleto, txtPerfilApellidos, txtPerfilBiografia, txtPerfilPais, txtPerfilCiudad, txtPerfilDireccion;
    String email, nombre, apellidos, biografia, pais, ciudad, direccion, fotoDePerfil, uid, tlfContacto = "";
    String usuarioCreadorUid;
    RandomString session = new RandomString();
    Usuarios usuario = new Usuarios();
    JSONObject mainObject = new JSONObject();
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
        setContentView(R.layout.activity_su_perfil_ui);

        getSupportActionBar().setTitle("MyGalaxyCatalogue");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        getSupportActionBar().setSubtitle("Mi Perfil");
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        Intent inputAnteriorActivity = getIntent();
        Bundle datos = inputAnteriorActivity.getExtras();

        if (datos != null) {
            usuarioCreadorUid = (String) datos.get("usuarioCreadorUid");
            Toast.makeText(SuPerfilActivity.this, usuarioCreadorUid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SuPerfilActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }


        System.out.println("*\n*\n*\n*\n*CONTINUAR = " + continuar + "*\n*\n*\n*\n*");

        txtPerfilNombre = findViewById(R.id.txtPerfilNombre);
        txtPerfilApellidos = findViewById(R.id.txtPerfilApellidos);
        txtPerfilNombreCompleto = findViewById(R.id.txtPerfilNombreCompleto);
        txtPerfilBiografia = findViewById(R.id.txtPerfilBiografia);
        txtPerfilPais = findViewById(R.id.txtPerfilPais);
        txtPerfilCiudad = findViewById(R.id.txtPerfilCiudad);
        txtPerfilDireccion = findViewById(R.id.txtPerfilDireccion);
        txtPerfilTlfContacto = findViewById(R.id.txtPerfilTlfContacto);
        ivImagenPerfil = findViewById(R.id.imagenPerfil);

        progressDialog = new ProgressDialog(this);

        /*//Declaramos un objeto FirebaseUser llamado user para obtener de el por ejemplo su Uid a posteriori con diferentes métodos. Tambien vamos a configuar el acceso anónimo sin usuario registrado
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseDatabase.getInstance().getReference();
        usuariosDB = FirebaseDatabase.getInstance().getReference("Usuarios");
        //STORAGE2 obtenemos la referencia de storage de firebase e inicializarla.
        myFirebaseStorage = FirebaseStorage.getInstance().getReference();
        *//* Consulta Firebase: SELECT * FROM Usuarios/UsuariosRegistrados WHERE ChildUid = user.getUid();Referencia a la base de datos a la tabla de nuestros usuarios*//*
        DatabaseReference usuariosFirebase = FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados").child(user.getUid());
        //////// Lo añado al Listener "valueEventListener" creado debajo del OnCreate. Con addListenerForSingleValueEvent lo que hago es obtener los datos del listener 1 VEZ y no estoy continuamente a la escucha para actualizar el dato
        usuariosFirebase.addValueEventListener(valueEventListener);


        String url = "https://i.imgur.com/7uZE61J.png";*/


        //ivImagenPerfil.setImageResource(R.mipmap.imagenperfilpordefecto);
        Picasso.with(getApplicationContext())
                .load(R.drawable.avatardefault)
                //.load(R.mipmap.imagenperfilpordefecto)
                .placeholder(R.drawable.avatardefault)
                .into(ivImagenPerfil);
        Log.e("WE", "SE PUSO LA FOTO POR DEFECTO");


        // MySQL Logic
        ejecutarServicioSelectById("https://my-galaxy-catalogue-php.herokuapp.com/index.php?mod=usuario&ope=selectById", usuarioCreadorUid);


    } //OnCreate

    /*ValueEventListener valueEventListener = new ValueEventListener() {
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
                Log.e("ISEMPTY","SE PUSO LA FOTO DEL ISEMPTY POR DEFECTO");
            } else {
                Picasso.with(getApplicationContext())
                        .load(fotoDePerfil)
                        .placeholder(R.drawable.animacioncargar)
                        //.resize(370, 370)//da problemas con imagenes grandes
                        .into(ivImagenPerfil);
                Log.e("PERFIL","SE PUSO FOTO PERFIL");
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
    }; // FIN EventListener*/

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(SuPerfilActivity.this, InterfazPrincipalActivity.class);
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
    private void ejecutarServicioSelectById(String URL, final String UidUser) {

        progressDialog.setMessage("Cargando perfil... Espere unos segundos");
        progressDialog.show();

        //final String param = etUname.getText().toString().trim();
        // final String password = etPass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parseData(response);

                try {
                    mainObject = new JSONObject(response);
                    String email = mainObject.getString("EmailUser");
                    String usuarioUid = mainObject.getString("UidUser");
                    String nombre = mainObject.getString("NomUser");
                    String apellidos = mainObject.getString("ApeUser");
                    String fotoPerfil = mainObject.getString("FotoPerfilUser");
                    String biografia = mainObject.getString("BioUser");
                    String pais = mainObject.getString("PaisUser");
                    String ciudad = mainObject.getString("CiuUser");
                    String direccion = mainObject.getString("DirUser");
                    String tlfContacto = mainObject.getString("MetContactoUser");


                    //Toast.makeText(SuPerfilActivity.this, paisUser , Toast.LENGTH_LONG).show();
                    usuario.setEmail(email);
                    usuario.setUsuarioUid(usuarioUid);
                    usuario.setNombre(nombre);
                    usuario.setApellidos(apellidos);
                    usuario.setFotoPerfil(fotoPerfil);

                    usuario.setPais(pais);
                    usuario.setCiudad(ciudad);
                    usuario.setDireccion(direccion);
                    usuario.setTlfContacto(tlfContacto);

                    if (biografia.isEmpty()) {
                        usuario.setBiografia("Sin biografía por ahora \uD83D\uDE01");
                    } else {
                        usuario.setBiografia(biografia);
                    }

                    System.out.println("USUARIO DENTRO DEL TRYCATCH" + usuario);

                    txtPerfilNombre.setText(usuario.getNombre());
                    txtPerfilNombreCompleto.setText(usuario.getNombre() + " " + usuario.getApellidos());
                    txtPerfilBiografia.setText(usuario.getBiografia());
                    txtPerfilPais.setText(usuario.getPais());
                    txtPerfilCiudad.setText(usuario.getCiudad());
                    //txtPerfilDireccion.setText(usuario.getDireccion());
                    txtPerfilTlfContacto.setText(usuario.getTlfContacto());

                    if (usuario.getFotoPerfil().isEmpty()) {
                        Picasso.with(getApplicationContext())
                                .load(R.drawable.avatardefault)
                                .resize(370, 370)
                                .into(ivImagenPerfil);
                        Log.e("ISEMPTY", "SE PUSO LA FOTO DEL ISEMPTY POR DEFECTO");
                    } else {
                        Picasso.with(getApplicationContext())
                                .load(usuario.getFotoPerfil())
                                .placeholder(R.drawable.animacioncargar)
                                //.resize(370, 370)//da problemas con imagenes grandes
                                .into(ivImagenPerfil);
                        Log.e("PERFIL", "SE PUSO FOTO PERFIL");
                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SuPerfilActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UidUser", UidUser);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        //System.out.println("USUARIO AL FINAL DEL METODO" + usuario);


    }









































    /*

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
            */
/**
 * MySQL LOGIC
 *//*

            System.out.println("usuario a editar:-->\n" + usuario);
            ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com/index.php?mod=usuario&ope=update", uid, nombre, apellidos, direccion, ciudad, pais ,email, fotoperfil, biografia, tlfContacto);

            // FIN LO GUARDA EN DATABASE

            progressDialog.dismiss();

            Toast.makeText(SuPerfilActivity.this, "Perfil actualizado correctamente :)", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SuPerfilActivity.this, InterfazPrincipalActivity.class));


        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    String path = saveImage(bitmap);
                    Toast.makeText(SuPerfilActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(SuPerfilActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    fotoDePerfil = uri.toString();
                                    continuar = true;
                                    System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                                }
                            });


                        }
                    });
                    ///////////////////////////////////////////////

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SuPerfilActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SuPerfilActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(SuPerfilActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fotoDePerfil = uri.toString();
                                continuar = true;
                                System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
                            }
                        });


                    }
                });
                ///////////////////////////////////////////////
            } else{
                continuar = true;
                System.out.println("*\n*\n*\n*\n*CONTINUARimagenSubida = " + continuar + "*\n*\n*\n*\n*");
            }


        }
    }
*/


}
