package users

//data class Users(var name : String, var mobileNumber : String, var emailId : String)
interface Users
class CurrentUser(){}
data class Customer(var customerName:String,var customerMobileNumber : String,
                    var customerEmailId : String, var address : String ) : Users

data class VaccineCampAdmin(var adminName:String,var adminMobileNumber : String,
                            var adminEmailId : String, var address : String) : Users

data class Provider(var id : Int, var providerName : String, var  providerArea : String,
                    var rating : Float)
data class Volunteer(var id : Int, var trustName: String, var area : String)

object CustomerList{
    private var listOfCustomer : ArrayList<Customer> = ArrayList()
    fun getCustomersList() : ArrayList<Customer>{
        return this.listOfCustomer
    }
    fun addCustomer(customer: Customer) : Boolean{
        return this.listOfCustomer.add(customer)
    }
    fun getCustomer(index : Int) : Customer{
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