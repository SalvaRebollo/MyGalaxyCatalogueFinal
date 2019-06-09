package com.ceviche.sareb.salvisapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceviche.sareb.salvisapp.Clases.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseRegisterActivity extends AppCompatActivity {
    private static final String TAG = FirebaseRegisterActivity.class.getSimpleName();
    private EditText txtRegisterTlfContacto, txtRegisterEmail, txtRegisterPassword, txtRegisterNombre, txtRegisterApellidos, txtRegisterPais, txtRegisterCiudad, txtRegisterDireccion;
    private Button btnRegistro;
    private ProgressDialog progressDialog;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    //Referencia a la base de datos
    private DatabaseReference usuariosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_register_activity);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Obtenemos la instancia, en el parentesis va la clase Java "Usuarios.java" que tenemos creada con los mismos atributos como variables
        usuariosDB = FirebaseDatabase.getInstance().getReference("Usuarios");

        //Referenciamos los views
        /*Buttons/Botones*/
        btnRegistro = findViewById(R.id.btnRegistrar);
        /*EditText*/
        txtRegisterEmail = findViewById(R.id.txtRegisterEmail);
        txtRegisterPassword = findViewById(R.id.txtRegisterPassword);
        txtRegisterNombre = findViewById(R.id.txtRegisterNombre);
        txtRegisterApellidos = findViewById(R.id.txtRegisterApellidos);
        txtRegisterPais = findViewById(R.id.txtRegisterPais);
        txtRegisterCiudad = findViewById(R.id.txtRegisterCiudad);
        txtRegisterDireccion = findViewById(R.id.txtRegisterDireccion);
        txtRegisterTlfContacto = findViewById(R.id.txtRegisterTlfContacto);

        progressDialog = new ProgressDialog(this);

        /*On Click Listener BOTON REGISTRAR */
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarUsuario();
                // Mensaje
                //Toast.makeText(FirebaseRegisterActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                // Para pasar de una actividad a otra
                //        startActivity(new Intent(FirebaseRegisterActivity.this, FirebaseLoginActivity.class));
                // FIN para pasar de una actividad a otra
            }
        });
        /*FIN On Click Listener*/


    }

    /*Registra el usuario e inicia sesion con el*/
    public void registrarUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = txtRegisterEmail.getText().toString().trim();
        final String password = txtRegisterPassword.getText().toString().trim();
        final String tlfContacto = txtRegisterTlfContacto.getText().toString().trim();
        final String nombre = txtRegisterNombre.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email de contacto", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Debes ingresar el nombre con el que te verás públicamente", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(tlfContacto)) {
            Toast.makeText(this, "Debes introducir un teléfono de contacto, en caso de no necesitarlo, introducir cualquier otro método de contacto.", Toast.LENGTH_LONG).show();
            return;
        }


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(tlfContacto) && !TextUtils.isEmpty(nombre)) {

            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();


            //Registrar nuevo usuario
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {

                                Toast.makeText(FirebaseRegisterActivity.this, "Se ha registrado el usuario con el email: " + email, Toast.LENGTH_LONG).show();

                                iniciarSesion(email, password);

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) { // Si se presenta una colision
                                    Toast.makeText(FirebaseRegisterActivity.this, "Error: Ya existe un usuario con el email: " + email, Toast.LENGTH_LONG).show();
                                }
                                Toast.makeText(FirebaseRegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }


    }

    private void iniciarSesion(final String email, String password) {

        progressDialog.setMessage("Realizando inicio de sesión con " + email + "...");
        progressDialog.show();

        //Loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            // LO GUARDA EN DATABASE TAMBIEN JUNTO CON SUS DEMAS ATRIBUTOS QUE EN EL FUTURO SE PODRAN LLENAR DESDE LA APP
                            String uid = firebaseAuth.getUid(); //Genera la key y la guarda en la id ; usuarioUid

                            //Obtenemos el email y la contraseña desde las cajas de texto
                            final String nombre = txtRegisterNombre.getText().toString().trim();
                            final String apellidos = txtRegisterApellidos.getText().toString().trim();
                            final String pais = txtRegisterPais.getText().toString().trim();
                            final String ciudad = txtRegisterCiudad.getText().toString().trim();
                            final String direccion = txtRegisterDireccion.getText().toString().trim();
                            final String tlfContacto = txtRegisterTlfContacto.getText().toString().trim();

                            // Firebase LOGIC
                            Usuarios usuario = new Usuarios(email, uid, nombre, apellidos, pais, ciudad, direccion, tlfContacto);
                            usuariosDB.child("UsuariosRegistrados").child(uid).setValue(usuario);

                            /**
                             * MySQL LOGIC
                             */
                            System.out.println("usuario a añadir:-->\n" + usuario);
                            ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com/index.php?mod=usuario&ope=insert", uid, nombre, apellidos, direccion, ciudad, pais, email, "foto", "bio", tlfContacto);

                            // FIN LO GUARDA EN DATABASE

                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(FirebaseRegisterActivity.this, "Bienvenido: " + email, Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            // Para pasar de una actividad a otra
                            Intent intencion = new Intent(FirebaseRegisterActivity.this, InterfazPrincipalActivity.class);
                            //intencion.putExtra(InterfazPrincipalActivity.userLoginActivity, user);
                            startActivity(intencion);
                            // FIN para pasar de una actividad a otra
                        } else {

                            Toast.makeText(FirebaseRegisterActivity.this, "No se pudo iniciar sesión, revise su conexion a internet y vuelva a intentarlo", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
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


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}

