// hola soy Dember :D
#include <bits/stdc++.h>

using namespace std;

int main(){
    vector<vector<int>> ans, G={{0, 0, 0, 1, 0, 0},
                                {0, 0, 0, 0, 1, 0},
                                {0, 0, 0, 0, 0, 1},
                                {1, 0, 0, 0, 0, 0},
                                {0, 1, 0, 0, 0, 0},
                                {0, 0, 1, 0, 0, 0}
    };
    
    int n=G.size();
    for(int i=0; i<n; i++)G[i][i]=1;
    
    for(int i=0; i<n; i++){
        for(int k=0; k<n; k++){
            for(int j=0; j<n; j++){
                if(G[i][j]==1){
                    for(int h=0; h<n; h++)G[i][h]|=G[j][h];
                }
            }
        }
    }
    
    vector<int> R(n, 0), D(n, 0), p(n), q(n);
    
    for(int i=0; i<n; i++){
        p[i]=q[i]=i;
        for(int j=0; j<n; j++){
            R[i]+=G[i][j];
            D[j]+=G[i][j];
        }
    }
    
    sort(p.rbegin(), p.rend(), [&](int& x, int& y){
        return R[x]<R[y];
    });
    sort(q.begin(), q.end(), [&](int& x, int& y){
        return D[x]<D[y];
    });
    
    auto M=G;
    
    for(int i=0; i<n; i++){
        for(int j=0; j<n; j++){
            M[i][j]=G[p[i]][q[j]];
        }
    }
    
    for(int i=0, x=0; i<n; i=x){
        vector<int> res;
        while(x<n && M[x][i])res.push_back(p[x]), x++;
        
        ans.push_back(res);
    }
    
    for(auto& v:G){
        for(auto& u:v)cout<<u;
        cout<<"\n";
    }
    
    for(auto& u:p)cout<<u<<' ';cout<<"\n";
    for(auto& u:q)cout<<u<<' ';cout<<"\n";
    
    for(auto& v:M){
        for(auto& u:v)cout<<u;
        cout<<"\n";
    }
    
    for(auto& v:ans){
        for(auto& u:v)cout<<u<<' ';
        cout<<"\n";
    }
    
    return 0;
}


/*
    vector<vector<int>> ans, G={{0, 0, 0, 1, 1, 0},
                                {0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 1},
                                {1, 1, 1, 0, 0, 0},
                                {0, 1, 1, 0, 0, 1},
                                {0, 0, 0, 0, 1, 0}
    };//Funciona
    vector<vector<int>> ans, G={{0, 0, 0, 1, 0, 0},
                                {0, 0, 0, 0, 1, 0},
                                {0, 0, 0, 0, 0, 1},
                                {1, 0, 0, 0, 0, 0},
                                {0, 1, 0, 0, 0, 0},
                                {0, 0, 1, 0, 0, 0}
    };//NO funciona
*/