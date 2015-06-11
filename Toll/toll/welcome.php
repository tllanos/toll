
<?php
  $connection = mysql_connect('toll.cqkduygrcpmt.us-east-1.rds.amazonaws.com:3306', 'root', 'rootroot');
    mysql_select_db('tollcontrol', $connection);
    session_start();

  $user_check = $_SESSION['login_user']; // Session.

  $utype = mysql_query("SELECT type FROM users WHERE userid = '$user_check';", $connection);
  $rstype = mysql_fetch_assoc($utype);
  $type = $rstype['type'];

  $name = mysql_query("SELECT uname FROM users WHERE userid = '$user_check';", $connection); // Name.
  $rsn = mysql_fetch_assoc($name);
  $uname = $rsn['uname'];

  $queryBal = mysql_query("SELECT funds FROM users WHERE userid = '$user_check';", $connection); // Balance.
  $rsb = mysql_fetch_assoc($queryBal);
  $balance = $rsb['funds'];

  if (!$connection) {
    die('No pudo conectarse: '.mysql_error());
  }

  if (!isset($uname)) { // Determines if the session exists.
    mysql_close($connection);
    header('Location: index.php');
  }
?>

<!DOCTYPE html>
<html>
  <head>
    <title>Welcome <?php echo $uname;?></title>
  </head>
  <body>
    <div id = "table" style = "margin: 0 auto; width: 282px;">
    <table border = "1" cellpadding="10">
      <tr>
        <td colspan = "2" align = "center"><h1>Welcome <?php echo $uname; ?></h1></td>
      </tr>
      <tr>
        <td>ID: </td>
        <td align="center"><?php echo $user_check;?></td>
      </tr>
      <tr>
        <td>Plate: </td>
        <td align="center">
          <?php
            if($type == 2) {
              $queryPlate = mysql_query("SELECT plate FROM users WHERE
                userid = '$user_check';", $connection); // Plate.
              $rsp = mysql_fetch_assoc($queryPlate);
              $plate = $rsp['plate'];
              echo $plate;
            } else { echo "N/A"; }
          ?>
        </td>
      </tr>
      <tr>
        <td>Name: </td>
        <td align="center"><?php echo $uname; ?></td>
      </tr>
      <tr>
        <td>Balance: </td>
        <td align="center"><?php echo "$".$balance;?></td>
      </tr>
      <tr>
        <td>Pending: </td>
        <td align="center">
          <?php
            $quePen = mysql_query("SELECT debt FROM users WHERE
              userid = '$user_check';", $connection);
            $rspen = mysql_fetch_assoc($quePen);
            echo "$".$rspen['debt'];
          ?>
        </td>
      </tr>
      <tr>
        <td>Sensor ID: </td>
        <td align="center">
          <?php
            if ($type == 1) {
              $querySensor = mysql_query("SELECT sensorid FROM users WHERE userid = '$user_check';", $connection); // Plate.
              $rss = mysql_fetch_assoc($querySensor);
              $sensor = $rss['sensorid'];
              echo $sensor;
            } else { echo "N/A"; }
          ?>
        </td>
      </tr>
      <tr>
        <td colspan = "2" align="center"><div id = "addbutton" onclick = "welcomeFunction();"
          style = "cursor: pointer;">Purchase Credits</div>
          <div id = "buyDiv"></div></td>

      </tr>
      <tr>
        <td>Last Activity: </td>
        <td align="center">
          <?php
            if ($type == 1) {
              $qqq = "SELECT date FROM tollsensor WHERE sensorid = '$sensor' ORDER BY date DESC LIMIT 1;";
            } else {
              $qqq = "SELECT date FROM tollphoto WHERE plate = '$plate' ORDER BY date DESC LIMIT 1;";
            }
            $qla = mysql_query($qqq, $connection);
            $rstLA = mysql_fetch_assoc($qla);
            echo $rstLA['date'];
          ?>
        </td>
      </tr>
      <tr>
        <td>Creation Date: </td>
        <td align="center">
          <?php
            if ($type == 1) {
              $qq = "SELECT date FROM tollsensor WHERE sensorid = '$sensor' ORDER BY date ASC LIMIT 1;";
            } else {
              $qq = "SELECT date FROM tollphoto WHERE plate = '$plate' ORDER BY date ASC LIMIT 1;";
            }
            $qlac = mysql_query($qq, $connection);
            $rstCD = mysql_fetch_assoc($qlac);
            echo $rstCD['date'];
          ?>
        </td>
      </tr>
      <tr>
        <td colspan = "2" align="center">
            <a href = "logout.php">Log Out</a>
        </td>
      </tr>

    </table>
  </div>
    <script>
      function welcomeFunction() {
        document.getElementById("buyDiv").innerHTML += '<form action = "purchase.php" '
        + 'name="form" onsubmit = "return validateForm()" method = "POST">'
        + '<input type = "text" id = "buyText" name = "purchase"><input type ="submit" '
        + 'id "purchaseButton" value = "Purchase" name = "submit"></form>';
        document.getElementById("addbutton").remove();
      }
      function validateForm() {
        var x = document.forms["form"]["buyText"].value;
        if (x == null || x == "") {
          var validate = document.createElement("p");
          var text = document.createTextNode("The input must be filled out.");
          validate.appendChild(text);
          document.getElementById("buyDiv").appendChild(validate);
          return false;
        }
        if (x < 0) {
          var validate = document.createElement("p");
          var text = document.createTextNode("The value must be positive.");
          validate.appendChild(text);
          document.getElementById("buyDiv").appendChild(validate);
          return false;
        }
      }
    </script>
    <?php mysql_close($connection); ?>
  </body>
</html>
