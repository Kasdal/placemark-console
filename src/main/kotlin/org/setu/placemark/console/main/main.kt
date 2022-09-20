package org.setu.placemark.console.main
import mu.KotlinLogging
private val logger = KotlinLogging.logger {}


fun main(args: Array<String>){
    logger.info { "Launching Placemark Console App" }
    println("Placemark Kotlin App Version 1.0")
    var input: Int

    do {
        input = menu()
        when(input){
            1 -> addPlacemark()
            2 -> updatePlacemarks()
            3 -> listAllPlacemarks()
            4 -> searchPlacemarks()
            5 -> importSavedData()
            -1 -> println("Exiting...")
            else -> println("Selection is not available, please choose another option from the list")
        }
        println()
    }
    while (input != -1)
    logger.info {"Console is shutting down..."}

}

fun menu() : Int {

    var option : Int
    var input : String? = null

    println("Main Menu")
    println(" 1. Add Placemark")
    println(" 2. Update Placemark")
    println(" 3. List All Placemarks")
    println(" 4. Search Placemarks")
    println(" 5. Import Saved Data")
    println(" -1. Exit")
    println()
    println(" Choose option from the list : ")
    input = readLine()!!
    option = if (input.toIntOrNull() !=null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option

}

fun addPlacemark(){

}

fun updatePlacemarks(){

}

fun listAllPlacemarks(){

}

fun searchPlacemarks(){

}

fun importSavedData(){

}