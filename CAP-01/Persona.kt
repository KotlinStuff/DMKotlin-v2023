open class Persona(nombre: String, apellido: String) {
    var nombre: String = "Vacío"
        set(value) {
            field = if (value.isEmpty()) "Sin nombre" else value
        }
        get() = "Nombre: $field"

    var apellido: String = "Vacío"
        get() {
            return "Apellido: " + field.uppercase()
        }
        set(value) {
            field = if (value.isEmpty()) "Sin apellido" else value
        }

    var edad: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
        }

    var anyo: Int = 0

    // Constructor primario
    init {
        this.nombre = nombre
        this.apellido = apellido
    }

    // Constructor secundario
    constructor(nombre: String, apellido: String, edad: Int) : this(nombre, apellido) {
        this.nombre = nombre
        this.apellido = apellido
        this.edad = edad
    }

    // Otro constructor secundario
    constructor(nombre: String, apellido: String, edad: Int, anyo: Int) :
            this(nombre, apellido) {
        this.nombre = nombre
        this.apellido = apellido
        this.edad = edad
        this.anyo = anyo
    }

    fun getNombreCompleto() = "$nombre $apellido"
}

class Conductor(nombre: String, apellido: String, permiso: String)
    : Persona(nombre, apellido) {
    var permiso: String = ""
        get() {
            return "Permiso: " + field.uppercase()
        }
        set(value) {
            field = if (value.isEmpty()) "No tiene permiso" else value
        }

    init {
        this.permiso = permiso
    }
}

fun main() {
    val persona1 = Persona("Nacho", "Cabanes")
    val persona2 = Persona("Javier", "Carrasco", 42)
    val persona3 = Persona("Pedro", "Pérez", 53, 1966)

    println("Persona 1")
    println(persona1.nombre)
    println(persona1.apellido)
    println("Edad: " + persona1.edad + "\n")

    println("Persona 2")
    println(persona2.nombre)
    println(persona2.apellido)
    println("Edad: " + persona2.edad + "\n")

    println("Persona 3")
    println(persona3.nombre)
    println(persona3.apellido)
    println("Edad: " + persona3.edad)
    println("Año: " + persona3.anyo)
}