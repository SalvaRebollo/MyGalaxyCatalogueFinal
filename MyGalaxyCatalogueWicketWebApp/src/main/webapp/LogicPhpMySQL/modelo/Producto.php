<?php

require_once "Database.php" ;

	class Producto
	{
        // Atributos
        private $IdProduct ;
        private $UidUser    ;
        private $TituloProduct    ;
		private $DescProduct     ;
		private $PrecioProduct     ;
		private $ImgProduct     ;
		private $CategProduct     ;
		private $EstadoProduct     ;

		// Constructor
		public function __construct()
		{

		}

        // Getter
        public function getIdProduct() { return $this->IdProduct ; }
        public function getUidUser() { return $this->UidUser ; }
        public function getTituloProduct() { return $this->TituloProduct     ; }
		public function getDescProduct() { return $this->DescProduct     ; }
		public function getPrecioProduct() { return $this->PrecioProduct     ; }
		public function getImgProduct() { return $this->ImgProduct     ; }
		public function getCategProduct() { return $this->CategProduct     ; }
		public function getEstadoProduct() { return $this->EstadoProduct     ; }

        // Setter
        public function setIdProduct($idp) { $this->IdProduct = $idp     ; }
        public function setUidUser($idu) { $this->UidUser = $idu     ; }
        public function setTituloProduct($tpr) { $this->TituloProduct = $tpr     ; }
		public function setDescProduct($dpr) { $this->DescProduct = $dpr     ; }
		public function setPrecioProduct($ppr) { $this->PrecioProduct = $ppr     ; }
		public function setImgProduct($ipr) { $this->ImgProduct = $ipr     ; }
		public function setCategProduct($cpr) { $this->CategProduct = $cpr     ; }
		public function setEstadoProduct($epr) { $this->EstadoProduct = $epr     ; }

		// Métodos
		public function insert()
		{
			$bd = Database::getInstance() ;
			return $bd->doQuery("INSERT INTO producto(IdProduct, UidUser, TituloProduct, DescProduct, PrecioProduct, ImgProduct, CategProduct, EstadoProduct) VALUES (:idp, :idu, :tpr, :dpr, :ppr, :ipr, :cpr, :epr) ;",
                [ ":idp" => $this->IdProduct,
                  ":idu" => $this->UidUser,
                  ":tpr" => $this->TituloProduct,
				  ":dpr" => $this->DescProduct,
				  ":ppr" => $this->PrecioProduct,
				  ":ipr" => $this->ImgProduct,
				  ":cpr" => $this->CategProduct,
				  ":epr" => $this->EstadoProduct ]) ;
		}

        public function update()
		{
			$bd = Database::getInstance() ;
			return $bd->doQuery("UPDATE producto SET TituloProduct=:tpr, DescProduct=:dpr, PrecioProduct=:ppr, ImgProduct=:ipr, CategProduct=:cpr, EstadoProduct=:epr WHERE IdProduct=:idp ;",
                [ ":idp" => $this->IdProduct,
                  ":tpr" => $this->TituloProduct,
				  ":dpr" => $this->DescProduct,
				  ":ppr" => $this->PrecioProduct,
				  ":ipr" => $this->ImgProduct,
				  ":cpr" => $this->CategProduct,
				  ":epr" => $this->EstadoProduct ]) ;
        }

		/*public function __toString()
		{
			return " [ { $this->idMensaje, $this->idChat, $this->texto, $this->epoch } ] " ;
		}*/

		public static function getProduct($idp)
		{
			$bd = Database::getInstance() ;
			$bd->doQuery("SELECT * FROM producto WHERE IdProduct=:idp ;",
				[ ":idp" => $idp ]) ;

			return $bd->getRow("Producto");
		}
		
		public static function deleteProduct($idp)
		{
			$bd = Database::getInstance() ;
			return $bd->doQuery("DELETE FROM producto WHERE IdProduct=:idp ;",
				[ ":idp" => $idp ]) ;
        }

	}
?>