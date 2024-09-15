# Intro

Welcome to Ross' solution to the WalMart take-home project.  Ultimately,
this solution is far from perfect, but I will attempt to explain some of
my decisions in this document as well as in comments throughout the
codebase.

# Design and Implementation

In general, I attempted to follow the MVVM pattern by grouping concerns
into View (Activities, Composeables, etc), ViewModel (the viewmodels), Data, and Repository layers.

I decided to use Jetpack Compose because I've been primarily using it for my recent Android 
projects, and the `LazyColumn` makes displaying a large list of data a lot simpler than the 
legacy RecyclerView, which can be difficult to deal with programmatically.

While not expressly required to display each country's flag, I found that it made the app more
visually appealing to do so. The Coil image loading library made it simple to fetch flag images from
an http endpoint. Note that the flag URLs contained in the original JSON data did not seem to be valid.

I also used the Koin dependency injection library, which is very straightforward and makes it very
low lift to add dependency injection in simple Android projects.

Http calls are accomplished using Ktor. JSON is unmarshalled to Kotlin objects using the GSON
library mostly because I was famailiar with it and Ktor provided a GSON plugin.

# Testing

I did not implement any automated testing. I started to write a unit test for the CountryRepository
leveraging Mockk to mock out the ktor http client, but I ran into some trouble successfully mocking
the http client calls, so I abandoned it in the interest of getting this project completed. However,
for a real project, I would definitely write a full set of unit and instrumentation 
tests.

