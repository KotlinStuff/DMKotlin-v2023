sealed class Vehiculo(var nRuedas: Int)
data class Motocicleta(var ruedas: Int = 2) : Vehiculo(ruedas)
data class Turismo(var ruedas: Int = 4, var puertas: Int = 2) : Vehiculo(ruedas)

fun main() {
    val vehiculos = listOf(Motocicleta(), Motocicleta(), Turismo())

    vehiculos.forEach {
        println(tipoVehiculo(it))
    }
}

fun tipoVehiculo(vehiculo: Vehiculo): String {
    return when (vehiculo) {
        is Motocicleta -> "Es del tipo Motocicleta"
        is Turismo -> "Es del tipo Turismo"
        else -> throw IllegalArgumentException("Tipo desconocido")
    }
}