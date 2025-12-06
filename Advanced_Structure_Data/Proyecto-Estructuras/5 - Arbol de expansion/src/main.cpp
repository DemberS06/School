#include <bits/stdc++.h>
#include "Prim.h"
#include "Kruskal.h"
using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Kruskal",
        "Prim"
    };

    while(true){
        for(int i=0; i<menu.size(); i++){
            cout<<'['<<i<<']'<<menu[i]<<"\n";
        }

        int x;
        cin>>x;

        switch(x){
            case 0: return 0;
            case 1: g.init(); break;
            case 2: startKruskal(g); break;
            case 3: startPrim(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}

/*
# Grafo 1:
5 8
0 1 1
0 2 3
0 3 3
1 2 2
1 4 3
2 3 2
2 4 1
3 4 2

Kruskal:
El peso del Arbol de Expansion Minima segun Kruskal es de 6
Estos son los hijos del nodo 0:
1 1, 2 2, 3 2.
Estos son los hijos del nodo 1:
El nodo 2 es una hoja.
El nodo 3 es una hoja.
El nodo 4 es una hoja.

El peso del Arbol de Expansion Maxima segun Kruskal es de 11
Estos son los hijos del nodo 0:
2 3, 3 3.
Estos son los hijos del nodo 1:
4 3, 0 2.
El nodo 2 es una hoja.
El nodo 3 es una hoja.
El nodo 4 es una hoja.

Prim:
El peso del Arbol de Expansion Minima segun Prim es de 6
El peso del Arbol de Expansion Maxima segun Prim es de 11


# Grafo 2:
10 10
0 1 2
0 2 1
0 3 3
1 3 5
2 3 4
4 5 10
6 7 6
6 8 7
6 9 8
7 8 9

Kruskal:
El peso del Arbol de Expansion Minima segun Kruskal es de 37
Estos son los hijos del nodo 0:
2 1, 1 2, 3 3.
Estos son los hijos del nodo 1:
Estos son los hijos del nodo 2:
El nodo 3 es una hoja.
Estos son los hijos del nodo 4:
5 10.
El nodo 5 es una hoja.
Estos son los hijos del nodo 6:
7 6, 8 7, 9 8.
Estos son los hijos del nodo 7:
El nodo 8 es una hoja.
El nodo 9 es una hoja.

El peso del Arbol de Expansion Maxima segun Kruskal es de 46
Estos son los hijos del nodo 0:
1 3.
Estos son los hijos del nodo 1:
3 5.
Estos son los hijos del nodo 2:
1 4.
El nodo 3 es una hoja.
Estos son los hijos del nodo 4:
5 10.
El nodo 5 es una hoja.
Estos son los hijos del nodo 6:
9 8, 7 7.
Estos son los hijos del nodo 7:
8 9.
El nodo 8 es una hoja.
El nodo 9 es una hoja.

Prim:
El peso del Arbol de Expansion Minima segun Prim es de 37
El peso del Arbol de Expansion Maxima segun Prim es de 46

*/