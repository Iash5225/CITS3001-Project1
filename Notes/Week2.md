# Optimisation

## Optimisation Algorithms

- Greedy Algorithms build up a solution bit-by-bit
  - At each step they make the locally-optimal choice for the next "bit"
- Dynamic Programming:
  - Define recursive rules for solving the problem, then optimise the algorithm by eliminating repeated work
- Appriximation algorithm focus on returning good solutions, but not necessarily the best one
  - Often they can oeprate very quickly
- Gradient-based search algorithms take known solutions and try to improve them
  - While they can be slow, they can work well in difficult problem domains

## Matroids

- Matroid - The set of problems that have been identified by a theorem , for which greedy algorithms work.

## P vs NP

- A Computational problem (x) is in the class P if there is a deterministic algorithm that solves (x) and that runs in polynomial time
  - These are polynomial-time problems, often called feasible or tractable
  
- A Computational problem (x) is in the class NP if there is a non-deterministic algorithm that solves (x) and that runs in polynomial time
  - These are non-determininstic polynomial-time problems, often called infeasible or intractable
  - But these algorithms require lucky guesses to work efficiently

## Approximation Algorithms

- An approximation algorithm is an algorithm that produces to NP-hard problems
  - But with no guarantee that the solutions are optimal

## Nearest Neighbour

### Greedy Algorithm

- Start at a randomly-chosen city
- Always visit next the closest unvisited city

#### Pseudocode 

1. Calculate “d(x, xi)” i =1, 2, ….., n; where d denotes the Euclidean distance between the points.
2. Arrange the calculated n Euclidean distances in non-decreasing order.
3. Let k be a +ve integer, take the first k distances from this sorted list.
4. Find those k-points corresponding to these k-distances.
5. Let ki denotes the number of points belonging to the ith class among k points i.e. k ≥ 0
6. If ki >kj ∀ i ≠ j then put x in class i.

#### Time Complexity

- Complexity: O(n^2)
- If trying all possible starting cities : O(n^3)

### Minimum Spanning Trees for TSP

#### Pseudocode 

- Find a minimum spanning tree for I
  - Using Prims Algorithm:
    - Creating a set mstSet that keeps track of vertices already included in MST.
    - Assigning a key value to all vertices in the input graph. Initialize all key values as INFINITE. Assign key value as 0 for the first vertex so that it is picked first.
    - [The Loop] While mstSet doesn’t include all vertices
    - Pick a vertex u which is not there in mstSet and has minimum key value.(minimum_key())
    - Include u to mstSet.
    - Update key value of all adjacent vertices of u. To update the key values, iterate through all adjacent vertices. 
    - For every adjacent vertex v, if weight of edge u-v is less than the previous key value of v, update the key value as weight of u-v.
- Do a Depth-first search on the tree
  - Push the starting_vertex to the final_ans vector.
  - Checking up the visited node status for the same node.
  - Iterating over the adjacency matrix (depth finding) and adding all the child nodes to the final_ans.
  - Calling recursion to repeat the same.
- Visit the vertices in order of discovery time

#### Time Complexity

- The time complexity for obtaining MST from the given graph is O(V^2) where V is the number of nodes.
- The worst case space complexity for the same is O(V^2)
- The time complexity for obtaining the DFS of the given graph is O(V+E) where V is the number of nodes and E is the number of edges.
- Hence the overall time complexity is O(V^2) and the worst case space somplexity of this algorithm is O(V^2).
- Complexity: 2x the optimal length


### Insertion Algorithms for the TSP

- Maintain a cycle through a subset of vertices, and insert new vertices into this cycle
- At each stage apply an insertion method that inserts 1 vertex into a closed tour C
- Then (u,v) is deleted, and (u,x) and (x,v) are added, creating a tour with 1 extra city
- 3 Common insertion methods:
  - Nearest insertion: choose the x closest C
  - Farthest insertion: chose the x furthest from C
  - Random insertion: choose x randomly

#### Time Complexity

- All 3 insertion methods: O(n^2)

### Iterative Improvement

- Requires
  - A rule for changing 1 feasible solution to another
  - A schedule for deciding which changes to make
- General Idea:
  - Create a feasible solution (quickly)
  - Modify it slightly (and repeatedly) to improve it


## K-Opt

- k-optimal : A tour that can't be improved by a k-edge exchange 

## A State space graph

- A huge graph:
  - With each vertex comprising of all feasible tours for I
  - 2 vertices are connected if they can be obtained by moving across an edge (legal move)


## Gradient Based Search

### Pseudocode

- Systematiclly generate neighbours T' of the current tour T and move to the first one with a lower cost than T
- The process termninates when it is at T that has no neighbours of lower cost
  - At this point T is 2-optimal
- Choosing the best move at each step = Greedy iterative improvement

## Local Optima

 - Local Minimum or Local Optimum of the state sppace = Finishing at a vertex which has a lwoer cost than its neighbours
 - Only 1 local optima = global optima
 - 3 Methods of escaping local optima
   - Simualted anneling
   - Tabu Search
   - Genetic Alogorithms

### Simulated Annealing

- Tries to avoid local optima by allowing the search process to take "backward" moves
- Each iteration takes the form
  - Randomly generate a neighbour T' of the current T
  - if c(T') <= c(T), accept T'
  - if c(T') > c(T), accept T' with probability p
  - Reduce p as time goes on (Make backware moves less likely to happen)
  - Highly problem specific

### Tabu Search

- Maintain a "tabu list" detailing the last (h) vertices that have been visited, and at each iteration
  - Select best possible neighbour T' of T
  - if T' is not on the tabu list, move to T', and update the list
- Very aggressive + Expensive
- Highly problem specific

### Genetic Algorithms

- Maintain a population of solutions to avoid getting stuck in a local optima
- Expected to be distributed across search space
- At each iteration (generation) population of size (n) is used to create (n) new solutions
- Best (n) of these "survives" into the next generation
- Extremely successful with fine tuning