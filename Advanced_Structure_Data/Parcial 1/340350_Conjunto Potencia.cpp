// hola soy Dember :D
/*
 *Juan Pablo Salinas Muñoz - 340350
 *15-08-2025
 *CoConjunto potencia
 Cumple con:
 Conjunto potencia con la tabla binaria
*/

#include <bits/stdc++.h>

using namespace std;

const long long mod=1e9+7;

int main(){
    //cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    
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
    cout<<"Ingrese la cantindad de objetos en el conjunto:\n";
    cin>>n;
    
    k=(expbin(2, n));
    cout<<k<<"\n";
    
    for(int i=0; i<k; i++){
        for(int h=n-1; h>=0; h--){
            cout<<((i>>h)&1);
        }cout<<"\n";
    }
    
    string s="";
    for(int i=0; i<k; i++){
        cout<<i+1<<' ';
        for(int h=0; h<n; h++){
            if((i>>h)&1)s+='a'+h;
        }
        cout<<s<<"\n";
        s.clear();
    }
    return 0;
}