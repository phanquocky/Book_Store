<?php
    include "connect.php";
    $customer_name = $_POST['customer_name'];
    $phone = $_POST['phone'];
    $address = $_POST['address'];

    // $customer_name = "Phan Quoc ky";
    // $phone = "0123456789";
    // $address = "binh dinh";

    if(strlen($customer_name) > 0 && strlen($phone) > 0 && strlen($address) > 0){
        $query = "INSERT INTO customer(id, customer_name, address, phone) VALUES (null, '$customer_name', '$phone', '$address')";
        if(mysqli_query($conn, $query)){
            $id = $conn->insert_id;
            echo $id;
        }
        else{
            echo "-1";
        }
    }else{
        echo "-1";
    }
?>