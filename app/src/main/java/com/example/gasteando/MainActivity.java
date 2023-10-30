package com.example.gasteando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void login(View v) {
        EditText campoCorreo = findViewById(R.id.correo);
        EditText campoContrasenia = findViewById(R.id.contrasenia);

        String correo = campoCorreo.getText().toString();
        String contrasenia = campoContrasenia.getText().toString();

        // Consultar Firestore para verificar las credenciales
        db.collection("usuarios")
                .whereEqualTo("correo", correo)
                .whereEqualTo("contrasenia", contrasenia)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if (result != null && !result.isEmpty()) {
                            // Usuario autenticado correctamente
                            Intent i = new Intent(this, MenuPrincipal.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(this, "Credenciales incorrectas o usuario no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error al consultar Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void crearCuenta(View v) {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }
}
