package com.tecmilenio.actividad9;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tecmilenio.actividad9.database.DatabaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextCodigo, editTextDescripcion, editTextCantidad, editTextCosto;
    private TextView textViewResultado;
    private Button buttonAgregar, buttonModificar, buttonEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        editTextCodigo = findViewById(R.id.edit_text_codigo);
        editTextDescripcion = findViewById(R.id.edit_text_descripcion);
        editTextCantidad = findViewById(R.id.edit_text_cantidad);
        editTextCosto = findViewById(R.id.edit_text_costo);
        textViewResultado = findViewById(R.id.text_view_resultado);

        buttonAgregar = findViewById(R.id.button_agregar);
        buttonModificar = findViewById(R.id.button_modificar);
        buttonEliminar = findViewById(R.id.button_eliminar);

        buttonAgregar.setOnClickListener(v -> agregarProducto());
        buttonModificar.setOnClickListener(v -> modificarProducto());
        buttonEliminar.setOnClickListener(v -> eliminarProducto());
    }

    private void agregarProducto() {
        String codigo = editTextCodigo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        int cantidad = Integer.parseInt(editTextCantidad.getText().toString());
        double costo = Double.parseDouble(editTextCosto.getText().toString());

        if (dbHelper.agregarProducto(codigo, descripcion, cantidad, costo)) {
            Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Producto ya existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarProducto() {
        String codigo = editTextCodigo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        int cantidad = Integer.parseInt(editTextCantidad.getText().toString());
        double costo = Double.parseDouble(editTextCosto.getText().toString());

        if (dbHelper.actualizarProducto(codigo, descripcion, cantidad, costo)) {
            Toast.makeText(this, "Producto modificado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarProducto() {
        String codigo = editTextCodigo.getText().toString();

        if (dbHelper.eliminarProducto(codigo)) {
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
