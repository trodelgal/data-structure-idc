import java.util.ArrayList;
import java.util.List;

/**
 * SplayTree.java
 * Implementation of a keyless Splay tree for representing a seuqence
 * of characters
 */
public class SplayTree {

    /**
     * Represents a node in the SplayTree.
     */
    public class Node {
        int size;
        Node[] children;
        Node parent;
        char character;
        int swap;

        /**
         * Constructs a new Node with the given character.
         * @param character the character to store in this Node.
         */
        public Node(char character) {
            this.parent = null;
            this.character = character;
            this.size = 1;
            this.children = new Node[2];
            this.swap = 0;
        }

        public Node getLeft() {
            return children[0] != null ? children[0] : null;
        }

        public Node getRight() {
            return children[1] != null ? children[1] : null;
        }
    }


    // The root of the SplayTree
    Node root;

    /**
     * Constructs an empty SplayTree.
     */
    public SplayTree() {
        this.root = null;
    }
    
    
    /**
     * Constructs a SplayTree from the given string.
     * @param s the string to use for constructing the tree.
     */
    public SplayTree(String s) {
        // Fill in your code here
        // initialize splay tree with s.
        root = buildBST(s, 0 , s.length()-1, root);
    }

    public Node buildBST(String s, int start, int end, Node parent){
        if(start > end)
            return null;
        else{
            int mid = (start + end) / 2;
            Node n = new Node(s.charAt(mid));
            n.parent = parent;
            n.children[0] = buildBST(s, start, mid-1, n);
            n.children[1] = buildBST(s,mid+1, end, n);
            if(n.children[0] != null && n.children[1] != null )
                n.size = 1 + n.children[0].size + n.children[1].size;
            else if (n.children[0] != null  && n.children[1] == null)
                n.size = 1 + n.children[0].size;
            else if(n.children[0] == null && n.children[1] != null)
                n.size = 1 +  n.children[1].size;
            return n;
        }
    }

    /**
     * Join Constructor.
     * Creates a new splay tree by joining two existing splay trees T1 and T2.
     * The inorder of nodes of the new tree is the concatenation of the inorders of T1 and of T2.
     * This operation invalidates T1 and T2.
     *
     * @param T1 The splay tree containing nodes less than those in T2.
     * @param T2 The splay tree containing nodes greater than those in T1.
     */
    public SplayTree(SplayTree T1, SplayTree T2) {
        // If T1 is null or its root is null, use T2 as the new splay tree
        if (null == T1 || null == T1.root){
            this.root = T2.root;
            return;
        }

        // If T2 is null or its root is null, use T1 as the new splay tree
        if (null == T2 || null == T2.root) {
            this.root = T1.root;
            return;
        }

        // Splay the rightmost node of T1 to the root so it has no left child
        Node max = T1.root;
        // Flag to determine the current swap thinking
        int currentSwap = max.swap;

        while(max.children[currentSwap ^ 1] != null) {
            max = max.children[currentSwap ^ 1];
            currentSwap = (currentSwap ^ max.swap);
        }
        T1.splay(max);


        // Make T2 the right subtree of the root of T1
        T1.root.children[T1.root.swap ^ 1] = T2.root;
        T2.root.parent = T1.root;
        // Update T2 swap
        T2.root.swap = (max.swap == 1) ? (max.swap ^ T2.root.swap) : (T2.root.swap);
        updateSize(T1.root);
        this.root = T1.root;

        // Invalidate T1 and T2
        T1.root = T2.root = null;
    }
    
    /**
     * Update the size field of the given Node based on its children
     * @param x the Node to update
     */
    private void updateSize(Node x) {
        x.size = 1 + size(x.children[0]) + size(x.children[1]);
    }

    /**
     * Get the size of the given Node (protected for null arguments)
     * @param x the Node to measure
     * @return the size of the Node
     */
    private int size(Node x) {
        return (x == null) ? 0 : x.size;
    }

    
    /**
     * Perform a rotation operation between a Node x in the Splay Tree
     * and its parent.
     * @param x the Node to rotate
     */
    private void rotate(Node x) {
        Node p = x.parent;
        Node gp = p.parent;

        //idx is index of x in p's children
        int idx = p.children[1] == x ? 1 : 0;

        //set x as child of gp instead of p
        if (gp != null) {
            gp.children[gp.children[1] == p ? 1 : 0] = x;
        }
        x.parent = gp;

        // x's child in the direction opposite to idx becomes the child of p instead of x
        p.children[idx] = x.children[idx ^ x.swap ^ 1];
        if (x.children[idx ^ x.swap ^ 1] != null) {
            x.children[idx ^ x.swap ^ 1].parent = p;
            // Update the child's swap
            x.children[idx ^ x.swap ^ 1].swap = x.swap ^ x.children[idx ^ x.swap ^ 1].swap;
        }

        //p becomes the child of x in the position opposite to idx
        x.children[idx ^ x.swap ^ 1] = p;
        p.parent = x;

        //Update the swap of p and x
        x.swap = (x.swap ^ p.swap);
        p.swap = (x.swap ^ p.swap);

        //update the sizes of p then x
        updateSize(p);
	    updateSize(x);

        //update the root if necessary
        if (root == p) {
            root = x;
        }
    }

    

    /**
     * Performs a splay operation on the given node, bringing it to the root of the tree 
     * using zigzig and zigzag operations.
     * @param x The node to be splayed.
     */
    private void splay(Node x) {
        //Keep rotating until x is the root.
        while (x.parent != null) {
	    
            Node p = x.parent; //parent
            Node gp = p.parent; //grandparent

            // If the node has a grandparent, determine the type of rotation needed.
            if (gp != null) {
		
                // If the relation between gp and p is the same as the relation between p and x - zigzig
                boolean zigzig = (gp.children[0] == p) == (p.children[0 ^ gp.swap] == x);
                if (zigzig) {
		            rotate(p); // In zigzig, rotate p first.
                } else {
                    rotate(x); // In zigzag, rotate x first.
                }
            }

            //in zigzig, zigzag, and when x is a child of the root, the last rotation is of x
            rotate(x);
        }
    }
    
    /**
     * Splays and returns the node whose rank is k in the tree.
     * Rank is defined by the position of a node in the in-order traversal of the tree.
     *
     * @param k The rank of the node to be selected.
     * @return The node at rank k, or null if such a node does not exist.
     */
    public Node select(int k) {

        Node x = root;
        int t, currentSwap = x.swap;

        // Finding node with rank k
        while (x != null) {

            // Determining left child based on swap
            if (x.parent != null) currentSwap = currentSwap ^ x.swap;
            t = size(x.children[currentSwap]);
            if (t > k) {
                // If left subtree has more than k nodes, go to the left subtree
                x = x.children[currentSwap];
            } else if (t < k) {
                // If left subtree has fewer than k nodes, go to the right subtree
                x = x.children[currentSwap ^ 1];
                k = k - t - 1; //relative rank in the right subtree
            } else {
                // If the size of the subtree is equal to k, x is the desired node
                // Splay x and return it
                splay(x);
                return x;
            }
        }
        // The node with rank k doesn't exist (k is out of bounds).
        return null;
    }

    /**
     * Splits the current splay tree into two splay trees T1 and T2 around a node x,
     * where T1 contains all nodes whose rank is smaller than x, and T2 contains nodes 
     * whose rank is at least the rank of x.
     * This operation invalidates the current tree.
     *
     * @param x The node around which the split operation is performed.
     * @return An array containing the T1 and T2 in this order.
     */
    public SplayTree[] split(Node x) {
	
        // After splaying x, T1 is the left subtree and T2 is x and its right subtree.
        splay(x);

        SplayTree T1 = new SplayTree();
        T1.root = x.children[x.swap];
        if (T1.root != null) {
            // Update T1.root.swap.
            T1.root.swap = x.swap ^ T1.root.swap;
            T1.root.parent = null;
        }

        // Create a new splay tree for the right subtree of x (nodes greater than or equal to x)
        SplayTree T2 = new SplayTree();
        T2.root = x;
        T2.root.children[x.swap] = null;
        updateSize(T2.root);

        //Invalidate current tree
        this.root = null;
        
        return new SplayTree[] {T1, T2};
    }


    /**
     * Set the i’th character in the sequence to be character c.
     * 
     * @param i The index of the character to be replaced in the splay tree. Index starts from 1.
     * @param c The new character to replace the old character.
     */
    public void substitute(int i, char c) {
        // Check if the index i is within the valid range.
        if (i < 0 || i >= this.root.size) {
	    System.out.println("Invalid index: " + i);
            return;
        }

        // Select the node at index i.
        Node node = select(i);

        // Replace the character in the selected node with c.
        node.character = c; 
    }

    /**
     * Insert the character c at position i in the sequence (shifting all the following
    characters).
     * This method creates a new splay tree t_c containing only the node to be inserted.
     * Then, it joins the current tree with t_c, which places the new character at the end of the tree.
     * If the target position is not at the end, the method translocates the new character to the target position.
     *
     * @param i The position at which the character is to be inserted.
     * @param c The character to be inserted.
     */
    public void insert(int i, char c){

        // Check if the index i is within the valid range.
        if (i < 0 || i > this.root.size) {
            throw new IllegalArgumentException("Invalid index: " + i);
        }

        // Create a new splay tree containing only the character to be inserted.
        SplayTree t_c = new SplayTree(String.valueOf(c));

        // Join the current tree with t_c.
        SplayTree new_t = new SplayTree(this, t_c);

        // Get the max rank of the new tree.
        int max_rank = new_t.root.size-1;

        // Update the root of the current tree.
        this.root = new_t.root;

        // If the target position is not at the end, translocate the new character to the target position.
        if (i < max_rank)
            translocate(max_rank,max_rank, i);	
    }


    /**
     * Remove the i’th character from the sequence (shifting all the following characters)
     * @param i The index of the node to be deleted from the splay tree. Index starts from 0.
     */
    public void delete(int i) {
        // Check if the index i is within the valid range.
        if (i < 0 || i >= this.root.size) {
            throw new IllegalArgumentException("Invalid index: " + i);
        }

        // Splay the node x with rank i.
        Node x = select(i);
        
        // Disconnect x from its children
        SplayTree t1 = new SplayTree();
        SplayTree t2 = new SplayTree();
        t1.root = x.children[x.swap];
        if (t1.root != null) {
            t1.root.parent = null;
            // Update the t1.root.swap
            t1.root.swap = t1.root.swap ^ x.swap;
        }   
        t2.root = x.children[x.swap ^ 1];
        if (t2.root != null) {
            t2.root.parent = null;
            // Update t2.root.swap.
            t2.root.swap = t2.root.swap ^ x.swap;
        }
        x.children[0] = x.children[1] = null;

        // Join the two subtrees
        SplayTree t = new SplayTree(t1,t2);
        this.root = t.root;
    }

    /**
     * Move the subsequence starting at i and ending at j to start at position k. 
     * k is relative to the sequence prior to the operation, and is guaranteed not to be in the range [i,j].
     * This method selects three nodes x_i, x_j, and x_k corresponding to the indices i, j, and k, respectively.
     * Then, it splits the tree at x_i and x_j, effectively separating the sequence from i to j.
     * The tree is then restructured by joining the left part (up to i), the right part (after j), and the selected subsequence at the new position k.
     *
     * @param i The start index (rank) of the subsequence to be translocated.
     * @param j The end index (rank) of the subsequence to be translocated.
     * @param k The index (rank) to which the subsequence is to be translocated.
     */

     public void translocate(int i, int j, int k){

        // Deal with invalid arguments
        if (i < 0 || j < i || j >= this.root.size || k < 0 || k > this.root.size) {
            throw new IllegalArgumentException("Invalid index: " + k);
        }
        if (k >= i && k <= j) {
            throw new IllegalArgumentException("Invalid index: " + k);
        }
        
        Node x_i, x_j, x_k;
        x_i = select(i);
        x_j = select(j+1); //select j+1 because we want to split immediately after j (not at j)
        x_k = select(k);

        // Split the tree at x_i.
        // tt[0] contains nodes with ranks smaller than i  [...i)
        // tt[1] contains nodes with ranks at least i  [i...]
        SplayTree[] tt = split(x_i);  //tt[0] is [0...i), tt[1] is [i...]

        SplayTree t,t_ij;
        SplayTree[] tt2;

        if (x_j != null) {
            // j is not the maximum rank
            // Split the [i...] part at x_j.
            // tt2[0] contains nodes with ranks from i to j (including)   [i...j]
            // tt2[1] contains nodes with ranks greater than j  (j...]
            tt2 = tt[1].split(x_j); //tt2[0] is [i,j], tt2[1] is (j...]	

            // Join [0...i) with (j...]
            t = new SplayTree(tt[0],tt2[1]); //t contains all nodes not in [i...j]
            t_ij = tt2[0];  //t_ij contains [i...j]
        }
        else {
            // j is the maximum element, so there is no (j...] part
            t = tt[0]; //t contains [0...i)
            t_ij = tt[1]; //t_ij contains [i...j] = [i...]
        }

        SplayTree[] tt3;

        if (x_k != null) {
            //k is not one location past the length of the sequence

            // Split the joined tree t at node x_k.
            tt3 = t.split(x_k);
            
            // Join the left part of the tree (up to k), the selected subsequence, and the remaining part of the tree.
            t = new SplayTree(tt3[0],t_ij);
            t = new SplayTree(t,tt3[1]);
        }
        else {
            //Otherwise should translocate [i,j] to the very end of the sequence
            t = new SplayTree(t,t_ij);
        }
        
        this.root = t.root;
	
    }

    /**
     * leftJoin method.
     * Concatenating a new splay tree by joining from the left two existing splay trees T1 and T2.
     *
     * @param T2 The splay tree containing nodes greater than those in T1.
     */

    private SplayTree leftJoin(SplayTree T1, SplayTree T2){
        // If T1 is null or its root is null, use T2 as the returned splay tree
        if (null == T1 || null == T1.root){
            return new SplayTree(T1, T2);
        }

        // If T2 is null or its root is null, use T1 as the returned splay tree
        if (null == T2 || null == T2.root) {
            return T1;
        }
        Node min = T1.select(0);
        // Make T2 the right subtree root at left position
        min.children[0] = T2.root;
        T2.root.parent = min;
        updateSize(T1.root);
        // Invalidate T2
        T2.root = null;
        return T1;
    }

    /**
     * Reverse the subsequences [i...j] of the splay tree.
     * @param i The starting index of the range to be inverted.
     * @param j The ending index of the range to be inverted.
     */
    public void invert(int i, int j){
        // Fill in your code here.
        // Check validity of j and i
        if (i < 0 || j < i || j > this.root.size) {
            throw new IllegalArgumentException("Invalid index: " + j);
        }
        // select nodes for split the tree and find subtree to invert
        Node nodeI, nodeJ;
        nodeI = select(i);
        nodeJ = select(j+1);

        // Split the tree at nodeI.
        SplayTree[] trees1 = split(nodeI);

        SplayTree invertTree,treeIJ;
        SplayTree[] trees2;

        if (nodeJ != null) {
            // Split the [i,n] part at nodeJ.
            trees2 = trees1[1].split(nodeJ);

            treeIJ = trees2[0];  //treeIJ contains [i...j]

            //Invert [i,j]
            treeIJ.root.swap = treeIJ.root.swap ^ 1;

            // Join T1 with inverted T2 into invertTree, and then join again with T3
            invertTree = new SplayTree(trees1[0], treeIJ);
            invertTree = new SplayTree(invertTree, trees2[1]);
        }
        else {
            // trees1[1] contains ranks [i,n]
            // j is the maximum element
            treeIJ = trees1[1];

            //Invert treeIJ
            treeIJ.root.swap = treeIJ.root.swap ^ 1;

            // Join [0,i) with inverted [i,j]
            invertTree = new SplayTree(trees1[0], treeIJ);
        }
        // current tree = inverted tree
        this.root = invertTree.root;
    }
    
    /**
     * Returns the sequence of characters represented by the data structure. That is, the characters stored
     * at each node in inorder.
     *
     * @return The sequence of characters represented by the tree.
     */
    public String toString() {
        return toString(root);
    }


    /**
     * Helper method for toString(). 
     * Recursively collects the characters of the nodes of the tree rooted at x using inorder traversal.
     *
     * @param x The root of the subtree to be visited.
     * @return The sequence of characters represented by the subtree rooted at x.
     */    
    public String toString(Node x) {
        if (x == null) {
            return "";
        }
        return toString(x.children[x.swap],x.swap) + x.character + toString(x.children[x.swap ^ 1], x.swap);
    }


    /**
     * Helper toString(Node x) method.
     * Recursively retrieves the characters of the nodes in the tree rooted at x using an inorder traversal.
     * The characters are obtained based on the accumulated swap factor along the path to x.
     * @param x The root of the subtree to be visited.
     * @param currentSwap Flag to determine the current swap thinking
     * @return The sequence of characters represented by the subtree rooted at x.
     */
    public String toString(Node x, int currentSwap) {
        if (x == null) {
            return "";
        }
        // Flag to determine the current swap thinking
        currentSwap = currentSwap ^ x.swap;
        return toString(x.children[currentSwap], currentSwap) + x.character + toString(x.children[currentSwap ^ 1],currentSwap);
    }


    public static void printTree(Node root) {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = "" + n.character + " - " + n.swap;
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null)
                        nn++;
                    if (n.getRight() != null)
                        nn++;
                }
            }

            if (widest % 2 == 1)
                widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null)
                                c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null)
                    f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }


    public Node find(int k) {
        Node x = root;
        int currentSwap = 0;
        while (x != null) {
            int t = size(x.children[(currentSwap + x.swap) % 2]);// Size of left
            if (t > k) {
                // If left subtree has more than k nodes, go to the left subtree
                x = x.children[(currentSwap + x.swap) % 2];
            } else if (t < k) {
                // If left subtree has fewer than k nodes, go to the right subtree
                x = x.children[1 - ((currentSwap + x.swap) % 2)];
                k = k - t - 1; //relative rank in the right subtree
            } else {
                // If the size of the subtree is equal to k, x is the desired node
                // Splay x and return it
                return x;
            }
            if(x != null && x.parent != null)
                currentSwap = (currentSwap + x.parent.swap) % 2;
        }
        // The node with rank k doesn't exist (k is out of bounds).
        return null;
    }

    /*
     * The main method used for testing.
     */
    public static void main(String[] args) {
        //MY TESTS
        SplayTree tree = new SplayTree("123456789");
       tree.root.swap = 1;
//        printTree(tree.root);
//        // [987654321] - check toString work correctly
        if(!tree.toString().equals("987654321"))
            System.out.println("Error in my-test 1");

//        //select
        Node n = tree.select(1);
//       printTree(tree.root);
        // 8 - check if select work correctly
        if(n.character != '8')
            System.out.println("Error in my-test 2.1");
        //[987654321] - without change the inorder traversal
        if(!tree.toString().equals("987654321"))
            System.out.println("Error in my-test 2.2");
        //delete
        tree.delete(1);
//      printTree(tree.root);
//      [97654321] - delete work without change the inorder traversal
        if(!tree.toString().equals("97654321"))
            System.out.println("Error in my-test 3");

//        //insert
        tree.insert(1, '8');
//      [987654321] - insert work correctly without change the inorder traversal
        if(!tree.toString().equals("987654321"))
            System.out.println("Error in my-test 4");
//        printTree(tree.root);
        // new tree for testing
        tree = new SplayTree("123456789");
//        printTree(tree.root);

        // invert
        tree.invert(0,8);
//        printTree(tree.root);
//      [987654321] - check toString work correctly
        if(!tree.toString().equals("987654321"))
            System.out.println("Error in my-test 4");

        tree.invert(2,6);
//      [983456721] - check toString work correctly
        if(!tree.toString().equals("983456721"))
            System.out.println("Error in my-test 6");

        // EXAMPLE TESTS FROM PDF
        tree = new SplayTree("ACCCGACTGTCCATAGAAA");
        tree.substitute(1,'T');
        if(!tree.toString().equals("ATCCGACTGTCCATAGAAA"))
            System.out.println("Error in test 1");
        tree.insert(2,'A');
        if(!tree.toString().equals("ATACCGACTGTCCATAGAAA"))
            System.out.println("Error in test 2");
        tree.delete(0);
        if(!tree.toString().equals("TACCGACTGTCCATAGAAA"))
            System.out.println("Error in test 3");
        tree.translocate(0,3,6);
        if(!tree.toString().equals("GATACCCTGTCCATAGAAA"))
            System.out.println("Error in test 4");
        tree.translocate(4,6,1);
        if(!tree.toString().equals("GCCCATATGTCCATAGAAA"))
            System.out.println("Error in test 5");
        tree.invert(2,5);
        if(!tree.toString().equals("GCTACCATGTCCATAGAAA"))
            System.out.println("Error in test 5");


    }

}
