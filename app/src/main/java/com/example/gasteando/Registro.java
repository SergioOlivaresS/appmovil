package com.example.gasteando;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {

    private EditText nombreR, apellidoR, correoR, contraseniaR, confirmarR;
    private CollectionReference usuariosRef;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreR = findViewById(R.id.nombreR);
        apellidoR = findViewById(R.id.apellidoR);
        correoR = findViewById(R.id.correoR);
        contraseniaR = findViewById(R.id.contraseniaR);
        confirmarR = findViewById(R.id.confirmarconR);
        Button botonRegistro = findViewById(R.id.button4);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuariosRef = db.collection("usuarios");

        botonRegistro.setOnClickListener(view -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombre = nombreR.getText().toString().trim();
        String apellido = apellidoR.getText().toString().trim();
        String correo = correoR.getText().toString().trim();
        String contrasenia = contraseniaR.getText().toString();
        String confirmarContrasenia = confirmarR.getText().toString();
        CheckBox checkBox = findViewById(R.id.checkBox);
        boolean aceptaTerminos = checkBox.isChecked();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || confirmarContrasenia.isEmpty() || !checkBox.isChecked()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(correo)) {
            Toast.makeText(this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contrasenia.length() < 3) {
            Toast.makeText(this, "La contraseña debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasenia.equals(confirmarContrasenia)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, correo, contrasenia);

        usuariosRef.add(nuevoUsuario)
                .addOnSuccessListener(documentReference -> {
                    String userID = documentReference.getId();

                    guardarUserIdEnSharedPreferences(userID);

                    Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al registrar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void guardarUserIdEnSharedPreferences(String userID) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", userID);
        editor.apply();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
