package foodorder

import enums.EatingTimeType
import transaction.Transaction
import users.Provider
import java.util.*

interface ProviderFilterHandler{
    fun searchByArea(area : String) : List<ProviderDetails>
    fun searchByFoodName(foodname : String) : List<ProviderDetails>
    fun getProviderBasedOnRating() : List<ProviderDetails>
    fun getProvidersList() :  ArrayList<ProviderDetails>
    fun getProvider(providerId : Int) : ProviderDetails
    //fun getProviderByI

}
object ProvidersList : ProviderFilterHandler{
    //val l : Tree
    private var listOfProvider : ArrayList<ProviderDetails> = ArrayList()
    override fun getProvidersList() :  ArrayList<ProviderDetails>{
        return this.listOfProvider
    }

    override fun getProviderBasedOnRating(): List<ProviderDetails> {
        //return this.listOfProvider.sortedWith(compareBy{it.getPersonalInformation().id})
        return this.listOfProvider.sortedByDescending { it.getPersonalInformation().rating }
    }
    fun addProvider(providerDetails : ProviderDetails) : Boolean{
        return this.listOfProvider.add(providerDetails)

    }
    override fun getProvider(providerId: Int) : ProviderDetails{
        var provider : ProviderDetails = ProviderDetails()
        for(providers in this.listOfProvider){
            if(providers.getPersonalInformation().id == providerId){
                provider = providers
                break
            }
        }
        println("In get provider : $provider")
        return provider

    }

    override fun searchByArea(area: String): List<ProviderDetails> {
        return this.listOfProvider.filter { it.getPersonalInformation().area.equals(area,true) }
    }

    override fun searchByFoodName(foodname: String): List<ProviderDetails> {
        var listOfProvider : ArrayList<ProviderDetails> = ArrayList()
        val list = this.listOfProvider
        for(provider in list){
            for(foodItem in provider.getFoodItems()){
                if(foodItem.foodName.equals(foodname, true)){
                    listOfProvider.add(provider)
                    break
                }
            }
        }
        return  listOfProvider
    }
}

interface  FoodProviderHandler {
    fun addFoodMenu(foodMenu : FoodMenu): Boolean
    fun addFoodItem(foodItem : FoodItem): Boolean
    fun addToBookings(booking : Bookings) : Boolean
    fun getMyBookings(time : EatingTimeType) : List<Bookings>
}

interface ProviderHandler : FoodProviderHandler {
    fun addPackage(packageScheme : PackageScheme): Boolean

    fun addToPackageBookings(pakageBookings: PackageBookings) : Boolean

    fun getMyPackageScheme() : List<PackageScheme>
    fun getPackageScheme(packageId : Int) : PackageScheme
    fun getMyPackageBookings(date  : Date, time : EatingTimeType) : List<PackageBookings>
    //fun getMyBookings()
}

interface VolunteerHandler : FoodProviderHandler {

}

interface ProviderTransactionHandler{
    fun addTransaction(transaction: Transaction) : Boolean
}
class ProviderDetails  : FoodProviderDetails,ProviderHandler , ProviderTransactionHandler{
   // private lateinit var personalInfo : FoodProviders

    //private var bookings : ArrayList<Bookings>
    //private var listOfFoodItems : ArrayList<FoodItem> = ArrayList()
    //private var listOfFoodMenu : HashMap<EatingTimeType,ArrayList<FoodItem>?> = HashMap()
    private var listOfPackageScheme : ArrayList<PackageScheme> = ArrayList()
    //private var myBookings : ArrayList<Bookings> = ArrayList()
    private var myPackageOrderBookings : ArrayList<PackageBookings> = ArrayList()
    private var listOfTransactions : ArrayList<Transaction> = ArrayList()
    private var reviews  : ArrayList<String> = ArrayList()
    private var specialBookings : ArrayList<String> = ArrayList()
    constructor() : super()

    constructor(personalInfo : Provider) : super(personalInfo){
        this.personalInfo = personalInfo
    }

    override fun addPackage(packageScheme: PackageScheme) : Boolean {
       // println("package added")
       // println(packageScheme)
       // println(this.listOfPackageScheme)
        return this.listOfPackageScheme.add(packageScheme)
    }

    /*override fun addFoodMenu(foodMenu: FoodMenu)  : Boolean{
        val foodList = this.getFoodList(foodMenu.type)
        //println(foodList)
        var list = foodList ?: ArrayList<FoodItem>()//foodMenu.foodItems
        //println(list)
        if(list.size == 0){
            //println("sie is 0")
            this.listOfFoodMenu.put(foodMenu.type, foodMenu.foodItems)
        }
        else{
            //println("sie is not 0")
        list.addAll(foodMenu.foodItems)
        this.listOfFoodMenu.put(foodMenu.type, foodList)}
        return true
    }*/

    /*override fun addFoodItem(foodItem: FoodItem)  : Boolean{
        return this.listOfFoodItems.add(foodItem)
    }*/

    /*override fun getMyBookings(time : EatingTimeType): List<Bookings> {
        var myBookings : ArrayList<Bookings> = BookingList.getMyBookings(this.getPersonalInformation().id)
        return myBookings.filter { it.status.equals(StatusType.BOOKED ) && (it.time.equals(time)) }
    }*/

    /*override fun addToBookings(booking: Bookings): Boolean {
        return this.myBookings.add(booking)
    }*/

    override fun getMyPackageBookings(date  : Date, time: EatingTimeType): List<PackageBookings> {

        var myPackageBookings : ArrayList<PackageBookings> = PackageBookingList.getMyPackageBookings(this.getPersonalInformation().id, date, time)
        //println(myPackageBookings)
        //var localDate = LocalDate.now()
        //var today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        return myPackageBookings
    }

    override fun getPackageScheme(packageId: Int): PackageScheme {
//        var packageScheme : PackageScheme = PackageScheme()
//        val packageSchemes = this.listOfPackageScheme
//        for(scheme in packageSchemes){
//            if(scheme.packageId == packageId){
//                packageScheme = scheme
//                break
//            }
//        }
        //println(this.getPackageSchemes())
        return this.listOfPackageScheme.get(packageId -1)
    }
    override fun addToPackageBookings(packageBookings: PackageBookings): Boolean {
        return this.myPackageOrderBookings.add(packageBookings)
    }

    override fun getMyPackageScheme(): List<PackageScheme> {
        return this.listOfPackageScheme.toList()
    }
    override fun addTransaction(transaction: Transaction) : Boolean{
        return this.listOfTransactions.add(transaction)
    }
//    fun getPersonalInformation() : FoodProviders{
//        return  this.personalInfo as Volunteer
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
//       // println(this.listOfFoodMenu)
//        return this.listOfFoodMenu
//    }
//

    fun getPackageSchemes() : ArrayList<PackageScheme>{
        return this.listOfPackageScheme
    }
    private fun getFoodList(type : EatingTimeType) : ArrayList<FoodItem>?{
        //var list : ArrayList<FoodItem> = this.listOfFoodMenu.filter { it.type.equals(type) }.flatMap { it.foodItems } as ArrayList<FoodItem>//.toMutableList()
       // var list2 = this.listOfFoodMenu.map { it.type }
        return (this.listOfFoodMenu.get(type))// as ArrayList<FoodItem>)
    }

}