package es.javiercarrasco.myroom.data.model

import androidx.room.Embedded
import androidx.room.Relation

// Relación de 1 a 1.
data class SupersWithEditorials(
    @Embedded val supers: SuperHero,
    @Relation(
        parentColumn = "idEditorial",
        entityColumn = "idEd"
    ) val editorials: Editorial
)