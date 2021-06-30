package foodorder

import enums.EatingTimeType

interface OrderOperationHandler{
    fun placeOrder(order : Order, time : EatingTimeType) : Boolean
    fun placePackageOrder(packageOrder: PackageOrder) : Boolean
}
class OrderOperations : OrderOperationHandler {
    val orderProviderController : OrderDataHandler = OrderDataMaintanance()
    override fun placeOrder(order: Order,time : EatingTimeType): Boolean {
        orderProviderController.mapOrderToProvider(order, time)
        //CurrentCustomerDetails.getInstance().addToMyOrders(order)

        return OrdersList.addOrder(order)

    }

    override fun placePackageOrder(packageOrder: PackageOrder): Boolean {
        orderProviderController.mapPackageOrderToProvider(packageOrder)
        return PackageOrderList.addPackageOrder(packageOrder)
    }
}