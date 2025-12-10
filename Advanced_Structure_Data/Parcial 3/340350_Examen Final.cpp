#include <bits/stdc++.h>

using namespace std;

int main(){
    int n, m, k, res=0;
    cin>>n>>m>>k;

    vector<int> match(m+1, -1), vis;
    vector<vector<int>> adj(n+1);

    for(int i=0, x, y; i<k; i++){
        cin>>x>>y;
        adj[x].push_back(y);
    }

    function<bool(int)> dfs = [&](int u){
        for(auto& v:adj[u]){
            if(vis[v])continue;
            vis[v]=1;
            if(match[v]==-1 || dfs(match[v])){
                match[v]=u;
                return true;
            }
        }
        return false;
    };

    for(int u=0; u<=n; u++){
        vis.assign(m+1, 0);
        if(dfs(u))res++;
    }

    cout<<res<<"\n";

    for(int v=0; v<=n; v++){
        if(match[v]==-1)continue;
        cout<<match[v]<<' '<<v<<"\n";
    }

    return 0;
}

/*
3 3 6
1 1
1 2
2 1
2 3
3 2
3 3
*/