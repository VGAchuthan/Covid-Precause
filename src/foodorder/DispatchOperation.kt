package foodorder

import enums.EatingTimeType
import enums.StatusType
import java.util.*

interface DispatchOperationHandler{
    //fun getMyBookings(type : EatingTimeType): List<Bookings>
    fun dispatchBooking(booking : Bookings)
    fun dispatchPackageBooking(packageBookings: PackageBookings, date : Date, time : EatingTimeType)
    fun getOrderDetails(orderId : Int) : List<OrderedItems>
    fun changeOrderStatus(booking : Bookings)
    fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType)
    //fun makeCallToCustomer(customerNumber : String)
}
class DispatchOperation : DispatchOperationHandler {
    val orderDispatcherHandler : OrderDispatcherHandler = OrdersList
    val packageOrderDispatchHandler : PackageBookingListOperationHandler = PackageBookingList
    val orderDataMaintanance : DispatchDataHandler = OrderDataMaintanance()
    val providerDetailsHandler : ProviderHandler = ProviderDetails()
    val providerFilterHandler : FoodProvidersFilterHandler = FoodProvidersList
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

    override fun dispatchPackageBooking(packageBookings: PackageBookings, date : Date,time : EatingTimeType) {
        println("Details")
        //packageBookings
        println("Delivery Address: ${packageBookings.deliveryAddress}")
        println("Customer Mobile Number : ${packageBookings.customerMobileNumber}")
        println("Ordered Items")
        val packageOrder = PackageOrderList.getPackageOrder(packageBookings.pakageOrderId)
        println("Package order \n$packageOrder")
        println("Package menu")
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

    override fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType) {
        val scheduleOfDate = packageBookings.getSchedule().get(date)
        println("In package change status")
        println(packageBookings.getSchedule())
        when(time){
            EatingTimeType.BREAKFAST ->{
                scheduleOfDate!![0].status = StatusType.DELIVERED

            }
            EatingTimeType.LUNCH ->{
                scheduleOfDate!![1].status = StatusType.DELIVERED
            }
            EatingTimeType.DINNER ->{
                scheduleOfDate!![2].status = StatusType.DELIVERED
            }
        }
        //println(scheduleOfDate!![0].status)
        //println(scheduleOfDate!![1].status)
        //println(scheduleOfDate!![2].status)
        //println(packageBookings.getSchedule())
    }

    override fun getOrderDetails(orderId : Int)  : List<OrderedItems> {
        return orderDispatcherHandler.getOrderedListById(orderId)

    }

//    override fun makeCallToCustomer(customerNumber: String) {
//        TODO("Not yet implemented")
//    }
    private fun getPackageFoodItemDetails(packageId : Int, time : EatingTimeType, providerId : Int) : List<FoodMenu>{

        val provider = providerFilterHandler.getProviderById(providerId) as ProviderDetails
        val packageScheme = provider.getPackageScheme(packageId)
        return packageScheme.foodMenus.filter { it.type == time }

    }

}