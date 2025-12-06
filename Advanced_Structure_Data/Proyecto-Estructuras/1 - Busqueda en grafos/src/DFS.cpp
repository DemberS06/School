#include "DFS.h"
#include <bits/stdc++.h>

using namespace std;

void dfs(Graph& g, vector<bool>& vis, int& x){
    vis[x]=1;
    for(auto& u:g.adj[x]){
        if(vis[u])continue;
        dfs(g, vis, u);
    }
}

void startDFS(Graph& g){
    int root, to;
    cout<<"Ingresa el nodo en el que inicia la busqueda (Indexado a 0): ";
    cin>>root;
    cout<<"Ingresa el nodo objetivo (Indexado a 0): ";
    cin>>to;

    vector<bool> vis(g.n, 0);

    dfs(g, vis, root);

    if(!vis[to])cout<<"No se puede alcanzar el nodo "<<to<<" desde el nodo "<<root<<".\n";
    cout<<"Los nodos alcanzados desde "<<root<<" son:\n";
    
    for(int i=0; i<g.n; i++){
        if(!vis[i])continue;
        cout<<i<<' ';
    }cout<<'\n';
    
    return;
}

