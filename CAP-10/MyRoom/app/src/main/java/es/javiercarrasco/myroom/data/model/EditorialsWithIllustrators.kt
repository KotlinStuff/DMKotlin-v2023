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
) {
    override fun toString(): String {
        var result = ""
        this.illustrator.forEach {
            result += "${it.idIllustrator} ${it.nameIllustrator}\n"
        }
        return "ID: ${this.editorial.idEd} - Editorial: ${this.editorial.name}\n$result"
    }
}

data class IllustratrosWithEditorials(
    @Embedded val illustrator: Illustrator,
    @Relation(
        parentColumn = "idIllustrator",
        entityColumn = "idEd",
        associateBy = Junction(EditorialsIllustrators::class)
    ) val editorial: List<Editorial>
) {
    override fun toString(): String {
        var result = ""
        this.editorial.forEach {
            result += "${it.idEd} ${it.name}\n"
        }
        return "ID: ${this.illustrator.idIllustrator} - Ilustrador: ${this.illustrator.nameIllustrator}\n$result"
    }
}