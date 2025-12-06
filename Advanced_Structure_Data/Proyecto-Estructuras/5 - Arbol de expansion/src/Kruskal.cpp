#include "Kruskal.h"
#include <bits/stdc++.h>

using namespace std;

struct Kruskal{
    int n;
    long long peso=0;
    set<vector<long long>> heap;
    vector<vector<int>> component;
    vector<int> id;

    Graph g;
    Kruskal(int _n):n(_n){
        component.resize(n);
        g.adj.resize(n);
        id.resize(n);
        for(int i=0; i<n; i++)id[i]=i, component[i].push_back(i);
    }

    void reverse(){
        peso=-peso;
        for(auto& v:g.adj)for(auto& e:v)e.weight=-e.weight;
    }

    void DSU(int x, int y){
        if(component[x].size()<component[y].size())swap(x,y);
        for(auto& u:component[y]){
            component[x].push_back(u);
            id[u]=x;
        }
        component[y].clear();
    }

    void kruskal(){
        while(!heap.empty()){
            auto v=*heap.begin();
            heap.erase(heap.begin());
            int w=v[0], x=id[v[1]], y=id[v[2]];

            if(x==y)continue;
            DSU(x, y);
            peso+=w;
            Edge e;
            e.to=y, e.weight=w;
            g.adj[x].push_back(e);
        }
    }
};

void startKruskal(Graph& g){
    Kruskal mn(g.n), mx(g.n);
    for(int i=0; i<g.n; i++){
        for(auto& e:g.adj[i]){
            mn.heap.insert({ e.weight, i, e.to});
            mx.heap.insert({-e.weight, i, e.to});
        }
    }

    mn.kruskal();
    mx.kruskal(); mx.reverse();


    cout<<"El peso del Arbol de Expansion Minima segun Kruskal es de "<<mn.peso<<"\n";
    for(int i=0; i<mn.n; i++){
        if(mx.g.adj[i].empty()){
            cout<<"El nodo "<<i<<" es una hoja.\n";
            continue;
        }
        cout<<"Estos son los hijos del nodo "<<i<<":\n";
        for(auto& e:mn.g.adj[i]){
            cout<<e.to<<" "<<e.weight;
            if(e.to==mn.g.adj[i].back().to)cout<<".\n";
            else cout<<", ";
        }
    }cout<<"\n";

    cout<<"El peso del Arbol de Expansion Maxima segun Kruskal es de "<<mx.peso<<"\n";
     for(int i=0; i<mx.n; i++){
        if(mx.g.adj[i].empty()){
            cout<<"El nodo "<<i<<" es una hoja.\n";
            continue;
        }
        cout<<"Estos son los hijos del nodo "<<i<<":\n";
        for(auto& e:mx.g.adj[i]){
            cout<<e.to<<" "<<e.weight;
            if(e.to==mx.g.adj[i].back().to)cout<<".\n";
            else cout<<", ";
        }
    }cout<<"\n";
    
    return;
}

