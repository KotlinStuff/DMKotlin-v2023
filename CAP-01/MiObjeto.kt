object MiObjeto {
    lateinit var usuario: String
    val base_URL: String = "http://www.javiercarrasco.es/"

    init {
        usuario = "Javier"
        println("Método INIT, solo se llamará una vez.")
    }

    fun funcionDeMiObjeto() {
        println("Has llamado a la función de un objeto.")
    }
}

fun main() {
    var nombreUsuario = MiObjeto.usuario

    println(nombreUsuario)

    MiObjeto.usuario = "Juan"
    nombreUsuario = MiObjeto.usuario

    println(nombreUsuario)
    println(MiObjeto.funcionDeMiObjeto())
}