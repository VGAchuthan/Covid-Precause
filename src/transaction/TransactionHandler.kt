package transaction

import foodorder.*
import users.CustomerList

object TransactionHelper {

    val providerFilterHandler : ProviderFilterHandler = ProvidersList
    val providerTransactionHandler : ProviderTransactionHandler = ProviderDetails()
    val customerTransactionHandler : CustomerTransactionHandler = CustomerDetails()
    fun processTransaction(customerId : Int, providerId : Int, transaction : Transaction){
        val customer = CustomerList.getCustomer(customerId-1)
        val provider =  providerFilterHandler.getProvider(providerId)


        if(provider.addTransaction(transaction) && customer.addTransaction(transaction)){
            println("Transaction Successful")
        }


//        if(customerTransactionHandler.addTransaction(transaction) && providerTransactionHandler.addTransaction(transaction) ){
//            println("Transaction Successful")
//        }

    }
}