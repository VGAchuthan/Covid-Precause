package foodorder

object CurrentCustomerDetails {
    lateinit private var instance : CustomerDetails
    fun getInstance() : CustomerDetails{
        return this.instance
    }
    fun setInstance(customer : CustomerDetails) {
        this.instance = customer
    }
}