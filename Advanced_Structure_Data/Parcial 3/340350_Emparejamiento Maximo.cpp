#include <bits/stdc++.h>
using namespace std;

int n, m;
vector<vector<int>> g;
vector<int> match, vis;

bool dfs(int u) {
    for (int v : g[u]) {
        if (!vis[v]) {
            vis[v] = 1;
            if (match[v] == -1 || dfs(match[v])) {
                match[v] = u;
                return true;
            }
        }
    }
    return false;
}

int maxMatching() {
    match.assign(n, -1);
    int res = 0;
    for (int u = 0; u < n; u++) {
        vis.assign(n, 0);
        if (dfs(u)) res++;
    }
    return res;
}

int main() {
    cin >> n >> m;
    g.assign(n, {});
    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;
        g[u].push_back(v);
    }
    cout << maxMatching() << endl;
}
