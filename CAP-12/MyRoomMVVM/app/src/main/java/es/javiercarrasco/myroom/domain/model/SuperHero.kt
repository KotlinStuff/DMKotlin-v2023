package es.javiercarrasco.myroom.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuperHero(
    @PrimaryKey(autoGenerate = true)
    val idSuper: Int = 0,
    val superName: String? = null,
    val realName: String? = null,
    val favorite: Int = 0,
    val idEditorial: Int = 0
)