package foodorder

import enums.EatingTimeType
import users.Volunteer

interface VolunteerFilterHandler{
    fun searchByArea(area : String) : List<VolunteerDetails>
    fun searchByFoodName(foodname : String) : List<VolunteerDetails>
    fun getVolunteersList() :  ArrayList<VolunteerDetails>
    fun getVolunteer(VolunteerId : Int) : VolunteerDetails
    //fun getVolunteerByI

}
/*object VolunteersList : VolunteerFilterHandler{
    private var listOfVolunteer : ArrayList<VolunteerDetails> = ArrayList()
    override fun getVolunteersList() :  ArrayList<VolunteerDetails>{
        return this.listOfVolunteer
    }
    fun addVolunteer(VolunteerDetails : VolunteerDetails) : Boolean{
        return this.listOfVolunteer.add(VolunteerDetails)

    }
    override fun getVolunteer(VolunteerId: Int) : VolunteerDetails{
        var Volunteer : VolunteerDetails = VolunteerDetails()
        for(Volunteers in this.listOfVolunteer){
            if(Volunteers.getPersonalInformation().id == VolunteerId){
                Volunteer = Volunteers
                break
            }
        }
        println("In get Volunteer : $Volunteer")
        return Volunteer

    }

    override fun searchByArea(area: String): List<VolunteerDetails> {
        return this.listOfVolunteer.filter { it.getPersonalInformation().providerArea.equals(area,true) }
    }

    override fun searchByFoodName(foodname: String): List<VolunteerDetails> {
        var listOfVolunteer : ArrayList<VolunteerDetails> = ArrayList()
        val list = this.listOfVolunteer
        for(Volunteer in list){
            for(foodItem in Volunteer.getFoodItems()){
                if(foodItem.foodName.equals(foodname, true)){
                    listOfVolunteer.add(Volunteer)
                    break
                }
            }
        }
        return  listOfVolunteer
    }
}*/

//interface VolunteerHandler{
//    fun addFoodMenu(foodMenu : FoodMenu): Boolean
//    fun addFoodItem(foodItem : FoodItem): Boolean
//    fun addToBookings(booking : Bookings) : Boolean
//    fun getMyBookings() : List<Bookings>
//}
class VolunteerDetails : FoodProviderDetails,VolunteerHandler {
    //private lateinit var personalInformation : FoodProviders
    //private var bookings : ArrayList<Bookings>
//    private var listOfFoodItems : ArrayList<FoodItem> = ArrayList()
//    private var listOfFoodMenu : HashMap<EatingTimeType,ArrayList<FoodItem>?> = HashMap()
//
//    private var myBookings : ArrayList<Bookings> = ArrayList()

    constructor() : super()

    constructor(personalInfo : Volunteer) : super(personalInfo){
        //this.personalInformation = personalInfo
    }


//    override fun addFoodMenu(foodMenu: FoodMenu)  : Boolean{
//        val foodList = this.getFoodList(foodMenu.type)
//        //println(foodList)
//        var list = foodList ?: ArrayList<FoodItem>()//foodMenu.foodItems
//        //println(list)
//        if(list.size == 0){
//            //println("sie is 0")
//            this.listOfFoodMenu.put(foodMenu.type, foodMenu.foodItems)
//        }
//        else{
//            //println("sie is not 0")
//            list.addAll(foodMenu.foodItems)
//            this.listOfFoodMenu.put(foodMenu.type, foodList)}
//        return true
//    }
//
//    override fun addFoodItem(foodItem: FoodItem)  : Boolean{
//        return this.listOfFoodItems.add(foodItem)
//    }
//
//    override fun getMyBookings(time : EatingTimeType): List<Bookings> {
//        var myBookings : java.util.ArrayList<Bookings> = BookingList.getMyBookings(this.personalInformation.id)
//        return myBookings.filter { it.status.equals(StatusType.BOOKED ) && (it.time.equals(time)) }
//    }
//
//    override fun addToBookings(booking: Bookings): Boolean {
//        return this.myBookings.add(booking)
//    }
////    fun getPersonalInformation() : Volunteer {
//        return  this.personalInfo
//    }
//    fun getFoodItems() : List<FoodItem>{
//        return this.listOfFoodItems
//    }
//    fun getFoodItems(type : EatingTimeType) : List<FoodItem>{
//        var list = this.listOfFoodMenu.get(type)?.toList() ?: listOf()
//        return list
//    }
//
//    fun getFoodMenus(): HashMap<EatingTimeType, ArrayList<FoodItem>?>{
//        // println(this.listOfFoodMenu)
//        return this.listOfFoodMenu
//    }
//    fun getPersonalInformation() : Volunteer{
//        return this.personalInformation as Volunteer
//    }



    private fun getFoodList(type : EatingTimeType) : ArrayList<FoodItem>?{
        //var list : ArrayList<FoodItem> = this.listOfFoodMenu.filter { it.type.equals(type) }.flatMap { it.foodItems } as ArrayList<FoodItem>//.toMutableList()
        // var list2 = this.listOfFoodMenu.map { it.type }
        return (this.listOfFoodMenu.get(type))// as ArrayList<FoodItem>)
    }
}