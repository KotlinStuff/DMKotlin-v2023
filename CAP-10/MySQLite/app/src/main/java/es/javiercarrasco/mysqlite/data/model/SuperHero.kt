package es.javiercarrasco.mysqlite.data.model

data class SuperHero(
    val id: Int = 0,
    val superName: String? = null,
    val realName: String? = null,
    val favorite: Int = 0,
    val editorial: Editorial = Editorial()
)