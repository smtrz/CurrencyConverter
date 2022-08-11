# Currency Converter Android App

This is an android application of currency converter written in 'KOTLIN' that lets you :

- Enter desired amount and currency(unit) and displays the converted amount in all possible currencies.
- Application also work offline, in case internet is not available.
---
This app is designed using android's Jetpack components such as :

- ViewModel
- LiveData
- Coroutines / Coroutine Flows
- Hilt for DI
- data binding
- Room persistence library for offline support
- Work Manager for periodic fetching of currency rates every 30 mins

# Application Working:

- Application makes use of open exchange to fetch all the currencies and currency data, and store them into the local database,
as i am using evaluation key of open exchange therefore while fethching the latest currency rates we cannot change the (defualt is USD).
Considering the above i have devised a formula to calculate relative rates :

(currencyRate_of_currency_from_the_list / selected_currency_rate) * amount

# Unit Tests:

- test cases included with fake remote and local repository to test :
- the underlying logic for testing the view model

# Instrumentation Tests:

- Complete database tests written with in memory database to test all the use cases.
- WorkManager test written in order to test the working of work manager

# Features:

- Easily extendable
- Clean UI and code
- Repository pattern

# Further improvements:

- Using jetpack compose for UI
- Adding UI tests

### Tech
This app uses a number of open source projects Libraries and frameworks:

* [Kotlin] - written in kotlin
* [SOLID] - Use of Solid Design principles
* [Repository pattern] - Use of repository pattern for data storage and retrieval
* [View Model]
* [Coroutine Flows] - for async programming framework
* [Work Manager] - for periodic work


License
----
Designed and developed by :

Tahir Raza<br/>
smtrz@yahoo.com<br/>
Skype: smtrz110<br/>
Profile: https://www.linkedin.com/in/tahiraza/<br/>
More about me : http://smtrz.github.io

