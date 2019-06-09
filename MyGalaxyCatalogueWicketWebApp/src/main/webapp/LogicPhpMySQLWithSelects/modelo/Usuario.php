<?php

require_once "Database.php" ;

	class Usuario implements JsonSerializable
	{
        // Atributos
        private $UidUser    ;
        private $NomUser    ;
		private $ApeUser     ;
		private $DirUser     ;
		private $CiuUser     ;
		private $PaisUser     ;
		private $EmailUser     ;
        private $FotoPerfilUser     ;
        private $BioUser     ;
        private $MetContactoUser     ;

		// Constructor
		public function __construct()
		{

		}

        // Getter
        public function getUidUser() { return $this->UidUser ; }
        public function getNomUser() { return $this->NomUser ; }
        public function getApeUser() { return $this->ApeUser     ; }
		public function getDirUser() { return $this->DirUser     ; }
		public function getCiuUser() { return $this->CiuUser     ; }
		public function getPaisUser() { return $this->PaisUser     ; }
		public function getEmailUser() { return $this->EmailUser     ; }
		public function getFotoPerfilUser() { return $this->FotoPerfilUser     ; }
        public function getBioUser() { return $this->BioUser     ; }
        public function getMetContactoUser() { return $this->MetContactoUser     ; }

        // Setter
        public function setUidUser($idu) { $this->UidUser = $idu     ; }
        public function setNomUser($nou) { $this->NomUser = $nou     ; }
        public function setApeUser($apu) { $this->ApeUser = $apu     ; }
		public function setDirUser($diu) { $this->DirUser = $diu     ; }
		public function setCiuUser($ciu) { $this->CiuUser = $ciu     ; }
		public function setPaisUser($pau) { $this->PaisUser = $pau     ; }
		public function setEmailUser($emu) { $this->EmailUser = $emu     ; }
		public function setFotoPerfilUser($fou) { $this->FotoPerfilUser = $fou     ; }
        public function setBioUser($biu) { $this->BioUser = $biu     ; }
        public function setMetContactoUser($meu) { $this->MetContactoUser = $meu     ; }

		// Métodos
		public function jsonSerialize() {			
			return array(
				'UidUser' => $this->UidUser,
				'NomUser' => $this->NomUser,
				'ApeUser' => $this->ApeUser,
				'DirUser' => $this->DirUser,
				'CiuUser' => $this->CiuUser,
				'PaisUser' => $this->PaisUser,
				'EmailUser' => $this->EmailUser,
				'FotoPerfilUser' => $this->FotoPerfilUser,
				'BioUser' => $this->BioUser,
				'MetContactoUser' => $this->MetContactoUser
			);
		}


		public function insert()
		{
			$bd = Database::getInstance() ;
			return $bd->doQuery("INSERT INTO usuario(UidUser, NomUser, ApeUser, DirUser, CiuUser, PaisUser, EmailUser, FotoPerfilUser, BioUser, MetContactoUser) VALUES (:idu, :nou, :apu, :diu, :ciu, :pau, :emu, :fou, :biu, :meu) ;",
                [ ":idu" => $this->UidUser,
                  ":nou" => $this->NomUser,
                  ":apu" => $this->ApeUser,
				  ":diu" => $this->DirUser,
				  ":ciu" => $this->CiuUser,
				  ":pau" => $this->PaisUser,
				  ":emu" => $this->EmailUser,
                  ":fou" => $this->FotoPerfilUser,
                  ":biu" => $this->BioUser,
				  ":meu" => $this->MetContactoUser ]) ;
		}

        public function update()
		{
			$bd = Database::getInstance() ;
			return $bd->doQuery("UPDATE usuario SET NomUser=:nou, ApeUser=:apu, DirUser=:diu, CiuUser=:ciu, PaisUser=:pau, EmailUser=:emu, FotoPerfilUser=:fou, BioUser=:biu, MetContactoUser=:meu WHERE UidUser=:idu ;",
                [ ":idu" => $this->UidUser,
                  ":nou" => $this->NomUser,
                  ":apu" => $this->ApeUser,
				  ":diu" => $this->DirUser,
				  ":ciu" => $this->CiuUser,
				  ":pau" => $this->PaisUser,
				  ":emu" => $this->EmailUser,
                  ":fou" => $this->FotoPerfilUser,
                  ":biu" => $this->BioUser,
				  ":meu" => $this->MetContactoUser ]) ;
        }

		/*public function __toString()
		{
			return " [ { $this->idMensaje, $this->idChat, $this->texto, $this->epoch } ] " ;
		}*/

		public static function getUsuario($idu)
		{
			$bd = Database::getInstance() ;
			$bd->doQuery("SELECT * FROM usuario WHERE UidUser=:idu ;",
				[ ":idu" => $idu ]) ;

			return $bd->getRow("Usuario");
		}


		public static function getAllUsuarios()
		{
			$bd = Database::getInstance() ;

			$bd->doQuery("SELECT * FROM usuario ;") ;

			$usuarios = [] ;

			while ($usr = $bd->getRow("Usuario")) {
				array_push($usuarios, $usr) ;
			}

			return $usuarios;
		}

		
		public static function deleteUsuario($idu)
		{
			$bd = Database::getInstance() ;
			return $bd->doQuery("DELETE FROM usuario WHERE UidUser=:idu ;",
				[ ":idu" => $idu ]) ;
        }
	}
?>