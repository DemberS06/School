#include <bits/stdc++.h>
#include "Dijkstra.h"
#include "Floyd.h"
#include "DP.h"
#include "Expbin.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Dijkstra",
        "Floyd",
        "Programacion Dinamica",
        "Exponenciacion Binaria de Matrices"
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
            case 2: startDijkstra(g); break;
            case 3: startFloyd(g); break;
            case 4: startDP(g); break;
            case 5: startExpBin(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}
/*
# Grafo 1:
15 16 
0 1 1
1 2 1 
2 3 1
3 4 1
4 5 1
5 6 1
6 7 1
7 8 1
8 9 1
0 9 11
0 10 2
0 11 3
10 12 2 
11 13 1
12 14 2
13 14 1

Dijkstra: 0 14
hijos de 0:
0 1 10 11
distancia de 0 a 1: 1
hijos de 1:
2
distancia de 0 a 2: 2
hijos de 2:
3
distancia de 0 a 3: 3
hijos de 3:
4
distancia de 0 a 4: 4
hijos de 4:
5
distancia de 0 a 5: 5
hijos de 5:
6
distancia de 0 a 6: 6
hijos de 6:
7
distancia de 0 a 7: 7
hijos de 7:
8
distancia de 0 a 8: 8
hijos de 8:
9
distancia de 0 a 9: 9
hijos de 9:

distancia de 0 a 10: 2
hijos de 10:
12
distancia de 0 a 11: 3
hijos de 11:
13
distancia de 0 a 12: 4
hijos de 12:

distancia de 0 a 13: 4
hijos de 13:
14
distancia de 0 a 14: 5
hijos de 14:

# Grafo 2:
5 6 
0 1 1
1 2 1 
2 0 1
0 3 1
3 4 1
1 4 1

Floyd:
El camino mas corto desde 0 a 0 tiene peso 0
El camino mas corto desde 0 a 1 tiene peso 1
El camino mas corto desde 0 a 2 tiene peso 2
El camino mas corto desde 0 a 3 tiene peso 1
El camino mas corto desde 0 a 4 tiene peso 2
El camino mas corto desde 1 a 0 tiene peso 2
El camino mas corto desde 1 a 1 tiene peso 0
El camino mas corto desde 1 a 2 tiene peso 1
El camino mas corto desde 1 a 3 tiene peso 3
El camino mas corto desde 1 a 4 tiene peso 1
El camino mas corto desde 2 a 0 tiene peso 1
El camino mas corto desde 2 a 1 tiene peso 2
El camino mas corto desde 2 a 2 tiene peso 0
El camino mas corto desde 2 a 3 tiene peso 2
El camino mas corto desde 2 a 4 tiene peso 3
No hay camino de 3 a 0
No hay camino de 3 a 1
No hay camino de 3 a 2
El camino mas corto desde 3 a 3 tiene peso 0
El camino mas corto desde 3 a 4 tiene peso 1
No hay camino de 4 a 0
No hay camino de 4 a 1
No hay camino de 4 a 2
No hay camino de 4 a 3
El camino mas corto desde 4 a 4 tiene peso 0

DP: 
El camino mas corto desde 0 a 0 pasando por 0 aristas tiene peso 0
No hay camino de 0 a 0 con 1aristas
No hay camino de 0 a 0 con 2aristas
El camino mas corto desde 0 a 0 pasando por 3 aristas tiene peso 3
No hay camino de 0 a 0 con 4aristas
No hay camino de 0 a 1 con 0aristas
El camino mas corto desde 0 a 1 pasando por 1 aristas tiene peso 1
No hay camino de 0 a 1 con 2aristas
No hay camino de 0 a 1 con 3aristas
El camino mas corto desde 0 a 1 pasando por 4 aristas tiene peso 4
No hay camino de 0 a 2 con 0aristas
No hay camino de 0 a 2 con 1aristas
El camino mas corto desde 0 a 2 pasando por 2 aristas tiene peso 2
No hay camino de 0 a 2 con 3aristas
No hay camino de 0 a 2 con 4aristas
No hay camino de 0 a 3 con 0aristas
El camino mas corto desde 0 a 3 pasando por 1 aristas tiene peso 1
No hay camino de 0 a 3 con 2aristas
No hay camino de 0 a 3 con 3aristas
El camino mas corto desde 0 a 3 pasando por 4 aristas tiene peso 4
No hay camino de 0 a 4 con 0aristas
No hay camino de 0 a 4 con 1aristas
El camino mas corto desde 0 a 4 pasando por 2 aristas tiene peso 2
No hay camino de 0 a 4 con 3aristas
No hay camino de 0 a 4 con 4aristas
No hay camino de 1 a 0 con 0aristas
No hay camino de 1 a 0 con 1aristas
El camino mas corto desde 1 a 0 pasando por 2 aristas tiene peso 2
No hay camino de 1 a 0 con 3aristas
No hay camino de 1 a 0 con 4aristas
El camino mas corto desde 1 a 1 pasando por 0 aristas tiene peso 0
No hay camino de 1 a 1 con 1aristas
No hay camino de 1 a 1 con 2aristas
El camino mas corto desde 1 a 1 pasando por 3 aristas tiene peso 3
No hay camino de 1 a 1 con 4aristas
No hay camino de 1 a 2 con 0aristas
El camino mas corto desde 1 a 2 pasando por 1 aristas tiene peso 1
No hay camino de 1 a 2 con 2aristas
No hay camino de 1 a 2 con 3aristas
El camino mas corto desde 1 a 2 pasando por 4 aristas tiene peso 4
No hay camino de 1 a 3 con 0aristas
No hay camino de 1 a 3 con 1aristas
No hay camino de 1 a 3 con 2aristas
El camino mas corto desde 1 a 3 pasando por 3 aristas tiene peso 3
No hay camino de 1 a 3 con 4aristas
No hay camino de 1 a 4 con 0aristas
El camino mas corto desde 1 a 4 pasando por 1 aristas tiene peso 1
No hay camino de 1 a 4 con 2aristas
No hay camino de 1 a 4 con 3aristas
El camino mas corto desde 1 a 4 pasando por 4 aristas tiene peso 4
No hay camino de 2 a 0 con 0aristas
El camino mas corto desde 2 a 0 pasando por 1 aristas tiene peso 1
No hay camino de 2 a 0 con 2aristas
No hay camino de 2 a 0 con 3aristas
El camino mas corto desde 2 a 0 pasando por 4 aristas tiene peso 4
No hay camino de 2 a 1 con 0aristas
No hay camino de 2 a 1 con 1aristas
El camino mas corto desde 2 a 1 pasando por 2 aristas tiene peso 2
No hay camino de 2 a 1 con 3aristas
No hay camino de 2 a 1 con 4aristas
El camino mas corto desde 2 a 2 pasando por 0 aristas tiene peso 0
No hay camino de 2 a 2 con 1aristas
No hay camino de 2 a 2 con 2aristas
El camino mas corto desde 2 a 2 pasando por 3 aristas tiene peso 3
No hay camino de 2 a 2 con 4aristas
No hay camino de 2 a 3 con 0aristas
No hay camino de 2 a 3 con 1aristas
El camino mas corto desde 2 a 3 pasando por 2 aristas tiene peso 2
No hay camino de 2 a 3 con 3aristas
No hay camino de 2 a 3 con 4aristas
No hay camino de 2 a 4 con 0aristas
No hay camino de 2 a 4 con 1aristas
No hay camino de 2 a 4 con 2aristas
El camino mas corto desde 2 a 4 pasando por 3 aristas tiene peso 3
No hay camino de 2 a 4 con 4aristas
No hay camino de 3 a 0 con 0aristas
No hay camino de 3 a 0 con 1aristas
No hay camino de 3 a 0 con 2aristas
No hay camino de 3 a 0 con 3aristas
No hay camino de 3 a 0 con 4aristas
No hay camino de 3 a 1 con 0aristas
No hay camino de 3 a 1 con 1aristas
No hay camino de 3 a 1 con 2aristas
No hay camino de 3 a 1 con 3aristas
No hay camino de 3 a 1 con 4aristas
No hay camino de 3 a 2 con 0aristas
No hay camino de 3 a 2 con 1aristas
No hay camino de 3 a 2 con 2aristas
No hay camino de 3 a 2 con 3aristas
No hay camino de 3 a 2 con 4aristas
El camino mas corto desde 3 a 3 pasando por 0 aristas tiene peso 0
No hay camino de 3 a 3 con 1aristas
No hay camino de 3 a 3 con 2aristas
No hay camino de 3 a 3 con 3aristas
No hay camino de 3 a 3 con 4aristas
No hay camino de 3 a 4 con 0aristas
El camino mas corto desde 3 a 4 pasando por 1 aristas tiene peso 1
No hay camino de 3 a 4 con 2aristas
No hay camino de 3 a 4 con 3aristas
No hay camino de 3 a 4 con 4aristas
No hay camino de 4 a 0 con 0aristas
No hay camino de 4 a 0 con 1aristas
No hay camino de 4 a 0 con 2aristas
No hay camino de 4 a 0 con 3aristas
No hay camino de 4 a 0 con 4aristas
No hay camino de 4 a 1 con 0aristas
No hay camino de 4 a 1 con 1aristas
No hay camino de 4 a 1 con 2aristas
No hay camino de 4 a 1 con 3aristas
No hay camino de 4 a 1 con 4aristas
No hay camino de 4 a 2 con 0aristas
No hay camino de 4 a 2 con 1aristas
No hay camino de 4 a 2 con 2aristas
No hay camino de 4 a 2 con 3aristas
No hay camino de 4 a 2 con 4aristas
No hay camino de 4 a 3 con 0aristas
No hay camino de 4 a 3 con 1aristas
No hay camino de 4 a 3 con 2aristas
No hay camino de 4 a 3 con 3aristas
No hay camino de 4 a 3 con 4aristas
El camino mas corto desde 4 a 4 pasando por 0 aristas tiene peso 0
No hay camino de 4 a 4 con 1aristas
No hay camino de 4 a 4 con 2aristas
No hay camino de 4 a 4 con 3aristas
No hay camino de 4 a 4 con 4aristas

ExpBin: 10
No hay camino de 0 a 0 con 10aristas
El camino mas corto desde 0 a 1 pasando por 10 aristas tiene peso 10
No hay camino de 0 a 2 con 10aristas
El camino mas corto desde 0 a 3 pasando por 10 aristas tiene peso 10
No hay camino de 0 a 4 con 10aristas
No hay camino de 1 a 0 con 10aristas
No hay camino de 1 a 1 con 10aristas
El camino mas corto desde 1 a 2 pasando por 10 aristas tiene peso 10
No hay camino de 1 a 3 con 10aristas
El camino mas corto desde 1 a 4 pasando por 10 aristas tiene peso 10
El camino mas corto desde 2 a 0 pasando por 10 aristas tiene peso 10
No hay camino de 2 a 1 con 10aristas
No hay camino de 2 a 2 con 10aristas
No hay camino de 2 a 3 con 10aristas
No hay camino de 2 a 4 con 10aristas
No hay camino de 3 a 0 con 10aristas
No hay camino de 3 a 1 con 10aristas
No hay camino de 3 a 2 con 10aristas
No hay camino de 3 a 3 con 10aristas
No hay camino de 3 a 4 con 10aristas
No hay camino de 4 a 0 con 10aristas
No hay camino de 4 a 1 con 10aristas
No hay camino de 4 a 2 con 10aristas
No hay camino de 4 a 3 con 10aristas
No hay camino de 4 a 4 con 10aristas
*/