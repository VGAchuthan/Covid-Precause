

//import java.util.
import Input.Companion.isProperInt
import enums.EatingTimeType
import foodorder.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import transaction.Transaction
import transaction.TransactionHelper
import users.*
import vaccinationcamp.*
import java.util.*
var currentUserType : Users = Users.PROVIDER
val campHandler : VaccineCampHandler = VaccinationCampList
var providersHandler : FoodProviderOperationHandler = FoodProvidersOperations()
val providerFilterHandler : FoodProvidersFilterHandler = FoodProvidersList
val customerHandler : CustomerOpertionHandler = CustomerOperations()
//val listOfProviders  = ProvidersList.getProvidersList().toList()
//= ArrayList()
lateinit var selectedProvider : FoodProviderDetails
enum class Users{
    CUSTOMER, PROVIDER, VOLUNTEER, VACCINECAMPADMIN
}
fun selectIndexValueFromList(list : List<Any>) : Int{
    var flag = true
    var value : Int
    do{
        value = Input.getIntValue()
        if(value <= list.size && value>0){
            flag = true

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
    println("Login as 1.Customer\t2. Food Providers\t3.Vaccine Camp Admin\t4.Logout")

        var choice = Input.getIntValue()

    //val userType : Users = Users.values()[choice-1]
    println("selected $choice")
    when(choice){
        1 ->{
            currentUserType = Users.CUSTOMER
            var index : Int = 1
            for(customer in CustomerList.getCustomersList()){
                println("$index. ${customer.getPersonalInfo().customerName}")
                index++
            }
            val selectPerson = selectIndexValueFromList(CustomerList.getCustomersList())
            val currentUser = CustomerList.getCustomersList().get(selectPerson-1)
            CurrentCustomerDetails.setInstance(currentUser)
            getCustomerFunctionalities()}
        2 ->{
           // currentUserType = Users.PROVIDER
            var index : Int = 1
            for(provider in FoodProvidersList.getFoodProvidersList()){
                println("$index. ${provider.getPersonalInformation().name} - ${provider.getPersonalInformation().rating}")
                index++
            }
            val selectedPerson = Input.getIntValue()
            val selectedProvider = FoodProvidersList.getFoodProvidersList().get(selectedPerson -1)
            //println(selectedProvider)
            CurrentFoodProviderDetails.setInstance(selectedProvider)
            if(selectedProvider is VolunteerDetails){
                currentUserType = Users.VOLUNTEER
            }
            if(selectedProvider is ProviderDetails){
                currentUserType = Users.PROVIDER
            }
            //println("current provider")
            //println(CurrentProviderDetails.getInstance().getPackageSchemes())


            getProviderFunctionalities()}


        3 ->{
            var index : Int = 1
            for(admin in VaccineAdminList.getAdminList()){
                println("$index. ${admin.adminName}")
                index++
            }
            val selectPerson = Input.getIntValue()
            val currentUser = VaccineAdminList.getAdminList().get(selectPerson - 1)

            getVaccineCampAdminFunctionalities()}
        4 -> {System.exit(4)}
    }
    }while(true)
}
private fun getCustomerFunctionalities(){
    println(FoodProvidersList.getFoodProvidersList())
    do{
        println("1. View Vaccination Camp Details\t2.Order Healthy Food\t3.View My Orders\n" +
                "4.View My Package Orders\t5.Add Review")
        var  choice  = Input.getIntValue()
        when(choice){
            1->{viewVaccinationCampDetails()}
            2 ->{ viewOrderFunctionalities()}
            3 ->{
                val listOfOrders  = CurrentCustomerDetails.getInstance().getMyOrders()
                viewCustomerOrders(listOfOrders)}
            4 -> {viewCustomerPackageOrders()}
            5 ->{
                val listOfOrders  = CurrentCustomerDetails.getInstance().getMyOrders()
                viewCustomerOrders(listOfOrders)
                println("Select Order to give Review/ Feedback")
                val selectedIndex = selectIndexValueFromList(listOfOrders)
                val selectedOrder = listOfOrders.get(selectedIndex -1)
                println("Enter Review/ Feedback")
                val reviewPoints = Input.getProperString()
                val makeReview = Review(selectedOrder.customerId, selectedOrder.providerId,reviewPoints)
                if(customerHandler.addReview(makeReview)){
                    println("Review Added")
                }

            }
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
private fun viewCustomerOrders(listOfOrders : List<Order>){

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
private fun viewCustomerPackageOrders(){
    val listOfPackageOrders  = CurrentCustomerDetails.getInstance().getMyPackageOrders()
    if(listOfPackageOrders.size == 0){
        println("No Orders Found")
        return
    }
    else{
        for(orders in listOfPackageOrders){
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
                viewProvidersDetails(providerFilterHandler.getProviderBasedOnRating())
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
                    println("$index. ${providers.name}")
                }
                var selectedIndex = selectIndexValueFromList(list)
               // selectedProvider = providerFilterHandler.getProvider(list.get(selectedIndex - 1).id)
                //println(selectedProvider.getPersonalInformation().id)
                performWithProviders()
            }
        }

        println("Do youu want to continue Customer Order Functionalities\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag ==1)


}

private fun viewProvidersDetails(listOfProvider : List<FoodProviderDetails>){
    if(listOfProvider.size == 0){
        println("No Providers Data found for this filter")
        return
    }
    do{
        var index =1
        for(provider in listOfProvider){
            println(provider is ProviderDetails)
           println(" ${index}. ${provider.getPersonalInformation().name} - ${provider.getPersonalInformation().rating}")
            index++
        }
        println("Select Provider")
        val selectedProviderIndex = selectIndexValueFromList(listOfProvider)
        selectedProvider = listOfProvider.get(selectedProviderIndex -1)
        if(selectedProvider is ProviderDetails){
            println("Current is provider")
        }
        if(selectedProvider is VolunteerDetails){
            println("Current is Volunteer")
        }

        performWithProviders()

        println("Do you want to search with another provider\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)
}
private fun performWithProviders(){
    println("1.View Food Items\t2.View Package Schemes\t3.Add to Bookmark")
    val choice = Input.getIntValue()
    when(choice){
        1->{

            listProvidersFoodItems()
            //listSelectedFoodItems()



        }
        2->{
            //selectedProvider as ProviderDetails
            if(selectedProvider is VolunteerDetails){
                println("No Packages ")
                return
            }
            else {
                var currentProvider = selectedProvider as ProviderDetails
                listProviderPackageSchemes(currentProvider.getPackageSchemes().toList())
            }


        }
        3 ->{
            var currentProvider = selectedProvider //as FoodProviderDetails
            addProviderToBookmark(currentProvider.getPersonalInformation())
        }
    }

}
private fun makeTransaction(price : Float) : Boolean{
    println("Amount : $price")
    println("Enter 1 to Process Transaction")
    flag = Input.getIntValue()
    if(flag == 1)
    {
        val transaction = Transaction(CurrentCustomerDetails.getInstance().getCustomerId(), selectedProvider.getPersonalInformation().id,price)
        return TransactionHelper.processTransaction(transaction)
    }
    return false


}
private fun addProviderToBookmark(foodprovider : FoodProviders){
    CurrentCustomerDetails.getInstance().addToBookmark(foodprovider)
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
            println("Select Package")
            val seletedIndex = selectIndexValueFromList(listOfPackageSchemes)
            val selectedPackageScheme = listOfPackageSchemes.get(seletedIndex -1)
            println("Selected Package Scheme \n$selectedPackageScheme")
            var transactionFlag = false
            do{
                transactionFlag = makeTransaction(selectedPackageScheme.price)

            }while(!transactionFlag)
            if(transactionFlag){
                orderPackageScheme(selectedPackageScheme, selectedPackageScheme.price)
            }

        }
        2->{ return }
    }

}
private fun listProvidersFoodItems(){
    var selectedFoodItems: ArrayList<OrderedItems>
    println("Enter Time")
    val time = eatingTimeType()
    val listOfFoodItems = selectedProvider.getFoodItems(time)
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


            var selectedFoodItemIndex = selectIndexValueFromList(listOfFoodItems)
            selectedFood = listOfFoodItems.get(selectedFoodItemIndex -1)
            println("Enter Count")
            val count = Input.getIntValue()
            orderedItems = OrderedItems(selectedFood, count)
            selectedFoodItems.add(orderedItems)

            println("Do you want to add another food item?\n1.Yes\t2.No")
            flag = Input.getIntValue()

        }while(flag == 1)

    }
    else{
        return
    }
    listSelectedFoodItems(selectedFoodItems.toList())
    var price = calculatePriceOfFoodItems(selectedFoodItems.toList())
    var transactionFlag = false
    do{
        transactionFlag = makeTransaction(price)

    }while(!transactionFlag)
    if(transactionFlag){
        orderFoodItems(selectedFoodItems, price,time)
    }



}
private fun listSelectedFoodItems(selectedFoodItems: List<OrderedItems>){
    println("selected foods")
    for(foodItem in selectedFoodItems) {
        println("${foodItem.foodItems.foodName} - ${foodItem.count}")
    }
}
private fun calculatePriceOfFoodItems(selectedFoodItems: List<OrderedItems>) : Float{
    var price =0.0f
    for(foodItem in selectedFoodItems){
        price += (foodItem.foodItems.price * foodItem.count)
    }
    return price
}

private fun orderPackageScheme(packageScheme : PackageScheme, price : Float){
    //println("Seleccted Rpovider $selectedProvider")
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
    //println("Current Provider")
   //println("${CurrentProviderDetails.getInstance().getPackageSchemes()}")
    var continueFlag = 1
    do{
        println("1.Create New Package\t2.Create New Food Menu\t3.Create New Food Item\n4.Dispatch Bookings\t5.Dispatch Package Bookings\t6.Get My Reviews")
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
            6 ->{
                viewMyReviews()
            }
        }


        println("Do You want to Continue as Provider\n1.Yes\t2.No ")
        continueFlag = Input.getIntValue()
    }while(continueFlag == 1)

}
private fun viewMyReviews(){
    val myReviews = providersHandler.getReviews(CurrentFoodProviderDetails.getInstance().getPersonalInformation().id)
    for(review in myReviews){
        println("${review.points}")
    }
}
private fun createNewPackageScheme(){
    if(currentUserType == Users.VOLUNTEER){
        println("No Package Creation Options")
        return
    }
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
                        var foodmenu = createNewFoodMenu()
                        listOfMenu.add(foodmenu)
                        println("Do You want to add another food menu\n1.Yes\t2.No")
                        flag = Input.getIntValue()
                    }while(flag == 1)

                   // println(foodmenu)

                }
                2 -> {

                    do{
                        println("Enter Type")
                        val type = eatingTimeType()
                        var index = 1
                        for(foodMenu in CurrentFoodProviderDetails.getInstance().getFoodItems())
                        {
                            println("$index. ${foodMenu}")
                            index++
                        }
                        val selectedItems = Input.getProperString()
                        val splitString : List<String> = selectedItems.split(',')
                        var foodItems = getSelectedFoodItems(CurrentFoodProviderDetails.getInstance().getFoodItems().toList(), splitString)
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
        //providersHandler as ProviderOperations
        var handler  : ProviderOperationHandler = ProviderOperations()
        handler.addPackageScheme(packageScheme)
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
            for(foodItem in CurrentFoodProviderDetails.getInstance().getFoodItems())
            {
                println("$index. ${foodItem.foodName}")
                index++
            }
            println("Select Food Item Numbers Seperated by commas (,)")
            val selectedItems = Input.getProperString()
            val splitString : List<String> = selectedItems.split(',')
            var foodItems = getSelectedFoodItems(CurrentFoodProviderDetails.getInstance().getFoodItems().toList(), splitString)
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
            if(valueOfSplittedString.trim().toInt() <= listOfFoodItems.size)
                list.add(listOfFoodItems.get(valueOfSplittedString.toInt() - 1))
        }
    }
    if(list.size == 0){
        println("No Proper items selected")
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
private fun showMyPackageBookings(date : Date, time : EatingTimeType) : List<PackageBookings>{
    //val localDate = LocalDate.now()
    //val todayDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    val currentProvider = CurrentFoodProviderDetails.getInstance() as ProviderDetails
    val listOfPackageBooking = currentProvider.getMyPackageBookings(date, time)
    var index =1
    for(packageBooking in listOfPackageBooking){
        println("$index . $packageBooking")
        index++
    }
    return listOfPackageBooking

}
private fun showMyPendingBookings(time : EatingTimeType) : List<Bookings>{
    val listOfbookings = CurrentFoodProviderDetails.getInstance().getMyBookings(time)
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


    do{
        println("Enter Time Schedule")

        val time = eatingTimeType()
        println("Enter Date")
        val date = Input.getProperDateWithPattern("dd/MM/yyyy")
        var listOfbookings = showMyPackageBookings(date, time)
        if(listOfbookings.size == 0){
            println("No Bookings to Dispatch")
            return
        }
        val selectIndexToDispatch: Int = selectIndexValueFromList(listOfbookings)
        var selectedBookings = listOfbookings.get(selectIndexToDispatch -1)
        //providersHandler as ProviderOperations
        var handler  : ProviderOperationHandler= ProviderOperations()
        handler.dispatchPackageBookings(selectedBookings, date,time)
        do{
            println("Enter 1 to Deliver Items")
            var value = Input.getIntValue()
            if(value == 1){
                println("Delivered")
                handler.changeStatusOfPackageOrder(selectedBookings,date,time)
                providersHandler.makeCallToCustomer(selectedBookings.customerMobileNumber)
            }
        }while(value != 1)

        println("Do You Want to Continue Dispatch Operations\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)
}
private fun dispatchBookings(){

    println("Enter Time Schedule")
    val time = eatingTimeType()

    do{
        var listOfbookings = showMyPendingBookings(time)
        if(listOfbookings.size == 0){
            println("No Bookings to Dispatch")
            return
        }
        val selectIdToDispatch: Int = selectIndexValueFromList(listOfbookings)
        var selectedBookings = listOfbookings.get(selectIdToDispatch -1)
        providersHandler.dispatchBookings(selectedBookings)

        do{
            println("Enter 1 to Deliver Items")
            var value = Input.getIntValue()
            if(value == 1){
                println("Delivered")
                providersHandler.changeOrderStatus(selectedBookings)
                providersHandler.makeCallToCustomer(selectedBookings.customerMobileNumber)

            }
            //println("Enter 1 to Deleiver Items")

        }while(value != 1)
        println("Do You Want to Continue Dispatch Operations\n1.Yes\t2.No")
        flag = Input.getIntValue()
    }while(flag == 1)
}
suspend private fun  getInitialValues(){
    val orderFileOPerationHandler : OrderStartUpHandler = OrdersList
   // println("insude initil vlue")
    CoroutineScope(Dispatchers.IO).launch{
        campHandler.setCampListFromFile()
        orderFileOPerationHandler.readFromOrdersFile()


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
        val provider1 = Provider(1,"Provider1","Anna Nagar","9898989898",4.0f)
        FoodProvidersList.addFoodProvider(ProviderDetails(provider1))
        val provider2 = Provider(2,"Provider2","Cheran Nagar","9797979797",4.7f)
        FoodProvidersList.addFoodProvider(ProviderDetails(provider2))
        var foodItem1 = FoodItem("food1","Increases Immune",20.0f)
        var foodItem2 = FoodItem("food2","Maintain Sugar Level",16.0f)
        var foodItem3 = FoodItem("food3","Reduces Cough",18.0f)
        var foodItem4 = FoodItem("food4","Reduces Kidney Problems",21.0f)
        var foodItem5 = FoodItem("food5","Reduces Bad Cholestrol",21.0f)
        // Food items for Provider1
        FoodProvidersList.getFoodProvidersList().get(0).addFoodItem(foodItem1)
        FoodProvidersList.getFoodProvidersList().get(0).addFoodItem(foodItem2)
        FoodProvidersList.getFoodProvidersList().get(0).addFoodItem(foodItem3)
        FoodProvidersList.getFoodProvidersList().get(0).addFoodItem(foodItem4)
        //ProvidersList.getProvidersList().get(0).addFoodItem(foodItem5)

        //Food items for Provider2
        FoodProvidersList.getFoodProvidersList().get(1).addFoodItem(foodItem1)
        FoodProvidersList.getFoodProvidersList().get(1).addFoodItem(foodItem2)
        FoodProvidersList.getFoodProvidersList().get(1).addFoodItem(foodItem3)
        FoodProvidersList.getFoodProvidersList().get(1).addFoodItem(foodItem4)
        FoodProvidersList.getFoodProvidersList().get(1).addFoodItem(foodItem5)

        var foodMenu1 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem1, foodItem2)))
        var foodMenu2 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem3)))
       // var foodMenu3 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem1, foodItem4)))
        var foodMenu4 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem4, foodItem2)))
        var foodMenu5 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem5)))
        var foodMenu6 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem3, foodItem4)))
        //Food Menu for Provider1
        FoodProvidersList.getFoodProvidersList().get(0).addFoodMenu(foodMenu1)
        FoodProvidersList.getFoodProvidersList().get(0).addFoodMenu(foodMenu2)
        //ProvidersList.getProvidersList().get(0).addFoodMenu(foodMenu3)

        //Food Menu for Provider2
        FoodProvidersList.getFoodProvidersList().get(1).addFoodMenu(foodMenu6)
        FoodProvidersList.getFoodProvidersList().get(1).addFoodMenu(foodMenu4)
        FoodProvidersList.getFoodProvidersList().get(1).addFoodMenu(foodMenu5)
        //Package 1 for Provider 1
        val providerdetails1 = FoodProvidersList.getFoodProvidersList().get(0) as ProviderDetails
        providerdetails1.addPackage(PackageScheme(1,"Package1", ArrayList( listOf(foodMenu1, foodMenu2)),2500.0f))

        val providerDetails2 = FoodProvidersList.getFoodProvidersList().get(1)as ProviderDetails
        providerDetails2.addPackage(PackageScheme(1,"Package2", ArrayList( listOf(foodMenu4,foodMenu5,foodMenu6)),2300.0f))
    }

    // Two Volunteer Values
    CoroutineScope(Dispatchers.Default).launch {
        val volunteer1 = Volunteer(1001,"Volunteer1","Anna Nagar","9999988888",3.5f)
        FoodProvidersList.addFoodProvider(VolunteerDetails(volunteer1))
        val volunteer2 = Volunteer(1002,"Volunteer2","Cheran Nagar","9988998899",4.3f)
        FoodProvidersList.addFoodProvider(VolunteerDetails(volunteer2))
        var foodItem1 = FoodItem("food1","Increases Immune",20.0f)
        var foodItem2 = FoodItem("food2","Maintain Sugar Level",16.0f)
        var foodItem3 = FoodItem("food3","Reduces Cough",18.0f)
        var foodItem4 = FoodItem("food4","Reduces Kidney Problems",21.0f)
        var foodItem5 = FoodItem("food5","Reduces Bad Cholestrol",21.0f)
        // Food items for Provider1
        FoodProvidersList.getFoodProvidersList().get(2).addFoodItem(foodItem1)
        FoodProvidersList.getFoodProvidersList().get(2).addFoodItem(foodItem2)
        FoodProvidersList.getFoodProvidersList().get(2).addFoodItem(foodItem3)
        FoodProvidersList.getFoodProvidersList().get(2).addFoodItem(foodItem4)
        //VolunteersList.getVolunteersList().get(0).addFoodItem(foodItem5)

        //Food items for Volunteer2
        FoodProvidersList.getFoodProvidersList().get(3).addFoodItem(foodItem1)
        FoodProvidersList.getFoodProvidersList().get(3).addFoodItem(foodItem2)
        FoodProvidersList.getFoodProvidersList().get(3).addFoodItem(foodItem3)
        FoodProvidersList.getFoodProvidersList().get(3).addFoodItem(foodItem4)
        FoodProvidersList.getFoodProvidersList().get(3).addFoodItem(foodItem5)

        var foodMenu1 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem1, foodItem2)))
        var foodMenu2 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem3)))
        // var foodMenu3 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem1, foodItem4)))
        var foodMenu4 = FoodMenu(EatingTimeType.BREAKFAST, ArrayList(listOf(foodItem4, foodItem2)))
        var foodMenu5 = FoodMenu(EatingTimeType.LUNCH, ArrayList(listOf(foodItem1, foodItem5)))
        var foodMenu6 = FoodMenu(EatingTimeType.DINNER, ArrayList(listOf(foodItem3, foodItem4)))
        //Food Menu for Volunteer1
        FoodProvidersList.getFoodProvidersList().get(2).addFoodMenu(foodMenu1)
        FoodProvidersList.getFoodProvidersList().get(2).addFoodMenu(foodMenu2)
        //VolunteersList.getVolunteersList().get(0).addFoodMenu(foodMenu3)

        //Food Menu for Volunteer2
        FoodProvidersList.getFoodProvidersList().get(3).addFoodMenu(foodMenu6)
        FoodProvidersList.getFoodProvidersList().get(3).addFoodMenu(foodMenu4)
        FoodProvidersList.getFoodProvidersList().get(3).addFoodMenu(foodMenu5)

    }


}