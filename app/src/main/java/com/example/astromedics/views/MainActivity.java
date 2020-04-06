package com.example.astromedics.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.astromedics.R;
import com.example.astromedics.views.general.Login;

public class MainActivity extends AppCompatActivity {


    private Button iniciarSesion;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarSesion=findViewById(R.id.button_iniciar_sesion);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),
                                         Login.class);
                startActivity(intent);
            }
        });

    }
}
