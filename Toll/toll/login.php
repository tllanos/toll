<?php
  session_start(); // Start login user.

  if (isset($_POST['submit'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    $connection = mysql_connect('toll.cqkduygrcpmt.us-east-1.rds.amazonaws.com:3306', 'root', 'rootroot');
      mysql_select_db('tollcontrol', $connection);

    $data = mysql_query("SELECT * FROM users WHERE userid = '$username' AND pass = '$password';", $connection);

    if (!$connection) {
      die('No pudo conectarse: '.mysql_error());
    }

    if ($row = mysql_fetch_array($data)) {
      $_SESSION['login_user'] = $username;
      header("Location: welcome.php");
    } else {
      $error = "Username or password invalid.";
    }

    mysql_close($connection);
  }
?>
