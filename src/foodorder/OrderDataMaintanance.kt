package foodorder
interface OrderDataHandler{
    fun mapOrderToProvider(order :Order)

}
interface  BookingDataHandler{
    fun updateStatusOfOrder(orderId : Int)

}
class OrderDataMaintanance  : OrderDataHandler, BookingDataHandler{
    val providersFilterHandler : ProviderFilterHandler = ProvidersList
    val bookingOperation : BookingOperationHelper = BookingOperationHelper()

    override fun mapOrderToProvider(order: Order) {
        println("Inside map order to provider")
        println(order)
        val provider = providersFilterHandler.getProvider(order.providerId)
        bookingOperation.addBooking(order,provider)
        println(provider)
    }

    override fun updateStatusOfOrder(orderId: Int) {
        TODO("Not yet implemented")
    }
}