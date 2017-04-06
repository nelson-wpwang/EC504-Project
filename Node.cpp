#include "Node.h"

bool operator< (DNode& dn1, DNode& dn2) {
  return dn1.getDist() < dn2.getDist() ;
}
