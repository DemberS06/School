#pragma once
#include "Graph.h"

void startEdmonds(Graph& g);

/*
Este algoritmo nos sirve para encontrar el emparejamiento maximo en un 
grafo que no necesariamente es bipartito.

La complejidad de Edmonds es de O(V^3).
*/