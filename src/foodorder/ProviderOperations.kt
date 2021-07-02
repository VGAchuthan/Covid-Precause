package foodorder

import enums.EatingTimeType
import java.util.*

interface ProviderOperationHandler{
    fun addPackageScheme(packgeScheme : PackageScheme) : Boolean
    fun dispatchPackageBookings(packageBookings: PackageBookings, date : Date, time: EatingTimeType)
    fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType)
}

open class ProviderOperations  : FoodProvidersOperations(), ProviderOperationHandler{
    //var dispatchHandler : DispatchOperationHandler = DispatchOperation()
    override fun addPackageScheme(packageScheme: PackageScheme): Boolean {
        //var provider = getProvider(providerId)
        if(checkIfFoodPackageAlreadyPresents(packageScheme)){
            return false
        }

        val provider = CurrentFoodProviderDetails.getInstance() as ProviderDetails
        return provider.addPackage(packageScheme)
    }






    override fun dispatchPackageBookings(packageBookings: PackageBookings, date : Date, time: EatingTimeType) {
        dispatchHandler.dispatchPackageBooking(packageBookings,date, time)
    }

    override fun changeStatusOfPackageOrder(packageBookings: PackageBookings, date : Date, time: EatingTimeType) {
        dispatchHandler.changeStatusOfPackageOrder(packageBookings, date, time)
    }

    private  fun checkIfFoodPackageAlreadyPresents(foodPackageScheme: PackageScheme) :Boolean{
        var provider = CurrentFoodProviderDetails.getInstance() as ProviderDetails
        return provider.getPackageSchemes().contains(foodPackageScheme)
    }

}