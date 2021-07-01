package foodorder

import enums.EatingTimeType
import enums.StatusType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

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
    fun getMyPackageBookings(providerId : Int,date : Date, time: EatingTimeType) : ArrayList<PackageBookings>
}


object PackageBookingList : PackageBookingListOperationHandler{
    private var listOfPackageBookings : ArrayList<PackageBookings> = ArrayList()
    override fun addPackageBooking(packageBookings: PackageBookings): Boolean {
        return this.listOfPackageBookings.add(packageBookings)
    }

    override fun getMyPackageBookings(providerId: Int, date : Date, time: EatingTimeType): ArrayList<PackageBookings> {
        val index = when(time){
            EatingTimeType.BREAKFAST->{0}
            EatingTimeType.LUNCH->{1}
            EatingTimeType.DINNER->{2}
        }
       // println("In pack boking list : $index")
        //println("In pack book Date : $date")
        val listOfBookings  : ArrayList<PackageBookings> = this.listOfPackageBookings.filter{ ((it.providerId == providerId) &&(it.endDate.compareTo(date)>=0) && (it.getSchedule().get(date)!![index].status == StatusType.BOOKED) )} as ArrayList<PackageBookings>

        //println(listOfBookings)
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
                           val customerMobileNumber : String,var providerId : Int,
                           val startDate  : Date,val endDate : Date){
    private var bookingId : Int = 0
    private var schedule : LinkedHashMap<Date,Array<DeliveryStatusSchedule>> = LinkedHashMap()
    init {
        bookingId = PackageBookingList.getPackageBookingSize() + 1
        var statusSchedule : Array<DeliveryStatusSchedule> = arrayOf(DeliveryStatusSchedule(EatingTimeType.BREAKFAST,StatusType.BOOKED),DeliveryStatusSchedule(EatingTimeType.LUNCH,StatusType.BOOKED) ,DeliveryStatusSchedule(EatingTimeType.DINNER,StatusType.BOOKED))
        var localDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd").format(this.startDate))
        schedule.put(startDate,statusSchedule)
        for(index in 1..14){
            var nextLocalDate = localDate.plusDays(index.toLong())
            var nextDate = Date.from(nextLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

            schedule.put(nextDate, statusSchedule)
        }
        println(schedule)


    }
    fun getBookingId() : Int{
        return this.bookingId
    }
    fun getSchedule() :LinkedHashMap<Date,Array<DeliveryStatusSchedule>> {
        return this.schedule
    }

    override fun toString(): String {
        return "PackageBookings(pakageOrderId=$pakageOrderId, deliveryAddress='$deliveryAddress', customerMobileNumber='$customerMobileNumber', providerId=$providerId, startDate=$startDate, endDate=$endDate, bookingId=$bookingId, schedule=$schedule)"
    }


}
//data class PackageOrderSchedule(val date : Date, var schedule : ArrayList<DeliveryStatusSchedule>)
data class DeliveryStatusSchedule(var time : EatingTimeType, var status : StatusType)