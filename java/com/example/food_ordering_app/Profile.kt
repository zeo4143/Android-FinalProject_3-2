package com.example.food_ordering_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Profile : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    var file = "key.txt"
    lateinit var userProfileName: TextView
    lateinit var userProfileAddress: TextView
    lateinit var userMobileNo: TextView

    override fun onCreateView( inflater: LayoutInflater,   container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val changeAddress = view.findViewById<TextView>(R.id.changeAddress)
        changeAddress.setOnClickListener {
            startActivity(Intent(context, ChangeAddress::class.java))
        }

        val logOut = view.findViewById<TextView>(R.id.profileLogOut)
        logOut.setOnClickListener {
            sharedPreferences = requireContext().getSharedPreferences("saved", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(context, Register::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        userProfileName = view.findViewById(R.id.userProfileName)
        userProfileAddress = view.findViewById(R.id.userAddress)
        userMobileNo = view.findViewById(R.id.userMobNo)

        val fin = requireContext().openFileInput("Address.txt")
        val bytes = ByteArray(fin.available())
        fin.read(bytes)
        fin.close()

        val content = String(bytes)
        val lines = content.split("\n")

        for (line in lines) {
            val parts = line.split("@")
            if (parts.size >= 3) {
                userProfileName.text = parts[0]
                userProfileAddress.text = parts[1]
                userMobileNo.text = parts[2]
            }
        }
    }
}