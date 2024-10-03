<?php

/* Connecting to the database. */
$host = "localhost";
$db_name = "computadores";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass) 
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Getting the values from the form. */
$admin = $_POST['admin'];
$pass = $_POST['pass'];

/* Query to check if the admin exists in the database. */
$query = "SELECT * FROM admins WHERE user = '$admin' AND pass = '$pass'";
$query_execute = mysqli_query($conn, $query) or die(mysqli_error($conn));

/* Checking if a matching record was found. */
if (mysqli_num_rows($query_execute) > 0) {
    echo json_encode(array("status" => "ok"));
} else {
    echo json_encode(array("status" => "error"));
}

mysqli_close($conn);

?>