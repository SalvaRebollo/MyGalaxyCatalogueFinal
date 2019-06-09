<?php

require_once "Database.php" ;

	class Statistics implements JsonSerializable
	{
        // Atributos
        private $Fecha ;
        private $Cantidad    ;

		// Constructor
		public function __construct()
		{

		}

        // Getter
        public function getFecha() { return $this->Fecha ; }
        public function getCantidad() { return $this->Cantidad ; }



        // Setter
        public function setFecha($fec) { $this->Fecha = $fec     ; }
        public function setCantidad($can) { $this->Cantidad = $can     ; }


		// Métodos
		public function jsonSerialize() {			
			return array(
				'Fecha' => $this->Fecha,
				'Cantidad' => $this->Cantidad
			);
		}

        public static function getProductStatistics()
        {
            $bd = Database::getInstance() ;

            $bd->doQuery("select DATE(`FechaCreacion`) AS 'Fecha', count(*) AS 'Cantidad'
                          from producto
                         where day(`FechaCreacion`)
                         group by DATE(`FechaCreacion`)
                         order by DATE(`FechaCreacion`) desc ;");

            $productos = [] ;

            while ($pro = $bd->getRow("Statistics")) {
                array_push($productos, $pro) ;
            }

            return $productos;
        }

	}
?>