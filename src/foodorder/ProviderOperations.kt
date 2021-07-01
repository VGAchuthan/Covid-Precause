package foodorder

import enums.EatingTimeType
import java.util.*

interface ProviderOperationHandler{
    fun addPackageScheme(packgeScheme : PackageScheme) : Boolean
    fun addFoodMenu(foodMenu: FoodMenu) : Boolean
    fun addFoodItem(foodItem: FoodItem) : Boolean
    fun dispatchBookings(booking : Bookings)
    fun dispatchPackageBookings(packageBookings: PackageBookings, date : Date, time: EatingTimeType)
    fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType)
    fun changeOrderStatus(booking: Bookings)
    fun makeCallToCustomer(customerNumber : String)
}
val detailsOperationHandler : ProviderHandler = ProviderDetails()
open class ProviderOperations  : ProviderOperationHandler{
    var dispatchHandler : DispatchOperationHandler = DispatchOperation()
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

    override fun changeOrderStatus(booking: Bookings) {
        dispatchHandler.changeOrderStatus(booking)

    }

    override fun dispatchBookings(booking: Bookings) {
        dispatchHandler.dispatchBooking(booking)

    }

    override fun dispatchPackageBookings(packageBookings: PackageBookings, date : Date, time: EatingTimeType) {
        dispatchHandler.dispatchPackageBooking(packageBookings,date, time)
    }

    override fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType) {
        dispatchHandler.changeStatusOfPackageOrder(packageBookings, date, time)
    }

    override fun makeCallToCustomer(customerNumber: String) {
        println("Calling ...$customerNumber")
        println("Informed Through Call")
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