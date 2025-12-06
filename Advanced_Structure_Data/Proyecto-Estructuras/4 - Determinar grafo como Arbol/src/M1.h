#pragma once
#include "Graph.h"

void startDef(Graph& g);

/*
Si partes un arbol en 2 partes, ambas partes son un arbol. Si unes dos arboles
con una sola arista, el resultado es un arbol. Un nodo sin aristas es el arbol
elemental, entonces si los vamos uniendo con las aristas del grafo y se cumple
que no se agrega una arista entre 2 nodos del mismo arbol, entonces el grafo
es un arbol.

Esto se verifica con DSU en O(E*log(V)).
*/