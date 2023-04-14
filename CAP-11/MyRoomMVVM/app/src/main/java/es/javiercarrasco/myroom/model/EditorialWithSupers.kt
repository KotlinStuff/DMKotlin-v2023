package es.javiercarrasco.myroom.model

import androidx.room.Embedded
import androidx.room.Relation

// Relación de 1 a N.
data class EditorialWithSupers(
    @Embedded val editorial: Editorial,
    @Relation(
        parentColumn = "idEd",
        entityColumn = "idEditorial"
    ) val supers: List<SuperHero>
) {
    override fun toString(): String {
        var result = ""
        this.supers.forEach {
            result += "${it.idSuper} ${it.superName}\n"
        }
        return "ID: ${this.editorial.idEd} - Editorial: ${this.editorial.name}\n$result"
    }
}
