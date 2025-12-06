#pragma once
#include "Graph.h"

void startBFS(Graph& g);

/*
Cada vez que se inicia un recorrido en el grafo desde un grafo no dirigido, se encuentra
una nueva componente.

La complejidad de BFS es de O(V+E).
*/