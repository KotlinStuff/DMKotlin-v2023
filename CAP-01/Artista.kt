class Artista(
    var id: Long,
    var nombre: String,
    var url: String,
    var mbid: String
)

fun main(args: Array<String>) {
    // No compilaría, Artista no puede ser nulo.
    //var notNullArtista: Artista = null

    // Artista puede ser nulo.
    val artista: Artista? = null

    // No compilará, artista podría ser nulo.
    artista.toString()

    // Mostrará por pantalla artista si es distinto de nulo.
    artista?.toString()

    // No necesitaríamos utilizar el operador ? si previamente
    // comprobamos si es nulo.
    if (artista != null) {
        artista.toString()
    }

    // Esta operación la utilizaremos si estamos completamente seguros
    // que no será nulo. En caso contrario se producirá una excepción.
    artista!!.toString()

    // También podríamos utilizar el operador Elvis (?:) para dar
    // una alternativa en caso que el objeto sea nulo.
    val nombre = artista?.nombre ?: "vacío"
}