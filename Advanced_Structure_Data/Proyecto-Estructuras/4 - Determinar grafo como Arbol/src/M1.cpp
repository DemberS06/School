#include "M1.h"
#include <bits/stdc++.h>

using namespace std;

int Def(Graph& g){
    if(g.n!=g.m+1)return 0;
    int cnt=0;
    vector<int> vis(g.n, 0);
    
    function<void(int)> DFS = [&](int x){
        cnt++;
        vis[x]=1;
        for(auto& u:g.adj[x])if(!vis[u])DFS(u);
    };
    
    DFS(0);
    
    return cnt==g.n;
}

void startDef(Graph& g){
    if(Def(g))cout<<"YES\n";
    else cout<<"NO\n";
    return;
}

