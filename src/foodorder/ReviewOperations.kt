package foodorder

import java.util.*

interface CustomerReviewHandler{
    fun addReview(review: Review) : Boolean
    fun getCustomerReviews(customerId : Int) : List<Review>

}
interface FoodProvidersReviewHandler{
    fun getFoodProviderReviews(foodProviderId : Int) : List<Review>
}
data class Review(var from : Int, var to : Int, var points : String){
    lateinit private var date : Date
    init{
        date = Date()
    }
    fun getDate(): Date{
        return this.date
    }
}
object ReviewList{
    private var listOfReviews : ArrayList<Review> = ArrayList()
    fun addReview(review: Review) : Boolean{
        return this.listOfReviews.add(review)

    }
    fun getCustomerReviews(customerId : Int) : List<Review>{
        return this.listOfReviews.filter{it.from == customerId}
    }
    fun getFoodProviderReviews(foodProviderId : Int) : List<Review>{
        return this.listOfReviews.filter{ it.to == foodProviderId}
    }
}
class ReviewOperations : CustomerReviewHandler,FoodProvidersReviewHandler{
    override fun addReview(review : Review) : Boolean{
        return ReviewList.addReview(review)

    }

    override fun getCustomerReviews(customerId: Int): List<Review> {
        return ReviewList.getCustomerReviews(customerId)
    }

    override fun getFoodProviderReviews(foodProviderId: Int): List<Review> {
        return  ReviewList.getFoodProviderReviews(foodProviderId)
    }
}