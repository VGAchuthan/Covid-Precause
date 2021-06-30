

//import java.util.
import Input.Companion.isProperInt
import enums.EatingTimeType
import foodorder.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import transaction.Transaction
import users.*
import vaccinationcamp.*
import java.util.*

val campHandler : VaccineCampHandler = VaccinationCampList
val providersHandler : ProviderOperationHandler = ProviderOperations()
val providerFilterHandler : ProviderFilterHandler = ProvidersList
val customerHandler : CustomerOpertionHandler = CustomerOperations()
val listOfProviders  = ProvidersList.getProvidersList().toList()
lateinit var selectedFoodItems: ArrayList<OrderedItems> //= ArrayList()
lateinit var selectedProvider : ProviderDetails
enum class Users{
    CUSTOMER, PROVIDER, VOLUNTEER, VACCINECAMPADMIN
}
fun selectIndexValueFromList(list : List<Any>) : Int{
    var flag = true
    var value : Int
    do{
        value = Input.getIntValue()
        if(value <= list.size && value>0){

        }else{
            flag = false
            println("Enter Value Less than ${list.size}")
        }
    }while(flag == false)
    return value


}
var flag = 1
fun main(/*args : Array<String>*/) = runBlocking{
    //var number  = getProperDateWithPattern("dd/MM/yyyy")

    CoroutineScope(Dispatchers.IO).launch {getInitialValues()}


    do{
//        println(VaccinationCampList.getCampList().toList().groupBy { it.vaccines }.v
    println("Login as 1.Customer\t2.Provider\t3.Volunteer\t4.Vaccine Camp Admin\t5.Logout")

        var choice = Input.getIntValue()

    //val userType : Users = Users.values()[choice-1]
    println("selected $choice")
    when(choice){
        1 ->{
            var index : Int = 1
            for(customer in CustomerList.getCustomersList()){
                println("$index. ${customer.getPersonalInfo().customerName}")
                index++
            }
            val selectPerson = Input.getIntValue()
            val currentUser = CustomerList.getCustomersList().get(selectPerson-1)
            CurrentCustomerDetails.setInstance(currentUser)
            getCustomerFunctionalities()}
        2 ->{
            var index : Int = 1
            for(provider in ProvidersList.getProvidersList()){
                println("$index. ${provider.getPersonalInformation().providerName}")
                index++
            }
            val selectedPerson = Input.getIntValue()
            val selectedProvider = ProvidersList.getProvidersList().get(selectedPerson -1)
            println(selectedProvider)
            CurrentProviderDetails.setInstance(selectedProvider)
            println("current provider")
            println(CurrentProviderDetails.getInstance().getPackageSchemes())


            getProviderFunctionalities()}
        /*3 ->{getVolunteerFunctionalities()}*/
        4 ->{
            var index : Int = 1
            for(admin in VaccineAdminList.getAdminList()){
                println("$index. ${admin.adminName}")
                index++
            }
            val selectPerson = Input.getIntValue()
            val currentUser = VaccineAdminList.getAdminList().get(selectPerson - 1)

            getVaccineCampAdminFunctionalities()}
        5 -> {System.exit(5)}
    }
    }while(true)
}
private fun getCustomerFunctionalities(){
    do{
        println("1. View Vaccination Camp Details\t2.Order Healthy Food\t3.View My Orders")
        var  choice  = Input.getIntValue()
        when(choice){
            1->{viewVaccinationCampDetails()}
            2 ->{ viewOrderFunctionalities()}
            3 ->{ viewCustomerOrders()}
        }
        println("Do You want to continue Customer functionalities\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)

}
private fun getVaccineCampAdminFunctionalities(){
    var flag =1
    do{
        println("1. View Vaccination Camp Details\n2.Add Vaccination Camp Details")
        val choice = Input.getIntValue()
        when(choice){
            1 -> {viewVaccinationCampDetails()}
            2 -> {addVaccinationCampDetails()}
            //3 -> {}
        }
        println("Do you want to continue\n1.Yes\t2.No")
        flag = Input.getIntValue()


    }while(flag == 1)


}
private fun viewVaccinationCampDetails(){
    val handler : VaccineCustomerHandler = VaccinationCampCenterOperation()
    val campDetails : List<VaccinationCamp> = handler.getCampDetails()
//    for(camp in campDetails){
//        println(camp)
//    }
    var flag =1
    do{
        println("1.No Filter\t2.Filter by Area\t3.Filter By Date\t4.Filter by Vaccine\n" +
                "5.Filter by Pincode\t6.Filter by Age Group")
        var choice = Input.getIntValue()
        when(choice){
            1-> {showFilteredView(campDetails)}
            2-> {

                val area = reader.readLine()
                showFilteredView(handler.getDetailsByArea(area))}
            3->{
                println("Enter Date")
                val date = Input.getProperDateWithPattern("dd/MM/yyyy")
                showFilteredView(handler.getDetailsByDate(date))

            }
            4->{
                println("Enter Vaccine Name")
                val vaccineName =Input.getProperString()
                showFilteredView(handler.getDetailsByVaccine(vaccineName))
            }
            5 -> {
                println("Enter Pincode")
                val pincode = Input.getProperPincode()
                showFilteredView(handler.getDetailsByPincode(pincode))
            }
            6 ->{
                println("Enter Age Group")
                val ageGroup = Input.getProperString()
                showFilteredView(handler.getDetailsByAgeGroup(ageGroup))
            }
        }
        println("Do You want to Continue Filter Operations\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1)
}
private  fun showFilteredView(listOfCamp : List<VaccinationCamp>){
    //val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    if (listOfCamp.isEmpty()){
        println("No Data Found for this Filter Value")
    }else{
        for(camp in listOfCamp){
            println(camp)
            println()
        }

    }


}
private fun viewCustomerOrders(){
    val listOfOrders  = CurrentCustomerDetails.getInstance().getMyOrders()
    if(listOfOrders.size == 0){
        println("No Orders Found")
        return
    }
    else{
        for(orders in listOfOrders){
            println(orders)
        }
    }
}
private fun viewOrderFunctionalities(){
    do{
        println("1.View All Providers\t2.View  Providers By Area\t3.View By Food Items\n" +
                "4.View Bookmark Provider")

        val choice = Input.getIntValue()
        when(choice){
            1->{
                viewProvidersDetails(providerFilterHandler.getProvidersList())
            }
            2-> {
                println("Enter Area")
                val area = Input.getProperString()
                viewProvidersDetails(providerFilterHandler.searchByArea(area))
            }
            3-> {
                println("Enter Food Name")
                val foodName = Input.getProperString()
                viewProvidersDetails(providerFilterHandler.searchByFoodName(foodName))
            }
            4->{
                println("Bookmarked Providers")
                var list = CurrentCustomerDetails.getInstance().getBookmarks()
                var index =1
                for(providers in list){
                    println("$index. ${providers.providerName}")
                }
                var selectedIndex = selectIndexValueFromList(list)
                selectedProvider = providerFilterHandler.getProvider(list.get(selectedIndex - 1).id)
                println(selectedProvider.getPersonalInformation().id)
                performWithProviders()
            }
        }

        println("Do youu want to continue Customer Order Functionalities\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1)


}

private fun viewProvidersDetails(listOfProvider : List<ProviderDetails>){
    if(listOfProvider.size == 0){
        println("No Providers Data found for this filter")
        return
    }
    do{
        var index =1
        for(provider in listOfProvider){
            println(" ${index}. ${provider.getPersonalInformation().providerName}")
            index++
        }
        println("Select Provider")
        val selectedProviderIndex = Input.getIntValue()
        var valueFlag = true
        //
        do{
            if(selectedProviderIndex <= listOfProvider.size && selectedProviderIndex > 0){
                valueFlag = true
                selectedProvider = listOfProvider.get(selectedProviderIndex -1)
            }

            else{
                valueFlag = false
                println("Select Proper Value")
            }
            performWithProviders()

        }while(valueFlag == false)

        println("Do you want to search with another provider\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)
}
private fun performWithProviders(){
    println("1.View Food Items\t2.View Package Schemes\t3.Add to Bookmark")
    val choice = Input.getIntValue()
    when(choice){
        1->{
            println("Enter Time")
            val time = eatingTimeType()
            val foodList = selectedProvider.getFoodItems(time)
            listProvidersFoodItems(foodList)
            listSelectedFoodItems()
            var price = calculatePriceOfFoodItems()
            makeTransaction(price : Float)
            orderFoodItems(selectedFoodItems, price,time)
        }
        2->{
            listProviderPackageSchemes(selectedProvider.getPackageSchemes().toList())


        }
        3 ->{
            addProviderToBookmark(selectedProvider.getPersonalInformation())
        }
    }

}
private fun makeTransaction(price : Float){
    val transaction = Transaction(CurrentCustomerDetails.getInstance().getPersonalInfo().customerMobileNumber, selectedProvider.getPersonalInformation().id)
}
private fun addProviderToBookmark(provider : Provider){
    CurrentCustomerDetails.getInstance().addToBookmark(provider)
}
private fun listProviderPackageSchemes(listOfPackageSchemes : List<PackageScheme>){
    if(listOfPackageSchemes.size == 0){
        println("No Package Scheme Available for this Provider")
        return
    }
    var index =1
    for(packageScheme in listOfPackageSchemes){
        println("$index. ${packageScheme}")
        index++
    }

    println("1.Order Package Scheme\t2.Back")
    val choice = Input.getIntValue()
    when(choice){
        1->{
            val seletedIndex = selectIndexValueFromList(listOfPackageSchemes)
            val selectedPackageScheme = listOfPackageSchemes.get(seletedIndex -1)
            println("se;ected package $selectedPackageScheme")
            orderPackageScheme(selectedPackageScheme, selectedPackageScheme.price)
        }
        2->{ return }
    }

}
private fun listProvidersFoodItems(listOfFoodItems : List<FoodItem>){
    selectedFoodItems = ArrayList()
    var index =1
    if(listOfFoodItems.size == 0){
        println("No Food Items Found")
        return
    }
    for(foodItem in listOfFoodItems){
        println("$index. ${foodItem}")
        index++
    }
    println("1.Order Food Item\t2.Back")
    val choice = Input.getIntValue()
    if(choice == 1){
        println("Select Food Items")
        do{
            lateinit var selectedFood : FoodItem
            lateinit var orderedItems : OrderedItems



            var valueFlag = true
            do{
                var selectedFoodItemIndex = Input.getIntValue()
                valueFlag = false
                if(selectedFoodItemIndex <= listOfFoodItems.size && selectedFoodItemIndex > 0){
                   // valueFlag = false
                    selectedFood = listOfFoodItems.get(selectedFoodItemIndex -1)
                    println("Enter Count")
                    val count = Input.getIntValue()
                    orderedItems = OrderedItems(selectedFood, count)
                    selectedFoodItems.add(orderedItems)
                }
                else{
                    valueFlag = true
                    println("Select Proper Value")
                }

            }while(valueFlag == true)


            println("Do you want to add another food item?\n1.Yes\t2.No")
            flag = Input.getIntValue()

        }while(flag == 1)

    }
    else{
        return
    }

    //listSelectedFoodItems()

}
private fun listSelectedFoodItems(){
    println("selected foods")

    for(foodItem in selectedFoodItems){
        println("${foodItem.foodItems.foodName} - ${foodItem.count}")

    }
    println("Price : ${calculatePriceOfFoodItems()}")
    //orderFoodItems(selectedFoodItems, price)
    //println("Price :${calculatePriceOfFoodItems()}")
   // orderFoodItems()
}
private fun calculatePriceOfFoodItems() : Float{
    var price =0.0f
    for(foodItem in selectedFoodItems){
        println("${foodItem.foodItems.foodName} - ${foodItem.count}")
        price += (foodItem.foodItems.price * foodItem.count)
    }
    println("Price : $price")

    return price
}

private fun orderPackageScheme(packageScheme : PackageScheme, price : Float){
    println("Seleccted Rpovider $selectedProvider")
    val orderPackageScheme = PackageOrder(packageScheme.packageId,selectedProvider.getPersonalInformation().id,packageScheme.price,CurrentCustomerDetails.getInstance().getCustomerId(), CurrentCustomerDetails.getInstance().getPersonalInfo().address)
    customerHandler.orderPackage(orderPackageScheme)

}
private fun orderFoodItems(foodItems : ArrayList<OrderedItems>, price : Float, time : EatingTimeType){
   // println("Seleccted Rpovider $selectedProvider")
    val makeOrder = Order(foodItems,price, selectedProvider.getPersonalInformation().id,CurrentCustomerDetails.getInstance().getCustomerId(),CurrentCustomerDetails.getInstance().getPersonalInfo().address)
    customerHandler.orderFoodItems(makeOrder,time)

}
//NOTE: Add Vaccine camp Details
private fun addVaccinationCampDetails(){
    println("Enter Camp Center Informations")
    //println("Select Date")
    var flag : Int
    var dates : ArrayList<Date> = ArrayList()
    do{
        var date = Input.getProperDateWithPattern("dd/MM/yyyy")
        dates.add(date)
        println("Do You want to add another date\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)

    println("Enter Place")
    var place = Input.getProperString()
    println("Enter Duration")

    var duration  = Input.getProperString()
    println("Enter Area")
    var area  = Input.getProperString()
    val campDetails = CampDetails(dates, place, duration, area)
    //var flag =1
    var dosageFlag = 1
    var vaccines : ArrayList<Vaccine> =  ArrayList<Vaccine>()

    do{
        flag =1
        dosageFlag =1
        println("Enter Vaccine Name")
        var vaccineName : String= Input.getProperString()
        var listOfDosage : ArrayList<Dosage> = ArrayList()

        println("Enter Dosage Details")
        do{
            println("Enter Dose Number")
            var doseNumber = Input.getIntValue()
            println("Enter Dosage Count")
            var dosesCount = Input.getIntValue()
            var dosage = Dosage(doseNumber,dosesCount)
            listOfDosage.add(dosage)
            println("Do You want to add another dose details \n1.Yes\t2.No")
            dosageFlag = Input.getIntValue()
            println("DOSAGEFLAG $dosageFlag")

        }while(dosageFlag ==1)
        vaccines.add(Vaccine(vaccineName,listOfDosage))
        println("Do you want to add another vaccine \n1.Yes\t2.No")
        flag = Input.getIntValue()

    }while(flag == 1)
    var organisers : ArrayList<String> = ArrayList()

    do{
        println("Enter Organiser Name")
        var organiser : String = Input.getProperString()
        organisers.add(organiser)
        println("Do you want to add another organiser\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1 )

    println("Enter Sponsor  Name")
    val sponsor = Input.getProperString()
    println("Enter Pincode")
    val pincode = Input.getProperPincode()
    var ageGroups : ArrayList<String> = ArrayList()
    do{
        println("Enter age group")
        var ageGroup = Input.getProperString()
        ageGroups.add(ageGroup)
        println("Do you want to add another age group\n1.Yes\t2.No")
        flag = Input.getIntValue()

    }while(flag == 1)

    println("Only for : ")
    val onlyFor  = Input.getProperString()
    var contactDetails : ArrayList<ContactDetails> = ArrayList()
    do{
        println("Enter Contact Person Name")
        var contactPersonName = Input.getProperString()
        println("Enter Contact Person Number")
        var contactNumber = Input.getProperMobileNumber()
        contactDetails.add(ContactDetails(contactPersonName, contactNumber))
        //contactNumbers.add(contactNumber)
        println("Do you want to add another contact person detail\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1)

    val vaccineCampDetails = VaccinationCamp(campDetails,vaccines,organisers,sponsor,pincode,ageGroups,onlyFor,contactDetails)
    val adminHandler : VaccineAdminHandler = VaccinationCampCenterOperation()
    println(vaccineCampDetails)
    if(!adminHandler.checkIfCampAlreadyAvailable(vaccineCampDetails)){
        adminHandler.addCamp(vaccineCampDetails)
        //adminHandler.writeToFile()
    }
    else{
        println("Camp Already Exists")
    }



}

//NOTE: Provider Functionalities
private fun getProviderFunctionalities(){
    println("Current Provider")
   println("${CurrentProviderDetails.getInstance().getPackageSchemes()}")
    var continueFlag = 1
    do{
        println("1.Create New Package\t2.Create New Food Menu\t3.Create New Food Item\n4.Dispatch Bookings\t5.Dispatch Package Bookings")
        val choice = Input.getIntValue()
        when(choice){
            1 -> {
                createNewPackageScheme()
            }
            2 -> {
                createNewFoodMenu()
            }
            3-> {
                createNewFoodItem()
            }
            4->{
                do{
                    dispatchBookings()
                    println("Do You Want to continue Dispatch\n1.Yes\t2.No")
                    flag = Input.getIntValue()
                }while(flag == 1)
            }
            5->{
                do{
                    //println("befor show")
                    //showMyPackageBookings()
                    dispatchPackageBookings()
                    println("Do You want to continue Package Dispatch\n1.Yes\t2.No ")
                    flag = Input.getIntValue()

                }while(flag==1)
            }
        }


        println("Do You want to Continue as Provider\n1.Yes\t2.No ")
        continueFlag = Input.getIntValue()
    }while(continueFlag == 1)

}

private fun createNewPackageScheme(){
    println("Enter Package Details")
    flag = 1
    do{
        var listOfMenu : ArrayList<FoodMenu> = ArrayList()
        println("Enter Package Id")
        val packageId = Input.getIntValue()
        println("Enter Package Name")
        val packageName = Input.getProperString()
        var menuFlag =1
        //do{
            println("1.Create New Food Item\t2.Select Food Items from List")
            var choice = Input.getIntValue()
            when(choice){
                1 -> {
                    do{
                        println("Do You want to add another food menu\n1.Yes\t2.No")
                        flag = Input.getIntValue()
                    }while(flag == 1)
                    var foodmenu = createNewFoodMenu()
                    listOfMenu.add(foodmenu)
                   // println(foodmenu)

                }
                2 -> {

                    do{
                        println("Enter Type")
                        val type = eatingTimeType()
                        var index = 1
                        for(foodMenu in CurrentProviderDetails.getInstance().getFoodItems())
                        {
                            println("$index. ${foodMenu}")
                            index++
                        }
                        val selectedItems = Input.getProperString()
                        val splitString : List<String> = selectedItems.split(',')
                        var foodItems = getSelectedFoodItems(CurrentProviderDetails.getInstance().getFoodItems().toList(), splitString)
                        //listOfFoodItems = foodItems
                        val menu = FoodMenu(type,foodItems)
                        if(providersHandler.addFoodMenu(menu))
                        {
                            println("Food Menu added Successfully")
                            listOfMenu.add(menu)
                        }
                        else{
                            println("Food Menu Already Exists")
                        }
                        println("Do You want to continue for another menu\n1.Yes\t2.No")
                        flag = Input.getIntValue()
                    }while(flag == 1)

                    //println("Do You wnt to ")


                }
            }
        //}while(menuFlag == 1)
        println("Enter Package Scheme Price")
        val packagePrice = Input.getFloatValue()
       val packageScheme = PackageScheme(packageId, packageName, listOfMenu, packagePrice )
        providersHandler.addPackageScheme(packageScheme)
        println("Do You Want to add Another Package Details\n1.Yes\t2.No")
        flag = Input.getIntValue()

    }while(flag == 1)
}
private fun eatingTimeType() : EatingTimeType{
    var index =1
    for(type in EatingTimeType.values()){
        print("$index. ${type}\t")
        index++
    }
    println()
    var flag =1
    do{
        var selectedTypeIndex = Input.getIntValue()
        if(selectedTypeIndex <= EatingTimeType.values().size){
            when(selectedTypeIndex){
                1 -> { return EatingTimeType.BREAKFAST}
                2 -> { return EatingTimeType.LUNCH}
                3-> {return EatingTimeType.DINNER}
            }
        }
        else{
            flag = 0
            println("Select valid Index")
        }

    }while(flag == 0)


    return EatingTimeType.BREAKFAST

}
private fun createNewFoodMenu() : FoodMenu{
    lateinit var foodMenu : FoodMenu
    var listOfFoodItems : ArrayList<FoodItem> = ArrayList()
    println("Enter Food Menu Details")
    println("Enter Type")
    val type = eatingTimeType()
    println("Enter Food Items")
    println("1.Create New Food Items\t2.Select from Item List")
    val choice = Input.getIntValue()
    when(choice){
        1 -> {
            do{
                var foodItem = createNewFoodItem()
                listOfFoodItems.add(foodItem)
                println("Do you want to add another food item\n1.Yes\t2.No")
                flag = Input.getIntValue()
            }while(flag == 1)
            foodMenu = FoodMenu(type,listOfFoodItems)
            //createNewFoodItem()
            }
        2->{
//            do{
//                println("Do You want to continue ")
//            }while(flag == 1)

            var index = 1
            for(foodItem in CurrentProviderDetails.getInstance().getFoodItems())
            {
                println("$index. ${foodItem.foodName}")
                index++
            }
            println("Select Food Item Numbers Seperated by commas (,)")
            val selectedItems = Input.getProperString()
            val splitString : List<String> = selectedItems.split(',')
            var foodItems = getSelectedFoodItems(CurrentProviderDetails.getInstance().getFoodItems().toList(), splitString)
            listOfFoodItems = foodItems
            foodMenu = FoodMenu(type,listOfFoodItems)
            if(providersHandler.addFoodMenu(foodMenu))
            {
                println("Food Menu added Successfully")
            }
            else{
                println("Food Menu Already Exists")
            }
        }
    }
    return foodMenu

}
private fun getSelectedFoodItems(listOfFoodItems : List<FoodItem>, splitString : List<String>) : ArrayList<FoodItem>{
    var list : ArrayList<FoodItem> = ArrayList()
    for(valueOfSplittedString in splitString){
        if(valueOfSplittedString.trim().isProperInt()){
            list.add(listOfFoodItems.get(valueOfSplittedString.toInt() - 1))
        }
    }
    return list

}
private fun createNewFoodItem() : FoodItem{
    lateinit var foodItem: FoodItem
    println("Enter New Food Item")
   // do{
        println("Enter Food Name")
        var foodName = Input.getProperString()
        println("Enter Food Benefits")
        var foodBenefits = Input.getProperString()
        println("Enter Food Price")
        var foodPrice = Input.getFloatValue()
        foodItem = FoodItem(foodName, foodBenefits, foodPrice)
        if(providersHandler.addFoodItem(foodItem)){
            println("Food item added successfully")
        }
        else{
            println("Food Item Not added successfully")
        }
     //   println("Do you want to add another food item")
       // flag = Input.getIntValue()
    //}while(flag == 1)
    return foodItem
}
private fun showMyPackageBookings() : List<PackageBookings>{
    val listOfPackageBooking = CurrentProviderDetails.getInstance().getMyPackageBookings()
    var index =1
    for(packageBooking in listOfPackageBooking){
        println("$index . $packageBooking")
        index++
    }
    return listOfPackageBooking

}
private fun showMyPendingBookings(time : EatingTimeType) : List<Bookings>{
    val listOfbookings = CurrentProviderDetails.getInstance().getMyBookings(time)
    //val listOfPackageBooking = CurrentProviderDetails.getInstance().getMyPackageBookings()
    //val list  = listOf<Any>(listOfPackageBooking, listOfbookings)
    var index =1
    for(booking in listOfbookings){
        println("$index. $booking")
        index++
    }
    return listOfbookings

}
private fun dispatchPackageBookings(){
    var dispatchHandler : DispatchOperationHandler = DispatchOperation()
    println("Enter Time Schedule")
    val time = eatingTimeType()
    do{
        var listOfbookings = showMyPackageBookings()
        if(listOfbookings.size == 0){
            println("No Bookings to Dispatch")
            return
        }
        var menuFlag = true
        do{
            val selectIndexToDispatch: Int = Input.getIntValue()
            if(selectIndexToDispatch<= listOfbookings.size && selectIndexToDispatch >-1){
                menuFlag = true
                var selectedBookings = listOfbookings.get(selectIndexToDispatch -1)
                dispatchHandler.dispatchPackageBooking(selectedBookings, time)
                println("Enter 1 to Deliver Items")
                var value = Input.getIntValue()
                if(value == 1){
                    println("Delivered")
                   // dispatchHandler.changeOrderStatus(selectedBookings)

                }
                else{

                }

            }
            else{
                menuFlag = false
                println("Select Proper Value")
            }
        }while(menuFlag == false)
        println("Do You Want to Continue Dispatch Operations\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)
}
private fun dispatchBookings(){
    var dispatchHandler : DispatchOperationHandler = DispatchOperation()
    println("Enter Time Schedule")
    val time = eatingTimeType()

    do{
        var listOfbookings = showMyPendingBookings(time)
        if(listOfbookings.size == 0){
            println("No Bookings to Dispatch")
            return
        }
        var menuFlag = true
        do{
            val selectIdToDispatch: Int = Input.getIntValue()
            if(selectIdToDispatch<= listOfbookings.size && selectIdToDispatch >-1){
                menuFlag = true
                var selectedBookings = listOfbookings.get(selectIdToDispatch -1)
                dispatchHandler.dispatchBooking(selectedBookings)
                println("Enter 1 to Deliver Items")
                var value = Input.getIntValue()
                if(value == 1){
                    println("Delivered")
                    dispatchHandler.changeOrderStatus(selectedBookings)

                }
                else{

                }

            }
            else{
                menuFlag = false
                println("Select Proper Value")
            }
        }while(menuFlag == false)
        println("Do You Want to Continue Dispatch Operations\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)





}
suspend private fun  getInitialValues(){
   // println("insude initil vlue")
    CoroutineScope(Dispatchers.IO).launch{
        campHandler.setCampListFromFile()
        //println("Inside setcamp Coro")
    }

    // Two Customer Values
    CoroutineScope(Dispatchers.Default).launch {
        var customer1  = Customer("Customer1","9090909090","customer1@gmail.com","11, Street 1, Anna Nagar, 666991")
        var customer2 = Customer("Customer2","9087654321","customer2@gmail.com","21R, Street 2, Senoy Nagar, 623451")
        CustomerList.addCustomer(CustomerDetails(1,customer1))
        CustomerList.addCustomer(CustomerDetails(2,customer2))
    }
    //CustomerList.addCustomer(Customer("Customer1","9090909090","customer1@gmail.com","11, Street 1, Anna Nagar, 666991") )
    //CustomerList.addCustomer(Customer("Customer2","9087654321","customer2@gmail.com","21R, Street 2, Senoy Nagar, 623451"))
    // Two Vaccine Admin Values
    VaccineAdminList.addVaccineAdmin(VaccineCampAdmin("Admin1","9876567890","admin1@gmail.com","12A, Cross Street 1, Anna Nagar, 654321"))
    VaccineAdminList.addVaccineAdmin(VaccineCampAdmin("Admin2","9876554433","admin2@gmail.com","12V, Cross Street 6, Anna Nagar, 654322"))
    // Two Provider Values
    CoroutineScope(Dispatchers.Default).launch{
        val provider1 = Provider(1,"Provider1","Anna Nagar",4.0f)
        ProvidersList.addProvider(ProviderDetails(provider1))
        val provider2 = Provider(2,"Provider2","Cheran Nagar",3.7f)
        ProvidersList.addProvider(ProviderDetails(provider2))
        var foodItem1 = FoodItem("food1","Increases Immune",20.0f)
        var foodItem2 = FoodItem("food2","Maintain Sugar Level",16.0f)
        var foodItem3 = FoodItem("food3","Reduces Cough",18.0f)
        var foodItem4 = FoodItem("food4","Reduces Kidney Problems",21.0f)
        var foodItem5 = FoodItem("food5","Reduces Bad Cholestrol",21.0f)
        // Food items for Provider1
        ProvidersList.getProvidersList().get(0).addFoodItem(foodItem1)
        ProvidersList.getProvidersList().get(0).addFoodItem(foodItem2)
        ProvidersList.getProvidersList().get(0).addFoodItem(foodItem3)
        ProvidersList.getProvidersList().get(0).addFoodItem(foodItem4)
        //ProvidersList.getProvidersList().get(0).addFoodItem(foodItem5)

        //Food items for Provider2
        ProvidersList.getProvidersList().get(1).addFoodItem(foodItem1)
        ProvidersList.getProvidersList().get(1).addFoodItem(foodItem2)
        ProvidersList.getProvidersList().get(1).addFoodItem(foodItem3)
        ProvidersList.getProvidersList().get(1).addFoodItem(foodItem4)
        ProvidersList.getProvidersList().get(1).addFoodItem(foodItem5)

        var foodMenu1 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem1, foodItem2)))
        var foodMenu2 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem3)))
       // var foodMenu3 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem1, foodItem4)))
        var foodMenu4 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem4, foodItem2)))
        var foodMenu5 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem5)))
        var foodMenu6 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem3, foodItem4)))
        //Food Menu for Provider1
        ProvidersList.getProvidersList().get(0).addFoodMenu(foodMenu1)
        ProvidersList.getProvidersList().get(0).addFoodMenu(foodMenu2)
        //ProvidersList.getProvidersList().get(0).addFoodMenu(foodMenu3)

        //Food Menu for Provider2
        ProvidersList.getProvidersList().get(1).addFoodMenu(foodMenu6)
        ProvidersList.getProvidersList().get(1).addFoodMenu(foodMenu4)
        ProvidersList.getProvidersList().get(1).addFoodMenu(foodMenu5)
        //Package 1 for Provider 1
        ProvidersList.getProvidersList().get(0).addPackage(PackageScheme(1,"Package1", ArrayList( listOf(foodMenu1, foodMenu2)),2500.0f))

        ProvidersList.getProvidersList().get(1).addPackage(PackageScheme(2,"Package2", ArrayList( listOf(foodMenu4,foodMenu5,foodMenu6)),2300.0f))

    }

    // Two Volunteer Values
    CoroutineScope(Dispatchers.Default).launch {
        val volunteer1 = Volunteer(1001,"Provider1","Anna Nagar")
        VolunteersList.addVolunteer(VolunteerDetails(volunteer1))
        val volunteer2 = Volunteer(1002,"Provider2","Cheran Nagar")
        VolunteersList.addVolunteer(VolunteerDetails(volunteer2))
        var foodItem1 = FoodItem("food1","Increases Immune",20.0f)
        var foodItem2 = FoodItem("food2","Maintain Sugar Level",16.0f)
        var foodItem3 = FoodItem("food3","Reduces Cough",18.0f)
        var foodItem4 = FoodItem("food4","Reduces Kidney Problems",21.0f)
        var foodItem5 = FoodItem("food5","Reduces Bad Cholestrol",21.0f)
        // Food items for Provider1
        VolunteersList.getVolunteersList().get(0).addFoodItem(foodItem1)
        VolunteersList.getVolunteersList().get(0).addFoodItem(foodItem2)
        VolunteersList.getVolunteersList().get(0).addFoodItem(foodItem3)
        VolunteersList.getVolunteersList().get(0).addFoodItem(foodItem4)
        //VolunteersList.getVolunteersList().get(0).addFoodItem(foodItem5)

        //Food items for Volunteer2
        VolunteersList.getVolunteersList().get(1).addFoodItem(foodItem1)
        VolunteersList.getVolunteersList().get(1).addFoodItem(foodItem2)
        VolunteersList.getVolunteersList().get(1).addFoodItem(foodItem3)
        VolunteersList.getVolunteersList().get(1).addFoodItem(foodItem4)
        VolunteersList.getVolunteersList().get(1).addFoodItem(foodItem5)

        var foodMenu1 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem1, foodItem2)))
        var foodMenu2 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem3)))
        // var foodMenu3 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem1, foodItem4)))
        var foodMenu4 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem4, foodItem2)))
        var foodMenu5 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem5)))
        var foodMenu6 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem3, foodItem4)))
        //Food Menu for Volunteer1
        VolunteersList.getVolunteersList().get(0).addFoodMenu(foodMenu1)
        VolunteersList.getVolunteersList().get(0).addFoodMenu(foodMenu2)
        //VolunteersList.getVolunteersList().get(0).addFoodMenu(foodMenu3)

        //Food Menu for Volunteer2
        VolunteersList.getVolunteersList().get(1).addFoodMenu(foodMenu6)
        VolunteersList.getVolunteersList().get(1).addFoodMenu(foodMenu4)
        VolunteersList.getVolunteersList().get(1).addFoodMenu(foodMenu5)

    }


}