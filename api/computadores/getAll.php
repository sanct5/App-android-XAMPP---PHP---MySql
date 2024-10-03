<?php

/* Connecting to the database. */
$host = "localhost";
$db_name = "computadores";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass) 
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Creating a query to get all the data from the table. */
$query = "SELECT * FROM componentes";

/* Making a query to the database. */
$query_execute = mysqli_query($conn, $query) or die(mysqli_error($conn));

/* Creating an array and then it is pushing the data from the database into the array. */
$arr1 = array();
while ($row = mysqli_fetch_array($query_execute)) {
    $arra = array(
        'ref' => $row['ref'],
        'cpu' => $row['cpu'],
        'gpu' => $row['gpu'],
        'ram' => $row['ram'],
        'ssd' => $row['ssd'],
        'psu' => $row['psu']
    );
    array_push($arr1, $arra);
}

/* Encoding the array into JSON format and echoing it. */
echo json_encode(array('componentes' => $arr1));

/* Closing the database connection. */
mysqli_close($conn);

?>