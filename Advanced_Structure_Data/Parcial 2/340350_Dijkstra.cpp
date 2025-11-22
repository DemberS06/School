/*
* Algoritmo de Camino más corto
* Dijkstra
* Minimum expaning tree desde un nodo
*/

// hola soy Dember :D
#include <bits/stdc++.h>

using namespace std;

int main(){
    int n, m;
    cin>>n>>m;
    
    vector<vector<pair<int, int>>> adj(n+1);
    vector<vector<int>> tree(n+1);
    
    for(int i=0, x, y, w; i<m; i++){
        cin>>x>>y>>w;
        adj[x].push_back({w, y});
    }
    
    int root=3;
    vector<int> dis(n+1, -1), vis(n+1, 0);
    
    priority_queue<vector<int>> djk;
    djk.push({0, root, 0});
    
    while(!djk.empty()){
        vector<int> v = djk.top();
        djk.pop();
        
        auto w=v[0], x=v[1], p=v[2];
        
        cout<<w<<' '<<x<<' '<<p<<"\n";
        
        if(vis[x])continue;
        vis[x]=1;
        dis[x]=-w;
        tree[p].push_back(x);
        
        for(auto& [W, u]:adj[x]){
            if(vis[u])continue;
            djk.push({w-W, u, x});
        }
    }
    
    for(int i=1; i<=n; i++){
        cout<<"distancia de "<<i<<": "<<dis[i]<<"\nhijos de "<<i<<":\n";
        for(auto& u:tree[i])cout<<u<<' '; cout<<"\n";
    }
    
    return 0;
}

/*
5 6 
1 2 1
1 5 1 
2 3 1
3 4 1
4 5 1
1 4 1
*/

/*
15 16 
1 2 1
2 3 1 
3 4 1
4 5 1
5 6 1
6 7 1
7 8 1
8 9 1
9 10 1
1 10 11
1 11 2
1 12 3
11 13 2 
12 14 1
13 15 2
14 15 1
*/

/*
9 18
1 2 3 
1 3 2 
2 4 4 
2 5 5 
3 2 2 
3 5 6 
3 7 8
4 6 3 
4 9 11
4 7 3 
5 7 1 
5 1 10
6 1 5 
7 6 4 
8 7 5 
8 9 4 
9 5 6 
9 6 4
*/