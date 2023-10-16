package com.example.gasteando;

import android.annotation.SuppressLint;
import android.content.Intent;
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

        // Inicializa las vistas
        nombreR = findViewById(R.id.nombreR);
        apellidoR = findViewById(R.id.apellidoR);
        correoR = findViewById(R.id.correoR);
        contraseniaR = findViewById(R.id.contraseniaR);
        confirmarR = findViewById(R.id.confirmarconR);
        Button botonRegistro = findViewById(R.id.button4);

        // Inicializa Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuariosRef = db.collection("usuarios");

        // Configura el clic del botón de registro
        botonRegistro.setOnClickListener(view -> registrarUsuario());
    }

    private void registrarUsuario() {
        // Obtén los datos del formulario
        String nombre = nombreR.getText().toString().trim();
        String apellido = apellidoR.getText().toString().trim();
        String correo = correoR.getText().toString().trim();
        String contrasenia = contraseniaR.getText().toString();
        String confirmarContrasenia = confirmarR.getText().toString();
        CheckBox checkBox = findViewById(R.id.checkBox);
        boolean aceptaTerminos = checkBox.isChecked();

        // Comprueba si los campos están vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || confirmarContrasenia.isEmpty() || !checkBox.isChecked()) {            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasenia.equals(confirmarContrasenia)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo objeto Usuario
        Usuario nuevoUsuario = new Usuario(nombre, apellido, correo, contrasenia);

        // Agregar el objeto a la base de datos
        usuariosRef.add(nuevoUsuario)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    // Una vez registrado el usuario en Firestore, puedes redirigirlo a la pantalla principal o a donde desees
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al registrar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
