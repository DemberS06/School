#pragma once
#include <bits/stdc++.h>

using namespace std;

struct Graph{
    int n, m;
    vector<vector<int>> adj;
    void init();
};