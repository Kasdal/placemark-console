package org.setu.placemark.console.main
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.setu.placemark.console.model.PlacemarkModel
import java.io.File
import java.sql.DriverManager
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.reflect.Type
import java.sql.Connection
import java.sql.SQLException

private val logger = KotlinLogging.logger {}
var placemarks = ArrayList<PlacemarkModel>()
//create mysql database connection
val url = "jdbc:mysql://localhost:3306/placemarkdb"
val username = "root"
val password = ""

//connect to database
val connection = DriverManager.getConnection(url, username, password)
val statement = connection.createStatement()
val resultSet = statement.executeQuery("SELECT * FROM placemarks")
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
            5 -> dummyData()
            6 -> save()
            7 -> load()
            8 -> connectToDatabase()
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
    println(" 6. Save Data")
    println(" 7. Load Data")
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

    var aPlacemark = PlacemarkModel()
    println("Add Placemark")
    println()
    print("Enter a Title : ")
    aPlacemark.title = readLine()!!
    print("Enter a Description : ")
    aPlacemark.description = readLine()!!
    println("You entered [ " + aPlacemark.title + " ] for title " +
            "and [ " + aPlacemark.description + " ] for description")

    if (aPlacemark.title.isNotEmpty() && aPlacemark.description.isNotEmpty()) {
        aPlacemark.id = placemarks.size.toLong()
        placemarks.add(aPlacemark.copy())
        logger.info("Placemark Added : [ $aPlacemark ]")


    }
    else
        logger.info("Placemark Not Added")

}

fun updatePlacemarks(){
//Search by id and update placemarks
    println("Update Placemark")
    println()
    listAllPlacemarks()
    var searchId = getId()
    val aPlacemark = search(searchId)


    var inputTitle = "";
    var inputDes = "";
    if (aPlacemark != null) {
        print("Enter a new Title for [ " + aPlacemark.title + " ] : ")
        inputTitle = readLine()!!
        print("Enter a new Description for [ " + aPlacemark.description + " ] : ")
        inputDes = readLine()!!
        if (inputTitle.isNotEmpty() && inputDes.isNotEmpty()) {
            aPlacemark.title = inputTitle
            aPlacemark.description = inputDes
            logger.info("Placemark Updated : [ $aPlacemark ]")
        }   
        else
            logger.info("Placemark Not Updated")
    }
    else
        println("Placemark Not Updated...")
}

fun listAllPlacemarks() {
    println("List All Placemarks")
    println()
    placemarks.forEach { logger.info("${it}") }
}




fun getId() : Long {
    var strId : String?
    var searchId : Long
    print("Enter id to search for : ") //ask user to enter id
    strId = readLine()!! //read user input
    searchId = if (strId.toLongOrNull() != null) //check if input is a number
        strId.toLong() //convert input to a number
    else
        -9 //if not a number return -9  to indicate error
    return searchId     //return the number
}



fun search(id: Long) : PlacemarkModel? {
    var foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.id == id }   //find the placemark with the id
    return foundPlacemark   //return the placemark
}



fun searchPlacemarks() {
    var searchId = getId() //get the id to search for

    var foundPlacemark = search(searchId) //search for the placemark
    print(foundPlacemark)   

}


fun dummyData() {
    placemarks.add(PlacemarkModel(1, "New York New York", "So Good They Named It Twice"))
    placemarks.add(PlacemarkModel(2, "Ring of Kerry", "Some place in the Kingdom"))
    placemarks.add(PlacemarkModel(3, "Waterford City", "You get great Blaas Here!!"))
}

//save data to json file
fun save() {
    val jsonString = Gson().toJson(placemarks, ArrayList::class.java)
    val writer = PrintWriter(FileWriter("placemarks.json"))
    writer.print(jsonString)
    writer.close()
}

//load data from json file
fun load() {
    val jsonString = File("placemarks.json").readText()
    val placemarksType = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type
    placemarks = Gson().fromJson(jsonString, placemarksType)
}

class Gson {
    fun toJson(placemarks: java.util.ArrayList<PlacemarkModel>, java: Class<java.util.ArrayList<*>>): Any {
        return placemarks


    }

    fun fromJson(jsonString: String, placemarksType: Type?): ArrayList<PlacemarkModel> {

        return placemarks

    }

}

//connect to mysql database
fun connectToDatabase() {
    try {
        Class.forName("com.mysql.jdbc.Driver")
    } catch (e: ClassNotFoundException) {
        throw RuntimeException("Cannot find the driver in the classpath!", e)
    }

    println("MySQL JDBC Driver Registered!")
    var connection: Connection? = null

    try {
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/placemarkdb", "root", "")

    } catch (e: SQLException) {
        println("Connection Failed! Check output console")
        e.printStackTrace()
        return
    }

    if (connection != null) {
        println("You made it, take control your database now!")
    } else {
        println("Failed to make connection!")
    }
}




