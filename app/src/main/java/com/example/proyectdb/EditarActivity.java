package com.example.proyectdb;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectdb.db.DbContactos;
import com.example.proyectdb.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo;
    Button btnGuarda;
    FloatingActionButton fabEditar , fabEliminar;
    boolean correcto = false;
    Contactos contactos;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = Integer.parseInt(null);
            }else{id= extras.getInt("ID");}
        }else{id = (int) savedInstanceState.getSerializable("ID");}
        final DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contactos = dbContactos.verContactos(id);

        if (contactos != null){
            txtNombre.setText(contactos.getNombre());
            txtTelefono.setText(contactos.getTelefono());
            txtCorreo.setText(contactos.getCorreo_electronico());
        }
        btnGuarda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")){
                    correcto = dbContactos.editarContacto(id,txtNombre.getText().toString(),txtTelefono.getText().toString(),txtCorreo.getText().toString());
                    if (correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistros();
                    }else{
                        Toast.makeText(EditarActivity.this , "ERROR AL MODIFICAR REGISTRO" , Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EditarActivity.this , "DEBEN DE LLENAR LOS CAMPOS OBLIGATORIOS" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void verRegistros(){
        Intent intent = new Intent(this,VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}