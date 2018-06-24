# FoodsbyCodeChallenge
Code challenge given by Foodsby. This should build on any device API 21+. An APK was also included 

## Technical Descisions
Below, I'll try to justify some of the technical decisions that were made in this app. There's in line comments explaining most of this too.

### Architecture 
For this app I decided to use [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) since with the inclusion of [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel) and [databinding](https://developer.android.com/topic/libraries/data-binding/) it makes the most sense for a UI heavy application. I chose not to use MVP since the 'ping pong' logic between the presenter and the view in MVP makes debugging quite difficult and can easily cause attachment exceptions. Essentially, MVP doesn't really play nice with the Android lifecycle in my opinion. MVI was considered; however, the logic required for MVI is pretty complicated and it relies heavily on RxJava. With MVVM, most of our async logic is done through coroutines instead of RxJava. 
