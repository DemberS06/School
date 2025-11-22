/*
* Un gráfo es Árbol
* Método 1: Definicion
* Método 2: Ciclos
* Método 3: Inductivo
*/

// hola soy Dember :D
#include <bits/stdc++.h>

using namespace std;

struct Graph{
    int n, m;
    vector<vector<int>> adj;
    
    Graph(int _n, int _m):n(_n), m(_m){
        adj.resize(n+1);
        for(int i=0, x, y; i<m; i++){
            cin>>x>>y;
            adj[x].push_back(y);
            adj[y].push_back(x);
        }
    }
};

int Def(auto g){
    if(g.n!=g.m+1)return 0;
    int cnt=0;
    vector<int> vis(g.n+1, 0);
    
    function<void(int)> DFS = [&](int x){
        cnt++;
        vis[x]=1;
        for(auto& u:g.adj[x])if(!vis[u])DFS(u);
    };
    
    DFS(1);
    
    return cnt==g.n;
}

int Cic(auto g){
    int cnt=0, ok=1;
    vector<int> vis(g.n+1, 0);
    
    function<void(int, int)> DFS = [&](int x, int y){
        if(vis[x]){
            ok=0;
            return;
        }
        cnt++;
        vis[x]=1;
        for(auto& u:g.adj[x])if(u!=y)DFS(u, x);
    };
    
    DFS(1, 0);
    
    return (cnt==g.n && ok);
}

int Ind(auto g){
    int ok=2;
    vector<int> pat(g.n+1, 0);
    vector<vector<int>> cmp(g.n+1);
    for(int i=1; i<=g.n; i++)pat[i]=i, cmp[i].push_back(i);
    
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
    
    for(int i=1; ok && i<=g.n; i++){
        for(auto& u:g.adj[i])if(u>=i)DSU(i, u);
    }
    
    for(auto& v:cmp){
        if(v.size()!=0)ok--;
    }
    
    return ok==1;
}

int main(){
    int n, m;
    cin>>n>>m;
    Graph g(n, m);
    
    auto Value = [&](int x){
        if(x)cout<<"YES\n";
        else cout<<"NO\n";
    };
    
    Value(Def(g));
    Value(Cic(g));
    Value(Ind(g));
    
    return 0;
}

