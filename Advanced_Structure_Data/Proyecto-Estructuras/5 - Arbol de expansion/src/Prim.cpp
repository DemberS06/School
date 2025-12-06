#include "Prim.h"
#include <bits/stdc++.h>

using namespace std;

struct Prim{
    int n;
    long long peso=0;
    set<pair<long long, int>> djk;
    vector<int> vis;

    Prim(int _n):n(_n){
        vis.resize(n, 0);
    }

    void prim(Graph& g, int& i, int k){
        djk.insert({0, i});
        while(!djk.empty()){
            auto [w, x]=*djk.begin();
            djk.erase(djk.begin());

            if(vis[x])continue;
            vis[x]=1;
            peso+=k*w;
            for(auto& e:g.adj[x]){
                if(vis[e.to])continue;
                djk.insert({k*e.weight, e.to});
            }
        }
    }
};

void startPrim(Graph &g){
    Prim mn(g.n), mx(g.n);
    for(int i=0; i<g.n; i++){
        if(!mn.vis[i])mn.prim(g, i, 1);
        if(!mx.vis[i])mx.prim(g, i, -1);
    }

    cout<<"El peso del Arbol de Expansion Minima segun Prim es de "<<mn.peso<<"\n";
    cout<<"El peso del Arbol de Expansion Maxima segun Prim es de "<<mx.peso<<"\n";
    
    return;
}

