#include "Graph.h"

void Graph::init(){
    cout<<"Ingrese la cantidad de nodos y de aristas: ";
    cin>>n>>m;

    adj.clear();
    adj.resize(n);

    for(int i=0, x, y; i<m; i++){
        cout<<"Ingresa la "<<i+1<<"-esima arista (indexado a 0): ";
        cin>>x>>y;
        adj[x].push_back(y);
        adj[y].push_back(x);
    }
}
