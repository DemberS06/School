#include <bits/stdc++.h>
#include "DFS.h"
#include "BFS.h"
#include "Backtracking.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "DFS",
        "BFS",
        "Backtracking"
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
            case 2: startDFS(g); break;
            case 3: startBFS(g); break;
            case 4: startBacktracking(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}

/*
# Grafo 1:
7 10
0 1
0 2
2 1
2 4
1 5
5 0
6 4
1 0
1 1
0 1

DFS: 0 3
No se puede alcanzar el nodo 3 desde el nodo 0.
Los nodos alcanzados desde 0 son:
0 1 2 4 5
BFS: 5 1
La distancia entre 5 y 1 es de 2
La distancia de los nodos alcanzados desde 5 son:
5->0=1
5->1=2
5->2=2
5->4=3
5->5=0
Backtracking: 5 1
Hay 3 maneras de ir desde 5 hasta 1.
El camino mas corto es:
5->0->1.
El camino mas largo es:
5->0->2->1.

# Grafo 2:
15 22
0 3
0 4
1 0
1 3
1 14
2 1
2 10 
2 12
3 1
3 4
3 10
4 5
5 0
8 10
9 10
10 7
10 11
10 12
11 14
12 13
13 12
14 2

DFS: 8 0
Los nodos alcanzados desde 8 son:
0 1 2 3 4 5 7 8 10 11 12 13 14

BFS: 10 6 
No se puede alcanzar el nodo 6 desde el nodo 10.
La distancia de los nodos alcanzados desde 10 son:
10->0=5
10->1=4
10->2=3
10->3=5
10->4=6
10->5=7
10->7=1
10->10=0
10->11=1
10->12=1
10->13=2
10->14=2

Backtracking: 0 13
Hay 4 maneras de ir desde 0 hasta 13.
El camino mas corto es:
0->3->10->12->13.
El camino mas largo es:
0->3->1->14->2->10->12->13.
*/