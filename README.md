# Challenge ONE | Java | 🔙🔚 | API REST | CRUD

## 📖:Descripción:
En este proyecto replicamos a nivel de back end el proceso que realiza un foro, y para eso crearemos una API REST usando Spring. Esta aplicación permite a los usuarios crear, visualizar, actualizar y eliminar 
tópicos según sus intereses y necesidades. Además, asegura que cada tópico sea único, evitando la duplicidad tanto de título como de mensaje.

## ⚙ Funcionalidades:
Nuestra API va a centrarse específicamente en los tópicos, y debe permitir a los usuarios:

:heavy_check_mark: `Funcionalidad 1: Crear un nuevo tópico` 
La API debe tener un endpoint para el registro de nuevos tópicos y debe aceptar solicitudes POST para el URI `/topico`.

✍️ Los datos del tópico (título, mensaje, autor y curso) deben enviarse en el cuerpo de la solicitud, en formato JSON.

#### 📣 Reglas del negocio
- Todos los campos son obligatorios.
- La API no debe permitir los registros duplicados (que contengan el mismo título y mensaje).

:heavy_check_mark: `Funcionalidad 2 Mostrar todos los tópicos creados:`
La API debe tener un endpoint para la lista de todos los tópicos y debe aceptar solicitudes GET para el URI `/topicos`.

✍️ Los datos del tópico (título, mensaje, fecha de creación, status, autor y curso) deben devolverse en el cuerpo de la respuesta, en formato JSON.

:heavy_check_mark: `Funcionalidad 3 Mostrar un tópico específico:`
La API debe tener un endpoint para la lista de todos los tópicos y debe aceptar solicitudes GET para el URI `/topicos/{id}`.

✍️ Los datos del tópico (título, mensaje, fecha de creación, status, autor y curso) deben devolverse en el cuerpo de la respuesta, en formato JSON.

:heavy_check_mark: `Funcionalidad 4 Actualizar un tópico:`
La API debe tener un endpoint para el registro de nuevos tópicos y debe aceptar solicitudes PUT para el URI `/topicos/{id}`.

#### 👀 Observación
Las mismas reglas de negocio para el registro de un tópico deben ser aplicadas en la actualización del mismo.

:heavy_check_mark: `Funcionalidad 5 Eliminar un tópico:`
La API debe tener un endpoint para la eliminación de tópicos y debe aceptar solicitudes DELETE para el URI `/topicos/{id}`.

### 🗄 Plugins:
:heavy_check_mark: Terminado

-Implementar el edpoint `/usuario` el cual nos permita crear, visualizar, actualizar y eliminar usuarios para poder loguearnos.

:construction: En construcción:

-Implementar el edpoint `/respuestas` el cual nos permita crear, visualizar, actualizar y eliminar respuestas para nuestros tópicos.
-Implementar el edpoint `/cursos` el cual nos permita crear, visualizar, actualizar y eliminar los cursos para nuestros tópicos.

## 📣 Autenticación Segura con BCrypt y Token
Para garantizar una autenticación segura en el foro, implementamos el algoritmo BCrypt para el almacenamiento y verificación de contraseñas de usuario. 

-Registro y Verificación con BCrypt: Cuando un usuario se registra en nuestra plataforma, su contraseña se cifra utilizando BCrypt antes de almacenarse en nuestra base de datos. Cuando intenta iniciar sesión, 
la contraseña proporcionada se compara con la versión cifrada usando BCrypt para verificar su validez.

-Generación y Uso del Token: Una vez que el usuario sé auténtica correctamente, se genera un token de autenticación. Debe incluirse en la cabecera de todas las solicitudes subsiguientes realizadas a nuestra API. 
El token actúa como una forma de identificación y autorización, permitiendo que el usuario acceda a recursos protegidos de manera segura.

## 💻 Tecnologías utilizadas:
- Java
- IntelIj
- Spring Boot
- MySql
- Hibernate
- JPA
- Git

## Desarrollado por:
| <img src="https://avatars.githubusercontent.com/u/125098940?v=4" width=115><br><sub>Jose Juan Varela Contreras</sub> |
| :---: |

