package foodorder

import enums.StatusType
interface BookingListOperationHandler{
    fun addBooking(booking: Bookings) : Boolean
}
object BookingList : BookingListOperationHandler{
    private var listOfBookings : ArrayList<Bookings> = ArrayList()
    override fun addBooking(booking : Bookings) : Boolean{
        return this.listOfBookings.add(booking)
    }
    fun getBookingListSize() : Int{
        return this.listOfBookings.size
    }
}

data class Bookings(val orderId : Int,
               val deliveryAddress : String, val customerMobileNumber : String,
               var status : StatusType = StatusType.BOOKED){
    private var bookingId : Int = 0
    init {
        bookingId = BookingList.getBookingListSize() + 1
    }


}