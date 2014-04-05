// JavaScript
/* El objetivo de este fichero es crear la clase objetoAjax (en 
JavaScript a las clases se les llama prototipos) */ 
function objetoAjax(metodo) { 
/*Primero necesitamos un objeto XMLHttpRequest que cogeremos del 
constructor para que sea compatible con la mayoría de navegadores 
posible. */ 
this.objetoRequest = new ConstructorXMLHttpRequest();  
this.metodo = metodo; 
} 
 
function peticionAsincrona(url,valores) { //Función asignada al método coger del objetoAjax. 
  /*Copiamos el objeto actual, si usamos this dentro de la 
función que asignemos a  onreadystatechange, no funcionará.*/
  var objetoActual = this; 
  this.objetoRequest.open(this.metodo, url, true);  //Preparamos la conexión. 
  /*Aquí no solo le asignamos el nombre de la función, sino la 
función completa, así cada vez que se cree un nuevo objetoAjax se 
asignara una nueva función. */ 
  this.objetoRequest.onreadystatechange = function() { 
      switch(objetoActual.objetoRequest.readyState) { 
        case 1: objetoActual.cargando(); 
        break; 
        case 2: objetoActual.cargado(); 
        break; 
        case 3: objetoActual.interactivo(); 
        break; 
        case 4: /* Función que se llama cuando se completo la transmisión, se le envían 4 parámetros.*/
  		/*Detección de errores, solo nos fijamos en el codigo que nos llega normalmente como bueno, como por ahora no es necesario elevar la  
  		complejidad de la detección la dejamos así. */ 
            /*  if(objetoActual.objetoRequest.status != 200)  
              {  
              	alert("Posible Error: " + objetoActual.objetoRequest.status + ", Descripción: "+ objetoActual.objetoRequest.statusText); 
				//Por si queremos hacer algo con el error             
				manejadorError(objetoActual.objetoRequest.status);  
              } 
              else //Si no hubo error, se deja al programa seguir su flujo normal. 
              {   
				/*Función que se llama cuando se completo la transmisión, se le envían 
				4 parámetros.*/ 
 				 objetoActual.completado(objetoActual.objetoRequest.status,objetoActual.objetoRequest.statusText,objetoActual.objetoRequest.responseText, objetoActual.objetoRequest.responseXML); 
             // } 
        break;
      } 
    } 
	if (this.metodo == "GET") 
	{ 
		this.objetoRequest.send(null);  //Iniciamos la transmisión de datos. 
	} 
	else if(this.metodo == "POST") 
	{ 
		this.objetoRequest.setRequestHeader('Content-Type','application/x-www-form-urlencoded'); 
		this.objetoRequest.send(valores); 
	}
} 
/*Las siguientes funciones las dejo en blanco ya que las 
redefiniremos según nuestra necesidad haciéndolas muy sencillas o 
complejas dentro de la página o omitiéndolas cuando no son 
necesarias.*/ 
function objetoRequestCargando() {} 
function objetoRequestCargado() {} 
function objetoRequestInteractivo() {} 
function objetoRequestCompletado(estado, estadoTexto,respuestaTexto, respuestaXML) {} 
 
/* Por último diremos que las funciones que hemos creado, pertenecen 
al ObjetoAJAX,  con prototype, de esta manera todos los objetoAjax 
que se creen, lo harán conteniendo estas funciones en ellos*/ 
 
//Definimos la función de recoger información. 
objetoAjax.prototype.coger = peticionAsincrona ; 
//Definimos una serie de funciones que sería posible utilizar y las dejamos en blanco en esta clase. 
objetoAjax.prototype.cargando = objetoRequestCargando ; 
objetoAjax.prototype.cargado = objetoRequestCargado ; 
objetoAjax.prototype.interactivo = objetoRequestInteractivo ; 
objetoAjax.prototype.completado = objetoRequestCompletado ;