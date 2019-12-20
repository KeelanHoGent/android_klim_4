# Klimaatmobiel Android - projecten III

This android project is the primary interface that will be used by students. It provides a (fake)
webshop that challenges users to think about the ecological impact of some products when they use it
in their STEM projects. Teachers can control and manage the webshop with the webinterface that we created
[here](https://github.com/HoGent-Projecten3/projecten3-1920-angular-klim03)

The back-end that supports this system can be found [here](https://github.com/HoGent-Projecten3/projecten3-1920-backend-klim03)

## Extra features
These are the extra features after the split of the group
```
* Groups
  * Students can change their groupsname
  * Students can add their names to their group
  * Students can change their name if needed
  
* Project detail
  * Students can see the details of the project they are working on
  
* webshop
  * Students can sort products by price, category and name
  * Students can see the amount of products in the shopping cart throughout the whole app
  
* Shopping Cart
  * Students can confirm their order
  * Students can clear the whole shopping cart
  
* general
  * When the internet drops the student is still able to see the overview of products, the details of product detail, etc
  * They can no longer make changes to the shopping cart when internet drops
  
```

## Getting started

Run the application together with the 'backend_klim_4' asp .net backend

Codes to start
```
212345: Project with no group members
1abcde: Project with group members
```


### Prerequisits

```
Android Studio
Visual studio 2019
```

### Installing

Clone the repository

```
git clone git@github.com:HoGent-Projecten3/projecten3-1920-android-klim03.git
```

Open the project in Android Studio and build

## Running tests

Open the root directory of the project in a terminal and run

```
./gradlew connectedAndroidTest
```

## Built with

* [Gradle](https://gradle.org) - Dependency Management
* [Moshi](https://github.com/square/moshi) - JSON parser
* [Timber](https://github.com/JakeWharton/timber) - Logging tool
* [Retrofit](https://github.com/square/retrofit) - Http client
* [Glide](https://github.com/bumptech/glide) - Image loading tool

## Authors

* **Daan Dedecker**
* **Robbe Decorte**
* **Florian Landuyt**
* **Keelan Savat**
* **Thomas Schuddinck**
* **Sofie Seru**
