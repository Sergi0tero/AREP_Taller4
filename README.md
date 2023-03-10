## Autor:
### Sergio Andrés Otero Herrera

# Taller 3 AREP:
En este taller se hace la creacion de una API REST. Se hizo la creacion de nuevas anotaciones @Component y @RequestMapping. Con el objertivo de usarlas para identificar clases y metodos de nuetros controller.

## Funcionamiento:
La aplicacion se inicia con el URL de localhost:35000, para navegar entre los distintos archivos se debe agregar los urls mostrados en la pantalla inicial.

Para poder cambiar el tipo de cada respuesta HTTP, se creo una funcion ```type()``` dentro de la clase Response, en un inicio todos los archivos se retornan como tipy HTML, esto se puese cambiar facilmente con esta funcion. Ademas de que se creo una variable directory con su respectiva funcion ```setDirectory()```, el cual permite realizar el cambio del directorio del que se quieren sacar los archivos estaticos.

## Prerrequisitos
- GIT
- JAVA
- MVN

## Instalación
De querer usar este codigo, se tiene que hacer lo siguiente:

Se clona el repositorio

```
git clone https://github.com/Sergi0tero/AREP_Taller4.git
```

Ahora, si queremos verificar la integridad del codigo

```
mvn package
```
## Correr el código
Para correr la clase main, la cual se encuentra en FirstApp.java, corremos los siguientes comando en la terminal:
```
mvn compile
```
```
mvn exec:java
```
o
```
java -cp target/classes org.webapps.FirstApp
```

## Diseño
El proyecto fue realizado en Java. El ciclo de vida empieza por el usuario, quien, usando las opciones dadas en el inicio, elige el archivo que desee. Continua con el servidor redirecciona dependiendo del servicio elegido.
Este servicio lee el archivo seleccionado en una direccion especificada. Llama a nuestra nueva clase Spark, la cual utiliza una funcion lambda anteriormente creada para saber que peticion HTTP esta haciendo el usuario (Actualmente implementadas estan el GET y el POST, se recomienda POSTMAN para usar el POST). Si no, realiza las consultas a los nuevos componentes creados

## Patrones
- Singleton

## Modular
Estas son las diferentes capaz que podemos ver:
- Servicios
- Anotaciones
- Servidor
- Spark

## Pruebas
- #### Metodo GET:
![image](https://user-images.githubusercontent.com/98189066/219531825-51a1ae9f-1b7b-4440-8883-2a77f4d84130.png)

- #### Metodo POST:
![image](https://user-images.githubusercontent.com/98189066/219531463-935067ba-2e49-403b-be03-61ba4dd196d5.png)

- #### Prueba anotaciones:
![image](https://user-images.githubusercontent.com/98189066/221082427-5eac3891-1503-4969-96be-fab4cc9520ac.png)
![image](https://user-images.githubusercontent.com/98189066/221082455-4d21d304-2a60-451a-8786-6212d211f0a4.png)


## Version
1.0
