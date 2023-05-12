<?php
    include "connect.php";
    $json = $_POST['json'];
    $data = json_decode($json,true);

    foreach ($data as $value){
        $id_product = $value['id_product'];
        $id_customer = $value['id_customer'];
        $amount = $value['amount'];
        $query = "INSERT INTO orders(id_product, id_customer, amount) VALUES ( '$id_product', '$id_customer', '$amount')";
        $Dta = mysqli_query($conn, $query);
    }

    if ($Dta){
        echo "1";
    }
    else{
        echo "0";
    }
    
?>