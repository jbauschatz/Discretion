Discretion
==========

A proof helper for discrete math

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
