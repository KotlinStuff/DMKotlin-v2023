package es.javiercarrasco.myroom.data.model

import androidx.room.Embedded
import androidx.room.Relation

// Relación de N a 1.
data class SupersWithEditorials(
    @Embedded val editorials: Editorial,
    @Relation(
        parentColumn = "idEd",
        entityColumn = "idEditorial"
    ) val supers: List<SuperHero> = emptyList()
)