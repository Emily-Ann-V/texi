package com.example.texi.model

import com.example.texi.R

data class Textbook (
    var imageResId: Int,
    var title: String,
    var author: String,
    var isbn: Int,
    var description: String,
    var price: Float,
    var university: String,
    var field: String,
    var degree: String
    )

val textbooks = mutableListOf(
    Textbook(
        imageResId = R.drawable.img_login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        university = "UCT",
        field = "Computer Science",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.ic_menu_all_textbooks,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        university = "Wits",
        field = "Business",
        degree = "BCom"
    ),
    Textbook(
        imageResId = R.drawable.ic_footer_home,
        title = "Mathematics 101",
        author = "Alan Turing",
        isbn = 1003,
        description = "Foundational mathematics",
        price = 200.0f,
        university = "UP",
        field = "Mathematics",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.img_login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        university = "UCT",
        field = "Computer Science",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.img_login_logo,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        university = "Wits",
        field = "Business",
        degree = "BCom"
    ),
    Textbook(
        imageResId = R.drawable.img_login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        university = "UCT",
        field = "Computer Science",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.img_login_logo,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        university = "Wits",
        field = "Business",
        degree = "BCom"
    ),
    Textbook(
        imageResId = R.drawable.ic_menu_home,
        title = "Mathematics 101",
        author = "Alan Turing",
        isbn = 1003,
        description = "Foundational mathematics",
        price = 200.0f,
        university = "UP",
        field = "Mathematics",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.ic_all_textbooks_filter,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        university = "UCT",
        field = "Computer Science",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.ic_menu_all_textbooks,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        university = "Wits",
        field = "Business",
        degree = "BCom"
    )
)
