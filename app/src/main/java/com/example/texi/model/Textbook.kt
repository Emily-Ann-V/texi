package com.example.texi.model

import com.example.texi.R

data class Textbook (
    var imageResId: Int,
    var title: String,
    var author: String,
    var isbn: Int,
    var description: String,
    var price: Float,
    var field: String,
    var university: String,
    var degree: String
    )

val textbooks = mutableListOf(
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        field = "Computer Science",
        university = "UCT",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.all_textbooks_menu_icon,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        field = "Business",
        university = "Wits",
        degree = "BCom"
    ),
    Textbook(
        imageResId = R.drawable.home_footer_icon,
        title = "Mathematics 101",
        author = "Alan Turing",
        isbn = 1003,
        description = "Foundational mathematics",
        price = 200.0f,
        field = "Mathematics",
        university = "UP",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        field = "Computer Science",
        university = "UCT",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        field = "Business",
        university = "Wits",
        degree = "BCom"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        field = "Computer Science",
        university = "UCT",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        field = "Business",
        university = "Wits",
        degree = "BCom"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Mathematics 101",
        author = "Alan Turing",
        isbn = 1003,
        description = "Foundational mathematics",
        price = 200.0f,
        field = "Mathematics",
        university = "UP",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Intro to Programming",
        author = "John Doe",
        isbn = 1001,
        description = "Basic programming concepts",
        price = 250.0f,
        field = "Computer Science",
        university = "UCT",
        degree = "BSc"
    ),
    Textbook(
        imageResId = R.drawable.login_logo,
        title = "Business Basics",
        author = "Jane Smith",
        isbn = 1002,
        description = "Introduction to business",
        price = 180.0f,
        field = "Business",
        university = "Wits",
        degree = "BCom"
    )
)
