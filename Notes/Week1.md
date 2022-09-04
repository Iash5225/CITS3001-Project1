# Algorithm Review

## Insertion sort

### Pseudocode

```
Insertion-Sort(A):
	For j <-- to length[A]
        do key <-- A[j]
            Insert A[j] into the sorted
            i = j-1
            while i>0 and A[i]>key
                do A[i+1] <-- A[i]
                    i = i-1
                A[i+1] <-- key
```

### Implementation Code

```
public void insertionSort(long[] a) {
    for (int j = 1; j < a.length; i++) {
        long key = a[j];
        int i = j - 1;
        while (i > -1 && a[i] > key) {
            a[i + 1] = a[i];
            i--;
        }
        a[i + 1] = key;
    }
```

### Explaination

The sorting algorithm aims to keep a marker of the list sorted at all times, and grow it repeatedly. The first element on its own is trivially already sorted. To add an element to the sorted marker, examine the first element after the sorted marker, and insert it into the correct position in the array. To find the correct position in the marker, liniear iteration is done over the order set , and then move every element up by one , to allow a space to be created for the element to be inserted to. After doing this, our sorted section is now one element longer. Repeat the process, for the unsorted section, until all elements are sorted.

### Time Complexity

- Shifting up elements and iterating through sorted section: O(N)
- Repeating the shift for every element: O(N)
- Overall : O(N^2)


## Mergesort

Merge sort is usually done recursively. Divide an conquer.

### Pseudocode

```
MERGESORT(a,p,r)
if p<r then
	q <- [(p+r)/2]
	MERGESORT(A,p,q)
	MERGESORT(A,q+1,r)
	MERGE(A,p,q,r)

MERGE(A,p,q,r)
n<- q-p+1
m<- r-q
for i<- 1 to n do L[i] <- A[p+i-1]
for j<- 1 to n do R[i] <- A[q+j]
i <- 1
j <- 1
k <- p
while i<=n and j<=m do
	if L[i]<=R[j] then A[k++] <-- L[i++]
	else A[k++] <- R[j++]
while i <= n do A[k++] <-- L[i++]
while j <= m do A[k++] <- R[j++]
```

### Explaination

1. Split the Array into two and recursively repeat this until each item exists in its own single element array
2. The single elements are trivially sorted.
3. Merge these single elements elements into a combined array.
4. This is done by placing a pointer over the first item in both lists.
5. The lower item is placed into the first position of the new list, and the pointer progressed.
6. Next, the values of the items at the pointers are considered and the lower is moved to the new list.
7. Next, combine two lists into one with the contents in order
8. Repeat this recursively until all lists are recombined to the original size.

### Time complexity

- Splitting the Array into two and recursively repeatings, reduced the size of the array by half every time.Leading to there being log(n) steps : O(logN)
  - As 2^x = N (N= number of items in array , x=the number of steps)
  - x= log2(N)
  - Hence number of division = log(N)
- At each level there are at most N items to merge: O(N)
- As there are O(log n) levels, overall : O(Nlog(N))


# String Algorithms

- The pattern-matching problem is to find occurences of P in T
  - Either all occurences or just the first occurence

## Naive Method

### Pseduocode

```
procedure naive(T,P):
result = {}
for s=0 to n-m //for each possible shift
    match = true
    for j = 0 to m-1 // check each item in P
    if T[s+j] != P[j]
        match = false
    if match
        result = result = {s}
```

### Analysis

- There are n-m+1 possible shifts
- In the worst case, each possible shift might fail at the last (the mth) character
- The naive string matcher is innefficient in 2 ways:
  - Shifting Process: When it checks the shift (s) it ignores whatever information it has learned which checking earlier shifts
  - Comparison Process: It compares the pattern and the text item-by-item

Worst Case Time Complexity: O(m(n-m+1)) 

## Rabin-Karl Algorithm

### Pseduocode

```
procedure rabinkarp(T,P):
p' =0
for j=0 to m-1 //turn P into a number
    p' = p' * 10 + P[j]
z=0
for j=0 to m-2 //Get the first number in T
    z = z*10 + T[j]
result = {}
for s = 0 to n-m //check each possible shift
    z= z mod 10^(m-1)*10 + T[s+m-1]
    if z==p'
        result = result + {s}
```
### Analysis

- Worst Case Time Complexity:
  -  O(m)for the pre-processing
  -  O(n-m) for the main loop
## Knuth-Morris-Pratt Algorithm

### Pseduocode

```
procedure kmp(T,P):
k = prefix-function(P)
q=0 //stores the no. of items matched
result = {}
for s =0 to n-1 // for each possible shift
    while q>0 and P[q] != T[s]
        q = k[q]
    if P[q] == T[s]
        q = q+1
    if q==m
        result = result + {s-m+1}
        q= k[q]
```

### Analysis

- Worst Case Time Complexity:
  -  O(m+n)

## Boyer-Moore Algorithm

- The characters in the pattern are checked right to left instead of left to right
### Heuristics

-A strategy that is used to guide a process or algorithm