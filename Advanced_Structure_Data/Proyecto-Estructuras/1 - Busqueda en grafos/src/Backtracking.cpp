#include "backtracking.h"
#include <bits/stdc++.h>

using namespace std;

void backtracking(Graph& g, vector<bool>& vis, vector<int>& sh, vector<int>& ln, vector<int>& path, int& w, int& x, int& y){
    vis[x]=1;
    path.push_back(x);

    if(x==y){
        w++;
        if(sh.empty() || sh.size()>path.size())sh=path;
        if(ln.empty() || ln.size()<path.size())ln=path;
        path.pop_back();
        vis[x]=0;
        return;
    }
    
    vis[x]=1;
    for(auto& u:g.adj[x]){
        if(vis[u])continue;
        backtracking(g, vis, sh, ln, path, w, u, y);
    }

    path.pop_back();
    vis[x]=0;
    return;
}

void startBacktracking(Graph& g){
    int root, to, ways=0;
    cout<<"Ingresa el nodo en el que inicia la busqueda (Indexado a 0): ";
    cin>>root;
    cout<<"Ingresa el nodo objetivo (Indexado a 0): ";
    cin>>to;

    vector<bool> vis(g.n, 0);
    vector<int> shortpath, longpath, path;

    backtracking(g, vis, shortpath, longpath, path, ways, root, to);

    if(!ways){
        cout<<"No se puede alcanzar el nodo "<<to<<" desde el nodo "<<root<<".\n";
        return;
    }
    cout<<"Hay "<<ways<<" maneras de ir desde "<<root<<" hasta "<<to<<".\n";
    cout<<"El camino mas corto es:\n";
    for(int i=0; i<shortpath.size(); i++)cout<<shortpath[i]<<((i!=shortpath.size()-1)?"->":".\n");
    cout<<"El camino mas largo es:\n";
    for(int i=0; i<longpath.size() ; i++)cout<<longpath[i] <<((i!=longpath.size() -1)?"->":".\n");
    
    for(int i=0; i<g.n; i++){
        if(!vis[i])continue;
        cout<<i<<' ';
    }cout<<'\n';
    
    return;
}

