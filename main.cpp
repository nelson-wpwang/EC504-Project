#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include <algorithm>
#include <math.h>
#include <string>
#include <sstream>
#include <queue>
#include <functional>
#include "KdTree.h"

using namespace std; 

int main() { 
  
  ifstream in("file.txt"); 
  vector<Point*> data ; 

  float val; 
  string str ; 
  while( getline(in,str) ) {
    Point* p = new Point();     
    istringstream is(str); 
    for( int i =  0 ; i < DIM ; i ++ ) {
      is >> val ; 
      (*p)[i] = val ; 
    }
    data.push_back(p); 
  }

  // construct k-d tree
  kdtree Tr ; 
  Tr.BuildTree(data); 
  Tr.traverse(Tr.getRoot()); 

  int K ; 
  cout << "Enter K " <<endl; 
  cin >> K ;
  cout << "K is " << K << endl; 

  Point pnt ; 
  while(1) { 
    cout << "Enter point " << endl; 

    for( int i = 0 ; i < DIM ; i ++ ) 
      cin >> pnt[i]  ; 
  
    vector<Node*> neighbors = Tr.get_knn(pnt,K) ; 

    cout << K << " Nearest Neighbors " << endl; 
    for( int i = 0 ; i < neighbors.size() ; i ++ ) { 
      (neighbors[i])->getPoint()->toString() ; 
      cout << endl; 
    }
  
  }

}
