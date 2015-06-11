<?php
  include("login.php");
  if (isset($_SESSION['login_user'])) {
    header("location: welcome.php");
  }
?>

<!DOCTYPE html>
<html>
  <link href = "style.css" rel = "stylesheet" type = "text/css">
  <head>
    <title>Login</title>
  </head>
  <body>
    <div id = "login">
      <form action = "" method = "POST" id = "form">
        <label>USERNAME: </label>
        <input type = "text" name = "username" id = "fieldLogin"
          class = "field" placeholder = "Username">

        <label>PASSWORD: </label>
        <input type = "password" name = "password" id = "fieldLogin"
          class = "field" placeholder = "**********">
        <input type = "submit" name = "submit" id = "buttonLogin"
          class = "button">
        <?php echo $error; ?>
      </form>
    </div>
  </body>
</html>
