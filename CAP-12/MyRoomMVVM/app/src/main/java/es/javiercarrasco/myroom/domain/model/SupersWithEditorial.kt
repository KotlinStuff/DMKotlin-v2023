package es.javiercarrasco.myroom.domain.model

import androidx.room.Embedded
import androidx.room.Relation

// Relación de 1 a 1.
data class SupersWithEditorial(
    @Embedded val supers: SuperHero,
    @Relation(
        parentColumn = "idEditorial",
        entityColumn = "idEd"
    ) val editorial: Editorial
)