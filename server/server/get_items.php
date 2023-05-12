
<?php
    header('Access-Control-Allow-Origin: *');
    header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Access");
    header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE');

    header('Content-Type: application/json; charset=utf8');
    include "connect.php";
    $page = $_GET['page'];
    $id_product = 1;

    $space = 5;
    $limit = ($page - 1) * $space;

    $list_item = array();
    $query = "SELECT * FROM items WHERE id_product = $id_product LIMIT $limit, $space ";

    $data = mysqli_query($conn, $query);
    while($row = mysqli_fetch_assoc($data) ){
        array_push($list_item, new Item($row['id'], $row['item_name'], $row['price'], $row['image'], $row['description'], $row['id_product']));
    }

  
    echo json_encode($list_item);

    class Item{
        function __construct($id, $name, $price, $image, $description, $id_product){
            $this->id = $id;
            $this->item_name = $name;
            $this->price = $price;
            $this->image =  $image;
            $this->description = $description;
            $this->id_product = $id_product;
        }
    }

?>