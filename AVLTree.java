//package DAY1;

//Vaishakhi Kulkarni
//vpk140230

import java.util.Scanner;

//AVLNode
class AVLNode<T extends Comparable<? super T>> {
	AVLNode<T> left, right;
	T data;
	int height;
	int flag;

	// Default constructor
	public AVLNode() {
		left = null;
		right = null;
		data = null;
		height = 0;
		flag=0;
	}

	// Parameterized constructor
	public AVLNode(T n) {
		left = null;
		right = null;
		data = n;
		height = 0;
		flag=0;
		
	}
}

// AVLTree
class AVLTree<T extends Comparable<? super T>> {
	// Root node is created of AVLNode
	private AVLNode<T> root;

	// Default constructor
	public AVLTree() {
		root = null;
	}

	// Find maximum of left or right leaf
	private int maximum(int l, int r) {
		if (l > r)
			return l;
		else
			return r;
	}

	// Decide the height of the tree
	private int height(AVLNode<T> t) {
		if (t == null)
			return -1;
		else
			return t.height;
	}

	//Function to find if element is present and have flag set to 1.
	boolean find(T x,AVLNode<T> check) {
		
		while(check != null) {
		    int cmp = x.compareTo(check.data);
		    if(cmp < 0) {
			check = check.left;
		    } else if(cmp == 0 && check.flag==1) {
			return true;   // found
		    } else {
			check = check.right;
		    }
		}
		return false;
	    }
	
	// Insert into AVL tree
	public boolean insert(T data) {
	
		root = insert(data, root);
		Boolean flag = find(data,root);
		
		if (flag)
			return true;
		else
			return false;
	}

	// Perform insert operation
	private AVLNode<T> insert(T x, AVLNode<T> temp) {
		if (temp == null) // create a root node
		{
			temp = new AVLNode<T>(x);
			temp.flag=1;
		}

		else if (x.compareTo(temp.data) < 0) // when compared value of x is less
												// than its parent node.Then go
												// to left side
		{
			
			temp.left = insert(x, temp.left);
			temp.flag=1;
			if (height(temp.left) - height(temp.right) == 2) // check difference
																// of height
			{
				if (x.compareTo(temp.left.data) < 0) // condition to check
														// single or double
														// rotation on left side
					temp = singleLeftRotate(temp);
				else
					temp = doubleLeftRotate(temp);
			}
			

		} else if (x.compareTo(temp.data) > 0) // when compared value of x is
												// greater than its parent
												// node.Then go to right side
		{
			
			temp.right = insert(x, temp.right);
			temp.flag=1;
			if (height(temp.right) - height(temp.left) == 2) // check difference
																// of height
			{
				if (x.compareTo(temp.right.data) > 0) // condition to check
														// single or double
														// rotation on right
														// side
					temp = singleRightRotate(temp);
				else
					temp = doubleRightRotate(temp);
			}
			
		} else {
			temp.flag=0;
			// when records are having same value don't insert.So no action is
			// performed
		}
		temp.height = maximum(height(temp.left), height(temp.right)) + 1; // update
																			// the
																			// height
		return temp;
	}

	// Rotate with left side LL
	private AVLNode<T> singleLeftRotate(AVLNode<T> k2) {
		AVLNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = maximum(height(k2.left), height(k2.right)) + 1;
		k1.height = maximum(height(k1.left), k2.height) + 1;
		return k1;
	}

	// Rotate with right side RR
	private AVLNode<T> singleRightRotate(AVLNode<T> k1) {
		AVLNode<T> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = maximum(height(k1.left), height(k1.right)) + 1;
		k2.height = maximum(height(k2.right), k1.height) + 1;
		return k2;
	}

	// Double rotation RL
	private AVLNode<T> doubleLeftRotate(AVLNode<T> k3) {
		k3.left = singleRightRotate(k3.left);
		return singleLeftRotate(k3);
	}

	// Double rotation LR
	private AVLNode<T> doubleRightRotate(AVLNode<T> k1) {
		k1.right = singleLeftRotate(k1.right);
		return singleRightRotate(k1);
	}

	// perform preOrder traversal to check the answer
	private void preOrder(AVLNode<T> r) {
		if (r != null) {
			System.out.print(r.data + " ");
			preOrder(r.left);
			preOrder(r.right);
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		AVLTree<Integer> avltree = new AVLTree<Integer>();

		System.out.println(" ********AVL Tree******** ");
		System.out.println("Enter number of element to insert");
		int n = scan.nextInt();

		System.out.println("***Insert the elements in AVL tree***");
		int i = 0;
		while (i != n) {
			// insert the elements
			if (avltree.insert(scan.nextInt()))
				System.out.println("Element is inserted successfully");
			else
				System.out.println("Element already present");
			i++;
		}
		scan.close();

		AVLNode<Integer> r = avltree.root;

		System.out.print("\nPreorder Traversal : ");
		// check the preorder output
		avltree.preOrder(r);
	}
}