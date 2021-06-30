package foodorder

import enums.EatingTimeType
import enums.StatusType
import transaction.Transaction
import users.Provider

interface ProviderFilterHandler{
    fun searchByArea(area : String) : List<ProviderDetails>
    fun searchByFoodName(foodname : String) : List<ProviderDetails>
    fun getProvidersList() :  ArrayList<ProviderDetails>
    fun getProvider(providerId : Int) : ProviderDetails
    //fun getProviderByI

}
object ProvidersList : ProviderFilterHandler{
    private var listOfProvider : ArrayList<ProviderDetails> = ArrayList()
    override fun getProvidersList() :  ArrayList<ProviderDetails>{
        return this.listOfProvider
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
        return this.listOfProvider.filter { it.getPersonalInformation().providerArea.equals(area,true) }
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

interface ProviderHandler{
    fun addPackage(packageScheme : PackageScheme): Boolean
    fun addFoodMenu(foodMenu : FoodMenu): Boolean
    fun addFoodItem(foodItem : FoodItem): Boolean
    fun addToBookings(booking : Bookings) : Boolean
    fun addToPackageBookings(pakageBookings: PackageBookings) : Boolean
    fun getMyBookings(time : EatingTimeType) : List<Bookings>
    fun getMyPackageScheme() : List<PackageScheme>
    fun getPackageScheme(packageId : Int) : PackageScheme
    fun getMyPackageBookings() : List<PackageBookings>
    //fun getMyBookings()
}
interface ProviderTransactionHandler{
    fun addTransaction(transaction: Transaction) : Boolean
}
class ProviderDetails  : ProviderHandler, ProviderTransactionHandler{
    private lateinit var personalInfo : Provider
    //private var bookings : ArrayList<Bookings>
    private var listOfFoodItems : ArrayList<FoodItem> = ArrayList()
    private var listOfFoodMenu : HashMap<EatingTimeType,ArrayList<FoodItem>?> = HashMap()
    private var listOfPackageScheme : ArrayList<PackageScheme> = ArrayList()
    private var myBookings : ArrayList<Bookings> = ArrayList()
    private var myPackageOrderBookings : ArrayList<PackageBookings> = ArrayList()
    private var listOfTransactions : ArrayList<Transaction> = ArrayList()
    private var reviews  : ArrayList<String> = ArrayList()
    private var specialBookings : ArrayList<String> = ArrayList()
    constructor()

    constructor(personalInfo : Provider){
        this.personalInfo = personalInfo
    }

    override fun addPackage(packageScheme: PackageScheme) : Boolean {
        println("package added")
        println(packageScheme)
        println(this.listOfPackageScheme)
        return this.listOfPackageScheme.add(packageScheme)
    }

    override fun addFoodMenu(foodMenu: FoodMenu)  : Boolean{
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
    }

    override fun addFoodItem(foodItem: FoodItem)  : Boolean{
        return this.listOfFoodItems.add(foodItem)
    }

    override fun getMyBookings(time : EatingTimeType): List<Bookings> {
        var myBookings : ArrayList<Bookings> = BookingList.getMyBookings(this.getPersonalInformation().id)
        return this.myBookings.filter { it.status.equals(StatusType.BOOKED ) && (it.time.equals(time)) }
    }

    override fun addToBookings(booking: Bookings): Boolean {
        return this.myBookings.add(booking)
    }

    override fun getMyPackageBookings(): List<PackageBookings> {
        return this.myPackageOrderBookings
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
        println(this.getPackageSchemes())
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
    fun getPersonalInformation() : Provider{
        return  this.personalInfo
    }
    fun getFoodItems() : List<FoodItem>{
        return this.listOfFoodItems
    }
    fun getFoodItems(type : EatingTimeType) : List<FoodItem>{
        var list = this.listOfFoodMenu.get(type)?.toList() ?: listOf()
        return list
    }

    fun getFoodMenus(): HashMap<EatingTimeType, ArrayList<FoodItem>?>{
       // println(this.listOfFoodMenu)
        return this.listOfFoodMenu
    }


    fun getPackageSchemes() : ArrayList<PackageScheme>{
        return this.listOfPackageScheme
    }
    private fun getFoodList(type : EatingTimeType) : ArrayList<FoodItem>?{
        //var list : ArrayList<FoodItem> = this.listOfFoodMenu.filter { it.type.equals(type) }.flatMap { it.foodItems } as ArrayList<FoodItem>//.toMutableList()
       // var list2 = this.listOfFoodMenu.map { it.type }
        return (this.listOfFoodMenu.get(type))// as ArrayList<FoodItem>)
    }

}