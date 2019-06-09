<?php

	require_once "modelo/Usuario.php";

	class controllerUsuario
	{
		// Constructor
		public function __construct() {}

			
		// Métodos
		public function insert()
		{
			$UidUser = $_POST["UidUser"] ;
            $NomUser = $_POST["NomUser"] ;
            $ApeUser = $_POST["ApeUser"] ;
            $DirUser = $_POST["DirUser"] ;
            $CiuUser = $_POST["CiuUser"] ;
            $PaisUser = $_POST["PaisUser"] ;
            $EmailUser = $_POST["EmailUser"] ;
            $FotoPerfilUser = $_POST["FotoPerfilUser"] ;
            $BioUser = $_POST["BioUser"] ;
            $MetContactoUser = $_POST["MetContactoUser"] ;

			$usuario = new Usuario() ;
			$usuario->setUidUser($UidUser);
			$usuario->setNomUser($NomUser);
			$usuario->setApeUser($ApeUser);
			$usuario->setDirUser($DirUser);
			$usuario->setCiuUser($CiuUser);
			$usuario->setPaisUser($PaisUser);
			$usuario->setEmailUser($EmailUser);
			$usuario->setFotoPerfilUser($FotoPerfilUser);
            $usuario->setBioUser($BioUser);
            $usuario->setMetContactoUser($MetContactoUser);

			print_r($usuario);
			
			if ($usuario->insert()) {
				header("HTTP/1.1 200 OK") ;
			} else {
				header("HTTP/1.1 400 ERROR") ;
			}
		}


        public function update()
        {
            $UidUser = $_POST["UidUser"] ;
            $NomUser = $_POST["NomUser"] ;
            $ApeUser = $_POST["ApeUser"] ;
            $DirUser = $_POST["DirUser"] ;
            $CiuUser = $_POST["CiuUser"] ;
            $PaisUser = $_POST["PaisUser"] ;
            $EmailUser = $_POST["EmailUser"] ;
            $FotoPerfilUser = $_POST["FotoPerfilUser"] ;
            $BioUser = $_POST["BioUser"] ;
            $MetContactoUser = $_POST["MetContactoUser"] ;


            $usuario = Usuario::getUsuario($UidUser) ;

            $usuario->setNomUser($NomUser);
            $usuario->setApeUser($ApeUser);
            $usuario->setDirUser($DirUser);
            $usuario->setCiuUser($CiuUser);
            $usuario->setPaisUser($PaisUser);
            $usuario->setEmailUser($EmailUser);
            $usuario->setFotoPerfilUser($FotoPerfilUser);
            $usuario->setBioUser($BioUser);
            $usuario->setMetContactoUser($MetContactoUser);



            if ($usuario->update()) {
                header("HTTP/1.1 200 OK") ;
            } else {
                header("HTTP/1.1 400 ERROR") ;
            }
        }


		public function delete()
		{
			$UidUser = $_POST["UidUser"] ;

			if (Usuario::deleteUsuario($UidUser)) {
				header("HTTP/1.1 200 OK") ;
			} else {
				header("HTTP/1.1 400 ERROR") ;
			}
		}


        public function selectById()
        {
            $UidUser = $_POST["UidUser"] ;

            $usuario = Usuario::getUsuario($UidUser) ;

            if ($usuario) {
                print_r(json_encode($usuario)) ;
                header("HTTP/1.1 200 OK") ;
            } else {
                header("HTTP/1.1 400 ERROR") ;
            }
        }

        public function selectAll()
        {
            $usuarios = Usuario::getAllUsuarios() ;

            if ($usuarios) {
                print_r(json_encode($usuarios)) ;
                header("HTTP/1.1 200 OK") ;
            } else {
                header("HTTP/1.1 400 ERROR") ;
            }
        }
	}