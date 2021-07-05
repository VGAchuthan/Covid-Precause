package foodorder

import enums.SpecialRequestType

interface FoodProviderOperationHandler{

    fun addFoodMenu(foodMenu: FoodMenu) : Boolean
    fun addFoodItem(foodItem: FoodItem) : Boolean
    fun dispatchBookings(booking : Bookings)
    fun getReviews(foodProviderId : Int) : List<Review>


    fun changeOrderStatus(booking: Bookings)
    fun makeCallToCustomer(customerNumber : String)
    fun getSpecialRequests() : List<SpecialRequest>
    fun dispatchSpecialRequest(specialRequest: SpecialRequest)
    fun updateSpecialRequestStatus(requestId : Int, amount: Float, status : SpecialRequestType)
    fun changeSpecialRequestStatus(specialRequest: SpecialRequest)
    fun getAcceptedSpecialRequest() : List<SpecialRequest>
}


open class FoodProvidersOperations  : FoodProviderOperationHandler{
    var dispatchHandler : DispatchOperationHandler = DispatchOperation
    val reviewHandler : FoodProvidersReviewHandler = ReviewOperations
    val specialRequestHandler : FoodProviderSpecialRequestHandler = SpecialRequestProcess



    override fun addFoodMenu(foodMenu: FoodMenu): Boolean {
        if(checkIfFoodMenuAlreadyPresents(foodMenu)){
            return false
        }
        return CurrentFoodProviderDetails.getInstance().addFoodMenu(foodMenu)
    }

    override fun addFoodItem(foodItem: FoodItem): Boolean {
        if(checkIfFoodItemAlreadyPresents(foodItem)){
            return false
        }

        return CurrentFoodProviderDetails.getInstance().addFoodItem(foodItem)
    }

    override fun changeOrderStatus(booking: Bookings) {
        dispatchHandler.changeOrderStatus(booking)

    }

    override fun dispatchBookings(booking: Bookings) {
        dispatchHandler.dispatchBooking(booking)

    }



    override fun makeCallToCustomer(customerNumber: String) {
        println("Calling ...$customerNumber")
        println("Informed Through Call")
    }

    override fun getReviews(foodProviderId: Int): List<Review> {
        return reviewHandler.getFoodProviderReviews(foodProviderId).sortedByDescending { it.getDate() }

    }

    override fun getSpecialRequests(): List<SpecialRequest> {
        return  specialRequestHandler.getSpecialRequestByProviderId(CurrentFoodProviderDetails.getInstance().getPersonalInformation().id).filter{it.SpecialRequestType == SpecialRequestType.REQUESTED}
    }
    override fun getAcceptedSpecialRequest() : List<SpecialRequest>{
        return specialRequestHandler.getSpecialRequestByProviderId(CurrentFoodProviderDetails.getInstance().getPersonalInformation().id).filter{it.SpecialRequestType == SpecialRequestType.ACCEPTED}
    }

    override fun dispatchSpecialRequest(specialRequest: SpecialRequest) {
        //specialRequestHandler.
        dispatchHandler.dispatchSpecialRequest(specialRequest)
    }

    override fun changeSpecialRequestStatus(specialRequest: SpecialRequest) {
        dispatchHandler.changeStatusOfSpecialRequest(specialRequest)
    }
    override fun updateSpecialRequestStatus(requestId : Int, amount: Float, status : SpecialRequestType){
        return specialRequestHandler.updateSpecialRequestStatus(requestId, amount, status)
    }

    private fun checkIfFoodItemAlreadyPresents(foodItem: FoodItem) :Boolean{
        return CurrentFoodProviderDetails.getInstance().getFoodItems().contains(foodItem)
    }
    private  fun checkIfFoodMenuAlreadyPresents(foodMenu: FoodMenu) :Boolean{
        val flag = CurrentFoodProviderDetails.getInstance().getFoodMenus().get(foodMenu.type)?.contains(foodMenu.foodItems) ?: false
        return flag
    }

}