#include "Karp.h"
#include <bits/stdc++.h>

using namespace std;

void bipartition(Graph& g, vector<int>& side){
    queue<int> q;

    for(int u=0; u<g.n; u++){
        if(side[u]!=-1)continue;
        side[u]=0;
        q.push(u);

        while(!q.empty()){
            auto x=q.front(); q.pop();
            for(auto& w:g.adj[x]){
                if(side[w]>=0)continue;
                side[w]=side[x]^1;
                q.push(w);
            }
        }
    }

    return;
}

bool bfs(Graph& g, vector<int>& side, vector<int>& dist, vector<int>& match){
    queue<int> q;

    for(int u=0; u<g.n; u++){
        if(!side[u] && match[u]==-1)dist[u]=0, q.push(u);
        else dist[u]=-1;
    }

    bool ok=false;

    while(!q.empty()){
        auto x=q.front(); q.pop();

        for(auto& u:g.adj[x]){
            if(!side[u])continue; 
            if(match[u]==-1)ok=true;
            else if(dist[match[u]]==-1){
                dist[match[u]]=dist[x]+1;
                q.push(match[u]);
            }
        }
    }

    return ok;
}

bool dfs(Graph& g, vector<int>& side, vector<int>& dist, vector<int>& match, int x){
    for(auto& u:g.adj[x]){
        if(side[u]!=1)continue;

        if(match[u]==-1 || (dist[match[u]]==dist[x]+1 && dfs(g, side, dist, match, match[u]))){
            match[x]=u;
            match[u]=x;
            return true;
        }
    }

    dist[x]=-1;
    return false;
}

int maxMatching(Graph& g, vector<int>& side, vector<int>& dist, vector<int>& match){
    int res=0;

    while(bfs(g, side, dist, match)){
        for(int u=0; u<g.n; u++){
            if(side[u]==0 && match[u]==-1){
                if(dfs(g, side, dist, match, u))res++;
            }
        }
    }

    return res;
}

void startKarp(Graph &g){
    vector<int> side(g.n, -1);
    vector<int> match(g.n, -1);
    vector<int> dist(g.n, -1);
    
    bipartition(g, side);

    cout<<"El Grafo tiene "<<maxMatching(g, side, dist, match)<<" aristas en su emparejamiento maximo.\n";
    cout<<"Para el algoritmo de Karp se determino este como el pareo:\n";
    
    for(int i=0; i<g.n; i++){
        if(match[i]<i)continue;
        cout<<i<<"<->"<<match[i]<<"\n";   
    }
    
    return;
}

