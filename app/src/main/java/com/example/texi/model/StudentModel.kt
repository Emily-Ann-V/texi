package com.example.texi.model

data class Student(
    var fullName: String,
    var emailAddress: String,
    var studentNumber: Int,
    var university: String,
    var field: String,
    var degree: String,
    var graduationYear: Int,
    var password: String
)

val students = mutableListOf<Student>()

fun registerStudent(
    fullNameRegisterInput: String,
    emailAddressRegisterInput: String,
    studentNumberRegisterInput: Int,
    universityRegisterInput: String,
    fieldRegisterInput: String,
    degreeRegisterInput: String,
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
        fieldRegisterInput,
        degreeRegisterInput,
        graduationYearRegisterInput,
        passwordRegisterInput
    )

    students.add(newStudent)

    return true
        }
}

fun loginStudent(studentEmailLoginInput: String, passwordLoginInput: String): Boolean {

    val existingStudent = students.find {
        it.emailAddress == studentEmailLoginInput &&
                it.password == passwordLoginInput
    }

    if (existingStudent == null) {
        return false
    } else {

        LoggedInStudent.fullName = existingStudent.fullName
        LoggedInStudent.emailAddress = existingStudent.emailAddress
        LoggedInStudent.studentNumber = existingStudent.studentNumber
        LoggedInStudent.university = existingStudent.university
        LoggedInStudent.field = existingStudent.field
        LoggedInStudent.degree = existingStudent.degree
        LoggedInStudent.graduationYear = existingStudent.graduationYear
        LoggedInStudent.password = existingStudent.password

        return true
    }
}

fun updateProfile(
    fullNameProfileInput: String,
    emailAddressProfileInput: String,
    passwordProfileInput: String
): Boolean {

    val existingEmail = LoggedInStudent.emailAddress

    val duplicate = students.any {
        it.emailAddress == emailAddressProfileInput &&
                it.emailAddress != existingEmail
    }

    if (duplicate){
        return false
    } else {

    val index = students.indexOfFirst {
        it.emailAddress == existingEmail
    }

    if (index == -1) return false

    val updatedStudent = students[index].copy(
        fullName = fullNameProfileInput,
        emailAddress = emailAddressProfileInput,
        password = passwordProfileInput
    )

    students[index] = updatedStudent

    LoggedInStudent.fullName = fullNameProfileInput
    LoggedInStudent.emailAddress = emailAddressProfileInput
    LoggedInStudent.password = passwordProfileInput

    return true
    }
}