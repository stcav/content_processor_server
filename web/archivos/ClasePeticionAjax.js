// JavaScript
/* El objetivo de este fichero es crear la clase objetoAjax (en 
JavaScript a las clases se les llama prototipos) */ 
function objetoAjax(metodo) { 
/*Primero necesitamos un objeto XMLHttpRequest que cogeremos del 
constructor para que sea compatible con la mayor�a de navegadores 
posible. */ 
this.objetoRequest = new ConstructorXMLHttpRequest();  
this.metodo = metodo; 
} 
 
function peticionAsincrona(url,valores) { //Funci�n asignada al m�todo coger del objetoAjax. 
  /*Copiamos el objeto actual, si usamos this dentro de la 
funci�n que asignemos a  onreadystatechange, no funcionar�.*/
  var objetoActual = this; 
  this.objetoRequest.open(this.metodo, url, true);  //Preparamos la conexi�n. 
  /*Aqu� no solo le asignamos el nombre de la funci�n, sino la 
funci�n completa, as� cada vez que se cree un nuevo objetoAjax se 
asignara una nueva funci�n. */ 
  this.objetoRequest.onreadystatechange = function() { 
      switch(objetoActual.objetoRequest.readyState) { 
        case 1: objetoActual.cargando(); 
        break; 
        case 2: objetoActual.cargado(); 
        break; 
        case 3: objetoActual.interactivo(); 
        break; 
        case 4: /* Funci�n que se llama cuando se completo la transmisi�n, se le env�an 4 par�metros.*/
  		/*Detecci�n de errores, solo nos fijamos en el codigo que nos llega normalmente como bueno, como por ahora no es necesario elevar la  
  		complejidad de la detecci�n la dejamos as�. */ 
            /*  if(objetoActual.objetoRequest.status != 200)  
              {  
              	alert("Posible Error: " + objetoActual.objetoRequest.status + ", Descripci�n: "+ objetoActual.objetoRequest.statusText); 
				//Por si queremos hacer algo con el error             
				manejadorError(objetoActual.objetoRequest.status);  
              } 
              else //Si no hubo error, se deja al programa seguir su flujo normal. 
              {   
				/*Funci�n que se llama cuando se completo la transmisi�n, se le env�an 
				4 par�metros.*/ 
 				 objetoActual.completado(objetoActual.objetoRequest.status,objetoActual.objetoRequest.statusText,objetoActual.objetoRequest.responseText, objetoActual.objetoRequest.responseXML); 
             // } 
        break;
      } 
    } 
	if (this.metodo == "GET") 
	{ 
		this.objetoRequest.send(null);  //Iniciamos la transmisi�n de datos. 
	} 
	else if(this.metodo == "POST") 
	{ 
		this.objetoRequest.setRequestHeader('Content-Type','application/x-www-form-urlencoded'); 
		this.objetoRequest.send(valores); 
	}
} 
/*Las siguientes funciones las dejo en blanco ya que las 
redefiniremos seg�n nuestra necesidad haci�ndolas muy sencillas o 
complejas dentro de la p�gina o omiti�ndolas cuando no son 
necesarias.*/ 
function objetoRequestCargando() {} 
function objetoRequestCargado() {} 
function objetoRequestInteractivo() {} 
function objetoRequestCompletado(estado, estadoTexto,respuestaTexto, respuestaXML) {} 
 
/* Por �ltimo diremos que las funciones que hemos creado, pertenecen 
al ObjetoAJAX,  con prototype, de esta manera todos los objetoAjax 
que se creen, lo har�n conteniendo estas funciones en ellos*/ 
 
//Definimos la funci�n de recoger informaci�n. 
objetoAjax.prototype.coger = peticionAsincrona ; 
//Definimos una serie de funciones que ser�a posible utilizar y las dejamos en blanco en esta clase. 
objetoAjax.prototype.cargando = objetoRequestCargando ; 
objetoAjax.prototype.cargado = objetoRequestCargado ; 
objetoAjax.prototype.interactivo = objetoRequestInteractivo ; 
objetoAjax.prototype.completado = objetoRequestCompletado ;