<?php
	// recibimos por método get (URL) el modelo y la operación a pedir
	$mod = $_GET["mod"] ?? "producto" ; // modelo
	$ope = $_GET["ope"] ?? "insert"   ; // operación (método del modelo)

	// importar el controlador
	require_once "controlador/$mod/controller.$mod.php" ;

	//
	$nme = "controller$mod" ;

	// crear el controlador
	$cont = new $nme() ;

	// llamamos al método correspondiente
	if (method_exists($cont, $ope)) {
		$cont->$ope() ;
	} else {
		header("HTTP/1.1 400 ERROR") ;
		die("***ERROR: Se ha producido un error en la Aplicación.") ;
	}