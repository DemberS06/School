#include "Graph.h"

void Graph::init(){
    cout<<"Ingrese la cantidad de nodos y de aristas: ";
    cin>>n>>m;

    adj.clear();
    adj.resize(n);

    int ok;
    cout<<"El grafo es ponderado? (1=si, 0=no): ";
    cin>>ok;

    for(int i=0, x; i<m; i++){
        Edge e;

        if(ok){
            cout<<"Ingresa la "<<i+1<<"-esima arista (indexado a 0): ";
            cin>>x>>e.to>>e.weight;
        }else{
            cout<<"Ingresa la "<<i+1<<"-esima arista (indexado a 0): ";
            cin>>x>>e.to;
            e.weight=1;
        }

        adj[x].push_back(e);
    }
}
