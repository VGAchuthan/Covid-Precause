package foodorder
interface OrderOperationHandler{
    fun placeOrder(order : Order) : Boolean
    fun placePackage(packageScheme : PackageScheme) : Boolean
}
class OrderOperations : OrderOperationHandler {
    val orderProviderController : OrderDataHandler = OrderDataMaintanance()
    override fun placeOrder(order: Order): Boolean {
        orderProviderController.mapOrderToProvider(order)

        return OrdersList.addOrder(order)

    }

    override fun placePackage(packageScheme: PackageScheme): Boolean {
        TODO("Not yet implemented")
    }
}