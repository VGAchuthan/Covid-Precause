package foodorder

object CurrentFoodProviderDetails {
    lateinit private var instance : FoodProviderDetails
    fun getInstance() : FoodProviderDetails{
        return this.instance
    }
    fun setInstance(foodProvider : FoodProviderDetails) {
        this.instance = foodProvider
    }
}