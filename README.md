# Amazon-Game-Circles-for-Unity
This is a plugin to use Amazon game circles for Unity Android.
Amazon provides a plugin for Unity ro be used on Android devices and IOS.
Too bad Android Platform is a little bit old and very confusing to use.

I create a new plugin to solve this.
This plugin should be very easy to use 

No need to Create a Custom Android Manifest, just load the GameCircles.aar library 
and call it from Unity, and just use One Unity Object to use all Game Circles feautures.
And of course this should work fine using another platform, so if you create a multiplatform game,
the Game Circles plugin should just simply ignored and only work for Android devices.

But I found a little problem here you need to compile the "aar" file, since you cannot send data to the android manifest
you will need to follo this instrucctions here.

## setup

First you will need to Use Android Studio in order to compile the "aar" file.
I leave a project template to make this easy, so open the the Android Studio Project from the directory.

* "Android/Amazon"...
This is the Android Studio Project

Open And Edit the Manifest from the Module called "gamecircles"
in this module you will find in the manifest, this line of code.

** <data android:host="com.hammergames.flappyplane" android:scheme="amzn" />

Replace "com.hammergames.flappyplane" with your package name.

Compile the project.

It should generate an "gamecircle.aar" file.
import that to your Unity Project

.....TODO COMPLETE THIS


### How it works ?

