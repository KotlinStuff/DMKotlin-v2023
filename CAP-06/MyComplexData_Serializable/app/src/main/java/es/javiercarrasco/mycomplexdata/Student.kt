package es.javiercarrasco.mycomplexdata

import java.io.Serializable

data class Student(
    val idStudent: Int,
    val name: String,
    val surname: String,
    val age: Int
) : Serializable
