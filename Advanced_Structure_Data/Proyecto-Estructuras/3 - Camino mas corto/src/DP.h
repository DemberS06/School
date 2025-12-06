#pragma once
#include "Graph.h"

void startDP(Graph& g);

/*
En esta funcion podemos encontrar la distancia minima entre cualesquiera dos nodos en
un grafo con pesos con exactamente k aristas intermedias, para k = 1, 2, ..., V.

La complejidad de esta DP es de O(V^4).
*/