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
object OrderDataMaintanance  : OrderDataHandler, DispatchDataHandler{
    val providersFilterHandler : FoodProvidersFilterHandler = FoodProvidersList
    val bookingOperation : BookingOperationHelper = BookingOperationHelper()
    val orderDispatcherHandler : OrderDispatcherHandler = OrdersList

    override fun mapOrderToProvider(order: Order,time : EatingTimeType) {
        //println("Inside map order to provider")
       // println(order)
        val provider = providersFilterHandler.getProviderById(order.providerId)
        bookingOperation.addBooking(order,provider,time)
        //println(provider)
    }

    override fun mapPackageOrderToProvider(packageOrder: PackageOrder) {
        val provider = providersFilterHandler.getProviderById(packageOrder.providerId) as ProviderDetails
        bookingOperation.addPackageBooking(packageOrder,provider)

    }

    private fun getOrderDetails(orderId: Int)  : Order{
        return orderDispatcherHandler.getOrder(orderId)
    }

    override fun updateStatusOfOrder(orderId: Int) {
        //println("Update status of order")
        val order = getOrderDetails(orderId)
        order.status = StatusType.DELIVERED
        //println(order.status)
    }
}