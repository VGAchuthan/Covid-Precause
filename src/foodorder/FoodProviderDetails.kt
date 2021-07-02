package foodorder

import enums.EatingTimeType
import enums.StatusType
import users.FoodProviders
interface FoodProvidersFilterHandler{
    fun getFoodProvidersList() : ArrayList<FoodProviderDetails>
    fun getProviderBasedOnRating() : List<FoodProviderDetails>
    fun getProviderById(id : Int) : FoodProviderDetails
    fun searchByArea(area : String) : List<FoodProviderDetails>
    fun searchByFoodName(foodname : String) : List<FoodProviderDetails>
}

object FoodProvidersList : FoodProvidersFilterHandler {
    private var listOfFoodProviders: ArrayList<FoodProviderDetails> = ArrayList()
    fun addFoodProvider(foodProviderDetails: FoodProviderDetails) {
        //println(foodProviderDetails)
        this.listOfFoodProviders.add(foodProviderDetails)
    }

    //    override fun getProviderById(id: Int): FoodProviderDetails {
//
//    }
    override fun getProviderById(id: Int): FoodProviderDetails {
        var provider: FoodProviderDetails = FoodProviderDetails()
        for (providers in this.listOfFoodProviders) {
            if (providers.getPersonalInformation().id == id) {
                provider = providers
                break
            }

        }
        return provider
    }

    override fun getProviderBasedOnRating(): List<FoodProviderDetails> {
        return this.listOfFoodProviders.sortedByDescending { it.getPersonalInformation().rating }
    }

    override fun getFoodProvidersList(): ArrayList<FoodProviderDetails> {
        return this.listOfFoodProviders
    }

    override fun searchByArea(area: String): List<FoodProviderDetails> {
        return this.listOfFoodProviders.filter { it.getPersonalInformation().area.equals(area, true) }
    }

    override fun searchByFoodName(foodname: String): List<FoodProviderDetails> {
        var listOfProvider: java.util.ArrayList<FoodProviderDetails> = java.util.ArrayList()
        val list = this.listOfFoodProviders
        for (provider in list) {
            for (foodItem in provider.getFoodItems()) {
                if (foodItem.foodName.equals(foodname, true)) {
                    listOfProvider.add(provider)
                    break
                }
            }
        }
        return listOfProvider
    }
}

open class FoodProviderDetails : FoodProviderHandler {
        protected lateinit var personalInfo: FoodProviders

        //private var bookings : ArrayList<Bookings>
        protected var listOfFoodItems: ArrayList<FoodItem> = ArrayList()
        protected var listOfFoodMenu: HashMap<EatingTimeType, ArrayList<FoodItem>?> = HashMap()

        protected var myBookings: ArrayList<Bookings> = ArrayList()

        constructor()

        constructor(personalInfo: FoodProviders) {
            this.personalInfo = personalInfo
        }


        override fun addFoodMenu(foodMenu: FoodMenu): Boolean {
            val foodList = this.getFoodList(foodMenu.type)
            //println(foodList)
            var list = foodList ?: ArrayList<FoodItem>()//foodMenu.foodItems
            //println(list)
            if (list.size == 0) {
                //println("sie is 0")
                this.listOfFoodMenu.put(foodMenu.type, foodMenu.foodItems)
            } else {
                //println("sie is not 0")
                list.addAll(foodMenu.foodItems)
                this.listOfFoodMenu.put(foodMenu.type, foodList)
            }
            return true
        }

        override fun addFoodItem(foodItem: FoodItem): Boolean {
            return this.listOfFoodItems.add(foodItem)
        }

        override fun getMyBookings(timeType: EatingTimeType): List<Bookings> {
            var myBookings: ArrayList<Bookings> = BookingList.getMyBookings(this.personalInfo.id)
            return this.myBookings.filter { it.status.equals(StatusType.BOOKED) }
        }

        override fun addToBookings(booking: Bookings): Boolean {
            return this.myBookings.add(booking)
        }

        fun getPersonalInformation(): FoodProviders {
            return this.personalInfo
        }

        fun getFoodItems(): List<FoodItem> {
            return this.listOfFoodItems
        }

        fun getFoodItems(type: EatingTimeType): List<FoodItem> {
            var list = this.listOfFoodMenu.get(type)?.toList() ?: listOf()
            return list
        }

        fun getFoodMenus(): HashMap<EatingTimeType, ArrayList<FoodItem>?> {
            // println(this.listOfFoodMenu)
            return this.listOfFoodMenu
        }


        private fun getFoodList(type: EatingTimeType): ArrayList<FoodItem>? {
            //var list : ArrayList<FoodItem> = this.listOfFoodMenu.filter { it.type.equals(type) }.flatMap { it.foodItems } as ArrayList<FoodItem>//.toMutableList()
            // var list2 = this.listOfFoodMenu.map { it.type }
            return (this.listOfFoodMenu.get(type))// as ArrayList<FoodItem>)
        }


    }