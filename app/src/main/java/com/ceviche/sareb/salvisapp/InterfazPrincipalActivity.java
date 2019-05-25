package com.ceviche.sareb.salvisapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ceviche.sareb.salvisapp.Adaptadores.AdaptadorFirebaseProductos;
import com.ceviche.sareb.salvisapp.Clases.UsuarioProductosItemListClass;
import com.ceviche.sareb.salvisapp.Clases.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InterfazPrincipalActivity extends AppCompatActivity {
    // Esta variable podrá ser alimentada por el FirebaseLoginActivity si asi lo queremos
    public static final String stringUser = "names";
    private static final String DEBUG_TAG = "Gestures";
    RecyclerView recyclerView;

    //TextView txtUser, txtFondo;
    //Button btnAtajoAnadirProductos, btnCerrarSesion;
    //private ImageView imgproducto;
    FirebaseUser user;
    String nombre;
    String fotoperfil;
    Boolean continuar;
    /*ESTO ES PARA VER SI SE CONSIGUE LEER LOS DATOS DE FIREBASE Y SE IMPRIMEN EN CONSOLA*/
    // INICIO Event Listener para leer de la base de de Firebase
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Usuarios elemento = dataSnapshot.getValue(Usuarios.class);

            nombre = elemento.getNombre();
            String apellidos = elemento.getApellidos();
            String pais = elemento.getPais();
            String ciudad = elemento.getCiudad();
            fotoperfil = elemento.getFotoPerfil();
            String uid = elemento.getUsuarioUid();

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
            getSupportActionBar().setSubtitle("Lista de Productos");
            getSupportActionBar().setDisplayUseLogoEnabled(true);


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


            if (continuar == false && fotoperfil.isEmpty()) {
                startActivity(new Intent(InterfazPrincipalActivity.this, EmpecemosActivityHome.class));
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // FIN EventListener
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfaz_principal_activity);

        // Definimos opciones de Display
        //getSupportActionBar().setTitle("MyGalaxyCatalogue");


        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        // mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        // mDetector.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener) this);

        recyclerView = findViewById(R.id.recyclerId);


        DatabaseReference referenciaDatabaseRecycler = FirebaseDatabase.getInstance().getReference("Productos").child("ProductosRegistrados");

        AdaptadorFirebaseProductos adaptadorFirebaseProductos = new AdaptadorFirebaseProductos(UsuarioProductosItemListClass.class, R.layout.item_list_productos,
                AdaptadorFirebaseProductos.ViewHolderProductos.class, referenciaDatabaseRecycler, InterfazPrincipalActivity.this);

        recyclerView.setAdapter(adaptadorFirebaseProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(InterfazPrincipalActivity.this, 1,
                LinearLayoutManager.VERTICAL, false));

        //Declaramos un objeto FirebaseUser llamado user para obtener de el por ejemplo su Uid a posteriori con diferentes métodos. Tambien vamos a configuar el acceso anónimo sin usuario registrado
        user = FirebaseAuth.getInstance().getCurrentUser();
        /*
         *     * *//*Vinculamos los items de la vista en xml con las variables Java*//*
  *     btnAtajoAnadirProductos=(Button) findViewById(R.id.atajoAnadirProductos);  // ATAJO DEBUG
  *     btnCerrarSesion = (Button) findViewById(R.id.btnLogOut);
  *     txtFondo = (TextView) findViewById(R.id.txtFondo);
  *
  *     //String correo = getIntent().getStringExtra("correos"); //Variable alimentada del LoginActivity
  *
  *     //imgproducto = (ImageView) findViewById(R.id.imgproducto);////
  *     // Imagen de producto sin foto que saldrá por defecto al iniciar la actividad de añadir productos
  *     //imgproducto.setImageBitmap(decodeStringBase64toByte(getResources().getString(R.string.img_defecto)));

        /*Listener BOTON AÑADIR PRODUCTO DEBUG
        btnAtajoAnadirProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.isAnonymous()) {
                    // Para pasar de una actividad a otra
                    startActivity(new Intent(InterfazPrincipalActivity.this, AnadirProductoAFirebaseActivity.class));
                    // FIN para pasar de una actividad a otra
                } else{
                    Toast.makeText(InterfazPrincipalActivity.this,"Debes registrarte para poder añadir productos.",Toast.LENGTH_LONG).show();
                }

            }
        });
        /*FIN On Click Listener*/

        /*Listener BOTON CERRAR SESION DEBUG
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(InterfazPrincipalActivity.this,"Has cerrado sesión.",Toast.LENGTH_LONG).show();
                txtFondo.setText("No estás logeado");
            }
        });
        /*FIN On Click Listener*/


        if (!user.isAnonymous()) { //SI ESTÁ REGISTRADO QUE HAGA ESTO
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
            //txtFondo.setText("Hola humano.");
            getSupportActionBar().setTitle("Anónimo");
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
            getSupportActionBar().setSubtitle("Lista de Productos");
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }


        /////////// UNA VEZ HECHAS TODAS LAS CONEXIONES/LISTENERS, EMPIEZA LO VISUAL A PARTIR DE ABAJO

        //Pongo el titulo a la actividad y configuro toolbar
        ////getSupportActionBar().setTitle(nombre);
        //getSupportActionBar().setTitle("MyGalaxyCatalogue");
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_TITLE);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher_round) ;
        //getSupportActionBar().setSubtitle("Lista de Productos");
        //getSupportActionBar().setDisplayUseLogoEnabled(true);


    } // Fin de OnCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Obtenemos el inflador para inflar el menú
        getMenuInflater().inflate(R.menu.interfaz_principal_opciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Comprobar qué opción ha elegido el usuario
        switch (item.getItemId()) {

            case R.id.opcMenuPrincipal:
                Toast.makeText(this, "Has elegido Menú Principal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InterfazPrincipalActivity.this, MenuNavegacion.class);
               /* intent.putExtra("nombre",nombre);
                intent.putExtra("fotoperfil",fotoperfil);*/
                intent.putExtra("continuar", continuar);
                startActivity(intent);
                finish();

                break;
            case R.id.opcPerfil:
                Toast.makeText(this, "Has elegido PERFIL", Toast.LENGTH_SHORT).show();

                if (!user.isAnonymous()) {
                    // Para pasar de una actividad a otra
                    startActivity(new Intent(InterfazPrincipalActivity.this, MiPerfilActivity.class));
                    // FIN para pasar de una actividad a otra
                    finish();
                } else {
                    Toast.makeText(InterfazPrincipalActivity.this, "Debes registrarte para poder tener perfil.", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.opcCerrarSesion:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(InterfazPrincipalActivity.this, "Has cerrado sesión.", Toast.LENGTH_LONG).show();

                Intent intent2 = new Intent(InterfazPrincipalActivity.this, FirebaseLoginActivity.class);
                startActivity(intent2);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    /*

    /////[EXPERIMENTAL] EventListener Secundario
    ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Productos elemento = dataSnapshot.getValue(Productos.class);
                String tituloProducto = elemento.getTitulo();
                String descripcion = elemento.getDescripcion();
                String precio = elemento.getPrecio();
                String imagen = elemento.getImagen();
                String productoId = elemento.getProductoid();

                Log.e("[[[[[[[[[[[[[[tituloProducto]]]]]]]]]]]]]",tituloProducto);
                Log.e("[[[[[[[[[[[[[[descripcion]]]]]]]]]]]]]",descripcion);
                Log.e("[[[[[[[[[[[[[[precio]]]]]]]]]]]]]",precio);
                Log.e("[[[[[[[[[[[[[[imagen]]]]]]]]]]]]]",imagen);
                Log.e("[[[[[[[[[[[[[[productoId]]]]]]]]]]]]]",productoId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  // FIN EXPERIMENTAL EventListener
    */

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(InterfazPrincipalActivity.this, MenuNavegacion.class);
               /* intent.putExtra("nombre",nombre);
                intent.putExtra("fotoperfil",fotoperfil);*/
        intent.putExtra("continuar", continuar);
        startActivity(intent);
        finish();

    }


    private Bitmap decodeStringBase64toByte(String encodedImage) {
        byte[] decodeString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);


        return decoded;
    }


    /*@Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        System.out.println("ME HAS PULSADO LEEEEEEEEEEEENTO");
        Log.e("ME HAS PULSADO LEEEEEEEEEEEENTO",e.toString());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/
} //FIN Documento
