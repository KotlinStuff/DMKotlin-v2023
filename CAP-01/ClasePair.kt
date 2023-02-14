fun main() {
    val parUno = Pair("Hola", "Mundo")
    val parDos = Pair("Adiós amigos", 150)
    val (usuario, contrasenya) = Pair("javier", "kotlin")

    println("Par UNO-1: ${parUno.first} || Par UNO-2: ${parUno.second}")
    println("Par DOS-1: ${parDos.first} || Par DOS-2: ${parDos.second}")
    println("Usu: $usuario - Pass: $contrasenya")
}