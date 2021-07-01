package foodorder

interface VolunteerOperationHandler{

    fun addFoodMenu(foodMenu: FoodMenu) : Boolean
    fun addFoodItem(foodItem: FoodItem) : Boolean
}
//val detailsOperationHandler : ProviderHandler = ProviderDetails()
class VolunteerOperations  : ProviderOperations(), VolunteerOperationHandler{


//    override fun addFoodMenu(foodMenu: FoodMenu): Boolean {
//        if(checkIfFoodMenuAlreadyPresents(foodMenu)){
//            return false
//        }
//        return CurrentVolunteerDetails.getInstance().addFoodMenu(foodMenu)
//    }

    override fun addFoodItem(foodItem: FoodItem): Boolean {
        if(checkIfFoodItemAlreadyPresents(foodItem)){
            return false
        }

        return CurrentVolunteerDetails.getInstance().addFoodItem(foodItem)
    }
    private fun checkIfFoodItemAlreadyPresents(foodItem: FoodItem) :Boolean{
        return CurrentVolunteerDetails.getInstance().getFoodItems().contains(foodItem)
    }
    private  fun checkIfFoodMenuAlreadyPresents(foodMenu: FoodMenu) :Boolean{
        val flag = CurrentVolunteerDetails.getInstance().getFoodMenus().get(foodMenu.type)?.contains(foodMenu.foodItems) ?: false
        return flag
    }


}