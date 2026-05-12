package com.example.texi.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.texi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ibMenu = findViewById<ImageButton>(R.id.ib_menu)
        val dlMenu = findViewById<DrawerLayout>(R.id.dl_menu)
        val ibHome = findViewById<ImageButton>(R.id.ib_home_footer)
        val llHome = findViewById<LinearLayout>(R.id.ll_home_menu)
        val llAllTextbooks = findViewById<LinearLayout>(R.id.ll_all_textbooks_menu)
        val tvLogout = findViewById<TextView>(R.id.tv_logout)

        loadLatestTextbooks()

        tvLogout.paintFlags =
            tvLogout.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        tvLogout.setOnClickListener {
            logout()
        }

        ibMenu.setOnClickListener {
                dlMenu.openDrawer(GravityCompat.END)
        }

        ibHome.setOnClickListener {
            loadLatestTextbooks()
        }

        llHome.setOnClickListener {
            loadLatestTextbooks()
            dlMenu.closeDrawer(GravityCompat.END)
        }

        llAllTextbooks.setOnClickListener {
            loadAllTextbooks()
            dlMenu.closeDrawer(GravityCompat.END)
        }
    }

    fun loadNewFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, fragment)
            .commit()
    }

    fun loadLatestTextbooks(){
        loadNewFragment(LatestTextbooksFragment())
    }

    fun loadAllTextbooks(){
        loadNewFragment(AllTextbooksFragment())
    }

    fun logout(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}