knn:/Users/NelsonWang/BU/kNN-with-K-d-Tree/main.o /Users/NelsonWang/BU/kNN-with-K-d-Tree/KdTree.o
	g++ -o knn /Users/NelsonWang/BU/kNN-with-K-d-Tree/main.o /Users/NelsonWang/BU/kNN-with-K-d-Tree/KdTree.o
/Users/NelsonWang/BU/kNN-with-K-d-Tree/main.o:main.cpp 
	g++ -c -o /Users/NelsonWang/BU/kNN-with-K-d-Tree/main.o main.cpp

/Users/NelsonWang/BU/kNN-with-K-d-Tree/Node.o:Node.cpp Node.h Point.h
	g++ -c -o /Users/NelsonWang/BU/kNN-with-K-d-Tree/Node.o Node.cpp Node.h Point.h

/Users/NelsonWang/BU/kNN-with-K-d-Tree/KdTree.o:KdTree.cpp KdTree.h Node.h Point.h 
	g++ -c -o /Users/NelsonWang/BU/kNN-with-K-d-Tree/KdTree.o KdTree.cpp

clean:
	rm -rf /Users/NelsonWang/BU/kNN-with-K-d-Tree/* knn *.gch *.o