package users

import foodorder.CustomerDetails

//data class Users(var name : String, var mobileNumber : String, var emailId : String)
interface Users
class CurrentUser(){}
data class Customer(var customerName:String,var customerMobileNumber : String,
                    var customerEmailId : String, var address : String ) : Users

data class VaccineCampAdmin(var adminName:String,var adminMobileNumber : String,
                            var adminEmailId : String, var address : String) : Users
open class FoodProviders(open var id : Int, open var name : String, open var  area : String,
                         open var mobileNumber : String,open var rating : Float){
    //abstract var id
    //constructor()

}
data class Provider(override var id : Int, override var name : String,override var  area : String,
                    override var mobileNumber : String ,override var rating : Float) : FoodProviders(id, name, area, mobileNumber,rating)
data class Volunteer(override var id : Int,override var name: String,
                     override var area : String, override var mobileNumber : String,override var rating : Float) : FoodProviders(id, name, area, mobileNumber,rating)

object CustomerList{
    private var listOfCustomer : ArrayList<CustomerDetails> = ArrayList()
    fun getCustomersList() : ArrayList<CustomerDetails>{
        return this.listOfCustomer
    }
    fun addCustomer(customer: CustomerDetails) : Boolean{
        return this.listOfCustomer.add(customer)
    }
    fun getCustomer(index : Int) : CustomerDetails{
        return this.listOfCustomer.get(index)
    }
}
object VaccineAdminList{
    private var listOfVaccineAdmin : ArrayList<VaccineCampAdmin> = ArrayList()
    fun getAdminList() : ArrayList<VaccineCampAdmin>{
        return this.listOfVaccineAdmin
    }
    fun addVaccineAdmin(admin : VaccineCampAdmin) : Boolean{
        return this.listOfVaccineAdmin.add(admin)
    }
    fun getVaccineAdmin(index : Int) : VaccineCampAdmin{
        return this.listOfVaccineAdmin.get(index)
    }
}