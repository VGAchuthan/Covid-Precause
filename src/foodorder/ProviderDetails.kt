package foodorder

import users.Provider

object ProvidersList{
    private var listOfProvider : ArrayList<ProviderDetails> = ArrayList()
    fun getProvidersList() :  ArrayList<ProviderDetails>{
        return this.listOfProvider
    }
    fun addProvider(provider : Provider){

    }
}

interface ProviderHandler{
    fun addPackage(packageScheme : PackageScheme): Boolean
    fun addFoodMenu(foodMenu : FoodMenu): Boolean
    fun addFoodItem(foodItem : FoodItem): Boolean
}
class ProviderDetails  : ProviderHandler{
    private lateinit var personalInfo : Provider
    //private var bookings : ArrayList<Bookings>
    private var listOfFoodItems : ArrayList<FoodItem> = ArrayList()
    private var listOfFoodMenu : ArrayList<FoodMenu> = ArrayList()
    private var listOfPackageScheme : ArrayList<PackageScheme> = ArrayList()
    private var reviews  : ArrayList<String> = ArrayList()
    private var specialBookings : ArrayList<String> = ArrayList()

    constructor(personalInfo : Provider){
        this.personalInfo = personalInfo
    }

    override fun addPackage(packageScheme: PackageScheme) : Boolean {
        return this.listOfPackageScheme.add(packageScheme)
    }

    override fun addFoodMenu(foodMenu: FoodMenu)  : Boolean{
        TODO("Not yet implemented")
    }

    override fun addFoodItem(foodItem: FoodItem)  : Boolean{
        TODO("Not yet implemented")
    }
}