#include "BFS.h"
#include <bits/stdc++.h>

using namespace std;

vector<int> BFS(Graph& g, vector<bool>& vis, int& root){
    vector<int> comp;
    queue<int> q;

    q.push(root);
    vis[root]=true;

    while(!q.empty()){
        auto x=q.front(); q.pop();

        comp.push_back(x);
        for(auto& e:g.adj[x]){
            if(vis[e.to])continue;
            vis[e.to]=true;
            q.push(e.to);
        }
    }

    return comp;
}

int GetComponents(Graph& g, vector<vector<int>>& component){
    vector<bool> vis(g.n, 0);
    
    for(int i=0; i<g.n; i++){
        if(vis[i])continue;
        component.push_back(BFS(g, vis, i));
    }
    
    return component.size();
}

void startBFS(Graph& g) {
    vector<vector<int>> component(g.n);

    cout<<"El Grafo tiene "<<GetComponents(g, component)<<" Componentes.\n";
    cout<<"Las componentes son: ";

    for(auto& v:component){
        if(v.empty())continue;
        cout<<"{";
        for(auto& u:v)cout<<u<<((u==v.back())?"}.\n":", ");
    }
}
