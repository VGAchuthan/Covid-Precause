package foodorder

class BookingOperationHelper {
    val bookingListHandler : BookingListOperationHandler = BookingList
    //val orderDataMaintanance : OrderDataHandler = OrderDataMaintanance()
    fun addBooking(order : Order, provider : ProviderDetails){
        val bookings = Bookings(order.getOrderId(),order.address,CurrentCustomerDetails.getInstance().getPersonalInfo().customerMobileNumber)
        provider.addToBookings(bookings)
        bookingListHandler.addBooking(bookings)
        println(bookings)


    }
}