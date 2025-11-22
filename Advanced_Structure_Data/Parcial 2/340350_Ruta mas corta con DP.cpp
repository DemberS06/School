// hola soy Dember :D
// 20/08/2025

#include <bits/stdc++.h>
#define ll long long

using namespace std;

const ll inf = 1e18;

int main(){
    cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    
    int n, m;
    cin>>n>>m;
    
    vector<vector<vector<ll>>> dp(n, vector<vector<ll>>(n, vector<ll>(n, inf)));
    
    for(int i=0, x, y; i<m; i++){
        cin>>x>>y>>dp[1][x][y];
    }
    for(int i=0; i<n; i++)dp[0][i][i]=0;
    
    for(int k=2; k<n; k++){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                for(int h=0; h<n; h++)dp[k][i][j]=min(dp[k][i][j], dp[1][i][h]+dp[k-1][h][j]);
            }   
        }
    }
    
    vector<vector<ll>> best(n, vector<ll> (n, inf));
    
    for(int i=0; i<n; i++){
        for(int j=0; j<n; j++){
            for(int k=0; k<n; k++){
                best[i][j]=min(best[i][j], dp[k][i][j]);
            }
        }
    }
    
    for(auto& v:best){
        for(auto& u:v)cout<<((u==inf)?-1:u)<<' ';
        cout<<"\n";
    }

    return 0;
}

