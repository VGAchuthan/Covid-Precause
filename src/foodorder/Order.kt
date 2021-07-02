package foodorder

import enums.StatusType
import foodorder.fileoperationhelper.OrderFileOperationHandler
import foodorder.fileoperationhelper.OrderFileOperationHelper
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

interface  OrderDispatcherHandler{
    fun getOrderedListById(orderId : Int): List<OrderedItems>
    fun getOrder(orderId : Int) : Order
}
interface OrderStartUpHandler{
    suspend fun readFromOrdersFile()
}

object OrdersList : OrderDispatcherHandler, OrderStartUpHandler{

    val fileOperationHandler : OrderFileOperationHandler = OrderFileOperationHelper()
    private var listOfOrders : ArrayList<Order> = ArrayList()
    fun addOrder(order : Order) : Boolean{
        //println(order)
        //println("Order is plced")
        val flag = this.listOfOrders.add(order)
        writeToFile(this.listOfOrders)
        return flag
    }
    fun getOrders(customerId : Int) : ArrayList<Order>{
        val listOfOrders  : ArrayList<Order> = this.listOfOrders.filter { it.customerId == customerId } as ArrayList<Order>
        return listOfOrders
    }

    override fun getOrder(orderId: Int): Order {
        return this.listOfOrders.get(orderId -1)
    }
    override fun getOrderedListById(orderId : Int): List<OrderedItems> {
        return this.listOfOrders.get(orderId-1).orderedItems
    }
    fun getOrderListCount() : Int{
        return this.listOfOrders.size
    }
    private fun writeToFile(listOfOrders : List<Order>){
        fileOperationHandler.addToOrdersFile(listOfOrders)

    }
    override suspend  fun readFromOrdersFile(){
        this.listOfOrders = fileOperationHandler.readFromOrdersFile()
    }
}

data class Order(var orderedItems : List<OrderedItems>, var amount : Float,
                 var providerId : Int, var customerId : Int,var  address : String ,
                 var status : StatusType = StatusType.BOOKED){
    private var orderId : Int =0
    init{
        orderId = OrdersList.getOrderListCount() + 1
    }
    fun getOrderId() : Int{
        return this.orderId
    }
}
object PackageOrderList {
    private var listOfpackageOrder : ArrayList<PackageOrder> = ArrayList()
    fun addPackageOrder(packageOrder: PackageOrder): Boolean{
        return this.listOfpackageOrder.add(packageOrder)
    }
    fun getPackageOrder(orderId: Int) : PackageOrder{
        return this.listOfpackageOrder.get(orderId -1)
    }
    fun getPackageOrders(customerId : Int) : List<PackageOrder>{
        val list = this.listOfpackageOrder.filter { it.customerId== customerId }
        return list

    }
    fun getPackageOrderListCount() : Int {
        return this.listOfpackageOrder.size
    }
}
data class PackageOrder( var packageId : Int, var providerId : Int,var price : Float,
                         var customerId : Int,var  address : String){
    private var packageOrderId = 0
    private var startDate : Date
    private var endDate : Date
    init{
        packageOrderId = PackageOrderList.getPackageOrderListCount() +  1
        val date : LocalDate = LocalDate.now()
        startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
        var today = LocalDate.now()
        var fifteenthDay = today.plusDays(14L)
        endDate = Date.from(fifteenthDay.atStartOfDay(ZoneId.systemDefault()).toInstant())
        //var todayStartTime =
        //endDate = startDate.
    }
    fun getPackageOrderId() : Int{
        return this.packageOrderId
    }
    fun getPackageOrderEndDate() : Date{
        return this.endDate
    }
    fun getPackageOrderStartDate() : Date{
        return this.startDate
    }

}