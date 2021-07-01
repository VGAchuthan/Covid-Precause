package foodorder

import enums.EatingTimeType

class BookingOperationHelper {
    val bookingListHandler : BookingListOperationHandler = BookingList
    val packageBookingListHandler : PackageBookingListOperationHandler = PackageBookingList
    //val orderDataMaintanance : OrderDataHandler = OrderDataMaintanance()
    fun addBooking(order : Order, provider : ProviderDetails,time : EatingTimeType){
        val bookings = Bookings(order.getOrderId(),time,order.address,CurrentCustomerDetails.getInstance().getPersonalInfo().customerMobileNumber, provider.getPersonalInformation().id)
        provider.addToBookings(bookings)
        bookingListHandler.addBooking(bookings)
        println(bookings)
    }

    fun addPackageBooking(packageOrder: PackageOrder, provider: ProviderDetails){
        val packageBooking = PackageBookings(packageOrder.getPackageOrderId(),packageOrder.address,CurrentCustomerDetails.getInstance().getPersonalInfo().customerMobileNumber,provider.getPersonalInformation().id, packageOrder.getPackageOrderStartDate(),packageOrder.getPackageOrderEndDate())
        provider.addToPackageBookings(packageBooking)
        packageBookingListHandler.addPackageBooking(packageBooking)
        println(packageBooking)

    }
}