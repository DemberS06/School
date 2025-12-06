#include <bits/stdc++.h>
#include "M1.h"
#include "M2.h"
#include "M3.h"

using namespace std;

Graph g;

int main(){
    vector<string> menu = {
        "Salir",
        "Leer Grafo",
        "Metodo 1: Definicion",
        "Metodo 2: Ciclos",
        "Metodo 3: Inductivo"
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
            case 2: startDef(g); break;
            case 3: startCic(g); break;
            case 4: startInd(g); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}

/*
# Grafo 1:
5 4
0 1
0 2
1 3
1 4
Metodo 1: YES
Metodo 2: YES
Metodo 3: YES


# Grafo 2:
5 4
0 1
0 2
0 3
1 3
Metodo 1: NO
Metodo 2: NO
Metodo 3: NO
*/