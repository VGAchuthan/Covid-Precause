package foodorder

object CurrentProviderDetails {
    lateinit private var instance : ProviderDetails
    fun getInstance() : ProviderDetails{
        return this.instance
    }
    fun setInstance(provider : ProviderDetails) {
        this.instance = provider
    }
}