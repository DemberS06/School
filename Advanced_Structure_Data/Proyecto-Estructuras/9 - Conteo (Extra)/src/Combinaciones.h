#pragma once
#include "Mathtools.h"

void startCombin();

/*
En esta funcion se calcula las maneras de escoger K objetos
de un conjunto de N objetos.

el valor de N en K se calcula en O(log(N)+log(K)). 
Todas las combinaciones se calculan en O(2^N).
*/