#include "BFS.h"
#include <bits/stdc++.h>

using namespace std;

void bfs(Graph& g, vector<int>& dis, int& x){
    queue<int> q;
    dis[x]=1;
    q.push(x);

    while(!q.empty()){
        auto p=q.front();
        q.pop();

        for(auto& u:g.adj[p]){
            if(dis[u])continue;
            dis[u]=dis[p]+1;
            q.push(u);
        }
    }

    for(auto& u:dis)u--;
}

void startBFS(Graph &g){
    int root, to;
    cout<<"Ingresa el nodo en el que inicia la busqueda (Indexado a 0): ";
    cin>>root;
    cout<<"Ingresa el nodo objetivo (Indexado a 0): ";
    cin>>to;

    vector<int> dis(g.n, 0);

    bfs(g, dis, root);

    if(dis[to]==-1)cout<<"No se puede alcanzar el nodo "<<to<<" desde el nodo "<<root<<".\n";
    else cout<<"La distancia entre "<<root<<" y "<<to<<" es de "<<dis[to]<<"\n";
    cout<<"La distancia de los nodos alcanzados desde "<<root<<" son:\n";
    
    for(int i=0; i<g.n; i++){
        if(dis[i]==-1)continue;
        cout<<root<<"->"<<i<<'='<<dis[i]<<"\n";
    }
    
    return;
}

