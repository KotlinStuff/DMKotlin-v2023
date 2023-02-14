fun main() {
    // Mapa inmutable, una vez creada no se puede modificar (read-only).
    //val mapOfItems = mapOf(Pair(1, "Item 1"), Pair(2, "Item 2"), Pair(3, "Item 3"))

    //println(mapOfItems) // Salida: {1=Item 1, 2=Item 2, 3=Item 3}

    val mapOfItems = mutableMapOf<Int, String>()

    mapOfItems[0] = "Item 0" // mapOfItems.put(0, "Item 0")
    mapOfItems[1] = "Item 1"
    mapOfItems[2] = "Item 2"
    println(mapOfItems) // Salida: {0=Item 0, 1=Item 1, 2=Item 2}

    mapOfItems.remove(1)
    println(mapOfItems) // Salida: {0=Item 0, 2=Item 2}
    

    println("ver. 1")
    for (item in mapOfItems) {
        println(item)
    }

    println("ver. 2")
    mapOfItems.forEach {
        println(it)
    }

    println("ver. 3")
    val mapOfItemsIterator = mapOfItems.iterator()

    while (mapOfItemsIterator.hasNext()) {
        println(mapOfItemsIterator.next())
    }
}