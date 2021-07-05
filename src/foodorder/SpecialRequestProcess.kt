package foodorder

import enums.EatingTimeType
import enums.SpecialRequestType
interface CustomerSpecialRequestHandler{
    fun addSpecialRequest(specialRequest: SpecialRequest) : Boolean
    fun getSpecialRequestByCustomerId(customerId : Int) : List<SpecialRequest>
}
interface FoodProviderSpecialRequestHandler{
    fun getSpecialRequestByProviderId(providerId: Int) :  List<SpecialRequest>
    fun updateSpecialRequestStatus(requestId : Int, amount: Float, status : SpecialRequestType)
}
object SpecialRequestList{
    private var listOfSpecialRequestList : ArrayList<SpecialRequest> = ArrayList()
    fun addSpecialRequest(specialRequest: SpecialRequest) : Boolean{
        return this.listOfSpecialRequestList.add(specialRequest)
    }
    fun getSpecialRequestByProviderId(providerId : Int) : List<SpecialRequest>{
        return this.listOfSpecialRequestList.filter{it.providerId == providerId}

    }
    fun getSpecialRequestByCustomerId(customerId : Int) : List<SpecialRequest>{
        return this.listOfSpecialRequestList.filter{ it.requester == customerId}
    }
    fun getSpecialRequestById(requestId : Int) : SpecialRequest{
        lateinit var request: SpecialRequest
        val listOfSpecialRequest = this.listOfSpecialRequestList
        for(list in listOfSpecialRequest){
            if(list.getSpecialRequestId() == requestId){
                request = list
                break
            }
        }
        return request
    }
    fun getSpecialRequestCount() : Int{
        return this.listOfSpecialRequestList.size
    }
}

data class SpecialRequest(var requester : Int, var foodItem : String,
                          var time : EatingTimeType,var providerId:Int ,var mobileNumber : String, var deliveryAddress : String,
                          var amount : Float = 0f,
                          var SpecialRequestType :SpecialRequestType = enums.SpecialRequestType.REQUESTED)
{
    private var specialRequestId : Int
    init {
        specialRequestId = SpecialRequestList.getSpecialRequestCount() + 1
    }
    fun getSpecialRequestId() : Int{
        return this.specialRequestId
    }
}
//var SpecialRequest.customerNumber : String
object SpecialRequestProcess : CustomerSpecialRequestHandler , FoodProviderSpecialRequestHandler{
    //val providersFilterHandler : FoodProvidersFilterHandler = FoodProvidersList
    override fun addSpecialRequest(specialRequest : SpecialRequest) : Boolean{
        return SpecialRequestList.addSpecialRequest(specialRequest)


    }

    override fun getSpecialRequestByCustomerId(customerId: Int): List<SpecialRequest> {
        return SpecialRequestList.getSpecialRequestByCustomerId(customerId).filter { it.requester == customerId }
    }

    override fun getSpecialRequestByProviderId(providerId: Int): List<SpecialRequest> {
        return SpecialRequestList.getSpecialRequestByProviderId(providerId).filter {it.providerId == providerId}
    }

    override fun updateSpecialRequestStatus(requestId: Int, amount: Float, status: SpecialRequestType) {
        val request = SpecialRequestList.getSpecialRequestById(requestId)
        request.SpecialRequestType = status
        request.amount = amount
    }
}