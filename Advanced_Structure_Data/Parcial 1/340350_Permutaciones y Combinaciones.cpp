// hola soy Dember :D
/*
 *Juan Pablo Salinas Muñoz - 340350
 *13-08-2025
 *Combinaciones y Permutaciones
 Cumple con:
 Cantidad de permutaciones (Formula) 
 Cantidad de combinaciones (Formula)
 Tabla permutaciones
 Tabla combinaciones
*/

#include <bits/stdc++.h>

using namespace std;

const long long mod=1e9+7;

int main(){
    cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    
    function<long long(long long, long long)> expbin = [](long long x, long long y){
        long long ans=1;
        
        while(y){
            if(y&1)ans=ans*x%mod;
            x=x*x%mod;
            y/=2;
        }
        
        return ans;
    };
    
    long long n, k;
    cout<<"Ingrese la cantindad de objetos en el conjunto y la cantindad de objetos a escoger:\n";
    cin>>n>>k;
    
    vector<long long> fac(n+1, 1), rev(n+1, 1);

    for(int i=2; i<=n; i++)fac[i]=fac[i-1]*i%mod, rev[i]=expbin(fac[i], mod-2);
    
    function<long long(long long, long long)> nPk = [&](long long n, long long k){
        return fac[n]*rev[k]%mod;
    };
    
    function<long long(long long, long long)> nCk = [&](long long n, long long k){
        return nPk(n, k)*rev[n-k]%mod;
    };
    
    cout<<"C: "<<nCk(n, k)<<" P: "<<nPk(n, k)<<"\n";
    
    function<void(long long, long long, int, string&)> backtrackingC = [&](long long n, long long k, int i, string& s){
        if(s.size()==k){
            cout<<s<<"\n";
            return;
        }
        
        for(; i<n; i++){
            s+=('a'+i);
            backtrackingC(n, k, i+1, s);
            s.pop_back();
        }
    };
    
    
    function<void(long long, long long, vector<bool>& , string&)> backtrackingP = [&](long long n, long long k, vector<bool>& v, string& s){
        if(s.size()==k){
            cout<<s<<"\n";
            return;
        }
        
        for(int i=0; i<n; i++){
            if(v[i])continue;
            v[i]=1;
            s+=('a'+i);
            backtrackingP(n, k, v, s);
            v[i]=0;
            s.pop_back();
        }
    };
    
    if(n<=8){
        string s="";
        vector<bool> v(n, 0);
        
        cout<<"Combinaciones:\n";
        backtrackingC(n, k, 0, s);
        cout<<"Permutaciones:\n";
        backtrackingP(n, k, v, s);
    }
    
    return 0;
}