package com.example.food_ordering_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar



class Kitchen : Fragment() {

     private lateinit var sharedPreferences: SharedPreferences
     private val fileName = "cartItems"

    override fun onCreateView( inflater: LayoutInflater,   container: ViewGroup?,  savedInstanceState: Bundle? ): View? {

        return inflater.inflate(R.layout.fragment_kitchen, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val RiceBowlBtn: Button = v.findViewById(R.id.RiceBowlBtn)
        RiceBowlBtn.setOnClickListener {
            RiceBowlBtn.text = "Added to Cart"
            editor.putInt("Rice Bowl", 70)
            editor.apply()
            showSnackbar(v)
        }

        val FrenchFriesBtn: Button = v.findViewById(R.id.FrenchFriesBtn)
        FrenchFriesBtn.setOnClickListener {
            FrenchFriesBtn.text = "Added to Cart"
            editor.putInt("French Fries", 50)
            editor.apply()
            showSnackbar(v)
        }
        val VegBiryaniBtn: Button = v.findViewById(R.id.VegBiryaniBtn)
        VegBiryaniBtn.setOnClickListener {
            VegBiryaniBtn.text = "Added to Cart"
            editor.putInt("Veg Biryani", 100)
            editor.apply()
            showSnackbar(v)
        }
        val StuffedGarlicBreadBtn: Button = v.findViewById(R.id.StuffedGarlicBreadBtn)
        StuffedGarlicBreadBtn.setOnClickListener {
            StuffedGarlicBreadBtn.text = "Added to Cart"
            editor.putInt("Garlic Bread", 80)
            editor.apply()
            showSnackbar(v)
        }
        val KitkatCrushBtn: Button = v.findViewById(R.id.KitkatCrushBtn)
        KitkatCrushBtn.setOnClickListener {
            KitkatCrushBtn.text = "Added to Cart"
            editor.putInt("Kitkat Crush", 110)
            editor.apply()
            showSnackbar(v)
        }
        val AlooParathaBtn: Button = v.findViewById(R.id.AlooParathaBtn)
        AlooParathaBtn.setOnClickListener {
            AlooParathaBtn.text = "Added to Cart"
            editor.putInt("Aloo Paratha", 60)
            editor.apply()
            showSnackbar(v)
        }
        val MasalaDosaBtn: Button = v.findViewById(R.id.MasalaDosaBtn)
        MasalaDosaBtn.setOnClickListener {
            RiceBowlBtn.text = "Added to Cart"
            editor.putInt("Masala Dosa", 50)
            editor.apply()
            showSnackbar(v)
        }
        val VadaPaavBtn: Button = v.findViewById(R.id.VadaPaavBtn)
        VadaPaavBtn.setOnClickListener {
            VadaPaavBtn.text = "Added to Cart"
            editor.putInt("Vada Paav", 70)
            editor.apply()
            showSnackbar(v)
        }
        val IdlyBtn: Button = v.findViewById(R.id.IdlyBtn)
        IdlyBtn.setOnClickListener {
            IdlyBtn.text = "Added to Cart"
            editor.putInt("Idly", 70)
            editor.apply()
            showSnackbar(v)
        }
        val MargaretaBowlBtn: Button = v.findViewById(R.id.MargaretaBowlBtn)
        MargaretaBowlBtn.setOnClickListener {
            MargaretaBowlBtn.text = "Added to Cart"
            editor.putInt("Margareta", 70)
            editor.apply()
            showSnackbar(v)
        }
        val MaggiOreganoBowlBtn: Button = v.findViewById(R.id.MaggiOreganoBowlBtn)
        MaggiOreganoBowlBtn.setOnClickListener {
            MaggiOreganoBowlBtn.text = "Added to Cart"
            editor.putInt("MaggiOregano", 70)
            editor.apply()
            showSnackbar(v)
        }
    }


    fun showSnackbar(v: View, ) {
        val snackbar = Snackbar.make(v, "Item Added Successfully", Snackbar.LENGTH_LONG)
        snackbar.setAction("View Cart") {
            val intent = Intent(requireContext(), AddToCart::class.java)
            startActivity(intent)
        }
        snackbar.setBackgroundTint(Color.BLACK)
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        snackbar.show()
    }
}