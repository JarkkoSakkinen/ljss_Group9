<?php
 //Getting post data 
 $longitude = $_POST['longitude'];
 $latitude = $_POST['latitude'];
 $altitude = $_POST['altitude'];
 $distanceTravelled = $_POST['distanceTravelled'];
 $speed = $_POST['speed'];

 require_once('dbConnect.php');

 $sql = "INSERT INTO GPSData (longitude,latitude,altitude,distanceTravelled,speed) 
 VALUES('$longitude','$latitude','$altitude','$distanceTravelled','$speed')";
 
 //Trying to insert the values to db 
 if(mysqli_query($con,$sql)){
 //If inserted successfully 
 echo 'successfully registered';
 }else{
 //In case any error occured 
 echo 'oops! Please try again!';
 }
 mysqli_close($con);
?>