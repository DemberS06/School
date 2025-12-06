#include "Edmonds.h"
#include <bits/stdc++.h>

using namespace std;



int lca(vector<int>& base, vector<int>& match, vector<int>& p, int a, int b){
    vector<bool> used_path(base.size(), false);
    while(true){
        a=base[a];
        used_path[a]=true;
        if(match[a]==-1)break;
        a=p[match[a]];
    }
    while(true){
        b=base[b];
        if(used_path[b])return b;
        b=p[match[b]];
    }
}

void markPath(vector<bool>& blossom, vector<int>& base, vector<int>& match, vector<int>& p, int v, int& b, int children){
    while(base[v]!=b){
        blossom[base[v]]=blossom[base[match[v]]]=true;
        p[v]=children;
        children=match[v];
        v=p[match[v]];
    }
}

int findPath(Graph& g, vector<bool>& blossom, vector<int>& match, int& root){
    vector<bool> used(g.n, false);
    vector<int> p(g.n, -1), base(g.n, 0);
    int n=g.adj.size();
    for(int i=0; i<n; i++)base[i]=i;

    queue<int> q;
    q.push(root);
    used[root]=true;

    while(!q.empty()){
        auto x=q.front(); q.pop();

        for(int u:g.adj[x]){
            if(base[x]==base[u] || match[x]==u)continue;

            if(u==root || (match[u]!=-1 && p[match[u]]!=-1)){
                int b=lca(base, match, p, x, u);
                blossom.assign(n, false);
                markPath(blossom, base, match, p, x, b, u);
                markPath(blossom, base, match, p, u, b, x);

                for(int i=0;i<n;i++){
                    if(!blossom[base[i]])continue;
                    base[i]=b;
                    if(!used[i])used[i]=true, q.push(i);
                }
            }
            else if(p[u]==-1){
                p[u]=x;
                if(match[u]==-1){
                    x=u;
                    while(x!=-1){
                        int pv = p[x];
                        int nv = (pv == -1 ? -1 : match[pv]);
                        match[x] = pv;
                        if(pv != -1) match[pv] = x;
                        x = nv;
                    }
                    return 1;
                }
                u = match[u];
                used[u] = true;
                q.push(u);
            }
        }
    }
    return 0;
}

int maxMatching(Graph &g, vector<int>& match){
    vector<bool> blossom(g.n, false);
    int res=0;

    for(int i=0;i<g.n;i++){
        if(match[i]==-1){
            if(findPath(g, blossom, match, i))res++;
        }
    }

    return res;
}

void startEdmonds(Graph& g){
    vector<int> match(g.n, -1);
    
    cout<<"El Grafo tiene "<<maxMatching(g, match)<<" aristas en su emparejamiento maximo.\n";
    cout<<"Para el algoritmo de Edmonds se determino este como el pareo:\n";
    
    for(int i=0; i<g.n; i++){
        if(match[i]<i)continue;
        cout<<i<<"<->"<<match[i]<<"\n";   
    }
    
    return;
}

