package com.ceviche.sareb.salvisapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ceviche.sareb.salvisapp.Clases.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MenuNavegacion extends AppCompatActivity {

    CardView menuInicio, menuMisProductos, menuMiPerfil, menuAcercaApp, menuCerrarSesion;
    ImageView botonHome, botonPerfil, botonMisProductos;
    String nombre, apellidos, pais, ciudad, fotoperfil, uid;
    Boolean continuar = true;
    /*ESTO ES PARA VER SI SE CONSIGUE LEER LOS DATOS DE FIREBASE Y SE IMPRIMEN EN CONSOLA*/
    // INICIO Event Listener para leer de la base de de Firebase
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Usuarios elemento = dataSnapshot.getValue(Usuarios.class);

            nombre = elemento.getNombre();
            apellidos = elemento.getApellidos();
            pais = elemento.getPais();
            ciudad = elemento.getCiudad();
            fotoperfil = elemento.getFotoPerfil();
            uid = elemento.getUsuarioUid();

            Log.e("[[[[[[[[[[[[[[nombreUsuario]]]]]]]]]]]]]", nombre);
            Log.e("[[[[[[[[[[[[[[apellidos]]]]]]]]]]]]]", apellidos);
            Log.e("[[[[[[[[[[[[[[pais]]]]]]]]]]]]]", pais);
            Log.e("[[[[[[[[[[[[[[ciudad]]]]]]]]]]]]]", ciudad);
            Log.e("[[[[[[[[[[[[[[fotoperfil]]]]]]]]]]]]]", fotoperfil);
            Log.e("[[[[[[[[[[[[[[UID]]]]]]]]]]]]]", uid);
            Log.e("[[[     --->INFO DE USUARIO REALIZADA<---     ]]]", "realizada");
            //txtFondo.setText(nombre+ " " + apellidos);

            getSupportActionBar().setTitle(nombre);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
            getSupportActionBar().setSubtitle("Menu Principal");
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            if (fotoperfil.isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.avatardefault)
                        .placeholder(R.drawable.avatardefault)
                        .into(botonPerfil);
                botonPerfil.setPadding(5, 5, 5, 5);
            } else {
                Picasso.with(getApplicationContext())
                        .load(fotoperfil)
                        .placeholder(R.drawable.avatardefault)
                        .into(botonPerfil);
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // FIN EventListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_menu_navegacionanonimo);*/

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (!user.isAnonymous()) {
            setContentView(R.layout.activity_menu_navegacion);
        } else {
            setContentView(R.layout.activity_menu_navegacionanonimo);
        }

        /*Vinculamos los items de la vista en xml con las variables Java*/
        menuInicio = findViewById(R.id.menuInicio);
        menuMisProductos = findViewById(R.id.menuMisProductos);
        menuMiPerfil = findViewById(R.id.menuMiPerfil);
        menuAcercaApp = findViewById(R.id.menuAcercaApp);
        menuCerrarSesion = findViewById(R.id.menuCerrarSesion);

        botonPerfil = findViewById(R.id.botonPerfil);
        botonMisProductos = findViewById(R.id.botonMisProductos);


        if (!user.isAnonymous()) { //SI ESTÁ REGISTRADO QUE HAGA ESTO

            Glide.with(getApplicationContext())
                    .asGif()
                    .load(R.drawable.gifproductos)
                    .placeholder(R.drawable.avatardefault)
                    .into(botonMisProductos);

            /* Consulta Firebase: SELECT * FROM Usuarios/UsuariosRegistrados WHERE ChildUid = user.getUid();Referencia a la base de datos a la tabla de nuestros usuarios*/
            DatabaseReference usuariosFirebase = FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados").child(user.getUid());
            //////// Lo añado al Listener "valueEventListener" creado debajo del OnCreate. Con addListenerForSingleValueEvent lo que hago es obtener los datos del listener 1 VEZ y no estoy continuamente a la escucha para actualizar el dato
            usuariosFirebase.addValueEventListener(valueEventListener);

            //Pongo el nombre del usuario como titulo

            /* Consulta Firebase: SELECT * FROM Productos/ProductosRegistrados*/
            //DatabaseReference productosFirebase = FirebaseDatabase.getInstance().getReference("Productos").child("ProductosRegistrados");
            // Lo añado al Listener "valueEventListener" creado debajo del OnCreate.
            //productosFirebase.addListenerForSingleValueEvent(valueEventListener);

        } else {
            Glide.with(getApplicationContext())
                    .asGif()
                    .load(R.drawable.gifproductosanom)
                    .placeholder(R.drawable.avatardefault)
                    .into(botonMisProductos);

            //txtFondo.setText("Hola humano.");
            getSupportActionBar().setTitle("Anónimo");
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
            getSupportActionBar().setSubtitle("Menú Principal");
            getSupportActionBar().setDisplayUseLogoEnabled(true);

        }












        /*Listeners */
        menuInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (fotoperfil == null || fotoperfil.isEmpty()) {

                    //CONTINUAR O NO ELIGIENDO AVATAR
                    continuar = false;
                    Intent outputEmpecemosDatosContinuar = getIntent();
                    Bundle datos = outputEmpecemosDatosContinuar.getExtras();

                    if (datos != null) {
                        continuar = (Boolean) datos.get("continuar");


                        if (continuar == null) {
                            continuar = false;
                        }

                        System.out.println("CONTINUAR?:" + continuar);

                    } else {
                        System.out.println("CONTINUAR?:" + continuar);
                    }


                }

                Intent intent = new Intent(MenuNavegacion.this, InterfazPrincipalActivity.class);
                intent.putExtra("continuar", continuar);

                startActivity(intent);
                finish();


            }
        });

        menuMisProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuNavegacion.this, "Has pulsado Mis Productos", Toast.LENGTH_LONG).show();


                if (!user.isAnonymous()) {
                    // Para pasar de una actividad a otra
                    Intent intent = new Intent(MenuNavegacion.this, MisProductosActivity.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("fotoperfil", fotoperfil);
                    startActivity(intent);
                    finish();

                    // FIN para pasar de una actividad a otra
                } else {
                    Toast.makeText(MenuNavegacion.this, "Debes registrarte para poder añadir productos.", Toast.LENGTH_LONG).show();
                }

            }
        });

        menuMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuNavegacion.this, "Has pulsado Mi Perfil", Toast.LENGTH_LONG).show();

                if (!user.isAnonymous()) {
                    // Para pasar de una actividad a otra
                    startActivity(new Intent(MenuNavegacion.this, MiPerfilActivity.class));
                    // FIN para pasar de una actividad a otra
                } else {
                    Toast.makeText(MenuNavegacion.this, "Debes registrarte para poder tener perfil.", Toast.LENGTH_LONG).show();
                }

            }
        });

        menuAcercaApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuNavegacion.this, NovedadesDesarrollo.class);
                startActivity(intent);
                Toast.makeText(MenuNavegacion.this, "Acerca de MyGalaxyCatalogue, creado por SalviRB", Toast.LENGTH_LONG).show();
            }
        });

        menuCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuNavegacion.this, "Has cerrado sesión.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MenuNavegacion.this, FirebaseLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /*FIN On Click Listener*/


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(MenuNavegacion.this, InterfazPrincipalActivity.class);
        startActivity(intent);
        finish();


    }


}
