## Assessment_Femi
 SMARTFLOW TECH - FIRST STAGE INTERVIEW (ASSESSMENT)

##  Now for the development, I used:
* Kotlin (I didn't use Jetpack Compose tho because it wasn't specified. Just normal XML)
* Retrofit for Api call
* Jetpack datastore to store the selected brand who's products to display (pass the data between FIRST and SECOND fragment)
* Androidx navigation for navigating between the fragments (I used bundle to pass the name of specific product between SECOND and THIRD fragment)
* Swipe to refresh layout in the first fragment
* Glide to load the product image
* Timber for logging
* A CI/CD workflow file to run an automated build on github (I had gradle issues with this at first I think because I just updated to Android studio Flamingo)
* MVVM
* Dagger - Hilt for dependency injection
* Kotlin Coroutines
* Kotlin Flow
* ViewBinding to bind the UI
* Jetpack Lifecycle