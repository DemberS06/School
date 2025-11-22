// hola soy Dember :D
#include <bits/stdc++.h>

using namespace std;

int main(){
    int n, m;
    cin>>n>>m;
    
    vector<vector<int>> G(n), ans(n);
    vector<int> p(n);
    
    for(int i=0, x, y; i<m; i++){
        cin>>x>>y;
        G[x].push_back(y);
    }
    
    for(int i=0; i<n; i++)p[i]=i, ans[i].push_back(i);
    
    function<void(int, int)> DSU = [&](int X, int Y){
        int x=p[X], y=p[Y];
        if(x==y)return;
        
        if(x>y)swap(x, y);
        if(ans[x].size()<ans[y].size())swap(ans[x], ans[y]);
        
        for(auto& u:ans[y]){
            p[u]=x;
            ans[x].push_back(u);
        }
        ans[y].clear();
    };
    
    for(int i=0; i<n; i++){
        for(auto& u:G[i])DSU(u, i);
    }
    
    cout<<"Componentes: \n";
    
    for(auto& v:ans){
        if(v.empty())continue;
        for(auto& u:v)cout<<u<<' ';
        cout<<"\n";
    }
    
    return 0;
}


/*
4 4

0 1
1 0
2 3
3 2
Componentes: 
0 1 
2 3


5 4 

0 1
0 2 
0 3 
2 3
Componentes: 
0 1 2 3 
4 
*/