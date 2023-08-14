
package com.example.food_ordering_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.IOException

class SignUp : AppCompatActivity() {
    lateinit var uName: EditText
    lateinit var name: EditText
    lateinit var pass: EditText
    lateinit var cpass: EditText
    lateinit var submit: Button
    lateinit var data0 : String
    lateinit var data1 : String
    lateinit var data2 : String
    lateinit var data3 : String
    var file = "key.txt"
    var file2 = "Address.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        uName = findViewById(R.id.username)
        name = findViewById(R.id.name)
        pass = findViewById(R.id.password)
        cpass = findViewById(R.id.confirmPassword)
        submit = findViewById(R.id.button)

        submit.setOnClickListener {
            data0 = uName.text.toString()
            data1 = name.text.toString()
            data2 = pass.text.toString()
            data3 = cpass.text.toString()

            if(data2 != data3){
                Toast.makeText(this,"Password doesn't match !!",Toast.LENGTH_LONG).show()
            }
            if(data1.isEmpty() || data2.isEmpty() || data3.isEmpty()){
                Toast.makeText(this, "Fill all the details !!",Toast.LENGTH_LONG).show()
            }
            if(((!data1.isNullOrEmpty() && data1.length >= 6) && (!data2.isNullOrEmpty() && data2.length >= 8 ) && (!data3.isNullOrEmpty() && data2.length >= 8 )) && (data2 == data3)) {

                try {
                    val fout = openFileOutput(file, MODE_APPEND)
                    data1 += " ";
                    data2 += "\n";
                    fout.write(data1.toByteArray())
                    fout.write(data2.toByteArray())
                    fout.close()
                    Toast.makeText(this, "You've Successfully Signed In", Toast.LENGTH_LONG).show()

                    val sharedPreferences = getSharedPreferences("saved", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("id", data1)
                    editor.putString("password", data2)
                    editor.apply()

                    val fout2 = openFileOutput(file2, MODE_APPEND)
                    var name = data0
                    var address = "Beside Jagadeesh General Store, Nagarigutta, Pulivendula,Andhra Pradesh - 516390" + "@"
                    var mobile = "9391704193"
                    name+= "@"
                    fout2.write(name.toByteArray())
                    fout2.write(address.toByteArray())
                    fout2.write(mobile.toByteArray())
                    fout2.close()

                    startActivity(Intent(this, MainActivity::class.java))

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

}