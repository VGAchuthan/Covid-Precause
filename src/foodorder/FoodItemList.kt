package foodorder

import enums.EatingTimeType

data class FoodItem(var foodName : String, var benefits :String, var price :Float )
data class FoodMenu(var type : EatingTimeType, var foodItems : ArrayList<FoodItem>)

data class PackageScheme(var packageId : Int, var packageName : String,
                         var foodMenus : ArrayList<FoodMenu>, var price : Float)

data class OrderedItems(var foodItems: FoodItem, var count:Int)
