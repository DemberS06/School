#include <bits/stdc++.h>
#include "BFS.h"
#include "Kosaraju.h"
#include "DSU.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Kosaraju",
        "DSU",
        "BFS",
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
            case 2: startKosaraju(g); break;
            case 3: startDSU(g); break;
            case 4: startBFS(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}

/*
# Grafo 1:
3 3
0 1
1 2
2 0

Componentes fuertemente conexas (Kosaraju):
Total de SCC: 1
SCC 1: 0 2 1

DSU:
El Grafo tiene 1 Componentes.
Las componentes son: 
{0, 1, 2}.

BFS:
El Grafo tiene 1 Componentes.
Las componentes son: 
{0, 1, 2}.

# Grafo 2:
6 5
0 1
1 3
2 0
3 0
5 

Componentes fuertemente conexas (Kosaraju):
Total de SCC: 4
SCC 1: 5
SCC 2: 4
SCC 3: 2
SCC 4: 0 3 1

DSU:
El Grafo tiene 2 Componentes.
Las componentes son: 
{0, 1, 3, 2}.
{4, 5}.

BFS:
El Grafo tiene 10 Componentes.
Las componentes son: 
{0, 1, 3}.
{2}.
{4}.
{5}.


*/