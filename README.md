# WeatherApp

Project Description:
--------------------
This application show weather details based on current location and also maintain history of weather.
This Project uses a public OpenWeather api to fetch weather details.
The application has four screens & are: SplashScreen, Login Screen, Registration Screen and Home
Screen with two tab’s, one to show the current weather and other details and the second tab will
have the history.

Tech Stack:
-----------
Architecture used: MVVM

Android studio version: Electric Eel | 2022.1.1

Programming language: Kotlin

Device supports: Api level 21 and above

Project Structure
-----------------
1) adapters: This folder contains viewpager2 adapters to show fragments in tabLayout.
2) data: This folder contains classes related to Room database
3) models: This folder contains data classes
4) repositories: This folder contains classes to fetch data from network as well as database.
5) rest: This folder contains Retrofit client and interface
6) ui: This folder contains all the activities and fragments
7) utils: This folder contains utility classes
8) viewmodels: This folder contains all the viewModels
9) di: This folder contains dependency injection classes


Security
---------
Room Database is protected using SQL Cipher

API_KEY path
-------------
Update your OpernWeather Api key to "local.properties" file for key name "API_KEY"

Unit Test
---------
Wrote testcases for ViewModels(LoginViewModel, HomeViewModel), DAO's(UserDao, WeatherDao) and Repositories(LoginRepository, HomeRepository) with test coverage above 90%

<img width="473" alt="full" src="https://user-images.githubusercontent.com/126142203/221539309-6544680d-0495-4aec-ac9f-97210e35e7c4.png">

App Screens
-------------

