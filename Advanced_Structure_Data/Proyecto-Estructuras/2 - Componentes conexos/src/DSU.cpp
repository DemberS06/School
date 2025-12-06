#include "DSU.h"
#include <bits/stdc++.h>

using namespace std;

void DSU(vector<vector<int>>& component, vector<int>& id, int& X, int& Y){
    int x=id[X], y=id[Y];
    if(x==y)return;

    if(x>y)swap(x, y);
    if(component[x].size()<component[y].size())swap(component[x], component[y]);

    for(auto& u:component[y]){
        id[u]=x;
        component[x].push_back(u);
    }component[y].clear();

    return;
}

int getComponents(Graph& g, vector<vector<int>>& component){
    vector<int> id(g.n);
    for(int i=0; i<g.n;i++)id[i]=i, component[i].push_back(i);
    
    for(int i=0; i<g.n; i++){
        for(auto& e:g.adj[i]){
            DSU(component, id, e.to, i);
        }
    }

    int res=0;
    for(auto& u:component)res+=(!u.empty());

    return res;
}

void startDSU(Graph& g){
    vector<vector<int>> component(g.n);

    cout<<"El Grafo tiene "<<getComponents(g, component)<<" Componentes.\n";
    cout<<"Las componentes son: ";

    for(auto& v:component){
        if(v.empty())continue;
        cout<<"{";
        for(auto& u:v)cout<<u<<((u==v.back())?"}.\n":", ");
    }

    return;
}
