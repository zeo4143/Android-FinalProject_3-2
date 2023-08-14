package com.example.food_ordering_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.IOException

@Suppress("DEPRECATION")
class ChangeAddress : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var address: EditText
    lateinit var mobNo: EditText
    lateinit var saveBtn: Button
    lateinit var myName : String
    lateinit var myAddress : String
    lateinit var mobileNo : String
    var file = "Address.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_address)

        name = findViewById(R.id.name)
        address = findViewById(R.id.address)
        mobNo = findViewById(R.id.MobNo)
        saveBtn = findViewById(R.id.saveBtn)

        val fin = openFileInput(file)
        val bytes = ByteArray(fin.available())
        fin.read(bytes)
        fin.close()

        val content = String(bytes)
        val lines = content.split("\n")

        for (line in lines) {
            val parts = line.split("@")
            if (parts.size >= 3) {
                var studentName = parts[0].trim()
                var studentAddress = parts[1].trim()
                var studentMobile = parts[2].trim()

                name.setText(studentName)
                address.setText(studentAddress)
                mobNo.setText(studentMobile)

            }
        }

        saveBtn.setOnClickListener {
            myName = name.text.toString()
            myAddress = address.text.toString()
            mobileNo = mobNo.text.toString()



            deleteFile("Address.txt")

            if (!myName.isNullOrEmpty() && !myAddress.isNullOrEmpty() && !mobileNo.isNullOrEmpty()) {

                try {
                    val fout=openFileOutput("Address.txt", MODE_APPEND)
                    myName += "@";
                    myAddress += "@";
                    mobileNo+= "\n"
                    fout.write(myName.toByteArray())
                    fout.write(myAddress.toByteArray())
                    fout.write(mobileNo.toByteArray())
                    fout.close()
                    Toast.makeText(this, "Address Updated Successfully", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, MainActivity::class.java))
                }
                catch (e:IOException) {
                    e.printStackTrace()
                }

            }
            else {
                Toast.makeText(this, "Complete All the Details", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.cart -> {
                startActivity(Intent(this, AddToCart::class.java))
            }
            R.id.logout -> {
                val sharedPref = getSharedPreferences("saved", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()

                startActivity(Intent(this, Register::class.java))
                finish()

            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}
