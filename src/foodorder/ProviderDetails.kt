package foodorder

import enums.EatingTimeType
import users.Provider
interface ProviderFilterHandler{
    fun searchByArea(area : String) : List<ProviderDetails>
    fun searchByFoodName(foodname : String) : List<ProviderDetails>
    fun getProvidersList() :  ArrayList<ProviderDetails>
    fun getProvider(providerId : Int) : ProviderDetails

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
    fun getMyBookings() : List<Bookings>
    //fun getMyBookings()
}
class ProviderDetails  : ProviderHandler{
    private lateinit var personalInfo : Provider
    //private var bookings : ArrayList<Bookings>
    private var listOfFoodItems : ArrayList<FoodItem> = ArrayList()
    private var listOfFoodMenu : HashMap<EatingTimeType,ArrayList<FoodItem>?> = HashMap()
    private var listOfPackageScheme : ArrayList<PackageScheme> = ArrayList()
    private var myBookings : ArrayList<Bookings> = ArrayList()
    private var reviews  : ArrayList<String> = ArrayList()
    private var specialBookings : ArrayList<String> = ArrayList()
    constructor()

    constructor(personalInfo : Provider){
        this.personalInfo = personalInfo
    }

    override fun addPackage(packageScheme: PackageScheme) : Boolean {
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

    override fun getMyBookings(): List<Bookings> {
        return this.myBookings.toList()
    }

    override fun addToBookings(booking: Bookings): Boolean {
        return this.myBookings.add(booking)
    }
    fun getPersonalInformation() : Provider{
        return  this.personalInfo
    }
    fun getFoodItems() : ArrayList<FoodItem>{
        return this.listOfFoodItems
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