#pragma once
#include "Graph.h"

void imprimirlistadeadj(Graph& g);

void imprimirmatrizdeadj(Graph& g);

void imprimirmatrizdeincidencia(Graph& g);

/*
Cuando trabajamos con grafos, lo primero que necesitamos decidir es cómo vamos a 
guardar sus conexiones. Al final, un grafo no es más que un conjunto de nodos y 
las relaciones entre ellos, pero la manera en que lo representemos cambia la forma 
en que lo usamos, la memoria que gasta y la velocidad a la que podemos trabajar 
con él. Por eso existen varias representaciones, cada una con su personalidad y 
propósito.

La lista de adyacencias es como tener, para cada nodo, una pequeña libreta donde 
anotas a qué otros nodos está conectado. Es una forma muy natural de ver el grafo: 
cada vértice tiene su lista propia, y en ella aparecen solo los vecinos verdaderos. 
Si un nodo no tiene muchas conexiones, su lista es corta; si es muy sociable, su 
lista es larga. Esta representación es ligera, flexible y eficiente para recorrer el 
grafo, ideal cuando las conexiones no son demasiadas.

Por otro lado, la matriz de adyacencias es más rígida pero también más inmediata. 
Imagínate una tabla cuadrada, donde las filas y columnas representan los nodos del 
grafo. En cada casilla, simplemente anotas si existe o no una conexión entre esos 
dos nodos. Es una visión muy clara, casi geométrica, del grafo entero. Pero pagarás 
el precio: esa tabla siempre ocupa un espacio enorme, incluso si el grafo tiene muy 
pocas aristas. Sin embargo, cuando necesitas comprobar rápidamente si dos nodos 
están conectados, la matriz te responde al instante.

Finalmente, la matriz de incidencia mira al grafo desde una perspectiva diferente: 
no se concentra solo en los nodos, sino en la relación que tienen con las aristas. 
Aquí la tabla no es cuadrada; las filas representan nodos y las columnas representan 
aristas. Cada columna dice exactamente qué nodos toca: en un grafo no dirigido, la 
columna de una arista marca con un “1” a los dos nodos involucrados; en uno dirigido, 
indica qué nodo es origen y cuál es destino. Esta representación es especialmente 
útil cuando las aristas son protagonistas, como en problemas de flujos, ciclos o 
combinaciones de conexiones.

Cada una de estas tres formas refleja el mismo grafo, pero cada una ilumina una parte 
distinta de su estructura. La lista de adyacencias resalta la vecindad, la matriz de 
adyacencias el acceso inmediato, y la matriz de incidencia las relaciones entre nodos 
y aristas. Elegir una u otra es, al final, elegir la perspectiva desde la cual quieres 
entender el grafo.
*/