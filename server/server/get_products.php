<?php

    include"connect.php";
    $query = "SELECT * FROM products";

    $data = mysqli_query($conn, $query);
    $products = array();
    while($row = mysqli_fetch_assoc($data)){
        // echo json_encode($row);
        $product = new Products(
            '1',
            'sach',
            'coduong');
        array_push($products, new Products($row['id'], $row['type_name'], $row['image_product']));
        // echo $row['id'], $row['type_name'], $row['image_product'];
    }
    echo json_encode($products);


    class Products{
        function __construct($id, $name, $image){
            $this->id = $id;
            $this->type_name = $name;
            $this->image_product = $image;
        }

        
    }
   
