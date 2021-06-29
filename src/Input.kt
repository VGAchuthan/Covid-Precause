
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

// Custom class for getting proper values from User
var reader = BufferedReader(InputStreamReader(System.`in`))
class Input{

    companion object{
         fun String.isProperInt() : Boolean{
            return (this.toIntOrNull() is Int)
        }
        fun String.isProperFloat() : Boolean{
            return (this.toFloatOrNull() is Float)
        }
         fun String.isProperString() : Boolean{
            return !this.isEmpty()
        }
         fun getProperString() : String{
            var value : String
            do{
                value = reader.readLine()
                var flag = value.isProperString()
                if(!flag){
                    println("Enter Non Empty String")
                }

            }while(!flag)
            return value
        }
         fun String.isProperMobileNumber() : Boolean{
            val pattern : Pattern = Pattern.compile("[6-9]{1}\\d{9}")
            val matcher : Matcher = pattern.matcher(this)
            return matcher.matches()
        }
         fun String.isProperPincode() : Boolean{
            val pincodePattern : Pattern = Pattern.compile("\\d{6}")
            val matcher : Matcher = pincodePattern.matcher(this)
            return matcher.matches()
        }
         fun String.isProperDate(pattern : String) : Boolean{
            val dateFormat = SimpleDateFormat(pattern)
            val pattern  = Pattern.compile("^(0[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}")
            //println(pattern)
            val matcher : Matcher = pattern.matcher(this)

            try{
                if(!matcher.matches()){
                    return false
                }
                var date : Date = dateFormat.parse(this)

                var today = LocalDate.now()
                var todayStartTime = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())
               // println(" todat strt time : $todayStartTime")
                //println(" input date : $date")

                if(date.compareTo(todayStartTime) == -1){
                    //println("date is small")
                    return false
                }
            }catch(e : Exception){

                return false
            }
            return true
        }
         fun getIntValue() : Int{
            var value : String
            do{
                value = reader.readLine()
                var flag = value.isProperInt()
                if(!flag){
                    println("Enter Proper Number")
                }
            }while(!flag)
            return value.toInt()

        }
         fun getProperMobileNumber() : String{
            var mobileNumber : String
            do{
                mobileNumber = reader.readLine()
                if(!mobileNumber.isProperMobileNumber()){
                    println("Enter Proper Mobile Number")
                }
            }while(!mobileNumber.isProperMobileNumber())
            return mobileNumber
        }
         fun getProperPincode() : String{
            var pincode : String
            do{
                pincode = reader.readLine()
                if(!pincode.isProperPincode()){
                    println("Enter Proper Pincode")
                }
            }while(!pincode.isProperPincode())
            return pincode
        }
         fun getProperDateWithPattern(pattern : String) : Date {
            var date : String
            println("Enter date in DD/MM/YYYY Format")
            do{
                date = reader.readLine()
                var flag = date.isProperDate(pattern)
                if(!flag){
                    println("Enter Proper Date ")
                }

            }while(!flag)
            var formatedDate = SimpleDateFormat(pattern)
            //formatedDate.parse(date)
            //println("PArsed date ${formatedDate.parse(date)}")
            return formatedDate.parse(date)
        }
        fun getFloatValue() : Float{
            var value : String
            do{
                value = reader.readLine()
                var flag = value.isProperFloat()
                if(!flag){
                    println("Enter Proper Number")
                }
            }while(!flag)
            return value.toFloat()

        }

    }


}