

//import java.util.
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import users.Customer
import users.CustomerList
import users.VaccineAdminList
import users.VaccineCampAdmin
import vaccinationcamp.*
import java.util.*
val campHandler : VaccineCampHandler = VaccinationCampList
enum class Users{
    CUSTOMER, PROVIDER, VOLUNTEER, VACCINECAMPADMIN
}
fun main(/*args : Array<String>*/) = runBlocking{
    //var number  = getProperDateWithPattern("dd/MM/yyyy")

    CoroutineScope(Dispatchers.IO).launch {getInitialValues()}


    do{
//        println(VaccinationCampList.getCampList().toList().groupBy { it.vaccines }.v
    println("Login as 1.Customer\t2.Provider\t3.Volunteer\t4.Vaccine Camp Admin\t5.Logout")

        var choice = Input.getIntValue()

    //val userType : Users = Users.values()[choice-1]
    println("selected $choice")
    when(choice){
        1 ->{
            var index : Int = 1
            for(customer in CustomerList.getCustomersList()){
                println("$index. ${customer.customerName}")
                index++
            }
            val selectPerson = Input.getIntValue()
            val currentUser = CustomerList.getCustomersList().get(selectPerson-1)
            getCustomerFunctionalities()}
       /* 2 ->{getProviderFunctionalities()}
        3 ->{getVolunteerFunctionalities()}*/
        4 ->{
            var index : Int = 1
            for(admin in VaccineAdminList.getAdminList()){
                println("$index. ${admin.adminName}")
                index++
            }
            val selectPerson = Input.getIntValue()
            val currentUser = VaccineAdminList.getAdminList().get(selectPerson - 1)

            getVaccineCampAdminFunctionalities()}
        5 -> {System.exit(5)}
    }
    }while(true)
}
private fun getCustomerFunctionalities(){
    println("1. View Vaccination Camp Details")
    var  choice  = Input.getIntValue()
    when(choice){
        1->{viewVaccinationCampDetails()}
    }
}
private fun getVaccineCampAdminFunctionalities(){
    var flag =1
    do{
        println("1. View Vaccination Camp Details\n2.Add Vaccination Camp Details")
        val choice = Input.getIntValue()
        when(choice){
            1 -> {viewVaccinationCampDetails()}
            2 -> {addVaccinationCampDetails()}
            //3 -> {}
        }
        println("Do you want to continue\n1.Yes\t2.No")
        flag = Input.getIntValue()


    }while(flag == 1)


}
private fun viewVaccinationCampDetails(){
    val handler : VaccineCustomerHandler = VaccinationCampCenterOperation()
    val campDetails : List<VaccinationCamp> = handler.getCampDetails()
//    for(camp in campDetails){
//        println(camp)
//    }
    var flag =1
    do{
        println("1.No Filter\t2.Filter by Area\t3.Filter By Date\t4.Filter by Vaccine\n" +
                "5.Filter by Pincode\t6.Filter by Age Group")
        var choice = Input.getIntValue()
        when(choice){
            1-> {showFilteredView(campDetails)}
            2-> {

                val area = reader.readLine()
                showFilteredView(handler.getDetailsByArea(area))}
            3->{
                println("Enter Date")
                val date = Input.getProperDateWithPattern("dd/MM/yyyy")
                showFilteredView(handler.getDetailsByDate(date))

            }
            4->{
                println("Enter Vaccine Name")
                val vaccineName =Input.getProperString()
                showFilteredView(handler.getDetailsByVaccine(vaccineName))
            }
            5 -> {
                println("Enter Pincode")
                val pincode = Input.getProperPincode()
                showFilteredView(handler.getDetailsByPincode(pincode))
            }
            6 ->{
                println("Enter Age Group")
                val ageGroup = Input.getProperString()
                showFilteredView(handler.getDetailsByAgeGroup(ageGroup))
            }
        }
        println("Do You want to Continue Filter Operations\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1)
}
private  fun showFilteredView(listOfCamp : List<VaccinationCamp>){
    //val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    if (listOfCamp.isEmpty()){
        println("No Data Found for this Filter Value")
    }else{
        for(camp in listOfCamp){
            println(camp)
            println()
        }

    }


}
private fun addVaccinationCampDetails(){
    println("Enter Camp Center Informations")
    //println("Select Date")
    var flag : Int
    var dates : ArrayList<Date> = ArrayList()
    do{
        var date = Input.getProperDateWithPattern("dd/MM/yyyy")
        dates.add(date)
        println("Do You want to add another date\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)

    println("Enter Place")
    var place = Input.getProperString()
    println("Enter Duration")

    var duration  = Input.getProperString()
    println("Enter Area")
    var area  = Input.getProperString()
    val campDetails = CampDetails(dates, place, duration, area)
    //var flag =1
    var dosageFlag = 1
    var vaccines : ArrayList<Vaccine> =  ArrayList<Vaccine>()

    do{
        flag =1
        dosageFlag =1
        println("Enter Vaccine Name")
        var vaccineName : String= Input.getProperString()
        var listOfDosage : ArrayList<Dosage> = ArrayList()

        println("Enter Dosage Details")
        do{
            println("Enter Dose Number")
            var doseNumber = Input.getIntValue()
            println("Enter Dosage Count")
            var dosesCount = Input.getIntValue()
            var dosage = Dosage(doseNumber,dosesCount)
            listOfDosage.add(dosage)
            println("Do You want to add another dose details \n1.Yes\t2.No")
            dosageFlag = Input.getIntValue()
            println("DOSAGEFLAG $dosageFlag")

        }while(dosageFlag ==1)
        vaccines.add(Vaccine(vaccineName,listOfDosage))
        println("Do you want to add another vaccine \n1.Yes\t2.No")
        flag = Input.getIntValue()

    }while(flag == 1)
    var organisers : ArrayList<String> = ArrayList()

    do{
        println("Enter Organiser Name")
        var organiser : String = Input.getProperString()
        organisers.add(organiser)
        println("Do you want to add another organiser\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1 )

    println("Enter Sponsor  Name")
    val sponsor = Input.getProperString()
    println("Enter Pincode")
    val pincode = Input.getProperPincode()
    var ageGroups : ArrayList<String> = ArrayList()
    do{
        println("Enter age group")
        var ageGroup = Input.getProperString()
        ageGroups.add(ageGroup)
        println("Do you want to add another age group\n1.Yes\t2.No")
        flag = Input.getIntValue()

    }while(flag == 1)

    println("Only for : ")
    val onlyFor  = Input.getProperString()
    var contactDetails : ArrayList<ContactDetails> = ArrayList()
    do{
        println("Enter Contact Person Name")
        var contactPersonName = Input.getProperString()
        println("Enter Contact Person Number")
        var contactNumber = Input.getProperMobileNumber()
        contactDetails.add(ContactDetails(contactPersonName, contactNumber))
        //contactNumbers.add(contactNumber)
        println("Do you want to add another contact person detail\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1)

    val vaccineCampDetails = VaccinationCamp(campDetails,vaccines,organisers,sponsor,pincode,ageGroups,onlyFor,contactDetails)
    val adminHandler : VaccineAdminHandler = VaccinationCampCenterOperation()
    println(vaccineCampDetails)
    if(!adminHandler.checkIfCampAlreadyAvailable(vaccineCampDetails)){
        adminHandler.addCamp(vaccineCampDetails)
        //adminHandler.writeToFile()
    }
    else{
        println("Camp Already Exists")
    }



}
suspend private fun  getInitialValues(){
   // println("insude initil vlue")
    CoroutineScope(Dispatchers.IO).launch{
        campHandler.setCampListFromFile()
        //println("Inside setcamp Coro")
    }

    // Two Customer Values
    CustomerList.addCustomer(Customer("Customer1","9090909090","customer1@gmail.com","11, Street 1, Anna Nagar, 666991") )
    CustomerList.addCustomer(Customer("Customer2","9087654321","customer2@gmail.com","21R, Street 2, Senoy Nagar, 623451"))
    // Two Vaccine Admin Values
    VaccineAdminList.addVaccineAdmin(VaccineCampAdmin("Admin1","9876567890","admin1@gmail.com","12A, Cross Street 1, Anna Nagar, 654321"))
    VaccineAdminList.addVaccineAdmin(VaccineCampAdmin("Admin2","9876554433","admin2@gmail.com","12V, Cross Street 6, Anna Nagar, 654322"))

}