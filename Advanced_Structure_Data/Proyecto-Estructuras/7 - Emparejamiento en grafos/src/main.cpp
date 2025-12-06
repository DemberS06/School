#include <bits/stdc++.h>
#include "Greedy.h"
#include "Bitmask.h"
#include "Backtracking.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Greedy",
        "Bitmask",
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
            case 2: startGreedy(g); break;
            case 3: startBitmask(g); break;
            case 4: startBacktracking(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}

/*
# Grafo 1: (Bipartito, Maximo de 3 aristas)
6 9
0 3
0 4
0 5
1 3
1 4
1 5
2 3
2 4
2 5

Greedy:
Tamano del emparejamiento: 3
Emparejamiento Greedy encontrado:
0<->3
1<->4
2<->5

Bitmask:
El Grafo tiene 3 aristas en su emparejamiento maximo.
Para el algoritmo de Bitmask se determino este como el pareo:
0<->5
1<->4
2<->3

Backtracking:
El Grafo tiene 3 aristas en su emparejamiento maximo.
Para el algoritmo de Backtracking se determino este como el pareo:
0<->3
1<->4
2<->5

# Grafo 2: (No bipartito, Maximo de 8 aristas)
20 23
0 1
0 5
1 5
1 2
2 3
3 4
4 5
0 6
6 7
6 8
7 8
0 9
9 10
9 11
10 11
0 12
12 13
13 14
14 15
12 16
16 17
17 18
12 19

Greedy:
Tamano del emparejamiento: 8
Emparejamiento Greedy encontrado:
0<->1
2<->3
4<->5
6<->7
9<->10
12<->13
14<->15
16<->17

Bitmask:
El Grafo tiene 8 aristas en su emparejamiento maximo.
Para el algoritmo de Bitmask se determino este como el pareo:
0<->5
1<->2
3<->4
6<->7
9<->10
12<->19
13<->14
16<->17

Backtracking:
El Grafo tiene 8 aristas en su emparejamiento maximo.
Para el algoritmo de Backtracking se determino este como el pareo:
0<->1
2<->3
4<->5
6<->7
9<->10
12<->13
14<->15
16<->17
*/