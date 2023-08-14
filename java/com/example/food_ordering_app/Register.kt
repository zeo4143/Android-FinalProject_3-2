package com.example.food_ordering_app

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.IOException

class Register : AppCompatActivity() {


    lateinit var signUp: TextView
    lateinit var sign: Button

    private var backPressedTime: Long = 0
    private val backPressedInterval: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        sign = findViewById(R.id.sign)
        sign.setOnClickListener {

            val id = findViewById<TextView>(R.id.user).text.toString()
            val password = findViewById<TextView>(R.id.pass).text.toString()

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                if (isValid(id, password)) {
                    val sharedPreferences = getSharedPreferences("saved", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("id", id)
                    editor.putString("password", password)
                    editor.apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid ID or password", Toast.LENGTH_SHORT).show()
                }
            }

        }

        signUp = findViewById(R.id.signup)
        signUp.setOnClickListener {
            intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

    }

    private fun isValid(id: String, password: String): Boolean {
        try {
            val fin = openFileInput("key.txt")
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            fin.close()

            val content = String(bytes)
            val lines = content.split("\n")

            for (line in lines) {
                val parts = line.split(" ")
                if (parts.size >= 2) {
                    val storedId = parts[0]
                    val storedPassword = parts[1].trim()

                    if (id == storedId && password == storedPassword) {
                        return true
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return false
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (backPressedTime + backPressedInterval > System.currentTimeMillis()) {
            return finish()
        } else {
            Toast.makeText(
                this,
                "Press back again to exit",
                Toast.LENGTH_SHORT
            ).show()
        }
        backPressedTime = System.currentTimeMillis()
    }


}