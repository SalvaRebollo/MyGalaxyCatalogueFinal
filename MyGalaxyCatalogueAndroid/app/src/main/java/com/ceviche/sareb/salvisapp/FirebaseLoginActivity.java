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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseLoginActivity extends AppCompatActivity {
    private static final String TAG = FirebaseLoginActivity.class.getSimpleName();

    Button btnLogin, btnRegistrar, btnAnonimoLogin;
    TextView txtLoginEmail, txtLoginPassword;
    ProgressDialog progressDialog;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_login_activity);


        FirebaseAuth.getInstance().signOut();
        /*Vinculamos los items de la vista en xml con las variables Java*/
        /*Buttons/Botones*/
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnAnonimoLogin = findViewById(R.id.btnAnonimoLogin);  // ATAJO DEBUG
        /*TextView*/
        txtLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (EditText) findViewById(R.id.txtLoginPassword);

        progressDialog = new ProgressDialog(this);

        btnLogin.setText("LOGIN \uD83C\uDF00");
        btnRegistrar.setText("REGISTRARSE \uD83D\uDCC7");
        btnAnonimoLogin.setText("INGRESAR DE FORMA\nANÓNIMA \uD83D\uDC40");

        /*On Click Listener BOTON LOGIN*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearUsuario();
                // Para pasar de una actividad a otra
                //startActivity(new Intent(FirebaseLoginActivity.this, FirebaseRegisterActivity.class));
                // FIN para pasar de una actividad a otra
            }
        });

        /*On Click Listener BOTON REGISTRAR */
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para pasar de una actividad a otra
                startActivity(new Intent(FirebaseLoginActivity.this, FirebaseRegisterActivity.class));
                // FIN para pasar de una actividad a otra
            }
        });
        /*FIN On Click Listener*/

        /*On Click Listener BOTON LOGIN ANONIMO */
        btnAnonimoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearUsuarioAnonimo();
                // Para pasar de una actividad a otra
                //startActivity(new Intent(FirebaseLoginActivity.this, InterfazPrincipalActivity.class));
                // FIN para pasar de una actividad a otra
            }
        });
        /*FIN On Click Listener*/

        //inicializamos el objeto mAuth
        mAuth = FirebaseAuth.getInstance();

    } // FIN On Create

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // NO LOGEADO
            System.out.println("*\n*\n*\n*\n*EL USER ES NULL*\n*\n*\n*\n*");
        } else {
            // SI LOGEADO
            System.out.println("*\n*\n*\n*\n*EL USER NO ES NULL*\n*\n*\n*\n*");
            FirebaseAuth.getInstance().signOut();
        }


    }
    // [END on_start_check_user]


    public void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = txtLoginEmail.getText().toString().trim();
        final String password = txtLoginPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            progressDialog.setMessage("Realizando inicio de sesión con " + email + "...");
            progressDialog.show();

            //Loguear usuario
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Comprueba que se ha logueado correctamente y si es así hace una serie de tareas
                            if (task.isSuccessful()) {

                                Log.d(TAG, "signInWithEmail:success");

                                Toast.makeText(FirebaseLoginActivity.this, "Bienvenido: " + email, Toast.LENGTH_LONG).show();
                                //FirebaseUser user = mAuth.getCurrentUser();


                                // Para pasar de una actividad a otra
                                Intent intencion = new Intent(FirebaseLoginActivity.this, InterfazPrincipalActivity.class);
                                intencion.putExtra(InterfazPrincipalActivity.stringUser, email);
                                startActivity(intencion);
                                // FIN para pasar de una actividad a otra


                            } else {

                                Toast.makeText(FirebaseLoginActivity.this, "No se pudo iniciar sesión con las credenciales aportadas", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    } // FIN Loguear Usuario


    // Loguear usuario ANONIMO
    private void loguearUsuarioAnonimo() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            Toast.makeText(FirebaseLoginActivity.this, "Bienvenido ANÓNIMO ", Toast.LENGTH_LONG).show();

                            currentUser = mAuth.getCurrentUser();


                            if (!currentUser.isEmailVerified()) {
                                Log.e("es anonimo? - EL USUARIO ES ANONIMO", "¿isAnonimous()? - EL USUARIO ES ANONIMO");
                            }
                            // Para pasar de una actividad a otra
                            Intent intencion = new Intent(FirebaseLoginActivity.this, InterfazPrincipalActivity.class);
                            //intencion.putExtra(InterfazPrincipalActivity.stringUser, currentUser);
                            startActivity(intencion);
                            // FIN para pasar de una actividad a otra
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(FirebaseLoginActivity.this, "Error. No se ha podido acceder de forma anóníma", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    } // FIN Loguear usuario ANONIMO


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FirebaseLoginActivity.this, FirebaseLoginActivity.class);

        startActivity(intent);

    } //OnBackPressed*/


}
