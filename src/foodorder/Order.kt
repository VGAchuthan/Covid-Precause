package foodorder

import enums.StatusType

object OrdersList{
    private var listOfOrders : ArrayList<Order> = ArrayList()
    fun addOrder(order : Order) : Boolean{
        println(order)
        println("Order is plced")
        return this.listOfOrders.add(order)
    }
    fun getOrderById() : Order{
        return this.listOfOrders.get(0)
    }
    fun getOrderListCount() : Int{
        return this.listOfOrders.size
    }
}

class Order(var orderedItems : List<OrderedItems>, var amount : Float,var providerId : Int, var customerId : Int,var  address : String ,var status : StatusType = StatusType.BOOKED){
    private var orderId : Int =0
    //lateinit var orderedItems: OrderedItems
    //var amount = 0.0f
    //constructor()
    init{
        orderId = OrdersList.getOrderListCount() + 1
    }
    fun getOrderId() : Int{
        return this.orderId
    }



}