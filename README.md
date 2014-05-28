ToasterSample
=============

A demo android app that shows the different options available while using the Toaster Lib.


<img src="http://i34.photobucket.com/albums/d147/cking24343/Github/collection_framed_zps5aac1aea.png" />

Demo Application
================
A simple demo application can be found on Google Play Store.

<a href="https://play.google.com/store/apps/details?id=king.chad.toastersample">
  <img alt="Google Play Store"
       src="https://developer.android.com/images/brand/en_app_rgb_wo_60.png" />
</a>


How To Use
==============
Create a Toaster Object and Initialize it:
```java
  //--Toaster Object--
  ToastIt toastIt;
	
  //--Initialize our toaster--
  toastIt = new ToastIt(this, true);
  
```

Make a call to show toaster object:
```java
  //--Basic call to toaster object--
  toastIt.show(
	getResources().getDrawable(R.drawable.ic_launcher), //--pass image--
	"Custom Toaster Message",         //--pass message to be displayed--
	font,                             //--pass typeface font file to be used--
	textHexCode,                      //--pass hexcode value for the text color of toaster--
	bgHexCode);                       //--pass hexcode value for the background color of toaster--
```
