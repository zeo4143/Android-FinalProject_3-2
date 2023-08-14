package com.example.food_ordering_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import org.w3c.dom.Text
import java.io.IOException
import java.lang.Integer.parseInt

class AddToCart : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences


    lateinit var data1 : String
    lateinit var data2 : String
    lateinit var editname : TextView
    lateinit var address: TextView
    lateinit var mobile: TextView
    lateinit var changeBtn: Button
    lateinit var radioGroup: RadioGroup

    lateinit var linear: LinearLayout
    private val file = "Address.txt"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        linear = findViewById(R.id.linear)
        editname = findViewById(R.id.editName)
        address = findViewById(R.id.address)
        mobile = findViewById(R.id.editNumber)
        changeBtn = findViewById(R.id.changeBtn)
        radioGroup = findViewById(R.id.radiogroup)

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

                editname.text = studentName
                address.text = studentAddress
                mobile.text = studentMobile

            }
        }

        changeBtn.setOnClickListener {
            startActivity(Intent(this, ChangeAddress::class.java))
        }

        val calendar = Calendar.getInstance()
        var orderDate = " ${calendar.get(Calendar.DAY_OF_MONTH)}/${(calendar.get(Calendar.MONTH)) + 1}/${calendar.get(Calendar.YEAR)}"
        var orderTime = " ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"


        var sum = 0
        val bill: TextView = findViewById(R.id.bill)

        sharedPreferences = getSharedPreferences("cartItems", Context.MODE_PRIVATE)
        val cartItems = sharedPreferences.all
        cartItems.forEach{entry ->
            constrainLayoutView(entry.key, "₹.${entry.value.toString()}/-", 18f, 20f)
            sum+= parseInt(entry.value.toString())
        }

        constrainLayoutView(orderDate, orderTime, 16f, 16f)

        bill.text = "Total Bill: ₹. ${sum.toString()}/-"
        val btn = Button(this)
        btn.setBackgroundColor(ContextCompat.getColor(this,R.color.zomato_red))
        btn.setTextColor(ContextCompat.getColor(this,R.color.white))

        btn.text = "Place Order"
        btn.setOnClickListener {


            val selectPayment = radioGroup.checkedRadioButtonId

            if(cartItems.isEmpty()) {
                Toast.makeText(this, "Add Items", Toast.LENGTH_SHORT).show()
            }
            else if (selectPayment == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
            else {
                val selectPaymentMethod = findViewById<RadioButton>(selectPayment)
                cartItems.forEach { entry ->
                    data1 = entry.key
                    data2 = entry.value.toString()
                    try {
                        val fout = openFileOutput("History.txt", AppCompatActivity.MODE_APPEND)
                        data1 += "@"
                        data2 += ","

                        fout.write(data1.toByteArray())
                        fout.write(data2.toByteArray())
                        fout.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                try {
                    val fout = openFileOutput("History.txt", AppCompatActivity.MODE_APPEND)
                    orderDate += "  "
                    orderTime += "\n"

                    fout.write(orderDate.toByteArray())
                    fout.write(orderTime.toByteArray())
                    fout.close()
                }
                catch (e: IOException) {
                    e.printStackTrace()
                }


                val i = Intent(this, OrderPlacedSplashScreen::class.java)
                startActivity(i)
                sharedPreferences.edit().clear().apply()
                finish()
            }

        }
        linear.addView(btn)
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



    private fun constrainLayoutView(tex1: String, text2: String, size1: Float, size2: Float) {
            val constraintLayout = ConstraintLayout(this)
            val textView1 = TextView(this)
            val textView2 = TextView(this)

            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, size1)
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size2)

            textView1.text = tex1
            textView2.text = text2

            constraintLayout.addView(textView1)
            constraintLayout.addView(textView2)


            textView1.setTextColor(ContextCompat.getColor(this,R.color.black))
            textView2.setTextColor(ContextCompat.getColor(this,R.color.black))

            val textParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            textParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            textParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            textView1.layoutParams = textParams

            val valueParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            valueParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            valueParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            textView2.layoutParams = valueParams


            val layoutParameters = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            constraintLayout.layoutParams = layoutParameters

            linear.addView(constraintLayout)
        }

}

