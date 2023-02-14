fun main() {
    // Lista inmutable, una vez creada no se puede modificar (read-only).
    //val listOfItems = listOf("Item 1", "Item 2", "Item 3")

    //println(listOfItems) // Salida: [Item 1, Item 2, Item 3]

    val listOfItems = mutableListOf<String>()

    listOfItems.add("Item 1")
    listOfItems.add("Item 2")
    listOfItems.add("Item 3")
    println(listOfItems) // Salida: [Item 1, Item 2, Item 3]

    listOfItems.removeAt(1)
    println(listOfItems) // Salida: [Item 1, Item 3]


    println("ver. 1")
    for (item in listOfItems) {
        println(item)
    }

    println("ver. 2")
    listOfItems.forEach {
        println(it)
    }

    println("ver. 3")
    val listOfItemsIterator = listOfItems.iterator()

    while (listOfItemsIterator.hasNext()) {
        println(listOfItemsIterator.next())
    }
}