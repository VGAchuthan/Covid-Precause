package foodorder

import users.Provider

interface CustomerOpertionHandler{
    fun orderPackage() : Boolean
    fun orderFoodItems(order : Order) : Boolean

    fun bookmarkProvider(provider : Provider)
    fun searchByProvider(provider : Provider)
    fun searchProviderByArea(area : String)
    fun searchByFood(food : FoodItem)
}
class CustomerOperations : CustomerOpertionHandler {
    val orderHandler : OrderOperationHandler = OrderOperations()
    override fun orderPackage(): Boolean {
        TODO("Not yet implemented")
    }

    override fun orderFoodItems(order : Order): Boolean {
        return orderHandler.placeOrder(order)
    }

    override fun bookmarkProvider(provider: Provider) {
        CurrentCustomerDetails.getInstance()
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