package fileoperation

import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter

object FileOperation{
    private lateinit var writer : FileWriter
    private lateinit var reader : FileReader
    private val path = System.getProperty("user.dir")
    private var directory: File = File("$path\\Files")
    init {


    }
    suspend fun writeToFile(fileName : String, jsonString: String) {
        println("In write file \n $jsonString")
        try{
            if(!this.directory.exists()){
                this.directory.mkdirs()
                // println("Direcoty made")
            }
            writer = FileWriter(File("${this.directory}\\${fileName}.json"))
            writer.write(jsonString)
            writer.close()
        }
        catch (e : FileNotFoundException){
            e.printStackTrace()
        }

    }
    suspend  fun readFromFile(fileName : String): String{

        var jsonString : String = ""
        try{
            reader  = FileReader(java.io.File("$directory\\${fileName}.json"))
            jsonString = reader.readText()
            if(jsonString.isEmpty()){
                jsonString = "[]"
            }
            //println(jsonString)
            reader.close()
            return jsonString
        }
        catch(e : FileNotFoundException){
            e.printStackTrace()
        }
        return jsonString
    }
}