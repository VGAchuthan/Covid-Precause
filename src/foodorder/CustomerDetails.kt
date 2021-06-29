package foodorder

import users.Customer
import users.Provider
//object CustomerList{
//    fun getCustomeList() : ArrayList<CustomerDetails>{
//        return this.listOfCustomer
//    }
//}
interface CustomerHandler{
    fun addToBookmark(provider: Provider) : Boolean
}
class CustomerDetails : CustomerHandler{
    private var customerId : Int = 0
    private lateinit var personalInfo : Customer
    private var myOrders : ArrayList<Order> = ArrayList()
    private var myBookmarks : ArrayList<Provider> = ArrayList()
    constructor()
    constructor(customerId : Int,personelDetails : Customer){
        this.personalInfo = personelDetails
        this.customerId = customerId
    }
    fun getPersonalInfo() : Customer{
        return this.personalInfo
    }
    fun getCustomerId() : Int{
        return this.customerId
    }
    override fun addToBookmark(provider: Provider) : Boolean{
        return this.myBookmarks.add(provider)
    }
}