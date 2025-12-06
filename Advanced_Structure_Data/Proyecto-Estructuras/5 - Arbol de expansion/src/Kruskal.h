#pragma once
#include "Graph.h"

void startKruskal(Graph& g);

/*
El algoritmo de Kruskal nos permite encontrar el arbol de expansion minima y 
maxima. Si el grafo no es conexo nos genera el bosque de expansion.

La complejidad de Kruskal es de O(E*log(E)).
*/

