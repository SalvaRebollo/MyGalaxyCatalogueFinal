package com.ceviche.sareb.salvisapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

public class EmpecemosActivityHome extends AppCompatActivity {

    ImageView gifEmpecemos;
    ScrollView scrollView;
    Button btnAvatar, btnOmitir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empecemos_home_activity);
        scrollView = findViewById(R.id.scrollviewempecemos);
        gifEmpecemos = findViewById(R.id.gifEmpecemos);
        btnAvatar = findViewById(R.id.btnAvatar);
        btnOmitir = findViewById(R.id.btnOmitir);


        Glide.with(getApplicationContext())
                .asGif()
                .load(R.drawable.empecemos)
                .placeholder(R.drawable.avatardefault)
                .into(gifEmpecemos);

        btnAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmpecemosActivityHome.this, MiPerfilActivity.class));
            }
        });

        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean continuar = true;
                Intent intent = new Intent(EmpecemosActivityHome.this, InterfazPrincipalActivity.class);
                intent.putExtra("continuar", continuar);
                startActivity(intent);
            }
        });


    }//OnCreate


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Boolean continuar = true;
        Intent intent = new Intent(EmpecemosActivityHome.this, InterfazPrincipalActivity.class);
        intent.putExtra("continuar", continuar);
        startActivity(intent);
        finish();


    }

}

