package es.javiercarrasco.myroom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Illustrator(
    @PrimaryKey(autoGenerate = true)
    val idIllustrator: Int = 0,
    val nameIllustrator: String? = null
)

@Entity(primaryKeys = ["idIllustrator", "idEd"])
data class EditorialsIllustrators(
    val idIllustrator: Int,
    val idEd: Int
)