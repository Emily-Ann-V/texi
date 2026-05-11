package com.example.texi.ui

import android.graphics.Paint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.texi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvLogout = findViewById<TextView>(R.id.tv_logout)

        tvLogout.paintFlags =
            tvLogout.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val ibMenu = findViewById<ImageButton>(R.id.ib_menu)
        val dlMenu = findViewById<DrawerLayout>(R.id.dl_menu)

        ibMenu.setOnClickListener {
                dlMenu.openDrawer(GravityCompat.END)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, NewTextbooksFragment())
            .commit()
    }
}