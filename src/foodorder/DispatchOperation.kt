package foodorder

import enums.EatingTimeType
import enums.SpecialRequestType
import enums.StatusType
import java.util.*

interface DispatchOperationHandler{
    //fun getMyBookings(type : EatingTimeType): List<Bookings>
    fun dispatchBooking(booking : Bookings)
    fun dispatchPackageBooking(packageBookings: PackageBookings, date : Date, time : EatingTimeType)
    fun getOrderDetails(orderId : Int) : List<OrderedItems>
    fun changeOrderStatus(booking : Bookings)
    fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType)
    fun dispatchSpecialRequest(specialRequest: SpecialRequest)
    fun changeStatusOfSpecialRequest(specialRequest: SpecialRequest)
    //fun makeCallToCustomer(customerNumber : String)
}
object DispatchOperation : DispatchOperationHandler {
    val orderDispatcherHandler : OrderDispatcherHandler = OrdersList
    //val packageOrderDispatchHandler : PackageBookingListOperationHandler = PackageBookingList
    val orderDataMaintanance : DispatchDataHandler = OrderDataMaintanance
    //val providerDetailsHandler : ProviderHandler = ProviderDetails()
    val providerFilterHandler : FoodProvidersFilterHandler = FoodProvidersList
    //lateinit var dispatchingList : List<Bookings>


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
//
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

    }

    override fun getOrderDetails(orderId : Int)  : List<OrderedItems> {
        return orderDispatcherHandler.getOrderedListById(orderId)

    }

    override fun dispatchSpecialRequest(specialRequest: SpecialRequest) {
        println("Details")
        println("Special Requested Food : ${specialRequest.foodItem}")
        println("Delivery Address: ${specialRequest.deliveryAddress}")
        println("Customer Mobile Number : ${specialRequest.mobileNumber}")

    }

    override fun changeStatusOfSpecialRequest(specialRequest: SpecialRequest) {
        specialRequest.SpecialRequestType = SpecialRequestType.DELIVERED
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