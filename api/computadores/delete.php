<?php

/* Connecting to the database. */
$host = "localhost";
$db_name = "computadores";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass) 
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Deleting the data from the database. */
$ref = $_POST['ref'];
$query = "DELETE FROM componentes WHERE ref = '$ref'";
$query_execute = mysqli_query($conn, $query) or die(mysqli_error($conn));
mysqli_close($conn);

?>