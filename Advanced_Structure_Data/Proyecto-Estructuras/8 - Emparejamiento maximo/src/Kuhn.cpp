#include "Kuhn.h"
#include <bits/stdc++.h>

using namespace std;

bool dfs(Graph& g, vector<bool>& vis, vector<int>& match, int x){
    for(auto& u:g.adj[x]){
        if(vis[u])continue;
        
        vis[u]=1;
        if(match[u]==-1 || dfs(g, vis, match, match[u])){
            match[u]=x;
            return true;
        }
        
    }
    return false;
}

int maxMatching(Graph& g, vector<bool>& vis, vector<int>& match){
    int res=0;
    for(int u=0; u<g.n; u++){
        vis.assign(g.n, 0);
        if(dfs(g, vis, match, u))res++;
    }
    return res;
}

void startKuhn(Graph& g){
    vector<bool> vis;
    vector<int> match(g.n,-1);

    cout<<"El Grafo tiene "<<maxMatching(g, vis, match)/2<<" aristas en su emparejamiento maximo.\n";
    cout<<"Para el algoritmo de Kuhn se determino este como el pareo:\n";
    
    for(int i=0; i<g.n; i++){
        if(match[i]<i)continue;
        cout<<i<<"<->"<<match[i]<<"\n";   
    }
    
    return;
}

