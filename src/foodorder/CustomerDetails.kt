package foodorder

import transaction.Transaction
import users.Customer
import users.Provider
//object CustomerList{
//    fun getCustomeList() : ArrayList<CustomerDetails>{
//        return this.listOfCustomer
//    }
//}
interface CustomerHandler{
    fun addToBookmark(provider: Provider) : Boolean
    fun getBookmarks() : List<Provider>
}
interface CustomerTransactionHandler{
    fun addTransaction(transaction: Transaction) : Boolean
}
class CustomerDetails : CustomerHandler, CustomerTransactionHandler{
    private var customerId : Int = 0
    private lateinit var personalInfo : Customer
    private var myOrders : ArrayList<Order> = ArrayList()
    private var myBookmarks : ArrayList<Provider> = ArrayList()
    private var listOfTransactions : ArrayList<Transaction> = ArrayList()
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
    fun addToMyOrders(order : Order){
        //this.myOrders.add(order)
    }
    fun getMyOrders() : List<Order>{
        return OrdersList.getOrders(this.getCustomerId())
        //return this.myOrders
    }
    override fun addTransaction(transaction: Transaction) :  Boolean{
        return this.listOfTransactions.add(transaction)
    }

    override fun getBookmarks(): List<Provider> {
        return this.myBookmarks.toList()
    }
    override fun addToBookmark(provider: Provider) : Boolean{
        return this.myBookmarks.add(provider)
    }
}