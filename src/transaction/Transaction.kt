package transaction

data class Transaction(val from : Int, val to : Int, val amount : Float) {
    private var transactionId : Int = 0
    init {
        transactionId = TransactionList.getTransactionsCount() + 1
    }
    fun getTransactionId() : Int{
        return this.transactionId
    }
}