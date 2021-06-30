package foodorder

import enums.EatingTimeType
import enums.StatusType

interface DispatchOperationHandler{
    //fun getMyBookings(type : EatingTimeType): List<Bookings>
    fun dispatchBooking(booking : Bookings)
    fun dispatchPackageBooking(packageBookings: PackageBookings, time : EatingTimeType)
    fun getOrderDetails(orderId : Int) : List<OrderedItems>
    fun changeOrderStatus(booking : Bookings)
}
class DispatchOperation : DispatchOperationHandler {
    val orderDispatcherHandler : OrderDispatcherHandler = OrdersList
    val packageOrderDispatchHandler : PackageBookingListOperationHandler = PackageBookingList
    val orderDataMaintanance : DispatchDataHandler = OrderDataMaintanance()
    val providerDetailsHandler : ProviderHandler = ProviderDetails()
    val providerFilterHandler : ProviderFilterHandler = ProvidersList
    lateinit var dispatchingList : List<Bookings>
//    override fun getMyBookings(type: EatingTimeType)  : List<Bookings>{
//        dispatchingList = providerDetailsHandler.getMyBookings()
//        var index = 1
//        println("In dop")
//        println(dispatchingList)
//
//        return dispatchingList
//
//    }

    override fun dispatchBooking(booking: Bookings) {
        println("Details")
        println("Delivery Address: ${booking.deliveryAddress}")
        println("Customer Mobile Number : ${booking.customerMobileNumber}")
        println("Ordered Items")
        val orderDetails =getOrderDetails(booking.orderId)
        for(items in orderDetails){
            println("${items.foodItems.foodName} : ${items.count}")
        }

    }

    override fun dispatchPackageBooking(packageBookings: PackageBookings, time : EatingTimeType) {
        println("Details")
        packageBookings
        println("Delivery Address: ${packageBookings.deliveryAddress}")
        println("Customer Mobile Number : ${packageBookings.customerMobileNumber}")
        println("Ordered Items")
        val packageOrder = PackageOrderList.getPackageOrder(packageBookings.pakageOrderId)
        println("Package order \n$packageOrder")
        println("PAckage menu")
        println(getPackageFoodItemDetails(packageOrder.packageId, time, packageBookings.providerId))
//        val foodDetails =getOrderDetails(packageBookings.providerId, time)
//        for(items in foodDetails){
//            println("${items.foodMenus}")
//        }
    }

    override fun changeOrderStatus(booking: Bookings) {
        booking.status = StatusType.DELIVERED
        orderDataMaintanance.updateStatusOfOrder(booking.orderId)
    }

    override fun getOrderDetails(orderId : Int)  : List<OrderedItems> {
        return orderDispatcherHandler.getOrderedListById(orderId)

    }
    private fun getPackageFoodItemDetails(packageId : Int, time : EatingTimeType, providerId : Int) : List<FoodMenu>{

        val provider = providerFilterHandler.getProvider(providerId)
        val packageScheme = provider.getPackageScheme(packageId)
        return packageScheme.foodMenus.filter { it.type == time }

    }

}