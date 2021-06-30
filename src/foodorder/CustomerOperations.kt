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
}
class CustomerOperations : CustomerOpertionHandler {
    val orderHandler : OrderOperationHandler = OrderOperations()
    override fun orderPackage(packageOrder: PackageOrder): Boolean {
        return orderHandler.placePackageOrder(packageOrder)
    }

    override fun orderFoodItems(order : Order, time: EatingTimeType): Boolean {
        return orderHandler.placeOrder(order, time)
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