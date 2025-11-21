#include <bits/stdc++.h>

using namespace std;

vector<vector<int>> adj;

void dfs(int x, vector<int>& vis){
    cout<<x;
    vis[x]=1;
    for(auto& u:adj[x]){
        if(vis[u])continue;
        cout<<"->";
        dfs(u, vis);
    }
}

void dfs(int root){
    vector<int> vis(adj.size(), 0);
    cout<<"DFS: ";
    dfs(root, vis);
}

void bfs(int root){
    vector<int> vis(adj.size(), 0);
    queue<int> bfs;
    bfs.push(root);
    vis[root]=1;

    cout<<"BFS: ";

    while(!bfs.empty()){
        auto x=bfs.front();
        bfs.pop();

        cout<<x;
        for(auto& u:adj[x]){
            if(vis[u])continue;
            vis[u]=1;
            bfs.push(u);
        }
        if(!bfs.empty())cout<<"->";
    }
}

int main(){
    int n, m, root;
    cin>>n>>m>>root;

    adj.resize(n+1);

    for(int i=0, x, y; i<m; i++){
        cin>>x>>y;
        adj[x].push_back(y);
    }

    dfs(root);
    cout<<"\n";

    bfs(root);

    return 0;
}

/*
Input:
8 9 3
1 2
3 1
1 3
2 4
6 5
5 8
1 8
4 5
Output:
DFS: 3->1->2->4->5->8
BFS: 3->1->2->8->4->5
*/