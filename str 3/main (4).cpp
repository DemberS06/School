#include <bits/stdc++.h>

using namespace std;

const long long mod=1e9+7;

string p="abcdefghijklm";

long long expbin(long long x, long long y){
    long long ans=1;
    
    while(y){
        if(y&1)ans=ans*x%mod;
        x=x*x%mod;
        y/=2;
    }
    return ans;
}

void BacktrackingC(int n, int k, int i, string& s){
    if(s.size()==k){
        cout<<s<<"\n";
        return;
    }
    
    for(; i<n; i++){
        s+=p[i];
        BacktrackingC(n, k, i+1, s);
        s.pop_back();
    }
    
    return;
}

void BacktrackingP(int n, int k, vector<int>& v, string& s){
    if(s.size()==k){
        cout<<s<<"\n";
        return;
    }
    
    for(int i=0; i<n; i++){
        if(v[i])continue;
        s+=p[i];
        v[i]++;
        BacktrackingP(n, k, v, s);
        s.pop_back();
        v[i]--;
    }
    
    return;
}

int main(){
    cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    int n, k;
    cin>>n>>k;
    
    vector<long long> f(n+1, 1), r(n+1, 1);
    for(int i=1; i<=n; i++)f[i]=f[i-1]*i%mod, r[i]=expbin(f[i], mod-2);
    
    auto nenk = [&](long long n, long long k){
        return (f[n]*r[k]%mod)*r[n-k]%mod;
    };
    auto PdK = [&](long long n, long long k){
        return f[n]*r[n-k]%mod;
    };
    
    cout<<"C: "<<nenk(n, k)<<"\nP: "<<PdK(n, k)<<"\n";
    
    if(n < 8){
        string s="";
        cout<<"\nCombi:\n";
        BacktrackingC(n, k, 0, s);
        cout<<"\nPer:\n";
        vector<int> v(n, 0);
        BacktrackingP(n, k, v, s);
    }
    
    return 0;
}