#include "M2.h"
#include <bits/stdc++.h>

using namespace std;

int Cic(Graph& g){
    int cnt=0, ok=1;
    vector<int> vis(g.n, 0);
    
    function<void(int, int)> DFS = [&](int x, int y){
        if(vis[x]){
            ok=0;
            return;
        }
        cnt++;
        vis[x]=1;
        for(auto& u:g.adj[x])if(u!=y)DFS(u, x);
    };
    
    DFS(0, -1);
    
    return (cnt==g.n && ok);
}

void startCic(Graph& g){
    if(Cic(g))cout<<"YES\n";
    else cout<<"NO\n";
    return;
}

