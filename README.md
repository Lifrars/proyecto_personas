# Gestión de un árbol genealógico con listas generalizadas

Este proyecto fue realizado para la materia Algoritmos y Programación IV. El objetivo fue representar un árbol genealógico n-ario sin utilizar una clase de árbol convencional. Para hacerlo se trabajó con listas generalizadas, donde cada persona puede ser una hoja o puede apuntar a una sublista con sus hijos.

## Explicación del funcionamiento

La información de cada integrante de la familia se guarda en la clase `Persona`. Cada registro contiene el nombre, la cédula y la fecha de nacimiento. La organización del árbol se maneja con la clase `Nodo`, que tiene las referencias `liga` y `ligaLista`, además de la variable `sw`.

La variable `sw` permite saber qué representa un nodo:

- Cuando `sw` vale 0, el nodo guarda directamente una persona. En este caso se considera un dato simple o una hoja.
- Cuando `sw` vale 1, el nodo representa una sublista. La referencia `ligaLista` conduce a una cabecera que contiene la persona y desde esa cabecera se enlazan sus hijos.

La referencia `liga` sirve para conectar personas que están en el mismo nivel y tienen el mismo padre. En otras palabras, enlaza hermanos. Por su parte, `ligaLista` permite bajar a la siguiente generación. De esta manera se pueden representar tantos hijos y tantos niveles como sean necesarios, que es la característica principal de un árbol n-ario.

El ancestro principal se guarda en la variable `ancestro` de la clase `Lista`. Como es la raíz, no necesita que se indique un padre. Sus hijos se conectan por medio de `liga`. Para las demás personas ocurre algo especial cuando se registra el primer hijo: el nodo que antes era una hoja se convierte en una sublista, su información pasa a una cabecera y el nuevo hijo queda enlazado desde ella.

La inserción se realiza buscando de forma recursiva la cédula del padre. Durante el recorrido se revisan primero los nodos de la lista actual y, cuando se encuentra una sublista, se continúa por sus hijos. Al encontrar al padre, el nuevo nodo se ubica entre sus hermanos por cédula, de menor a mayor. Esto evita tener que ordenar toda la lista después de cada registro.

La misma idea de recorrer listas y sublistas se usa para consultar padres, hermanos, tíos, sobrinos, primos, ancestros y descendientes. Algunas consultas son recursivas y otras utilizan una pila con `ArrayDeque`. La pila fue útil en operaciones como mostrar los descendientes, visualizar el árbol completo, calcular la altura y buscar la persona con más hijos, porque permite bajar por una rama sin olvidar los hermanos que todavía faltan por visitar.

Para visualizar el árbol se guarda en la pila el nodo, el prefijo que se debe imprimir y un valor que indica si es el último hijo. Así se pueden mostrar líneas como `|--` y `` `-- `` para diferenciar las ramas y las generaciones.

## Dificultades y soluciones

Una de las primeras dificultades fue entender que una persona no se representa siempre de la misma forma. Mientras no tenga hijos, sus datos están directamente en el nodo. Cuando se convierte en padre, pasa a estar representada por la cabecera de una sublista. Para evitar repetir validaciones en todo el programa, los métodos `getPersona()` y `getCedula()` de `Nodo` consultan el valor de `sw` y retornan la información correcta en ambos casos.

Otro reto fue realizar recorridos sin confundir la cabecera de una sublista con uno de sus hijos. La solución fue iniciar los recorridos de descendencia desde `getLigaLista().getLiga()`: el primer acceso entra a la cabecera y el segundo avanza al primer hijo. También fue necesario llevar variables de control para detener las búsquedas recursivas cuando el dato ya había sido encontrado.

La eliminación fue una de las operaciones que más cuidado necesitó. No era suficiente desconectar el nodo, porque si tenía hijos también se perdía el acceso a toda su descendencia. Para conservar el linaje se busca al hijo de mayor edad comparando las fechas de nacimiento y se pasan sus datos a la cabecera de la sublista. Luego se ajustan las ligas de los demás nodos y se recupera el orden ascendente por cédula. En la eliminación de un nivel completo, los nodos se procesan desde el último hermano hacia el primero. Esto evita que un cambio de enlace deje nodos sin visitar y permite que los descendientes suban al nivel anterior.

El traslado de una rama presentó un problema parecido. Primero se debe encontrar el nodo anterior a la persona que se va a mover para poder desconectarla de su lista actual. La referencia a su sublista no se modifica, por lo que sus descendientes se trasladan con ella. Después se prepara al nuevo padre: si era una hoja, se convierte en sublista; si ya tenía hijos, se utiliza la cabecera existente. Finalmente, la rama se enlaza en la nueva lista de hermanos. También se valida que el nuevo padre no sea descendiente de la persona trasladada, ya que eso produciría un ciclo y la estructura dejaría de ser un árbol.

Al actualizar una cédula apareció otro detalle con los enlaces. Como los hijos se mantienen ordenados, cambiar la cédula puede dejar el nodo en una posición incorrecta. La estrategia utilizada fue retirarlo de la lista y volverlo a insertar en la posición correspondiente, sin modificar su posible sublista de descendientes.

Para encontrar el ancestro común más cercano se revisa si las dos personas pertenecen a una misma rama. Mientras ambas sigan dentro de la misma sublista, la búsqueda continúa bajando. Cuando ya no se encuentran juntas en una rama más específica, la última cabecera compartida corresponde al ancestro común más cercano.

## Aprendizajes

Con esta práctica entendimos mejor que un árbol no depende solamente de crear nodos, sino de mantener correctamente las referencias entre ellos. Un cambio pequeño en una liga puede desconectar una persona, una lista de hermanos o una rama completa. Por eso fue importante revisar qué referencia se debía conservar antes de hacer una inserción, eliminación o traslado.

También practicamos recursividad en una estructura que no tiene un número fijo de hijos. A diferencia de una lista lineal, en cada nodo se debe decidir si se continúa con el siguiente hermano o si se baja a una sublista. Al principio este recorrido no era tan fácil de seguir, pero separar los métodos de búsqueda ayudó a entender mejor cada caso.

El uso de pilas permitió comparar los recorridos recursivos con una solución iterativa. Guardar temporalmente los hermanos pendientes hizo posible recorrer el árbol en profundidad y, al mismo tiempo, calcular datos como la altura o el nodo con mayor grado.

Finalmente, la práctica ayudó a relacionar conceptos vistos por separado durante la carrera: listas enlazadas, recursividad, pilas y árboles. Lo más importante fue comprender que las operaciones deben conservar la estructura completa, no solamente producir el resultado visible. En este proyecto, verificar los enlaces después de cada cambio fue tan importante como encontrar la persona buscada.
