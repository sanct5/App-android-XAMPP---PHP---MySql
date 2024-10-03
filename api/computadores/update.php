<?php

/* Connecting to the database. */
$host = "localhost";
$db_name = "computadores";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass) 
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Getting the data from the form. */
$ref = $_POST['ref'];
$cpu = $_POST['cpu'];
$gpu = $_POST['gpu'];
$ram = $_POST['ram'];
$ssd = $_POST['ssd'];
$psu = $_POST['psu'];

/* Updating the database. */
$query = "UPDATE componentes SET 
        cpu = '$cpu',
        gpu = '$gpu',
        ram = '$ram',
        ssd = '$ssd',
        psu = '$psu'
        WHERE ref = '$ref'";
        
mysqli_query($conn, $query) or die(mysqli_error($conn));
mysqli_close($conn);

?>