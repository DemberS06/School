#include <bits/stdc++.h>
using namespace std;

bool bipartito(vector<vector<int>>& g) {
    int n = g.size();
    vector<int> color(n, -1);
    for (int i = 0; i < n; i++) {
        if (color[i] == -1) {
            queue<int> q;
            q.push(i);
            color[i] = 0;
            while (!q.empty()) {
                int u = q.front(); q.pop();
                for (int w : g[u]) {
                    if (color[w] == -1) {
                        color[w] = 1 - color[u];
                        q.push(w);
                    } else if (color[w] == color[u]) {
                        return false;
                    }
                }
            }
        }
    }
    return true;
}

int main() {
    int n, m;
    cin >> n >> m;
    vector<vector<int>> g(n);
    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;
        g[u].push_back(v);
        g[v].push_back(u);
    }
    cout << (bipartito(g) ? "YES" : "NO");
}