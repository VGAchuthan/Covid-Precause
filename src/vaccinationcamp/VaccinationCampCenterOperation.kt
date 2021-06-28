package vaccinationcamp

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

interface  VaccineCustomerHandler{
    fun getCampDetails() : List<VaccinationCamp>
    fun getDetailsByDate(date : Date) : List<VaccinationCamp>
    fun getDetailsByVaccine(vaccineName : String) : List<VaccinationCamp>
    fun getDetailsByArea(area : String) : List<VaccinationCamp>
    fun getDetailsByPincode(pincode : String) : List<VaccinationCamp>
    fun getDetailsByAgeGroup(ageGroup : String) : List<VaccinationCamp>

}
interface  VaccineAdminHandler{
    fun addCamp(vaccinationCamp : VaccinationCamp)  : Boolean
    fun getCampDetails() : List<VaccinationCamp>
    fun checkIfCampAlreadyAvailable(vaccinationCamp : VaccinationCamp) : Boolean
    //fun writeToFile()
}
interface  StartUpHandler{
     suspend fun readFromFile()  : ArrayList<VaccinationCamp>
}
class VaccinationCampCenterOperation : VaccineCustomerHandler, VaccineAdminHandler, StartUpHandler{
    val campHandler : VaccineCampHandler = VaccinationCampList
    val fileWriter = FileOperationHelper()
    val fileReader = FileOperationHelper()
    override fun addCamp(vaccinationCamp : VaccinationCamp) : Boolean{
        try{
            checkIfCampAlreadyAvailable(vaccinationCamp)
            //var flag : Boolean  = false //= campHandler.addCamp(vaccinationCamp)
           // for(v in 1..100){
                val flag = campHandler.addCamp(vaccinationCamp)
                writeToFile()
            //}

            return  flag
        }catch(e :Exception){
            e.printStackTrace()
        }

            return false
    }
    override fun getCampDetails() : List<VaccinationCamp>{
        var today = LocalDate.now()
        var todayStartTime = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val listOfVaccineCamp  = campHandler.getCampList().filter { it.campDetails.date.contains(todayStartTime) || (it.campDetails.date[0].compareTo(todayStartTime) == 1) }
        return listOfVaccineCamp
    }
    override fun checkIfCampAlreadyAvailable(vaccinationCamp : VaccinationCamp) : Boolean{
        val listOfVaccineCamps = VaccinationCampList.getCampList().toList()
        for(camps in listOfVaccineCamps){
            if(camps.campDetails.date.containsAll(vaccinationCamp.campDetails.date)&&
                    camps.campDetails.place == vaccinationCamp.campDetails.place&&
                    camps.contactDetails.containsAll(vaccinationCamp.contactDetails)&&
                    camps.organiser.containsAll(vaccinationCamp.organiser)){
                //println("camp exists")
                return true
            }
        }
        return false
    }

    override fun getDetailsByDate(searchDate: Date): List<VaccinationCamp> {
        val listOfCamps = this.getCampDetails() //campHandler.getCampList()//.toList().groupBy { it.vaccines.forEach { it.name } }.values.flatten().
        //var list = ArrayList<VaccinationCamp>()
        return listOfCamps.filter { it.campDetails.date.contains(searchDate) }
        /*for(camp in listOfCamps){
            for(date in camp.campDetails.date){
                if(date == searchDate){
                    list.add(camp)
                    break
                }
            }
        }
        return list*/
    }

    override fun getDetailsByVaccine(vaccineName: String): List<VaccinationCamp> {
        val listOfCamps = this.getCampDetails()//VaccinationCampList.getCampList()//.toList().groupBy { it.vaccines.forEach { it.name } }.values.flatten().
        var list = ArrayList<VaccinationCamp>()

        //val vaccineList = listOfCamps.map{ it.vaccines }.forEach { it. }
//        return listOfCamps.filter { it.vaccines.filter { it.name == vaccineName } }
        //listOfCamps.forEach { list.add( it.vaccines.filter { it.name == vaccineName } )}
        //listOfCamps.map { it.vaccines }.flatMapIndexed { index: Int, arrayList: ArrayList<Vaccine> -> arrayList.contains(vaccineName) }
        //listOfCamps.filter { it.vaccines in filter.keys }
        for(camp in listOfCamps){
            for(vaccine in camp.vaccines){
                if(vaccine.name == vaccineName){
                    list.add(camp)
                    break
                }
            }
        }
        return list
    }

    override fun getDetailsByArea(area: String): List<VaccinationCamp> {
        val listOfCamps = this.getCampDetails().filter { it.campDetails.area == area }
        return listOfCamps
    }

    override fun getDetailsByPincode(pincode: String): List<VaccinationCamp> {
        val listOfCamps = this.getCampDetails().filter { it.pincode == pincode }
        return listOfCamps
    }

    override fun getDetailsByAgeGroup(ageGroup: String): List<VaccinationCamp> {
        val listOfCamps = this.getCampDetails().filter { it.ageGroup.contains(ageGroup)}
        return listOfCamps
    }

    private fun writeToFile() {
        //CoroutineScope(Dispatchers.IO).launch {
            fileWriter.writeToFile(campHandler.getCampList())
            //println("write to file coro")
            //println("${Date(System.currentTimeMillis())}")
        //}


    }
    override suspend fun   readFromFile()  : ArrayList<VaccinationCamp>{
        //val list : ArrayList<VaccinationCamp>
//        println("${Date(System.currentTimeMillis())}")
        //list=CoroutineScope(Dispatchers.IO).async { fileReader.readFromFile() }.await()
//        println(" InList\n${list}")
        return fileReader.readFromFile()
    }
}