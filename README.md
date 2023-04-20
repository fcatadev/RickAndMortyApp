# RickAndMortyApp

An Android application where you can examine the characters in the famous Rick and Morty series. 
In my application, where you can access the details of the characters, you can also filter the characters according to the location data.


Used in the project.
- https://rickandmortyapi.com/
- MVVM
- Retrofit
- Shared Preferences
- Composite Disposable
- Lazy Loading


Extra features I added
- An "All" button has been added to bring all characters when clicked at the beginning of the horizontal list prepared for location filtering.

- When all the characters were listed, only the first 20 characters were visible according to the page number due to the data coming from the api. 
To solve this, I added the "Next" and "Previous" buttons, so you can see 20 different characters each time by going back and forth.

- If it was made according to the dimensions given on the page where we saw the details of the characters, some details of some characters did not fit on the screen.
To solve this problem (location, origin, etc.), I cut the sentences that did not fit at a certain point and added "..." to the end. (There could have been a better solution :D)
In Episodes, I found a solution that I think is different and more beautiful. 
As you can see in the pictures, there is an "All" button at the end of the episodes that don't fit. 
When we click on this button, a certain part of the detail page stretches down and shows all the episode values, I tried to create a system that is both legible and pleasing to the eye.
