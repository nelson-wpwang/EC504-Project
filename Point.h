#ifndef POINT_H
#define POINT_H

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
#include "global_defs.h"

using namespace std; 


// Data points

class Point {
private:
  float coord[DIM];
public:
  Point() { } ; 
  Point(float pnt[] ) { for( int i = 0 ; i < DIM ; i ++ ) coord[i] = pnt[i]; }; 
  float& operator[] (int i)  { return coord[i] ; }	; 
  const float& operator[] (int i) const  { return coord[i] ; }      ;
  Point& operator= (Point& pnt) { for( int i = 0 ; i < DIM ; i ++ ) { coord[i] = pnt.coord[i] ; } } ; 
  void toString() {  cout << "(" ; for( int i = 0 ; i < DIM ; i ++ ) { cout << coord[i] << "," ; } ;   cout << ") " ; } ; 
  static float distance( const Point& x, const Point& y ) {
    float dist = 0 ; 
    for( int i = 0 ; i < DIM ; i ++ ) 
      dist += pow(x[i]-y[i],2.0); 
    return dist ; 
  } ; 
}; 

#endif
