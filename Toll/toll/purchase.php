<?php
  session_start();
  $purchase = $_POST['purchase'];

  $user_check = $_SESSION['login_user']; // Session.

  if (isset($_POST['submit'])) {
    echo "entro";
    $connection = mysql_connect('toll.cqkduygrcpmt.us-east-1.rds.amazonaws.com:3306', 'root', 'rootroot');
      mysql_select_db('tollcontrol', $connection);

    if (!$connection) {
      die('No pudo conectarse: '.mysql_error());
    }

    if($purchase < 0) $purchase = $purchase * -1;

    $updateFunds = mysql_query("UPDATE users SET funds = funds + '$purchase' WHERE userid = '$user_check';", $connection);

    header("Location: welcome.php");
    mysql_close($connection);
  } else { header("location: index.php"); }
?>
