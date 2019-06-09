package com.ceviche.sareb.salvisapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ceviche.sareb.salvisapp.Adaptadores.AdaptadorListMisProductos;
import com.ceviche.sareb.salvisapp.Clases.UsuarioProductosItemListClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MisProductosActivity extends AppCompatActivity {

    String nombre, fotoperfil;

    String titulo, productoid, usuarioCreadorUid, descripcion, precio, categoria, estado, imagen, nombreUsuarioCreador, fotoUsuarioCreador;

    ArrayList<UsuarioProductosItemListClass> misProductosLista = new ArrayList<>();

    RecyclerView recyclerMisProductos;
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UsuarioProductosItemListClass miProductoi = snapshot.getValue(UsuarioProductosItemListClass.class);

                    miProductoi.setFotoUsuarioCreador(fotoperfil);
                    misProductosLista.add(miProductoi); //LISTA CON TODOS LOS PRODUCTOS GUARDADOS

                    String titulo = miProductoi.getTitulo();
                    String productoid = miProductoi.getProductoid();
                    String usuarioCreadorUid = miProductoi.getUsuarioCreadorUid();
                    String descripcion = miProductoi.getDescripcion();
                    String precio = miProductoi.getPrecio();
                    String categoria = miProductoi.getCategoria();
                    String estado = miProductoi.getEstado();
                    String imagen = miProductoi.getimagen();

                    String nombreUsuarioCreador = miProductoi.getNombreUsuarioCreador();
                    String fotoUsuarioCreador = miProductoi.getFotoUsuarioCreador();



                    /*Log.e("[[[[[[[[[[[[[[titulo]]]]]]]]]]]]]", titulo);
                    Log.e("[[[[[[[[[[[[[[productoid]]]]]]]]]]]]]", productoid);
                    Log.e("[[[[[[[[[[[[[[usuarioCreadorUid]]]]]]]]]]]]]", usuarioCreadorUid);
                    Log.e("[[[[[[[[[[[[[[descripcion]]]]]]]]]]]]]", descripcion);
                    Log.e("[[[[[[[[[[[[[[precio]]]]]]]]]]]]]", precio);
                    Log.e("[[[[[[[[[[[[[[categoria]]]]]]]]]]]]]", categoria);
                    Log.e("[[[[[[[[[[[[[[estado]]]]]]]]]]]]]", estado);
                    Log.e("[[[[[[[[[[[[[[imagen]]]]]]]]]]]]]", imagen);
                    Log.e("[[[[[[[[[[[[[[nombreUsuarioCreador]]]]]]]]]]]]]", nombreUsuarioCreador);
                    Log.e("[[[[[[[[[[[[[[fotoUsuarioCreador]]]]]]]]]]]]]", fotoUsuarioCreador);


                    Log.e("[[[     --->INFO DE PRODUCTOS REALIZADA<---     ]]]", "realizada");
                    */
                }

                for (UsuarioProductosItemListClass productoItemClass : misProductosLista) {

                    titulo = productoItemClass.getTitulo();
                    System.out.println("*\n*\n[titulo]" + titulo);

                    productoid = productoItemClass.getProductoid();
                    System.out.println("*\n*\n[productoid]" + productoid);

                    usuarioCreadorUid = productoItemClass.getUsuarioCreadorUid();
                    System.out.println("*\n*\n[usuarioCreadorUid]" + usuarioCreadorUid);

                    descripcion = productoItemClass.getDescripcion();
                    System.out.println("*\n*\n[descripcion]" + descripcion);

                    precio = productoItemClass.getPrecio();
                    System.out.println("*\n*\n[precio]" + precio);

                    categoria = productoItemClass.getCategoria();
                    System.out.println("*\n*\n[categoria]" + categoria);

                    estado = productoItemClass.getEstado();
                    System.out.println("*\n*\n[estado]" + estado);

                    imagen = productoItemClass.getimagen();
                    System.out.println("*\n*\n[imagen]" + imagen);

                    nombreUsuarioCreador = productoItemClass.getNombreUsuarioCreador();
                    System.out.println("*\n*\n[nombreUsuarioCreador]" + nombreUsuarioCreador);

                    fotoUsuarioCreador = productoItemClass.getFotoUsuarioCreador();
                    System.out.println("*\n*\n[fotoUsuarioCreador]" + fotoUsuarioCreador);

                    System.out.println("************************************************" +
                            "\n************************************************");

                }

                // AdaptadorListMisProductos adaptadorListMisProductos = new AdaptadorListMisProductos(UsuarioProductosItemListClass.class, R.layout.item_list_productos,
                //       AdaptadorListMisProductos.ViewHolderMisProductos.class, misProductosLista, MisProductosActivity.this);


                recyclerMisProductos.setLayoutManager(new GridLayoutManager(MisProductosActivity.this, 1,
                        LinearLayoutManager.VERTICAL, false));

                AdaptadorListMisProductos adapter = new AdaptadorListMisProductos(misProductosLista, MisProductosActivity.this);

                recyclerMisProductos.setAdapter(adapter);

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_productos);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("MyGalaxyCatalogue");
        toolbar.setSubtitle("Tus productos y servicios subidos a la comunidad");

        recyclerMisProductos = findViewById(R.id.recyclerMisProductos);

        Intent outputInterfazPrincipalDatosUsu = getIntent();
        Bundle datos = outputInterfazPrincipalDatosUsu.getExtras();

        if (datos != null) {
            nombre = (String) datos.get("nombre");
            fotoperfil = (String) datos.get("fotoperfil");
        }

        if (fotoperfil.isEmpty()) {
            startActivity(new Intent(MisProductosActivity.this, EmpecemosActivity.class));
        }

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user.isAnonymous()) {
                    // Para pasar de una actividad a otra

                    // Para pasar de una actividad a otra
                    Intent intent = new Intent(MisProductosActivity.this, AnadirProductoAFirebaseActivity.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("fotoperfil", fotoperfil);
                    startActivity(intent);
                    finish();
                    // FIN para pasar de una actividad a otra
                } else {
                    Toast.makeText(MisProductosActivity.this, "Debes registrarte para poder añadir productos.", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Consulta SELECT * FROM Productos WHERE usuarioCreadorUid=user.getUid()
        Query query = FirebaseDatabase.getInstance().getReference("Productos").child("ProductosRegistrados")
                .orderByChild("usuarioCreadorUid")
                .equalTo(user.getUid());

        query.addValueEventListener(valueEventListener);


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(MisProductosActivity.this, MenuNavegacion.class);
        startActivity(intent);
        finish();


    }

}
