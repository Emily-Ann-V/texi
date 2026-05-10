package com.example.texi.model

class Student(
    var fullName: String,
    var emailAddress: String,
    var studentNumber: Int,
    var university: String,
    var graduationYear: Int,
    var password: String
)

val students = mutableListOf<Student>()

fun registerStudent(
    fullNameRegisterInput: String,
    emailAddressRegisterInput: String,
    studentNumberRegisterInput: Int,
    universityRegisterInput: String,
    graduationYearRegisterInput: Int,
    passwordRegisterInput: String
): Boolean {

    val duplicate = students.any {
        it.emailAddress == emailAddressRegisterInput
    }

    if (duplicate) {
        return false
    } else {

    val newStudent = Student(
        fullNameRegisterInput,
        emailAddressRegisterInput,
        studentNumberRegisterInput,
        universityRegisterInput,
        graduationYearRegisterInput,
        passwordRegisterInput
    )

    students.add(newStudent)

    return true
        }
}

fun loginStudent(studentEmailLoginInput: String, passwordLoginInput: String): Boolean {

    val authentication = students.any {
        it.emailAddress == studentEmailLoginInput &&
                it.password == passwordLoginInput
    }

    return authentication
}