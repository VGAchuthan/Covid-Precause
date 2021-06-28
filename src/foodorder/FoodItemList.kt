package foodorder

data class FoodItem(var foodName : String, var benefits :String, var price :Float )
data class FoodMenu(var type : String, var foodItems : ArrayList<FoodItem>)

data class PackageScheme(var packageId : Int, var packageName : String,
                         var foodMenus : ArrayList<FoodMenu>, var price : Float)

