package foodorder.fileoperationhelper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fileoperation.FileOperation
import foodorder.Bookings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface  BookingFileOperationHandler{
    fun addToBookingsFile(listOfBookings : List<Bookings>)
    suspend fun readFromBookingsFile() : ArrayList<Bookings>
}
class BookingFileOperationHelper :  BookingFileOperationHandler {
    override fun addToBookingsFile(listOfBookings: List<Bookings>) {
        var json = Gson().toJson(listOfBookings)
        println(json)
        CoroutineScope(Dispatchers.IO).launch{
            FileOperation.writeToFile("bookings",json.toString())
        }
    }

    override suspend fun readFromBookingsFile(): ArrayList<Bookings> {
        var jsonString = CoroutineScope(Dispatchers.IO).async {
            FileOperation.readFromFile("bookings")
        }.await()
//        if(jsonString.isEmpty()){
//            jsonString = ""
//        }

        val gson = Gson()
        val parseType = object : TypeToken<List<Bookings>>() {}.type
        val bookingList = gson.fromJson<List<Bookings>>(jsonString, parseType)
        println("IN READ FROM ${bookingList}")

        return ArrayList(bookingList)

    }
}