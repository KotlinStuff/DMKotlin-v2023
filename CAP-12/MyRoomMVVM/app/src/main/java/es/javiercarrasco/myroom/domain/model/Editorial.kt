package es.javiercarrasco.myroom.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Editorial")
data class Editorial(
    @PrimaryKey(autoGenerate = true)
    val idEd: Int = 0,
    val name: String? = null
)