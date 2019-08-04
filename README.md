# ElMenus-Assignment

## Background
This is an android app which talk to two dummy Apis a food tags comes in pages the page has eight tags each of which has a list of of food items which comes from the scond api in a static list of items this app mainly consists of two activities one for tags and it's items and the other one for the item details.

## Screenshots
<img  src="https://user-images.githubusercontent.com/5509571/62429178-7e1f1400-b70b-11e9-9c05-b6b9da92c8be.png" alt="Splash" width="250"/> <img  src="https://user-images.githubusercontent.com/5509571/62429179-8414f500-b70b-11e9-9a4a-8a3b2a0638d9.png" alt="Normal View" width="250"/> <img src="https://user-images.githubusercontent.com/5509571/62429180-89723f80-b70b-11e9-8c0a-054df16a43f9.png" alt="Item Details" width="250"/> 
<img src="https://user-images.githubusercontent.com/5509571/62429184-9abb4c00-b70b-11e9-9df2-0729aacce189.png" alt="Offline and cache has no data" width="250"/> <img src="https://user-images.githubusercontent.com/5509571/62429186-9db63c80-b70b-11e9-9021-cfb7ab48aec0.png" alt="Offline and cache has data" width="250"/>
<img src="https://user-images.githubusercontent.com/5509571/62429608-f2a88180-b710-11e9-9bd1-9da763d4ef0c.png" alt="Error loading selected tag items" width="250"/>


## Technical overview
 1. I used MVVM with the modern architecture components in the architecture of the app 
 ![image](https://user-images.githubusercontent.com/5509571/62429675-c6413500-b711-11e9-8bb2-40828606f41d.png)
 and the new paging technology in the infinite tag list:
 ![alt](https://codelabs.developers.google.com/codelabs/android-paging/img/a4f392ad4ae49042.gif)
 because I'm using room the data layer handled in Room itself the data source and it's factory, I just implemented the Boundary Callback.
 2. The API retured name including the page number i.e. ```1 - Pizza``` I beautified the name and make it configurable throw the constant in Constants file so the default names are beautified if you need to see the api names just change the constant to true 
 in the file ``` com.elmenus.assignment.constants.AppConstants ```
 make the variable value to false:
 ``` SHOW_BEAUTIFIED_NAMES = false ``` 
 3. The connection state changes are observed be noted that there are different states in offline mode:
  * There is no cached data: you will see a dionsaur in this state after you getting online automatically we try loading data 
  to get in this state make mobile offline in a fresh install and open the app
  * There is cached data: you will see a snack bar tells you you're offline and whenever  you get connected it will disappear 
  
  4. Error handling, I didn't handled the error well, but I made two basic error handling:
   * If there are a error in connecting or parsing data it will try up to 10 times to reconnect and get data 
   * If any error occures including the offline state while loading the list of items (not tags) you will see error message with retry button
   * also if any loading of images errors occurred the error place holder image will be shown.
   5. There is always a room for improvments I like to begin with more test cases to cover more scenarios, also the item details needs some enhancements  
   
## Notes: 
   * The Api needs a header of Content-Type to be Application/json which is only supposed to be needed in POST requests only, but it's seems it's removed as a condition in a request  
   * There is a fast splash screen with default wait time of a half second
   The main activity is big a little bit due to ui handling including show/hide  errors and progress bars and views
   * I named the app El-Menus with dash to be different from the *elmenus* app
   * Naming: I'm not familiar with this kind of apps, you might be confused of names,btw I considerd the tags as FoodTags and list of items as TagItems or SelectedTagItems  ^_^ 
 
## Libs:
* Retrofit
Used as RESTful client,also I made the ok http client loges the apis response in only debug builds!
* Glide
For loading and caching images
* Anko
Used in:

1.multithreading e.g.
```
doAsync{
  .... coroutine to run in background thread
  uiThread{
  ....coroutine to run in main thread
  }
}
````

2. start activties e.g.
```startActivity<MainActivity>()```
* Room
used to cache both of food tags and food items in a local sqlite database.

* Robolectric
Used for just mocking activities and thier context

## References 
### Paging Library
* https://developer.android.com/topic/libraries/architecture/paging/
* https://codelabs.developers.google.com/codelabs/android-paging/index.html

### Anko 
* https://github.com/Kotlin/anko/wiki

### Room
* https://developer.android.com/training/data-storage/room/index.html

### Architecture components
* https://developer.android.com/jetpack/docs/guide


Thank you! :wink:
