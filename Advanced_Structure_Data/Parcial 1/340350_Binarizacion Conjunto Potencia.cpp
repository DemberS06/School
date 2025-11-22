#include <bits/stdc++.h>

using namespace std;

int main(){
    //cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    int n;
    cin>>n;
    
    vector<vector<bool>> m((1<<n), vector<bool>(n));
    
    for(int i=0; i<(1<<n); i++){
        for(int h=0; h<n; h++){
            m[i][n-h-1]=(i>>h)&1;
            cout<<m[i][h];
        }cout<<"\n";
    }
    
    return 0;
}