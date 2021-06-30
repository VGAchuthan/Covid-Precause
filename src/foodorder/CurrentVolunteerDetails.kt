package foodorder

object CurrentVolunteerDetails {
    lateinit private var instance : VolunteerDetails
    fun getInstance() : VolunteerDetails{
        return this.instance
    }
    fun setInstance(volunteer : VolunteerDetails) {
        this.instance = volunteer
    }
}