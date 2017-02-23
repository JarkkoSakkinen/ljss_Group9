<?php

define('HOST', 'db4free.net');
define('USER', 'ljssprojektiryhm');
define('PASS', 'LJSSProjektiPW');
define('DB', 'ljssprojektidb');

$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
?>