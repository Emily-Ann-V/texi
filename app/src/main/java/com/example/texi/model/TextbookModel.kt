package com.example.texi.model

import com.example.texi.R

data class Textbook (
    val uploadedImageResId: Int? = null,
    val uploadedImageUri: String? = null,
    var title: String,
    var author: String,
    var isbn: Long,
    var description: String,
    var price: Float,
    var university: String,
    var field: String,
    var degree: String
    )

val textbooks = mutableListOf(
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_introduction_to_law_and_legal_skills_in_south_africa_2ed,
        title = "Introduction to Law and Legal Skills in South Africa 2ed.",
        author = "Du Plessis et al",
        isbn = 	9780190748111,
        description = "Lightly used, in good condition.",
        price = 300.0f,
        university = "UNISA",
        field = "Law",
        degree = "Bachelor’s"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_textbook_of_environmental_studies_for_undergraduate_courses_3ed,
        title = "Textbook of Environmental Studies for Undergraduate Courses 3ed.",
        author = "Erach Bharucha",
        isbn = 9789389211788,
        description = "Used and loved, back page torn.",
        price = 180.0f,
        university = "Wits",
        field = "Environmental Studies",
        degree = "Diploma"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_the_principles_of_computer_hardware_3ed,
        title = "The Principles of Computer Hardware 3ed.",
        author = "Alan Clements",
        isbn = 9780198564539,
        description = "Highlighted pages.",
        price = 200.0f,
        university = "UCT",
        field = "Information Technology",
        degree = "Higher Certificate"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_introduction_to_business_management_11ed,
        title = "Introduction to Business Management 11ed.",
        author = "Barney Erasmus et al",
        isbn = 9780190745769,
        description = "Pen marks in margin.",
        price = 250.0f,
        university = "UP",
        field = "Business",
        degree = "Bachelor’s"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_marketing_research_4ed,
        title = "Marketing Research 4ed.",
        author = "Ja Wiid & Cn Diggines",
        isbn = 9781485129240,
        description = "Great condition, no marks or tears.",
        price = 350.0f,
        university = "UP",
        field = "Business",
        degree = "Bachelor’s"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_introduction_to_teaching_making_a_difference_in_student_learning_3ed,
        title = "Introduction to Teaching Making a Difference in Student Learning 3ed.",
        author = "Gene E. Hall et al",
        isbn = 9781071827956,
        description = "Excellent condition, literally did not use it once.",
        price = 450.0f,
        university = "STADIO Higher Education",
        field = "Education",
        degree = "Higher Certificate"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_environmental_education_and_education_for_sustainability_2ed,
        title = "Environmental Education And Education For Sustainability 2ed.",
        author = "C.P. Loubser",
        isbn = 9780627030161,
        description = "Pen marks in margin.",
        price = 150.0f,
        university = "Wits",
        field = "Environmental Studies",
        degree = "Diploma"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_computer_networks_a_systems_approach_6ed,
        title = "Computer Networks a Systems Approach 6ed.",
        author = "Larry L Peterson",
        isbn = 9780128182000,
        description = "Basically new, comes with a clear cover.",
        price = 600.0f,
        university = "STADIO Higher Education",
        field = "Information Technology",
        degree = "Bachelor’s"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_juta_criminal_procedure_handbook_12ed,
        title = "Juta Criminal Procedure Handbook 12ed.",
        author = "J.R Du Toit Et Al.",
        isbn = 9781485118749,
        description = "Highlighted pages.",
        price = 180.0f,
        university = "UP",
        field = "Law",
        degree = "Bachelor’s"
    ),
    Textbook(
        uploadedImageResId = R.drawable.img_textbook_introducing_javafx_8_programming_1ed,
        title = "Introducing JavaFX 8 Programming 1ed",
        author = "Herbert Schildt",
        isbn = 9780071808552,
        description = "Good condition with minor creasing.",
        price = 250.0f,
        university = "Wits",
        field = "Information Technology",
        degree = "Higher Certificate"
    )
)

    fun uploadTextbook(
        uploadedImageResIdInput: Int? = null,
        uploadedImageUriInput: String? = null,
        titleInput: String,
        authorInput: String,
        isbnInput: Long,
        descriptionInput: String,
        priceInput: Float,
        universityInput: String,
        fieldInput: String,
        degreeInput: String) : Boolean {

            val newTextbook = Textbook(
                uploadedImageResIdInput,
                uploadedImageUriInput,
                titleInput,
                authorInput,
                isbnInput,
                descriptionInput,
                priceInput,
                universityInput,
                fieldInput,
                degreeInput
            )

        textbooks.add(newTextbook)
        return true
}