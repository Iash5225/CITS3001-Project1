# Informed Search Algorithms

## Uninformed vs Informed

- Uninformed
  - Selects nodes for expansion on the basis of distance/cost from the start state
  - Uses only information contained in the graph
  - No indication of distance to go
- Informed
  - Selects nodes for expansion on the basis of some estimate of distance to the goal state
  - Requires addition information
    - heuristic rules
    - evaluation function
  - Selects "best" node i.e. most promising

## Greedy Search

- Always selects the unisited node with the smallest estimated distance to goal.
- Heuristic h(n) (evaluation function) is the estimate of the cost of getting from (n) to the goal.

## A* Search

- Uses estimate of total path-cost as heuristic:
  - f(n) = g(n)+h(n)
    - g(n) = actual cost from start to (n)
    - h(n) = estimated cost from (n) to goal
    - f(n) = estimated total cost from start to goal via (n).

## A* Optimality

- A* Search is complete + optimal under 2 conditions:
  - Heuristic must be admissible 
  - The costs along a given path must be monotonic
- A heuristic (h) is admissible: iff h(n) <= h<sup>*</sup>(n) for all (n)
  - h<sup>*</sup>(n) is the actual path-cost from (n) to the goal
  - i.e. (h) must never over-estimate the cost
- A heuristic (h) is monotonic : iff h(n) <= c(n,a,n')+h(n') for all (n,a,n')
  - n' = successor to (n) by action (a)
  - 