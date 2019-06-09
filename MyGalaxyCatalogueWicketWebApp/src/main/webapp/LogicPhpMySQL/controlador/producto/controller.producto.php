<?php

	require_once "modelo/Producto.php";

	class controllerProducto
	{
		// Constructor
		public function __construct() {}

			
		// Métodos
		public function insert()
		{
			$IdProduct = $_POST["IdProduct"] ;
			$UidUser = $_POST["UidUser"] ;
			$TituloProduct = $_POST["TituloProduct"] ;
			$DescProduct = $_POST["DescProduct"] ;
			$PrecioProduct = $_POST["PrecioProduct"] ;
			$ImgProduct = $_POST["ImgProduct"] ;
			$CategProduct = $_POST["CategProduct"] ;
			$EstadoProduct = $_POST["EstadoProduct"] ;

			$producto = new Producto() ;
			$producto->setIdProduct($IdProduct);
			$producto->setUidUser($UidUser);
			$producto->setTituloProduct($TituloProduct);
			$producto->setDescProduct($DescProduct);
			$producto->setPrecioProduct($PrecioProduct);
			$producto->setImgProduct($ImgProduct);
			$producto->setCategProduct($CategProduct);
			$producto->setEstadoProduct($EstadoProduct);

			print_r($producto);
			
			if ($producto->insert()) {
				header("HTTP/1.1 200 OK") ;
			} else {
				header("HTTP/1.1 400 ERROR") ;
			}
		}


		public function update()
		{
			$IdProduct = $_POST["IdProduct"] ;
			$TituloProduct = $_POST["TituloProduct"] ;
			$DescProduct = $_POST["DescProduct"] ;
			$PrecioProduct = $_POST["PrecioProduct"] ;
			$ImgProduct = $_POST["ImgProduct"] ;
			$CategProduct = $_POST["CategProduct"] ;
			$EstadoProduct = $_POST["EstadoProduct"] ;

			$producto = Producto::getProduct($IdProduct) ;

			$producto->setTituloProduct($TituloProduct);
			$producto->setDescProduct($DescProduct);
			$producto->setPrecioProduct($PrecioProduct);
			$producto->setImgProduct($ImgProduct);
			$producto->setCategProduct($CategProduct);
			$producto->setEstadoProduct($EstadoProduct);

			if ($producto->update()) {
				header("HTTP/1.1 200 OK") ;
			} else {
				header("HTTP/1.1 400 ERROR") ;
			}
		}


		public function delete()
		{
			$IdProduct = $_POST["IdProduct"] ;

			if (Producto::deleteProduct($IdProduct)) {
				header("HTTP/1.1 200 OK") ;
			} else {
				header("HTTP/1.1 400 ERROR") ;
			}
		}
	}