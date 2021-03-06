package com.alejandrazuleta.formularioderegistro

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var cal=Calendar.getInstance()
    private lateinit var fecha : String //lateinint para no inicializar las variables, pero sí toca decir el tipo de la variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataSetListener =object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR,year)
                cal.set(Calendar.MONTH,month)
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                val format = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(format,Locale.US)
                fecha =sdf.format(cal.time).toString()
                Log.d("fecha: ",fecha)
            }
        }

        tv_showPicker.setOnClickListener {
            DatePickerDialog(this,
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Initializing a String Array
        val ciudades = arrayOf("Bogotá","Medellín","Santa Marta","Cali","Pasto")

        // Initializing an ArrayAdapter
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            ciudades // Array
        )

        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with dapter
        spCiudad.adapter = adapter;

        var ciudad="";

        spCiudad.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                ciudad = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback, nothing in this case
            }
        }

        bt_registrar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            val correo = et_correo.text.toString()
            val telefono = et_telefono.text.toString()
            val password = et_password.text.toString()
            val repPassword = et_repassword.text.toString()

            var sexo ="Masculino"

            if(rb_masculino.isChecked) sexo="Masculino"
            else sexo="Femenino"

            var pasatiempos=""

            if(cb_cine.isChecked)pasatiempos=pasatiempos+ " " +cb_cine.text
            if(cb_gimnasio.isChecked)pasatiempos=pasatiempos+ " " +getString(R.string.gimnasio) //otra opcion para que traduzca todos los strings
            if(cb_leer.isChecked)pasatiempos=pasatiempos+ " " +cb_leer.text
            if(cb_series.isChecked)pasatiempos=pasatiempos+ " " +cb_series.text

            if (nombre.isEmpty()||correo.isEmpty()||password.isEmpty()||repPassword.isEmpty()){
                Toast.makeText(this,getString(R.string.msg_error_campos_vacios),Toast.LENGTH_SHORT).show()
            }else if (password!=repPassword){
                Toast.makeText(this,getString(R.string.contrasenasdiferentes),Toast.LENGTH_SHORT).show()
            }else if (password.length<6){
                Toast.makeText(this,"La contraseña es muy corta, debe escribir mínimo 6 dígitos",Toast.LENGTH_SHORT).show()
            }
            else{
                tv_resultado.text = "Nombre: " + nombre +
                        "\nCorreo: " + correo +
                        "\nTeléfono: " + telefono +
                        "\nContraseña: " + password +
                        "\nGénero: "+ sexo +
                        "\nPasatiempos: "+ pasatiempos +
                        "\nFecha de nacimiento: "+ fecha +
                        "\nLugar de nacimiento: " + ciudad
            }
        }
    }

    fun showDatePickerDialog(view: View) {

    }
}

