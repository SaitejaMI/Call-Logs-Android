<?php

require ('connect.php');

// $username = "ali";
// $password = "ali123";
// $manager = 1;
// $j_date = "12-09-2020";


$name = $_POST['nam'];
$number = $_POST['num'];
$duration = $_POST['dur'];


$sql = "INSERT INTO call_logs (c_name, c_number , c_duration )
VALUES ('$name', '$number', '$duration' )";
$result = mysqli_query($conn , $sql) or die ("Regsiter Failed");

if ($result) {
  echo "Registered";
} else {
  echo "Failed";
}
?>