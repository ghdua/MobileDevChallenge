# Aplicación Android con Firebase
## Algunas características básicas
- Autenticación con número telefónico
- Almacenamiento de datos con Realtime Database

## Para la prueba de Login
- Teléono de prueba: +51999666999
- Código validador: 123456
- Se puede usar cualquier otro teléfono y llegará un sms con un código validador
- De no poder loguearse con un número cuaquier, usar el número de prueba previamente mencionado

## Librerías usadas en gradle
- implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
- implementation("com.google.firebase:firebase-analytics")
- implementation("com.google.firebase:firebase-auth")
- implementation("com.google.firebase:firebase-database-ktx")
- implementation("com.googlecode.libphonenumber:libphonenumber:8.12.12")
- testImplementation("io.mockk:mockk:1.12.2")
- testImplementation("org.mockito:mockito-core:4.6.1")

## Pantallas
![Pantalla Login 1](/assets/images/Login1.jpg)
![Pantalla Login 2](/assets/images/Login2.jpg)
![Pantalla Login 3](/assets/images/Login3.jpg)
![Pantalla Login 4](/assets/images/Login4.jpg)
![Pantalla Login 5](/assets/images/Login5.jpg)
![Pantalla Login 6](/assets/images/Login6.jpg)
![Pantalla Home 1](/assets/images/Home1.jpg)
![Pantalla Home 2](/assets/images/Home2.jpg)
![Pantalla Home 3](/assets/images/Home3.jpg)
![Pantalla Home 4](/assets/images/Home4.jpg)

## Pruebas unitarias
Contiene pruebas unitarias para la clase modelo que representa los datos ingresado en la bd firebase, la clase se llama Client, dentro del paquete Model, asimismo, contiene una prueba unitaria para la clase de RealtimeManager

### Clases Test
- RealtimeManagerTest
- ClientTest

### Utils
Se usa una librería para obtener los códigos telefónicos por países, la clase se llama PhoneNumberUtil y en gradle es: implementation("com.googlecode.libphonenumber:libphonenumber:8.12.12")
