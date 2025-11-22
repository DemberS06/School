// hola soy Dember :D
// 20/08/2025

#include <bits/stdc++.h>

#define ll long long 
#define pll pair<ll,ll>
#define F first
#define S second 
#define Z size()
#define pb push_back
#define bp pop_back
#define all(n) n.begin(), n.end()
#define lla(n) n.rbegin(), n.rend()
#define arr(x,y,z) x+y, x+y+z

using namespace std;

using i128 = __int128_t;

void value(ll in){cout<<((in)?"YES\n":"NO\n");}

const ll N=7e7+7, M=3e3+5, mod=998244353, md=1e9+7, inf=1e18;

ll n, m, vis[N], ans;
vector<ll> G[N];

void dfs(ll x){
    vis[x]=1;
    
    for(auto& u:G[x]){
        if(vis[u])continue;
        dfs(u);
    }
}

void solve(){
    cin>>n>>m;
    
    for(int i=1, x, y; i<m; i++){
        cin>>x>>y;
        G[x].push_back(y);
        G[y].push_back(x);
    }
    
    for(int i=1; i<=n; i++){
        if(!vis[i])dfs(i), ans++; 
    }
    
    cout<<ans;
    
    return;
}

int main(){
    cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    
    int t=1; 
    //cin>>t;
    
    while(t--)solve();

    return t+1;
}