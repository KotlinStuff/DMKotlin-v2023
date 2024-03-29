package es.javiercarrasco.myroom.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Editorial")
data class Editorial(
    @PrimaryKey(autoGenerate = true)
    val idEd: Int = 0,
    val name: String? = null,
    val year: Int? = 0
)