# ticarum-hospitalclinicovet-api
TICARUM - Ejercicio prueba selectiva 6/2024 - Autor: Santa Cruz Martínez Moñino

Hay 2 ficheros de colección de POSTMAN iguales:
- Ticarum HospitalClinicoVet API.postman_collection.json
- Ticarum HospitalClinicoVet API.postman_collection_2_1.json

Se añaden ambos ficheros al proyecto para evitar problemas de importación en caso de haber añadido solo uno. Contienen las mismas peticiones pero varía la forma de exportación empleada en POSTMAN. (Collections versión 2 y Collections versión 2.1)

Base de datos H2 accesible desde: http://localhost:8080/h2-console
- Data source URL: jdbc:h2:mem:hospitalclinicovet
- Usuario: h2user
- Contraseña: ticarumhospitalclinicovet

Documentación OpenApi/Swagger para el API REST accesible desde: http://localhost:8080/swagger-ui/index.html

Aclaración: Al hacer el DELETE de una mascota se marca como dada de baja y no se podrá hacer ninguna operación de modificación sobre ella, pero si que se pueden realizar operaciones de consulta con el API para mostrar que sigue persistida en base de datos. En caso de no querer que se muestre en operaciones de consulta habría que hacer una consulta al repositorio filtrando por "activa = true" o realizando el filtrado en el servicio. Esto se ha realizado con fines de demostrar que no es eliminada de la base de datos.
