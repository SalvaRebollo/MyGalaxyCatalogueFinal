<?php
	class Database
	{
		// Atributos
		private $dbHost = "remotemysql.com" ;
		private $dbUser = "XigWZvmqYw"	  	;
		private $dbPass = "RP78oRmbYV"		;
		private $dbPort = "3306"  		 	;
		private $dbName = "XigWZvmqYw"		;

		//
		private static $prp = null	  	 ;
		private static $pdo = null 		 ;

		//
		private static $instancia = null ;

		// Constructor
		public function __construct() { $this->connect() ; }

		//
		private function __clone() {}

		//
		// Obtener Instancia
	    public static function getInstance()
	    {
	    	if (is_null(self::$instancia)) {
	    		self::$instancia = new Database() ;
	    	}
	    	return self::$instancia ;
	    }

	    //
	    // Conectar a BBDD
		public function connect()
		{
			try {
				self::$pdo = new PDO("mysql:host={$this->dbHost};port={$this->dbPort};dbname={$this->dbName};charset=utf8mb4;",
				$this->dbUser,
				$this->dbPass) ;
			} catch (Exception $e) {
				die("Error en conectar la BBDD; ".$e) ;
			}
		}

		//
		// Realizar consulta a la BBDD
		public function doQuery($sql, $params = [])
		{
			self::$prp = self::$pdo->prepare($sql) ;
			$flg = self::$prp->execute($params) ;
			if ( ($flg) && (self::$prp->rowCount() > 0) ) {
				return true  ;
			} else {
				return false ;
			}
		}

		//
		// Obtener nueva entrada de la BBDD de la clase dada (Por defecto será la StdClass si no se especifica otra)
		public function getRow($class="StdClass")
		{
			if (self::$prp) {
				return self::$prp->fetchObject($class) ;
			}
		}

		//
		// Obtener el ID de la última entrada de la BBDD
		public function getLastId()
		{
			return self::$pdo->lastInsertId() ;
		}

	}
?>	