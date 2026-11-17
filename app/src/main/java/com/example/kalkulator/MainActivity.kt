package com.example.kalkulator

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private val riwayat = ArrayList<String>()
    private lateinit var Angka1: EditText
    private lateinit var Angka2: EditText
    private lateinit var Hasil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Angka1 = findViewById(R.id.Angka1)
        Angka2 = findViewById(R.id.Angka2)
        Hasil = findViewById(R.id.Hasil)


        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnKurang = findViewById<Button>(R.id.btnKurang)
        val btnKali = findViewById<Button>(R.id.btnKali)
        val btnBagi = findViewById<Button>(R.id.btnBagi)

        if (savedInstanceState == null) {
            replaceFragment(tambah())
        }

        btnTambah.setOnClickListener {
            hitung("+")
        }
        btnKurang.setOnClickListener {
            hitung("-")
        }
        btnKali.setOnClickListener {
            hitung("*")
        }
        btnBagi.setOnClickListener {
            hitung("/")
        }
    }

    private fun hitung(operasi: String) {
        val nilai1 = Angka1.text.toString().toDoubleOrNull()
        val nilai2 = Angka2.text.toString().toDoubleOrNull()

        if (nilai1 == null || nilai2 == null) {
            Toast.makeText(this, "Isi kedua angka dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        if (operasi == "/" && nilai2 == 0.0) {
            Toast.makeText(this, "Tidak bisa dibagi dengan 0", Toast.LENGTH_SHORT).show()
            return
        }

        val hasil: Double
        if (operasi == "+") {
            hasil = nilai1 + nilai2
        } else if (operasi == "-") {
            hasil = nilai1 - nilai2
        } else if (operasi == "*") {
            hasil = nilai1 * nilai2
        } else {
            hasil = nilai1 / nilai2
        }


        Hasil.text = "Hasil : $hasil"

        if (hasil < 0) {
            Hasil.setBackgroundColor(Color.RED)
        } else {
            Hasil.setBackgroundColor(Color.parseColor("#303E55"))
        }

        riwayat.add("$nilai1 $operasi $nilai2 = $hasil")

        if (operasi == "+") {
            replaceFragment(tambah())
        } else if (operasi == "-") {
            replaceFragment(kurang())
        } else if (operasi == "*") {
            replaceFragment(kali())
        } else {
            replaceFragment(bagi())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val dataKirim = Bundle()
        dataKirim.putStringArrayList("riwayat", riwayat)
        fragment.arguments = dataKirim

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()
    }
}