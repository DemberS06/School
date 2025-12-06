#pragma once
#include "Graph.h"

void startMicali(Graph& g);

/*
Este algoritmo nos sirve para encontrar el emparejamiento maximo en un 
grafo que no necesariamente es bipartito. Este es el metodo mas rapido
para esta tarea y el modelo estandar actual. 

La complejidad de Micali Vazirani es de O(E√V).
*/

