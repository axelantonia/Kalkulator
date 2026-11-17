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

    private lateinit var etAngka1: EditText
    private lateinit var etAngka2: EditText
    private lateinit var tvHasil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etAngka1 = findViewById(R.id.Angka1)
        etAngka2 = findViewById(R.id.Angka2)
        tvHasil = findViewById(R.id.Hasil)

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
        val angka1 = etAngka1.text.toString().toDoubleOrNull()
        val angka2 = etAngka2.text.toString().toDoubleOrNull()

        if (angka1 == null || angka2 == null) {
            Toast.makeText(this, "Isi kedua angka dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        if (operasi == "/" && angka2 == 0.0) {
            Toast.makeText(this, "Tidak bisa dibagi dengan 0", Toast.LENGTH_SHORT).show()
            return
        }

        val hasil: Double
        if (operasi == "+") {
            hasil = angka1 + angka2
        } else if (operasi == "-") {
            hasil = angka1 - angka2
        } else if (operasi == "*") {
            hasil = angka1 * angka2
        } else {
            hasil = angka1 / angka2
        }

        tvHasil.text = "Hasil : $hasil"

        if (hasil < 0) {
            tvHasil.setBackgroundColor(Color.RED)
        } else {
            tvHasil.setBackgroundColor(Color.parseColor("#303E55"))
        }

        riwayat.add("$angka1 $operasi $angka2 = $hasil")

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