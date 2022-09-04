# Uninformed Search Algorithms

## A generalised search algorithm

### Pseudocode

```
GeneralSearch(problem,strategy):
initialise the tree with the initial state of problem
while no solution found
    if there are no possible expansion left
    then return failure
    else use strategy to choose a leaf node (x) to expand
    if (x) is a goal state
        then return success
    also expand (x)
        add the new nodes to the tree
```

## Alternative Formulation

- Given :
  - A set of possible states (S)
  - A start (s<sub>o</sub>)
  - A goal function g(s) -->{true,false }
  - a terminal condition -->{true,false }

### Psuedocode

``` 
U = {s0} --unvisited nodes
V = {} --visited nodes
While U != {} -- Check if U is in visited nodes
    s= select a node from U
    if s ∈ V -- occurs check
        then discard s
    else if g(s)
        then return success
    else if t(s) -- cut off check
        then discard s
    else 
        V = V + {s}
        U = U - {s} + successors(s)

```

## Comparing Search Strategies

- Completeness: Is the strategy guaranteed to find a solution, assuming that there is one?
- Optimality: is the strategy guaranteed to find the optimal solution
- Time Complexity: how long does the strategy take to find a solution
- Space Complexity: How much memeory is needed to conduct the search

Terms:
- (b) = maximum branching factor of the tree
- (m) = maximum depth of the search space
- (d) = depth of the least cost solution
  
## Uninformed Search Strageies

### Breadth-first search

- Expand the shallowest node next
- Expand all nodes at one level before
- Space is the big problem
  
### Uniform-cost search

- Expand the lowest-cost node next
- Breadth-first that allows for varying step-costs

### Depth-first search

- Expand the deepest node next
- Follows path to terminus, then back tracks to last choice and try an alternative
- Space is advantage
- Other metrics are big disadvantages
  
### Depth-limited search

- Depth-first, but with a cut off depth
- Terminate paths at depth L
- Apply depth-first to infinite spaces
- Good if L is good

### Iterative deepening depth-first search

- Repeated depth-limited, with increasing cut offs
- Check deeper and deeper, iteratively increasing L
- For typical b- last tree layer dominates space requirements
- Worth it for space complexity
- ID allows system to adapt to resource limits
- "Anytime algorithm"

### Bidirectional search

- Search from both ends concurrently
- Usually expands many fewer nodes than unidirectional (2b<sup>d/2</sup> << b<sup>d</sup> )
- Raises other dificulties:
  - Mayb be more goal states to start from
  - Formalising backward step may be difficult
  - "backward branching factor" may be bigger than b
  - Cost of checking when converging maybe high
  
## Summary

|            Algorithm            |                     Complete                      |                       Optimal                       |                              Time                              |       Space        |
|:-------------------------------:|:-------------------------------------------------:|:---------------------------------------------------:|:--------------------------------------------------------------:|:------------------:|
|          Breadth-First          |               Yes if B is infinite                |             Yes for constant step costs             |                        O(b<sup>d</sup>)                        |  O(b<sup>d</sup>)  |
|       Uniform Cost Search       |            yes, if all step costs >= 0            |             Yes it will get a solution              | O(n), n is the number of nodes with cost less than the optimum |  O(b<sup>d</sup>)  |
|           Depth-First           |        No, fails in infinite-depth spaces         |             No,hits any solution first              |                        O(b<sup>m</sup>)                        |       O(b*m)       |
|          Depth-Limited          |                        No                         |                         No                          |                        O(b<sup>l</sup>)                        |       O(b*l)       |
| Iterative-Deepining Depth First |                        Yes                        |             Yes, for constant step-cost             |                        O(b<sup>d</sup>)                        |       O(b*d)       |
|         Bi-Directional          | Yes if b is finite and if both directions use bfs | Yes,if all step costs are identical and if both use |                       O(b<sup>d/2</sup>)                       | O(b<sup>d/2</sup>) |
