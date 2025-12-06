#include <bits/stdc++.h>
#include "Graph.h"
#include "Coloracion.h"
#include "Backtracking.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Verificar si el grafo es bipartito por coloracion",
        "Verificar si el grafo es bipartito por backtracking"
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
            case 2: startColoracion(g); break;
            case 3: startBacktracking(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}
