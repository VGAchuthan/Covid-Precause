package foodorder.fileoperationhelper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fileoperation.FileOperation
import foodorder.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface  OrderFileOperationHandler{
    fun addToOrdersFile(listOfOrders : List<Order>)
    suspend fun readFromOrdersFile() : ArrayList<Order>
}
class OrderFileOperationHelper : OrderFileOperationHandler{
    override fun addToOrdersFile(listOfOrders: List<Order>) {
        var json = Gson().toJson(listOfOrders)
        //println(json)
        CoroutineScope(Dispatchers.IO).launch{
            FileOperation.writeToFile("orders",json.toString())
        }
    }

    override suspend fun readFromOrdersFile(): ArrayList<Order> {
        var jsonString = CoroutineScope(Dispatchers.IO).async {
            FileOperation.readFromFile("orders")
        }.await()
//        if(jsonString.isEmpty()){
//            jsonString = ""
//        }

        val gson = Gson()
        val parseType = object : TypeToken<List<Order>>() {}.type
        val orderList = gson.fromJson<List<Order>>(jsonString, parseType)
        //println("IN READ FROM ${orderList}")

        return ArrayList(orderList)

    }
}