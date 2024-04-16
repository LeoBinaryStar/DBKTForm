package com.example.dbktform

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var no_control: EditText? = null
    private var nombres: EditText? = null
    private var carreras: EditText? = null
    private var semestres: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // proviene del layout, son los campos de texto
        no_control = findViewById(R.id.no_controlET)
        nombres = findViewById(R.id.nombreET)
        carreras = findViewById(R.id.carreraET)
        semestres = findViewById(R.id.semestreET)
    }

    fun altas(view: View?) {
        // Se va a construir la base de datos
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db: SQLiteDatabase = admin.getWritableDatabase()
        val nocontrol = no_control!!.text.toString()
        val nombre = nombres!!.text.toString()
        val carrera = carreras!!.text.toString()
        val semestre = semestres!!.text.toString()
        val registro = ContentValues()
        registro.put("nocontrol", nocontrol)
        registro.put("nombre", nombre)
        registro.put("carrera", carrera)
        registro.put("semestre", semestre)
        // los inserto en la base de datos
        db.insert("usuario", null, registro)
        db.close()
        // ponemos los campos a vacío para insertar el siguiente usuario
        no_control!!.setText("")
        nombres!!.setText("")
        carreras!!.setText("")
        semestres!!.setText("")
        Toast.makeText(this, "Datos del usuario cargados", Toast.LENGTH_SHORT).show()
    }

    fun buscar(view: View?) {
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd: SQLiteDatabase = admin.getWritableDatabase()
        val nocontrol = no_control!!.text.toString()
        val fila = bd.rawQuery(
            "select nombre, carrera, semestre from usuario where nocontrol='$nocontrol'",
            null
        )
        if (fila.moveToFirst()) {
            nombres!!.setText(fila.getString(0))
            carreras!!.setText(fila.getString(1))
            semestres!!.setText(fila.getString(2))
        } else Toast.makeText(
            this,
            "No existe ningún usuario con ese número de control",
            Toast.LENGTH_SHORT
        ).show()
        bd.close()
    }

    fun eliminar(view: View?) {
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db: SQLiteDatabase = admin.getWritableDatabase()
        val nocontrol = no_control!!.text.toString()
        val filasEliminadas = db.delete("usuario", "nocontrol=?", arrayOf(nocontrol))
        db.close()
        if (filasEliminadas > 0) {
            Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
            // Limpiar los campos después de eliminar
            no_control!!.setText("")
            nombres!!.setText("")
            carreras!!.setText("")
            semestres!!.setText("")
        } else {
            Toast.makeText(
                this,
                "No se encontró ningún usuario con ese número de control",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun modificar(view: View?) {
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val db: SQLiteDatabase = admin.getWritableDatabase()
        val nocontrol = no_control!!.text.toString()
        val nombre = nombres!!.text.toString()
        val carrera = carreras!!.text.toString()
        val semestre = semestres!!.text.toString()
        val valores = ContentValues()
        valores.put("nombre", nombre)
        valores.put("carrera", carrera)
        valores.put("semestre", semestre)
        val filasActualizadas = db.update("usuario", valores, "nocontrol=?", arrayOf(nocontrol))
        db.close()
        if (filasActualizadas > 0) {
            Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this,
                "No se encontró ningún usuario con ese número de control",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun limpiarCampos(view: View?) {
        no_control!!.setText("")
        nombres!!.setText("")
        carreras!!.setText("")
        semestres!!.setText("")
    }
}

