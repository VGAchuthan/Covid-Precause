package transaction

interface TransactionHandler {
    fun addTransaction(transaction: Transaction) : Boolean
    fun getTransaction(id : Int) : List<Transaction>
}
object TransactionList : TransactionHandler{
    private var listOfTransactions : ArrayList<Transaction> = ArrayList()
    override fun addTransaction(transaction: Transaction) : Boolean{
        return this.listOfTransactions.add(transaction)
    }
    override fun getTransaction(id : Int) : List<Transaction>{
        return this.listOfTransactions.filter{it.from == id}
    }
    fun getTransactionsCount() : Int{
        return this.listOfTransactions.size
    }
}

object TransactionHelper {

    val transactionHandler : TransactionHandler = TransactionList
    fun processTransaction(transaction : Transaction) : Boolean{
        return transactionHandler.addTransaction(transaction)
    }
}