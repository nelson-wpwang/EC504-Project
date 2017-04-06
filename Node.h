#ifndef NODE_H
#define NODE_H

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
#include "Point.h"

using namespace std; 

// Nodes of k-d tree 
class Node { 
  friend class kdtree ; 
private: 
  Node* left;
  Node* right;
  Node* parent; 
  Point* p; 
  int split ; 

public:
  Node( Point* pnt) : p(pnt), left(0), right(0), parent(0), split(-1) { } ;
  Node& operator= (Node& n) { p = n.p; left = n.left ;  right = n.right ; parent = n.parent; } ;  
  void setLeftChild(Node* n) { left = n ; }; 
  Node* getLeftChild() { return left ; } ;
  void setRightChild(Node* n) { right = n ; }; 
  Node* getRightChild() { return right ; } ; 
  void setParent(Node* n) { parent = n ; };
  Node* getParent() { return parent ; } ; 
  void setPoint(Point* pnt) { p = pnt ; } ; 
  Point* getPoint() { return p ; } ; 
  void setSplitCoord(int split) { this->split = split ; } ; 
  int getSplitCoord() { return split ; }
}; 

class DNode  {
private : 
  Node* pNode ;   
  float dist ; 
public: 
  DNode( Node* n, float distance ) : pNode(n), dist(distance) { } ; 
  float getDist() const { return dist ; } ; 
  Node* getNode() const { return pNode ; } ; 
  bool operator< (DNode& comp_Node) {
    return (this->dist < comp_Node.dist) ; 
  };
  bool operator<= (DNode& comp_Node) {
    return (this->dist <= comp_Node.dist) ;
  };
  bool operator> (DNode& comp_Node) {
    return (this->dist > comp_Node.dist) ;  
  }; 
  bool operator>= (DNode& comp_Node) {
    return (this->dist >= comp_Node.dist) ;
  };
}; 

class LessDNode {
  int index ;
public:
  LessDNode() {}
  bool operator()(DNode& p1, DNode& p2)  {
    return (p1.getDist() < p2.getDist() ) ;
  }
};



#endif
