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

        // Binding menu UI elements
        val dlMenu = findViewById<DrawerLayout>(R.id.dl_menu)
        val ibMenu = findViewById<ImageButton>(R.id.ib_header_menu_icon)

        // Binding menu navigation UI elements
        val llHome = findViewById<LinearLayout>(R.id.ll_menu_home)
        val llUploadTextbook = findViewById<LinearLayout>(R.id.ll_menu_upload_textbook)
        val llMyProfile = findViewById<LinearLayout>(R.id.ll_menu_my_profile)
        val llAllTextbooks = findViewById<LinearLayout>(R.id.ll_menu_all_textbooks)

        // Binding footer navigation UI elements
        val ibHome = findViewById<ImageButton>(R.id.ib_footer_home_icon)
        val ibUploadTextbook = findViewById<ImageButton>(R.id.ib_footer_upload_textbook_icon)
        val ibMyProfile = findViewById<ImageButton>(R.id.ib_footer_my_profile_icon)
        val tvLogout = findViewById<TextView>(R.id.tv_menu_logout)

        // Opening textbooks screen
        loadLatestTextbooks()

        // Opening header menu
        ibMenu.setOnClickListener {
            dlMenu.openDrawer(GravityCompat.END)
        }

        // Opening home screen (menu)
        llHome.setOnClickListener {
            loadLatestTextbooks()
            dlMenu.closeDrawer(GravityCompat.END)
        }

        // Opening upload textbook screen (menu)
        llUploadTextbook.setOnClickListener {
            loadUploadTextbook()
            dlMenu.closeDrawer(GravityCompat.END)
        }

        // Opening my profile screen (menu)
        llMyProfile.setOnClickListener {
            loadMyProfile()
            dlMenu.closeDrawer(GravityCompat.END)
        }

        // Opening all textbooks screen (menu)
        llAllTextbooks.setOnClickListener {
            loadNewFragment(AllTextbooksFragment())
            dlMenu.closeDrawer(GravityCompat.END)
        }

        // Opening home screen (footer)
        ibHome.setOnClickListener {
            loadNewFragment(LatestTextbooksFragment())
        }

        // Opening upload textbook screen (footer)
        ibUploadTextbook.setOnClickListener {
            loadUploadTextbook()
        }

        // Opening my profile screen (footer)
        ibMyProfile.setOnClickListener {
            loadMyProfile()
        }

        // Applying underline styling to logout text
        tvLogout.paintFlags =
            tvLogout.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        // Handling logout action
        tvLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Helper function for fragment navigation
    fun loadNewFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Helper function to open latest textbooks screen
    fun loadLatestTextbooks() {
        loadNewFragment(LatestTextbooksFragment())
    }

    // Helper function to open upload textbook screen
    fun loadUploadTextbook() {
        loadNewFragment(UploadTextbookFragment())
    }

    // Helper function to open profile screen
    fun loadMyProfile() {
        loadNewFragment(MyProfileFragment())
    }
}