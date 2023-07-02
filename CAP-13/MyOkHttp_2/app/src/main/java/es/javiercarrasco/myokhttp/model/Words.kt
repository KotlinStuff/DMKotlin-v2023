package es.javiercarrasco.myokhttp.model

data class Words (
    val idPalabra: String,
    val palabra: String,
    val definicion: String
){
    override fun toString(): String {
        return "$idPalabra - $palabra: ${
            if (definicion.length > 30)
                definicion.subSequence(0, 15)
            else definicion
        }...\n"
    }
}