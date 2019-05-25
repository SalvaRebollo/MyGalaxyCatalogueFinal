package com.ceviche.sareb.salvisapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceviche.sareb.salvisapp.Clases.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class VerProducto extends AppCompatActivity {

    String titulo, descripcion, precio, categoria, estado, imagenproducto, imagenusuario, usuarioCreadorUid;
    AppBarLayout encabezadoConFoto;
    String email, nombre, apellidos, biografia, pais, ciudad, direccion, fotoDePerfil, uid, tlfContacto;
    TextView tvTitulo, tvDescripcion, tvPrecio, tvEstado, tvCategoria, tvUsuario, tvEmail, tvMetodoContacto;
    ImageView ivFotoProducto, ivFotoUsuario;
    LinearLayout ilMyProfileContainer;

    ValueEventListener valueEventListener = new ValueEventListener() {
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

            Log.e("[[[[[[[[[[[[[[nombreUsuario]]]]]]]]]]]]]", nombre);
            Log.e("[[[[[[[[[[[[[[apellidos]]]]]]]]]]]]]", apellidos);
            Log.e("[[[[[[[[[[[[[[pais]]]]]]]]]]]]]", pais);
            Log.e("[[[[[[[[[[[[[[ciudad]]]]]]]]]]]]]", ciudad);
            Log.e("[[[[[[[[[[[[[[fotoperfil]]]]]]]]]]]]]", fotoDePerfil);
            Log.e("[[[[[[[[[[[[[[UID]]]]]]]]]]]]]", uid);
            Log.e("[[[[[[[[[[[[[[email]]]]]]]]]]]]]", email);
            Log.e("[[[[[[[[[[[[[[direccion]]]]]]]]]]]]]", direccion);
            Log.e("[[[[[[[[[[[[[[tlfContacto]]]]]]]]]]]]]", tlfContacto);
            Log.e("[[[     --->INFO DE USUARIO REALIZADA<---     ]]]", "realizada");


            if (fotoDePerfil.isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.avatardefault)
                        .resize(370, 370)
                        .into(ivFotoUsuario);
            } else {
                Picasso.with(getApplicationContext())
                        .load(fotoDePerfil)
                        .placeholder(R.drawable.avatardefault)
                        .into(ivFotoUsuario);
            }

            tvUsuario.setText(nombre);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // FIN EventListener
    private DatabaseReference usuariosDB;
    ///////////
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(VerProducto.this, InterfazPrincipalActivity.class));
                    break;
                case R.id.navigation_contactar:
                    Intent intent = new Intent(VerProducto.this, SuPerfilActivity.class);
                    intent.putExtra("usuarioCreadorUid", usuarioCreadorUid);
                    startActivity(intent);
                    finish();

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_producto_activity);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tvTitulo = findViewById(R.id.ilTextoTitulo);
        tvDescripcion = findViewById(R.id.ilTextoDescripcion);
        tvPrecio = findViewById(R.id.ilTextoPrecio);
        tvEstado = findViewById(R.id.ilTextoEstado);
        tvCategoria = findViewById(R.id.ilTextoCategoria);
        tvUsuario = findViewById(R.id.ilTextoUsuario);

        tvEmail = findViewById(R.id.ilTextoEmail);
        tvMetodoContacto = findViewById(R.id.ilTextoMetodoContacto);

        ivFotoProducto = findViewById(R.id.ilImagenProducto);
        ivFotoUsuario = findViewById(R.id.ilImagenUsuario);

        ilMyProfileContainer = findViewById(R.id.ilMyProfileContainer);
        //tvDescripcion.setText("111DescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcionDescripcion222");




        Intent outputInterfazPrincipalDatosUsu = getIntent();
        Bundle datos = outputInterfazPrincipalDatosUsu.getExtras();

        if (datos != null) {
            titulo = (String) datos.get("titulo");
            descripcion = (String) datos.get("descripcion");
            precio = (String) datos.get("precio");
            categoria = (String) datos.get("categoria");
            estado = (String) datos.get("estado");
            imagenproducto = (String) datos.get("imagenproducto");
            imagenusuario = (String) datos.get("imagenusuario");
            usuarioCreadorUid = (String) datos.get("usuarioCreadorUid");

            tvTitulo.setText(titulo);
            tvDescripcion.setText(Html.fromHtml("<b>Descripción: </b>" + descripcion));
            tvPrecio.setText(Html.fromHtml("<b>Precio: </b>" + precio + "€"));
            tvCategoria.setText(Html.fromHtml("<b>Categoría: </b>\n" + categoria));
            tvEstado.setText(Html.fromHtml("<b>Estado: </b>" + estado));

            if (imagenproducto.isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.productosinfoto)
                        .into(ivFotoProducto);
            } else {
                Picasso.with(getApplicationContext())
                        .load(imagenproducto)
                        .placeholder(R.drawable.productosinfoto)
                        .into(ivFotoProducto);
            }

            if (imagenusuario.isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.avatardefault)
                        .resize(370, 370)
                        .into(ivFotoUsuario);
            } else {
                Picasso.with(getApplicationContext())
                        .load(imagenusuario)
                        .placeholder(R.drawable.avatardefault)
                        .into(ivFotoUsuario);
            }

            ilMyProfileContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VerProducto.this, SuPerfilActivity.class);
                    intent.putExtra("usuarioCreadorUid", usuarioCreadorUid);
                    startActivity(intent);
                    finish();
                }
            });

            ivFotoProducto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    registerForContextMenu(ivFotoProducto);

                    ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com/");

                    return false;
                }
            });


        } else {
            tvTitulo.setText("No se ha podido cargar los datos");
            tvDescripcion.setText(Html.fromHtml("<b>Descripción: </b>?"));
            tvPrecio.setText(Html.fromHtml("<b>Precio: </b>?"));
            tvCategoria.setText(Html.fromHtml("<b>Categoría: </b>?"));
            tvEstado.setText(Html.fromHtml("<b>Estado: </b>?"));

            Picasso.with(getApplicationContext())
                    .load(R.drawable.productosinfoto)
                    .into(ivFotoProducto);

            Picasso.with(getApplicationContext())
                    .load(R.drawable.avatardefault)
                    .resize(370, 370)
                    .into(ivFotoUsuario);
        }

        System.out.println("*\n*\n*\n*TITULO: " + titulo
                + "\nDescripcion:" + descripcion
                + "\nDescripcion:" + descripcion
                + "\nprecio:" + precio
                + "\ncategoria:" + categoria
                + "\nestado:" + estado
                + "\nimagenproducto:" + imagenproducto
                + "\nimagenusuario:" + imagenusuario
                + "\nusuarioCreadorUid:" + usuarioCreadorUid
                + "*\n*\n*");


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usuariosDB = FirebaseDatabase.getInstance().getReference("Usuarios");
        DatabaseReference usuariosFirebase = FirebaseDatabase.getInstance().getReference("Usuarios").child("UsuariosRegistrados").child(usuarioCreadorUid);
        usuariosFirebase.addValueEventListener(valueEventListener);


    } //OnCreate

    @Override
    public void onBackPressed() {
        //super.onBackPressed();


        Intent intent = new Intent(VerProducto.this, InterfazPrincipalActivity.class);

        startActivity(intent);


    } //OnBackPressed*/

    /**
     * MySQL LOGIC
     */
    private void ejecutarServicio(String URL) {


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
                parametros.put("IdProduct", "10");
                parametros.put("UidUser", "5");
                parametros.put("TituloProduct", "HEEEY");
                parametros.put("DescProduct", "Hola");
                parametros.put("PrecioProduct", "que");
                parametros.put("ImgProduct", "tal");
                parametros.put("CategProduct", "estas");
                parametros.put("EstadoProduct", "amigo");


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Elige una opción");
        getMenuInflater().inflate(R.menu.ver_perfil_opcionlongpress, menu);
    }


    /********/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcPerfil:
                //Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerProducto.this, SuPerfilActivity.class);
                intent.putExtra("usuarioCreadorUid", usuarioCreadorUid);
                startActivity(intent);
                finish();
                return true;
            case R.id.opcMetodoContacto:
                //Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
                ScrollView scroll = findViewById(R.id.scrollproducto);
                scroll.fullScroll(View.FOCUS_DOWN);
                tvEmail.setText(Html.fromHtml("<b>Email: </b>" + email));

                if (tlfContacto.isEmpty()) {
                    tlfContacto = "Sin métodos de contacto.";
                }

                tvMetodoContacto.setText(Html.fromHtml("<b>Método(s) de contacto: </b>" + tlfContacto));

                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }


}





