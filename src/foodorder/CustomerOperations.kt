package foodorder

import enums.EatingTimeType
import users.Provider

interface CustomerOpertionHandler{
    fun orderPackage(packageOrder: PackageOrder) : Boolean
    fun orderFoodItems(order : Order, time : EatingTimeType) : Boolean

    fun bookmarkProvider(provider : Provider)
    fun searchByProvider(provider : Provider)
    fun searchProviderByArea(area : String)
    fun searchByFood(food : FoodItem)
    fun addReview(review : Review) : Boolean
    fun getMyReviews(customerId: Int) : List<Review>
    fun getMySpecialBookings() : List<SpecialRequest>
    fun makeSpecialRequest(specialRequest: SpecialRequest) : Boolean
}
class CustomerOperations : CustomerOpertionHandler {
    val orderHandler : OrderOperationHandler = OrderOperations
    val reviewHandler : CustomerReviewHandler = ReviewOperations
    val customerSpecialRequest : CustomerSpecialRequestHandler = SpecialRequestProcess
    override fun orderPackage(packageOrder: PackageOrder): Boolean {
        return orderHandler.placePackageOrder(packageOrder)
    }

    override fun orderFoodItems(order : Order, time: EatingTimeType): Boolean {
        return orderHandler.placeOrder(order, time)
    }

    override fun bookmarkProvider(provider: Provider) {
        CurrentCustomerDetails.getInstance()
    }

    override fun addReview(review: Review) : Boolean {
        return reviewHandler.addReview(review)
    }

    override fun getMyReviews(customerId : Int): List<Review> {
        return reviewHandler.getCustomerReviews(customerId)
    }

    override fun getMySpecialBookings(): List<SpecialRequest> {
        return customerSpecialRequest.getSpecialRequestByCustomerId(CurrentCustomerDetails.getInstance().getCustomerId())
    }

    override fun makeSpecialRequest(specialRequest: SpecialRequest)  : Boolean{
        return customerSpecialRequest.addSpecialRequest(specialRequest)
    }

    override fun searchByProvider(provider: Provider) {
        TODO("Not yet implemented")
    }

    override fun searchProviderByArea(area: String) {
        TODO("Not yet implemented")
    }

    override fun searchByFood(food: FoodItem) {
        TODO("Not yet implemented")
    }
}