#include "Dijkstra.h"
#include <bits/stdc++.h>

using namespace std;

void dijkstra(Graph& g, vector<long long>& dis, vector<bool>& vis, vector<vector<int>>& tree, int& root){
    priority_queue<vector<long long>> djk;
    djk.push({0, root, 0});
    
    while(!djk.empty()){
        vector<long long> v = djk.top();
        djk.pop();
        
        auto w=v[0], x=v[1], p=v[2];
        
        
        if(vis[x])continue;
        vis[x]=1;
        dis[x]=-w;
        tree[p].push_back(x);
        
        for(auto& e:g.adj[x]){
            if(vis[e.to])continue;
            djk.push({w-e.weight, e.to, x});
        }
    }
}

void startDijkstra(Graph& g){
    int root, to;
    cout<<"Ingresa el nodo en el que inicia la busqueda (Indexado a 0): ";
    cin>>root;
    cout<<"Ingresa el nodo objetivo (Indexado a 0): ";
    cin>>to;
    
    vector<vector<int>> tree(g.n);
    vector<long long> dis(g.n, -1);
    vector<bool> vis(g.n, 0);
    
    dijkstra(g, dis, vis, tree, root);

    if(!vis[to])cout<<"No se puede alcanzar el nodo "<<to<<" desde el nodo "<<root<<".\n";
    
    for(int i=0; i<g.n; i++){
        cout<<"distancia de "<<root<<" a "<<i<<": "<<dis[i]<<"\nhijos de "<<i<<":\n";
        for(auto& u:tree[i])cout<<u<<' '; cout<<"\n";
    }
    
    return;
}

