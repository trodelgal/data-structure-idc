# Splay Tree

DNA is represented as sequences of four nucleotide bases: Adenine (A), Thymine (T), Cytosine (C), and
Guanine (G). The arrangement of these characters models the intricate double helix structure of DNA.
Mutations in DNA can be modeled through operations such as substitution (changing one base to
another), insertion (adding a base), and deletion (removing a base). In addition, more complex structural
variations occur, like translocation (relocating a DNA segment from one position to another within the
genome), and inversion (reversing the order of a DNA sequence). These alterations introduce genetic
variation, which underlies the principles of evolution and disease progression.

## Assignment

In this exercise you will implement a class that represents a sequence of characters using splay trees.
The following methods should be supported. Indices start at 0.
SplayTree(String s) – construct a balanced BST from the sequence of characters in the string s.
substitute(int i, char c) – set the i’th character in the sequence to be c
insert(int i, char c) – insert the character c at position i in the sequence (shifting all the following
characters).
delete(int i) – remove the i’th character from the sequence (shifting all the following characters)
translocate(int i, int j, int k) - move the subsequence starting at i and ending at j to start at position k. k
is relative to the sequence prior to the operation, and is guaranteed not to be in the range [i,j].
invert(int i, int j) – reverse the subsequence starting at i and ending at j.
ToString() – returns the string represented by the data structure.
The time complexity of the constructor should be O(|s|) worst case.

The file SplayTree.java contains a partial implementation of the SplayTree class. It supports all methods
except the constructor and invert. The code is well documented, so you should first make sure you
understand how it works. The main ideas were presented (albeit not in full detail) in question 3 in
recitation 8. As we explained in that recitation, to represent a sequence by a BST, we do not represent
the keys explicitly, but rather the inorder number of each node (i.e., the position of the node in the
inorder traversal of the tree) is the implicit key. In the implementation we provide, each Node x is
augmented with a size field, which stores the size of the subtree rooted at x. This field is maintained
correctly by all the implemented methods.

## Examples:

Assume the first operation is SplayTree(“ACCCGACTGTCCATAGAAA”)
substitute(1,’T’) produces “ATCCGACTGTCCATAGAAA”
then, insert(2,’A’) produces “ATACCGACTGTCCATAGAAA”
then, delete(0) produces “TACCGACTGTCCATAGAAA”
then, translocate(0,3,6) produces “GATACCCTGTCCATAGAAA”
then, translocate(4,6,1) produces “GCCCATATGTCCATAGAAA”
then, invert (2,5) produces “GCTACCATGTCCATAGAAA”
