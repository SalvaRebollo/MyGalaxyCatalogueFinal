package com.ceviche.sareb.salvisapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class EmpecemosActivity extends AppCompatActivity {

    ImageView gifEmpecemos;
    ScrollView scrollView;
    Button btnAvatar, btnCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empecemos);
        scrollView = findViewById(R.id.scrollviewempecemos);
        gifEmpecemos = findViewById(R.id.gifEmpecemos);
        btnAvatar = findViewById(R.id.btnAvatar);
        btnCancelar = findViewById(R.id.btnCancelar);


        Glide.with(getApplicationContext())
                .asGif()
                .load(R.drawable.empecemos)
                .placeholder(R.drawable.avatardefault)
                .into(gifEmpecemos);

        btnAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmpecemosActivity.this, MiPerfilActivity.class));
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //continuar = true;
                Intent intent = new Intent(EmpecemosActivity.this, MenuNavegacion.class);
                intent.putExtra("continuar", true);
                startActivity(intent);
            }
        });

        Toast.makeText(EmpecemosActivity.this, "Debes tener un avatar para subir tus productos y servicios.", Toast.LENGTH_SHORT).show();


    }//OnCreate


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(EmpecemosActivity.this, MenuNavegacion.class);
        intent.putExtra("continuar", true);
        startActivity(intent);


    }

}

