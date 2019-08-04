# ElMenus-Assignment

## Background
This is an android app which talk to two dummy Apis a food tags comes in pages the page has eight tags each of which has a list of of food items which comes from the scond api in a static list of items this app mainly consists of two activities one for tags and it's items and the other one for the item details.

## Screen shots:

## Technical view
 I used MVVM with the modern archticture components in the architicure of the app 
 and the new paging technology in the infinite tag list:
 ![alt](https://codelabs.developers.google.com/codelabs/android-paging/img/a4f392ad4ae49042.gif)
## Libs:
* Retrofit
Used as RESTful client  
* Glide
For loading and caching images
* Anko
Used in:
** multithreading e.g.
```
doAsync{
  .... coroutine to run in background thread
  uiThread{
  ....coroutine to run in main thread
  }
}
````

** start activties e.g.
```startActivity<MainActivity>()```
* Room
used to cache both of food tags and food items

* Robolectric
Used for just mocking activities and thier context


* I used Jetpack Paging, you can find a main overview here:
https://codelabs.developers.google.com/codelabs/android-paging/index.html#2
