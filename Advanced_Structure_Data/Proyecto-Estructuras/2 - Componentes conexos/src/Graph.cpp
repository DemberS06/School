#include "Graph.h" 

void Graph::init(){ 
    cout<<"Ingrese la cantidad de nodos y de aristas: "; 
    cin>>n>>m; 
    adj.clear(); adj.resize(n); 
    Edge e; 
    for(int i=0, x; i<m; i++){ 
        cout<<"Ingresa la "<<i+1<<"-esima arista (indexado a 0): "; 
        cin>>x>>e.to; adj[x].push_back(e); 
    } 
}