#include <bits/stdc++.h>
#include "Karp.h"
#include "Kuhn.h"
#include "Edmonds.h"
#include "Micali Vazirani.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Hopcroft-Karp",
        "Kuhn",
        "Edmonds",
        "Micali Vazirani"
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
            case 2: startKarp(g); break;
            case 3: startKuhn(g); break;
            case 4: startEdmonds(g); break;
            case 5: startMicali(g); break;
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

Karp:
El Grafo tiene 3 aristas en su emparejamiento maximo.
Para el algoritmo de Karp se determino este como el pareo:
0<->3
1<->4
2<->5

Kuhn:
El Grafo tiene 3 aristas en su emparejamiento maximo.
Para el algoritmo de Kuhn se determino este como el pareo:
0<->5
1<->4
2<->3

Edmonds:
El Grafo tiene 3 aristas en su emparejamiento maximo.
Para el algoritmo de Edmonds se determino este como el pareo:
0<->3
1<->4
2<->5

Micali Vazirani:
El Grafo tiene 6 aristas en su emparejamiento maximo.
Para el algoritmo de Micali Vazirani se determino este como el pareo:
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

Karp:
El Grafo tiene 8 aristas en su emparejamiento maximo.
Para el algoritmo de Karp se determino este como el pareo:
0<->1
2<->3
4<->5
6<->7
9<->10
12<->13
14<->15
16<->17

Kuhn:
El Grafo tiene 9 aristas en su emparejamiento maximo.
Para el algoritmo de Kuhn se determino este como el pareo:
0<->5
6<->8
9<->11
12<->13
14<->15
16<->17

Edmonds:
El Grafo tiene 8 aristas en su emparejamiento maximo.
Para el algoritmo de Edmonds se determino este como el pareo:
0<->1
2<->3
4<->5
6<->7
9<->10
12<->13
14<->15
16<->17

Micali Vaziarni:
El Grafo tiene 8 aristas en su emparejamiento maximo.
Para el algoritmo de Micali Vazirani se determino este como el pareo:
0<->1
2<->3
4<->5
6<->7
9<->10
12<->13
14<->15
16<->17
*/