Discretion
==========

A proof helper for discrete math

## Partial Credit
One goal for this project is automated proof solving. Discretion can flesh out the broad structure of a potential solution, aiming for as much "partial credit" for the problem as possible.

```
Suppose A ⊆ U, B ⊆ U, and C ⊆ U.
Further suppose x ∈ A ∪ (B ∪ C).
  ???
So x ∈ (A ∪ B) ∪ C.
A ∪ (B ∪ C) ⊆ (A ∪ B) ∪ C by the definition of subset.
Now suppose x ∈ (A ∪ B) ∪ C.
  ???
So x ∈ A ∪ (B ∪ C).
(A ∪ B) ∪ C ⊆ A ∪ (B ∪ C) by the definition of subset.
Therefore A ∪ (B ∪ C) = (A ∪ B) ∪ C.
```
The partial proof is constructed with knowledge that a set equality proof consists of two subset proofs. Each these subset proofs is structured according to the definition of subset. In this example the Solver has stopped short without the insight to complete the subset proofs in detail.

## Pretty Printing
Displays a proof in a logical and nested manner like this:

```
Suppose X ⊆ Y and Y ⊆ Z.
Further suppose x ∈ X.
  x ∈ Y
So x ∈ Z.
Therefore X ⊆ Z.
```
Or a more complicated example:
```
Suppose A ⊆ U, B ⊆ U, and C ⊆ U.
Further suppose x ∈ A ∪ (B ∪ C).
  x ∈ A ∨ x ∈ B ∪ C
  x ∈ A ∨ (x ∈ B ∨ x ∈ C)
  (x ∈ A ∨ x ∈ B) ∨ x ∈ C
  x ∈ A ∪ B ∨ x ∈ C
  x ∈ (A ∪ B) ∪ C
So A ∪ (B ∪ C) ⊆ (A ∪ B) ∪ C.
Now suppose x ∈ (A ∪ B) ∪ C.
  x ∈ A ∪ B ∨ x ∈ C
  (x ∈ A ∨ x ∈ B) ∨ x ∈ C
  x ∈ A ∨ (x ∈ B ∨ x ∈ C)
  x ∈ A ∨ x ∈ B ∪ C
  x ∈ A ∪ (B ∪ C)
So (A ∪ B) ∪ C ⊆ A ∪ (B ∪ C).
Therefore A ∪ (B ∪ C) = (A ∪ B) ∪ C.
```

Sub-proofs introduce a level of indentation, and conslusory words like "so" and "therefore" increase readability.
