package es.javiercarrasco.myroom.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EditorialsWithIllustrators(
    @Embedded val editorial: Editorial,
    @Relation(
        parentColumn = "idEd",
        entityColumn = "idIllustrator",
        associateBy = Junction(EditorialsIllustrators::class)
    ) val illustrator: List<Illustrator>
)

data class IllustratrosWithEditorials(
    @Embedded val illustrator: Illustrator,
    @Relation(
        parentColumn = "idIllustrator",
        entityColumn = "idEd",
        associateBy = Junction(EditorialsIllustrators::class)
    ) val editorial: List<Editorial>
)