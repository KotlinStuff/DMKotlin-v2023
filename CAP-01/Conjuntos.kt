fun main() {
    // Conjunto inmutable, una vez creada no se puede modificar (read-only).
    //val setOfItems = setOf(1, 2, 3, 4)

    //println(setOfItems) // Salida: [1, 2, 3, 4]

    val setOfItems = mutableSetOf<Int>()

    setOfItems.add(1)
    setOfItems.add(2)
    setOfItems.add(3)
    println(setOfItems) // Salida: [1, 2, 3]

    setOfItems.remove(2) // Elimina por elemento, no por índice.
    println(setOfItems) // Salida: [1, 3]


    println("ver. 1")
    for (item in setOfItems) {
        println(item)
    }

    println("ver. 2")
    setOfItems.forEach {
        println(it)
    }

    println("ver. 3")
    val setOfItemsIterator = setOfItems.iterator()

    while (setOfItemsIterator.hasNext()) {
        println(setOfItemsIterator.next())
    }
}