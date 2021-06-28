package vaccinationcamp

//import com.sun.xml.internal.ws.developer.Serialization
import java.util.*

//import kotlinx.serialization.*
//import kotlinx.serialization.json.*
interface VaccineCampHandler{
    fun getCampList() : ArrayList<VaccinationCamp>
    fun addCamp(vaccinationCamp : VaccinationCamp) : Boolean
    fun getCamp(index : Int) : VaccinationCamp
    suspend fun setCampListFromFile()
}
object VaccinationCampList  : VaccineCampHandler{
   private var listOfVaccinationCampList : ArrayList<VaccinationCamp> = ArrayList<VaccinationCamp>()
    override fun getCampList() : ArrayList<VaccinationCamp>{
        return this.listOfVaccinationCampList

    }
    override fun addCamp(vaccinationCamp : VaccinationCamp) : Boolean {
        return this.listOfVaccinationCampList.add(vaccinationCamp)
    }
    override fun getCamp(index : Int) : VaccinationCamp{
        return this.listOfVaccinationCampList[index]
    }
//    fun setCampList(listOfCamp : List<VaccinationCamp>){
//        this.listOfVaccinationCampList = ArrayList(listOfCamp)
//    }
    override   suspend  fun  setCampListFromFile() {
        val startUpHandler: StartUpHandler = VaccinationCampCenterOperation()
        //for(vlue in 1..100)
        this.listOfVaccinationCampList =  startUpHandler.readFromFile()

    }
}

data class VaccinationCamp(var campDetails : CampDetails,
                           var vaccines : ArrayList<Vaccine>,var organiser : ArrayList<String>,
                           var sponsor : String, var pincode : String, var ageGroup : ArrayList<String>,
                           var onlyFor : String, var contactDetails : ArrayList<ContactDetails>)


data class CampDetails(var date : ArrayList<Date>, var place: String, var duration : String, var area : String )

data class Dosage(var number : Int, var count : Int)

data class Vaccine(var name : String, var dosageAvailablity :List<Dosage>)

data class ContactDetails(var name : String, var contactNumber : String)