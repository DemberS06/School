#include "M3.h"
#include <bits/stdc++.h>

using namespace std;

int Ind(Graph& g){
    int ok=2;
    vector<int> pat(g.n, 0);
    vector<vector<int>> cmp(g.n);
    for(int i=0; i<g.n; i++)pat[i]=i, cmp[i].push_back(i);
    
    function<void(int, int)> DSU = [&](int X, int Y){
        int x=pat[X], y=pat[Y];
        if(x==y){
            ok=0;
            return;
        }
        if(cmp[x].size()<cmp[y].size())swap(x,y);
        
        for(auto& u:cmp[y]){
            cmp[x].push_back(u);
            pat[u]=x;
        }
        cmp[y].clear();
    };
    
    for(int i=0; ok && i<g.n; i++){
        for(auto& u:g.adj[i])if(u>=i)DSU(i, u);
    }
    
    for(auto& v:cmp){
        if(v.size()!=0)ok--;
    }
    
    return ok==1;
}

void startInd(Graph& g){
    if(Ind(g))cout<<"YES\n";
    else cout<<"NO\n";
    return;
}

