package vaccinationcamp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fileoperation.FileOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface FileOperationHandler{
     fun writeToFile(listOfVaccinationCamp : ArrayList<VaccinationCamp>)
      suspend  fun readFromFile() : ArrayList<VaccinationCamp>

}
class FileOperationHelper : FileOperationHandler{
    override  fun writeToFile(listOfVaccinationCamp: ArrayList<VaccinationCamp>) {
        //var jsonArray = JSONArray()
        //println("Inside write file")
        //println(" in file op write${Date(System.currentTimeMillis())}")
        var json = Gson().toJson(listOfVaccinationCamp)
        //println(json)
        CoroutineScope(Dispatchers.IO).launch{
            FileOperation.writeToFile("camps",json.toString())
        }


    }

    override  suspend fun readFromFile() : ArrayList<VaccinationCamp>{
        //println(" in file op red${Date(System.currentTimeMillis())}")
        //val jsonString :String = FileOperation.readFromFile("camps")
        var jsonString = CoroutineScope(Dispatchers.IO).async {
               FileOperation.readFromFile("camps")
        }.await()
//        if(jsonString.isEmpty()){
//            jsonString = ""
//        }

        val gson = Gson()
        val parseType = object : TypeToken<List<VaccinationCamp>>() {}.type
        val vaccnineCampList = gson.fromJson<List<VaccinationCamp>>(jsonString, parseType)
        //println("IN READ FROM ${vaccnineCampList}")

        return ArrayList(vaccnineCampList)

    }
}
