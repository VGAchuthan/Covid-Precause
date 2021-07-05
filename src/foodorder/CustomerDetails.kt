package foodorder

import transaction.Transaction
import users.Customer
import users.FoodProviders
//object CustomerList{
//    fun getCustomeList() : ArrayList<CustomerDetails>{
//        return this.listOfCustomer
//    }
//}
interface CustomerHandler{
    fun addToBookmark(foodprovider: FoodProviders) : Boolean
    fun getBookmarks() : List<FoodProviders>
}
interface CustomerTransactionHandler{
    fun addTransaction(transaction: Transaction) : Boolean
}
class CustomerDetails : CustomerHandler, CustomerTransactionHandler{
    private var customerId : Int = 0
    private lateinit var personalInfo : Customer
    private var myOrders : ArrayList<Order> = ArrayList()
    private var myBookmarks : ArrayList<FoodProviders> = ArrayList()
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
    fun getMyPackageOrders() : List<PackageOrder>{
        return PackageOrderList.getPackageOrders(this.getCustomerId())

    }
    fun getMySpecialRequest(): List<SpecialRequest>{
        return SpecialRequestList.getSpecialRequestByCustomerId(this.getCustomerId())
    }
    override fun addTransaction(transaction: Transaction) :  Boolean{
        return this.listOfTransactions.add(transaction)
    }

    override fun getBookmarks(): List<FoodProviders> {
        return this.myBookmarks.toList()
    }
    override fun addToBookmark(foodprovider: FoodProviders) : Boolean{
        return this.myBookmarks.add(foodprovider)
    }
}