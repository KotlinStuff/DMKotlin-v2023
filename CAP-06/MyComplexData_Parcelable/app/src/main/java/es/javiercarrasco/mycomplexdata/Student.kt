package es.javiercarrasco.mycomplexdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val idStudent: Int,
    val name: String,
    val surname: String,
    val age: Int
) : Parcelable
