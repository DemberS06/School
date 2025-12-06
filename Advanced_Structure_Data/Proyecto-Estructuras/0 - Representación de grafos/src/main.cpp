#include <bits/stdc++.h>
#include "Graph.h"
#include "Representaciones.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer grafo",
        "Mostrar lista de adyacencia",
        "Mostrar matriz de adyacencia",
        "Mostrar matriz de incidencia"
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
            case 2: imprimirlistadeadj(g); break;
            case 3: imprimirmatrizdeadj(g); break;
            case 4: imprimirmatrizdeincidencia(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}