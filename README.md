# Coding Challenge Android

Clicking on a user listing should open the page for that user's profile. I tried to make the design similar to how user info is shown on Stackoverflow questions. I used a RecyclerView to display the data (the first page of Stackoverflow users). I started writing the app using HttpUrlConnection and AsyncTask to access the Stackoverflow API. Then I refactored it to use Volley for caching functionality instead. To further improve the app, I would want to add unit tests and convert the activity to be an activity using a single fragment, so the fragment can be used elsewhere. I would also want to specify more layouts for different devices and improve the UI. If there were a need for more calls to the StackOverflow API endpoint, I would create an access token for it.

## Technical Requirements

- Platform Version: 5.1+

## Third-party libraries

Volley
> Volley was used because it provides a simpler way to handle async HTTP requests. It also provides a caching mechanism which is useful in limiting the number of requests and also in the event of not having a network connection. Volley also helps avoid exceptions that can come from using a HttpUrlConnection as an AsyncTask. I originally used a HttpUrlConnection which complicated the problem. There are more benefits than what is listed, but those are the primary benefits for this app.

Gson
> Gson simplified the process of deserializing JSON to Java objects. Writing a separate deserializer would take more time and be repetitive.

Picasso
> Picasso was useful in loading the gravatars. The library automatically caches images and runs asynchronously. The cache makes it easier to handle the photo being downloaded only once.  
