package com.example.food_ordering_app


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.IOException
import kotlin.reflect.typeOf


class MyOrders : Fragment() {


    lateinit var linear: LinearLayout

    lateinit var dateAndTime: String

    private val fileName = "History.txt"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linear = view.findViewById(R.id.linear_layout)

        try {
            val fin = requireContext().openFileInput(fileName)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            fin.close()

            val content = String(bytes)
            val lines = content.split("\n")

            for (line in lines) {
                creatingAView(5f, 0f, R.color.black)
                creatingAView(5f, 0f, R.color.white)

                val parts = line.split(",")
                dateAndTime = parts.last().trim()
                val data = parts.dropLast(1)

                val foodItems = data.flatMap { it.split("@") }
                for (i in foodItems.indices step 2) {
                    constrainLayoutView(foodItems[i], foodItems[i+1], 18f)


                }

                val splitDateAndTime = dateAndTime.split("  ")
                if (splitDateAndTime.size >= 2) {
                    val date = splitDateAndTime[0].trim()
                    val time = splitDateAndTime[1].trim()

                    creatingAView(1f, 10f, R.color.black)
                    creatingAView(1f, 10f, R.color.white)
                    constrainLayoutView(date, time, 14f)
                }


            }

        } catch (e: IOException) {
            e.printStackTrace()
        }



    }

    private fun creatingAView(size:Float, setMargin:Float, setColor: Int) {
        val myView = View(context)
        myView.id = View.generateViewId()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                size,
                context?.resources?.displayMetrics
            ).toInt()
        )
        val r: Resources = resources
        val margin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, setMargin, r.displayMetrics)
                .toInt()
        layoutParams.setMargins(margin, 0, margin, 0)

        myView.layoutParams = layoutParams

        myView.setBackgroundColor(ContextCompat.getColor(requireContext(),setColor))

        linear.addView(myView)
    }


    private fun constrainLayoutView(tex1: String, text2: String, size:Float) {
        val constraintLayout = ConstraintLayout(requireContext())
        val textView1 = TextView(requireContext())
        val textView2 = TextView(requireContext())

        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)

        textView1.text = tex1
        textView2.text = text2

        constraintLayout.addView(textView1)
        constraintLayout.addView(textView2)


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
