#pragma once
#include <bits/stdc++.h>

using namespace std;

struct Edge{
    long long weight;
    int to;
    bool operator<(const Edge& other){
        return weight<other.weight;
    }
};

struct Graph{
    int n, m;
    vector<vector<Edge>> adj;
    void init();
};