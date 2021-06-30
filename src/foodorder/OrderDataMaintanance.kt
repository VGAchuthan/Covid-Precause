package foodorder

import enums.EatingTimeType
import enums.StatusType

interface OrderDataHandler{
    fun mapOrderToProvider(order :Order, time : EatingTimeType)
    fun mapPackageOrderToProvider(packageOrder: PackageOrder)

}
interface  DispatchDataHandler{
    fun updateStatusOfOrder(orderId : Int)
    //fun getOrderDetails(orderId : Int) : Order

}
class OrderDataMaintanance  : OrderDataHandler, DispatchDataHandler{
    val providersFilterHandler : ProviderFilterHandler = ProvidersList
    val bookingOperation : BookingOperationHelper = BookingOperationHelper()
    val orderDispatcherHandler : OrderDispatcherHandler = OrdersList

    override fun mapOrderToProvider(order: Order,time : EatingTimeType) {
        //println("Inside map order to provider")
       // println(order)
        val provider = providersFilterHandler.getProvider(order.providerId)
        bookingOperation.addBooking(order,provider,time)
        //println(provider)
    }

    override fun mapPackageOrderToProvider(packageOrder: PackageOrder) {
        val provider = providersFilterHandler.getProvider(packageOrder.providerId)
        bookingOperation.addPackageBooking(packageOrder,provider)

    }

    private fun getOrderDetails(orderId: Int)  : Order{
        return orderDispatcherHandler.getOrder(orderId)
    }

    override fun updateStatusOfOrder(orderId: Int) {
        println("Update status of order")
        val order = getOrderDetails(orderId)
        order.status = StatusType.DELIVERED
        println(order.status)
    }
}