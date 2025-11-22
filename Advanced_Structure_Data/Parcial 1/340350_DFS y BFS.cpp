#include <bits/stdc++.h>

using namespace std;

int main(){
    int n, m;
    cin>>n>>m;

    vector<vector<int>> adj(n+1);

    for(int i=0, x, y; i<m; i++){
        cin>>x>>y;
        adj[x].push_back(y);
    }

    vector<bool> visdfs(n+1, 0), visbfs(n+1, 0);

    function<void(int)> DFS = [&](int x){
        cout<<x;
        visdfs[x]=1;
        for(auto& u:adj[x]){
            if(visdfs[u])continue;
            cout<<"->";
            DFS(u);
        }
    };

    cout<<"DFS: ";
    DFS(1);

    cout<<"\nBFS: ";
    queue<int> bfs;
    bfs.push(1);
    visbfs[1]=1;

    while(!bfs.empty()){
        auto x=bfs.front();
        bfs.pop();

        cout<<x;
        for(auto& u:adj[x]){
            if(visbfs[u])continue;
            visbfs[u]=1;
            bfs.push(u);
        }
        if(!bfs.empty())cout<<"->";
    }

    return 0;
}

/*
Input:
4 3
1 2
1 3
2 4
Output:
DFS: 1->2->4->3
BFS: 1->2->3->4
*/