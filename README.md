# Filtering Customers 

[Korean README](./README_KOR.md)

## Description

After reading an [article](https://www.youtube.com/watch?v=uD4xbRCqR00) about malicious customers on delivery apps, I have decided to create an application addressing this issue. The article discusses the controversy surrounding these malicious customers and the problems they cause.

Recently, online games have implemented a system where users report and act as jurors for unauthorized program use, known as "hacks." I was inspired by this system and developed an app that allows sellers (restaurants on delivery apps) to directly report and judge malicious customers.

Initially, the idea was to create a blacklist where sellers could share information about malicious customers and obtain relevant information from the list. However, I realized that there may be cases where a customer has a valid complaint, so I implemented a feature where other sellers could express agreement with the report and other community users could verify it.

<br>

## Activity
* __SignInActivity__

    You can log in with the ID and password registered on the server. If the login information is incorrect, a message will be displayed below.


* __CreateAccountActivity__
    
    You can register a new ID and password on the server. Duplicate IDs are not allowed.

* __MenuSelectionActivity__

    This is a screen where you can decide whether to create a new post or search for existing posts.

* __NewPostActivity__

    You can write a post by selecting a title, main text, nickname and phone number of the reported target, and an attached image file. When the post is written, it is uploaded to the server with the date, unique ID, and the name of the post's author. The attached image can be loaded from the gallery and is registered by encoding it with Base64.

* __ListUpPostActivity__

    You can enter a nickname or phone number you want to search for and retrieve a list of corresponding posts from the server. You can then select the desired post to view it.

* __ViewPostActivity__

    You can view the body of the posts that appear through the search. The post information is retrieved from the server and displayed on the screen. At this time, attached images are decoded again into ImageView through Base64. When you view a post, the number of views for that post increases and you can choose to like or dislike it. This is similar to Facebook's "Like" and "Dislike" feature. When you close the post view, the corresponding like or dislike will be reflected in the post stored on the server.


## Flowchart
<img width="675" alt="스크린샷 2023-04-10 오전 4 23 34" src="https://user-images.githubusercontent.com/50101334/230792855-45d16889-2dab-476d-ab19-fe542f71f594.png">

<br>

## Network API design (Communication Protocol between AWS lambda and application)
All communication in this application is implemented to access AWS S3 through boto3 in Python.

First, the login process checks whether the ID and password match by accessing the file where they are registered. The flag indicating whether the ID and password match is received as a response, and the login result is displayed on the screen.

For registration, the application registers a new ID and password in the file, ensuring that it does not overlap with existing IDs. Since S3 cannot modify files already uploaded to the server, the application creates a new file with the same name and overwrites it.

When a post is created, the application uses the put_object function of the S3 client to register the post's contents, author name, unique post ID, and date in JSON format via a POST method to the server.

When searching for a post by name and phone number, the application first loops through all posts to retrieve the key value of the post that matches the name and phone number. Then, using the key value, the application retrieves the post with the get_object function and returns the search results as a JSON array, or jsonArray, consisting of the post's creation date, title, and view count.

When a user views a post found through a search, the application retrieves the post information using the key value obtained earlier with the get_object function. When the user finishes viewing the post, the view count and likes are updated by updating the post again on the server. This is implemented by overriding the onDestroy() function of the activity.

<br>

## Challenge
* __MVVM Pattern__
    
    I felt that the most difficult and essential aspect of this project was to learn actual architecture patterns that are used in commercial applications, such as MVP and MVVM, in addition to simply developing an Android app.

    It took a lot of time to understand the roles of View, ViewModel, and Model, and to use data binding to eliminate dependencies between them, starting from dividing all operations performed in one activity into these components.

* __Save image and display__

    Regarding image saving and displaying, we encoded the images using Base64 to store them in string-based data such as JSON, and then decoded them to display on the ImageView using data binding and Binding Adapter. This process required a deep understanding and a lot of time.

<br>

## Appendix

### MVVM Pattern

As previously described, this application follows the MVVM pattern.

__Model__ is implemented to only contain data.

__View__ is composed of UI and essential UI logic. Here, the UI refers to the elements displayed on the screen, and the UI logic refers to actions that require a Context. Performing actions such as launching another activity from an activity or loading an image from the gallery requires the Context of that activity. However, if the ViewModel holds the Context of the activity, it can cause issues such as memory leaks. Therefore, the ViewModel is designed to allow the View to call functions such as startActivity.

__ViewModel__ is designed to handle all actions performed in an activity, except for UI logic. It holds data such as instances of the Model, essential data to construct the UI, and input data passed from the UI. Its logic includes HTTP communication, updating the Model, and modifying the UI. Input data from the UI is passed to the ViewModel through data binding, and the ViewModel updates the Model with the received data.
