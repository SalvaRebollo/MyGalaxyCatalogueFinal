<?php

	require_once "modelo/Statistics.php";

	class controllerStatistics
	{
		// Constructor
		public function __construct() {}

			
		// Métodos
        public function getProductStatistics()
        {
            $productos = Statistics::getProductStatistics() ;

            if ($productos) {
                print_r(json_encode($productos)) ;
                header("HTTP/1.1 200 OK") ;
            } else {
                header("HTTP/1.1 400 ERROR") ;
            }
        }
	}