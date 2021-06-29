package foodorder

import enums.EatingTimeType

interface DispatchOperationHandler{
    fun getMyBookings(type : EatingTimeType) : List<Bookings>
    fun dispatchBooking(bookingId : Int)
}
class DispatchOperation : DispatchOperationHandler {
    val orderDataMaintanance : OrderDataHandler = OrderDataMaintanance()
    val providerDetailsHandler : ProviderHandler = ProviderDetails()
    lateinit var dispatchingList : List<Bookings>
    override fun getMyBookings(type: EatingTimeType): List<Bookings> {
        dispatchingList = providerDetailsHandler.getMyBookings()
        for(booking in dispatchingList){
            println("booking")
        }
        TODO("Not yet implemented")
    }

    override fun dispatchBooking(bookingId: Int) {
        TODO("Not yet implemented")
    }
}