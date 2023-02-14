class Empleados(nom: String, ape: String) {
    var idEmpleados: Int
    lateinit var nombre: String
    lateinit var apellido: String

    init {
        // Se ejecuta con cada instancia de Empleado.
        println("Init clase Empleado.")
        this.nombre = nom
        this.apellido = ape
        numEmpleados++
        idEmpleados = numEmpleados

    }

    companion object {
        // Se ejecutará una sola vez.
        init {
            println("Init Companion Object Empleado.")
        }

        var numEmpleados = 0
    }
}

fun main() {
    val empleado1 = Empleados("Javier", "Carrasco")
    val empleado2 = Empleados("Nacho", "Cabanes")

    println("Empleado 1: ${empleado1.nombre} ${empleado1.apellido}")
    println("Empleado 2: ${empleado2.nombre} ${empleado2.apellido}")
    println("Total empleados: ${Empleados.numEmpleados}")
}