package foodorder
interface ProviderOperationHandler{
    fun addPackageScheme(packgeScheme : PackageScheme) : Boolean
    fun addFoodMenu(foodMenu: FoodMenu) : Boolean
    fun addFoodItem(foodItem: FoodItem) : Boolean
}
val detailsOperationHandler : ProviderHandler = ProviderDetails()
class ProviderOperations  : ProviderOperationHandler{
    override fun addPackageScheme(packageScheme: PackageScheme): Boolean {
        //var provider = getProvider(providerId)
        if(checkIfFoodPackageAlreadyPresents(packageScheme)){
            return false
        }
        return CurrentProviderDetails.getInstance().addPackage(packageScheme)
    }

    override fun addFoodMenu(foodMenu: FoodMenu): Boolean {
        if(checkIfFoodMenuAlreadyPresents(foodMenu)){
            return false
        }
        return CurrentProviderDetails.getInstance().addFoodMenu(foodMenu)
    }

    override fun addFoodItem(foodItem: FoodItem): Boolean {
        if(checkIfFoodItemAlreadyPresents(foodItem)){
            return false
        }

        return CurrentProviderDetails.getInstance().addFoodItem(foodItem)
    }
    private fun checkIfFoodItemAlreadyPresents(foodItem: FoodItem) :Boolean{
        return CurrentProviderDetails.getInstance().getFoodItems().contains(foodItem)
    }
    private  fun checkIfFoodMenuAlreadyPresents(foodMenu: FoodMenu) :Boolean{
        val flag = CurrentProviderDetails.getInstance().getFoodMenus().get(foodMenu.type)?.contains(foodMenu.foodItems) ?: false
        return flag
    }
    private  fun checkIfFoodPackageAlreadyPresents(foodPackageScheme: PackageScheme) :Boolean{
        return CurrentProviderDetails.getInstance().getPackageSchemes().contains(foodPackageScheme)
    }

}