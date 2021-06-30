package foodorder

import enums.EatingTimeType
import enums.StatusType
interface BookingListOperationHandler{
    fun addBooking(booking: Bookings) : Boolean
    fun getMyBookings(providerId : Int) : ArrayList<Bookings>
}
object BookingList : BookingListOperationHandler{
    private var listOfBookings : ArrayList<Bookings> = ArrayList()
    override fun addBooking(booking : Bookings) : Boolean{
        return this.listOfBookings.add(booking)
    }

    override fun getMyBookings(providerId: Int): ArrayList<Bookings> {
        val listOfBookings  : ArrayList<Bookings> = this.listOfBookings.filter { it.providerId == providerId } as ArrayList<Bookings>
        return listOfBookings
    }
    fun getBookingListSize() : Int{
        return this.listOfBookings.size
    }
}

interface PackageBookingListOperationHandler{
    fun addPackageBooking(packageBookings: PackageBookings) : Boolean
    fun getMyPackageBookings(providerId : Int) : ArrayList<PackageBookings>
}


object PackageBookingList : PackageBookingListOperationHandler{
    private var listOfPackageBookings : ArrayList<PackageBookings> = ArrayList()
    override fun addPackageBooking(packageBookings: PackageBookings): Boolean {
        return this.listOfPackageBookings.add(packageBookings)
    }

    override fun getMyPackageBookings(providerId: Int): ArrayList<PackageBookings> {
        val listOfBookings  : ArrayList<PackageBookings> = this.listOfPackageBookings.filter{ it.providerId == providerId} as ArrayList<PackageBookings>
        return listOfBookings
    }
    //fun getPackage
    fun getPackageBookingSize() : Int{
        return this.listOfPackageBookings.size
    }
}
data class Bookings(val orderId : Int, val time : EatingTimeType,
               val deliveryAddress : String, val customerMobileNumber : String,var providerId : Int,
               var status : StatusType = StatusType.BOOKED){
    private var bookingId : Int = 0
    init {
        bookingId = BookingList.getBookingListSize() + 1
    }


}

data class PackageBookings(val pakageOrderId : Int,val deliveryAddress : String,
                           val customerMobileNumber : String,var providerId : Int){
    private var bookingId : Int = 0
    init {
        bookingId = PackageBookingList.getPackageBookingSize() + 1
    }
    fun getBookingId() : Int{
        return this.bookingId
    }
}