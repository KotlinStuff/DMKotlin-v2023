enum class DiasSemana(val numero: Int, val estado: String) {
    LUNES(1, "Trabajando"),
    MARTES(2, "Trabajando"),
    MIERCOLES(3, "Trabajando"),
    JUEVES(4, "Trabajando"),
    VIERNES(5, "Trabajando"),
    SABADO(6, "Descanso"),
    DOMINGO(7, "Descanso")
}

fun main() {
    val dia = DiasSemana.DOMINGO

    val estado = when (dia) {
        DiasSemana.SABADO -> "Fiesta"
        DiasSemana.DOMINGO -> "Descanso dominical"
        else -> "Trabajo"
    }

    println("Variable estado: $estado \n")

    DiasSemana.values().forEach {
        println("${it.numero} - ${it.name} - ${it.estado}")
    }
}