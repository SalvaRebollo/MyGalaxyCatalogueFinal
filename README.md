# MyGalaxyCatalogue

## Índice
1. [Tecnologías utilizadas](#tecnologías-utilizadas)
2. [Aplicación Android](#mygalaxycatalogue-android-app)
    - [Home](#home)
    - [Perfil del usuario logeado](#perfil-del-usuario-logeado)
	- [Visitando un perfil de la comunidad](#visitando-un-perfil-de-la-comunidad)
	- [Detalles del producto/servicio](#cuando-pulsamos-un-producto-se-nos-abre-su-información-para-poder-verla-más-en-detalle-y-contactar-con-el-creador)
	- [Añadir producto/servicio](#añadir-un-productoservicio)
	- [Elegir entre cámara o galería](#elegir-entre-cámara-o-galería)
	- [Borrar y editar un producto/servicio](#para-borrar-y-editar-un-productoservicio)
	- [Acceso anónimo](#acceso-anónimo)
	- [Tablón de novedades](#tablón-de-novedades-botón-acerca-de-la-app-creado-para-publicar-las-próximas-actualizaciones-y-novedades-de-la-aplicación)
3. [Aplicación Web](#mygalaxycatalogue-web-app)
    - [Home](#home-1)
	- [Escaparate](#escaparate)
		- [Versión Web/3 Columnas](#versión-web3-columnas)
		- [Versión Tablet/2 Columnas](#versión-tablet2-columnas)
		- [Versión Móvil/1 Columna](#versión-móvil1-columna)
	- [Estadísticas](#estadísticas)
	- [FAQ / Preguntas Frecuentes](#faq--preguntas-frecuentes)
4. [Descarga del APK](https://github.com/SalvaRebollo/MyGalaxyCatalogueFinal/blob/master/MyGalaxyCatalogue.apk)
* [API REST en PHP con conexión MySQL hosteada en Heroku](https://my-galaxy-catalogue-php.herokuapp.com/)
* [Servicio usado para crear y administrar una base de datos MySQL remota](https://remotemysql.com/)

<img width="400px" src="/img/logo.png">

### Developed by SalvaRebollo 2º Desarrollo de Aplicaciones Multiplataforma

# Tecnologías utilizadas
<img width="100%" src="/img/tecnologias1.jpg">
<img width="100%" src="/img/tecnologias2.jpg">
	
# MyGalaxyCatalogue Android App

### Aplicación para dispositivos móviles basada en Android Studio y Firebase (Realtime Database, Storage y Auth), está sencilla aplicación nos permite crear nuestro propio catálogo de productos/servicios/etc y almacenarla en la nube para que el resto de la comunidad pueda contactar con usted, vamos a echar un vistazo a su interfaz:

<img width="100%" src="/img/screenshotsandpresentation/MyGalaxyCatalogue Exp_page-0001.jpg">
<img width="100%" src="/img/screenshotsandpresentation/MyGalaxyCatalogue Exp_page-0002.jpg">
<img width="100%" src="/img/screenshotsandpresentation/MyGalaxyCatalogue Exp_page-0003.jpg">
<img width="100%" src="/img/screenshotsandpresentation/MyGalaxyCatalogue Exp_page-0004.jpg">

#### Esta es la pantalla de bienvenida, es lo primero que encontraremos al abrir la aplicación. Como veis disponemos de tres botones, con "LOGIN" accederemos al home, al igual que con "INGRESAR DE FORMA ANÓNIMA", sólo que con esta última tendremos ciertas limitaciones como la creación de nuestro perfil o añadir productos, solo podremos echar un vistazo a lo que el resto de la comunidad ha compartido y ver el tablón de anuncios.
<img width="400px" src="/img/1 (6).jpeg">

<img width="100%" src="/img/screenshotsandpresentation/MyGalaxyCatalogue Exp_page-0005.jpg">

## Pantalla de registro

<img width="400px" src="/img/1 (5).jpeg">

<img width="100%" src="/img/screenshotsandpresentation/MyGalaxyCatalogue Exp_page-0006.jpg">

## HOME

#### Aquí se muestra nuestro catálogo con los productos que hemos ido añadiendo + los del resto de la comunidad. Como peculiaridad y gracias a la tecnología de Firebase no hay absolutamente ningún botón de cargar o actualizar en toda la aplicación, ya que esta se mantendrá 100% actualizada en tiempo real mientras tenga conexión a internet.
<div>
  <img width="400px" src="/img/1 (4).gif">
  <img width="400px" src="/img/1 (4).jpeg">
</div>

## Perfil del usuario logeado
<div>
  <img width="400px" src="/img/2.gif">
  <img width="400px" src="/img/perfil.jpeg">
</div>

## Visitando un perfil de la comunidad
<div>
  <img width="400px" src="/img/verPerfil.gif">
  <img width="400px" src="/img/verPerfil.jpeg">
</div>

### Cuando pulsamos un producto se nos abre su información para poder verla más en detalle y contactar con el creador:
<img width="400px" src="/img/1 (8).gif">

## Añadir un producto/servicio:
<div>
  <img width="400px" src="/img/1 (1).gif">
</div>

## Elegir entre cámara o galería
<div>
  <img width="400px" src="/img/modalElegirImg.gif">
</div>

## Para borrar y editar un producto/servicio:
<div>
  <img width="400px" src="/img/1 (6).gif">
</div>

## Acceso anónimo
### El acceso anónimo nos brinda el acceso sin registro a la aplicación pero con funciones más limitadas como la subida de productos/servicios o la creación de tu perfil, esta funcionará a modo de escaparate donde podrás revisar únicamente los productos/servicios subidos por los demás.
<div>
  <img width="400px" src="/img/anonimo.gif">
  
  ### Opciones limitadas
  
  <img width="400px" src="/img/anonimo.jpeg">
</div>

## Tablón de novedades, botón "Acerca de la app" creado para publicar las próximas actualizaciones y novedades de la aplicación.
<div>
  <img width="400px" src="/img/1.gif">
</div>

# MyGalaxyCatalogue Web App

### La aplicación web funciona como landing page a modo de publicidad e incentivo para descargar la aplicación Android, en ella podrás descargar la aplicación, acceder a este repositorio para tener acceso al source-code, ver el escaparate de la web con todos los productos/servicios, estadísticas de productos/servicios compartidos y una página de preguntas frecuentes o "FAQ". Funciona con Apache Wicket Framework como motor servlet y es empleado en procesos como el enrutamiento de paginas, obtención de datos, iteración de tarjetas usando modelos, paneles y diferentes componentes de Wicket, etc Veamos cada una de sus pestañas con sus diferentes vistas:

## HOME
<img width="100%" src="/img/home.gif">
<img width="100%" src="/img/home.png">

## Escaparate
### Versión Web/3 Columnas
<img width="100%" src="/img/a3columnas.gif">
<img width="100%" src="/img/escaparate.png">

### Versión Tablet/2 Columnas
<img width="100%" src="/img/a2columnas.gif">

### Versión Móvil/1 Columna
<img width="100%" src="/img/a1columna.gif">

## Estadísticas
### Aquí podemos ver otra clase de información a modo de histórico en el que se recoge para ver gráficamente la cantidad de productos/servicios subidos ordenados por fecha en los últimos 10 días
<img width="100%" src="/img/estadisticas.gif">
<img width="100%" src="/img/estadisticas.png">

## FAQ / Preguntas Frecuentes
### Aquí se recopila en forma de preguntas en un menú de acordeón los principales fuertes de la aplicación con información a destacar y cómo obtener la experiencia completa.
<img width="100%" src="/img/faq.gif">
<img width="100%" src="/img/faq.png">


### Esto ha sido todo, puedes probar la aplicación en tu dispositivo Android descargando el fichero MyGalaxyCatalogue.apk que se encuentra en este repositorio. Gracias por su atención.

