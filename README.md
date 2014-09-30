Discretion
==========

A proof helper for discrete math

## Partial Credit
One goal for this project is automated proof solving. Given a set of definitions, theorems, and suppositions, a proof could be worked out automatically. When this is not possible the program should flesh out the broad structure of a potential solution, aiming for "partial credit" for the problem.

```
Suppose A, B, and C are sets. Show that A ∪ (B ∪ C) = (A ∪ B) ∪ C.
Proof:
Further suppose x ∈ A ∪ (B ∪ C).
  ???
  x ∈ (A ∪ B) ∪ C
So A ∪ (B ∪ C) ⊆ (A ∪ B) ∪ C.
Now suppose x ∈ (A ∪ B) ∪ C.
  ???
  x ∈ A ∪ (B ∪ C)
So (A ∪ B) ∪ C ⊆ A ∪ (B ∪ C).
Therefore A ∪ (B ∪ C) = (A ∪ B) ∪ C.
```
The proof above could easily be constructed by knowing the nature of a set-equality proof, and setting up the two halfs of the proof accordingly. Each subset proof begins and ends according to the definition of subset, so that much is obvious as well. The program may not be able to apply the definition of union and disjunction to complete the proof, but the strucutre is there.

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
