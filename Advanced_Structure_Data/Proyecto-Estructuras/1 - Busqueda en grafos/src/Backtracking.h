#pragma once
#include "Graph.h"

void startBacktracking(Graph& g);

/*
En esta funcion podemos encontrar la cantidad de caminos, el camino mas corto y el camino mas 
largo entre dos nodos.

Backtracking suele tener una complejidad de O(n!), pero en este caso tiene comlejidad O(2^V), ya
que no estamos buscando permutaciones, sino que únicamente combinaciones, al no permitir caminos
con ciclos.
*/