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

class sorter {
  int index ; 
public:
  sorter(int idx) : index(idx) {}
  bool operator()(Point* p1, Point* p2)  {
    return ((*p1)[index] < (*p2)[index]) ; 
  }
};


Node* kdtree::build(vector<PVec> arr, int l, int u, int coord, Node* parent) { 
  int mpos = (l+u)/2; 
  Point* median ;
  
  if( l == u ) 
    {
      Node* n = new Node(arr[0][l]);
      n->setParent(parent); 
      return n; 
    }
  else if( l > u )
    return 0 ; 
  
  while( (mpos!=l) && ( (*(arr[0][mpos-1]))[coord] == (*(arr[0][mpos]))[coord]  ) ) {  
    mpos -- ;
  }
  median = (arr[0][mpos]); 
  PVec tmp = arr[0]; 
  int indx1 = 0 ; 
  int indx2 = 0 ; 
  
  for( int i = 1 ; i < DIM ; i ++ ) {
    indx1 = 0 ; indx2 = 0 ; 
    for( int j = l ; j <= u ; j ++ ) {
      if( (arr[i][j] != median ) ) {
	if( (*arr[i][j])[coord] < (*median)[coord] ) {
	  arr[i-1][l+indx1] = arr[i][j]; 
	  indx1 ++ ; 
	} else {
	  arr[i-1][mpos+1+indx2] = arr[i][j] ; 
	  indx2 ++ ; 
	}
      } 
    }
  }
  arr[DIM-1] = tmp ;   
  
  Node* n = new Node(median); 
  if( indx1 > 0 )
    n->left = build(arr,l,l+indx1-1,(coord+1)%DIM, n) ; 
  if( indx2 > 0 )
    n->right = build(arr,mpos+1,mpos+indx2,(coord+1)%DIM, n) ; 
  n->setSplitCoord(coord) ; 
  return n ; 
}

void kdtree::BuildTree(vector<Point*> vec) {
  int len = vec.size();
  vector<PVec> arr ;  
  for( int i = 0 ; i < DIM ; i ++ ) {
    arr.push_back(vec) ; 
    sort(arr[i].begin(), arr[i].end(),sorter(i)) ;  
  }
  head = build(arr,0,len-1,0,0); 
}

void kdtree::knn_traverse(Point& x, Node* top,   priority_queue<DNode, vector<DNode>, LessDNode >* current_best, int K ) {
  if( !top )
    return ; 

  int coord = top->getSplitCoord();
  Node* child1 = 0 ; Node* child2 = 0  ; 
  if( coord >= 0 ) {
    if( x[coord] < (*(top->getPoint()) )[coord] ) {   
	child1= top->left ; 
	child2 = top->right ; 
      } else { 
	child1= top->right ; 
	child2 = top->left ; 
      }
  }

  // traverse to the Node where point would be if inserted 
  // obtain distance at leaf Node
  if(child1) {
    knn_traverse(x, child1, current_best, K ) ; 
  } else { 
    float dist = (Point::distance(x,*(top->getPoint()) ) ) ;

    if( ( current_best->size() < K ) || ( dist < (current_best->top()).getDist()  ) ) {
      current_best->push(DNode(top,dist)) ;
      if( current_best->size() > K ) { 
	current_best->pop(); 
      }
    }
  }

  // check if difference along axis of splitting coordinate is lower than best distance found
  // if so traverse to the other Node from parent 

  if( coord >= 0 && 
      ( ( fabs((float)( (*(top->getPoint()))[coord]-x[coord] )) < (current_best->top()).getDist() )  || 
	( current_best->size() < K ) ) ) { 
    if( child2 ) { 
      knn_traverse(x, child2, current_best, K ) ; 
    }
    float dist = Point::distance(x,*(top->getPoint())) ; 
    if( ( current_best->size() < K ) || ( dist < (current_best->top()).getDist() ) ) {
      current_best->push(DNode(top,dist)) ;
      if( current_best->size() > K ) {
        current_best->pop();
      }
    }

  }
  
 }

vector<Node*> kdtree::get_knn(Point& x, int K) {
  Node* current_best = 0 ; 
  //  float current_dist = 10000000 ;
  priority_queue<DNode, vector<DNode>, LessDNode > pq ;   
  knn_traverse(x, this->head, &pq, K) ; 
  vector<Node*> vNode ; 
  while(pq.size()>0) {
    vNode.push_back(pq.top().getNode()) ; 
    pq.pop(); 
  }
  return vNode ; 
}


