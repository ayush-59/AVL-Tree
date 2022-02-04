package AVLTree;

public class AVLTree<K extends Comparable<K>, V> {
	private Node root;
	
	public AVLTree() {
		this.root = null;
	}
	
	private class Node{
		private K key;
		private V value;
		private int size;
		private int height;
		private Node left;
		private Node right;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			height =0;
			size = 1;
			left = null;
			right = null;
		}

	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public int height() {
		return height(root);
	}
	
	private int height(Node root) {
		if(root == null)
			return -1;
		return root.height;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node root) {
		if(root == null)
			return 0;
		return root.size;
	}
	
	public V get(K key) {
		if(key == null) 
			throw new IllegalArgumentException("");
		Node x = get(root, key);
		return x == null ? null : x.value; 
	}
	
	private Node get(Node root, K key) {
		if(root == null)
			return null;
		System.out.print(root.key + " ");
		int cmp = key.compareTo(root.key);
		
		if(cmp == 0)
			return root;
		else if(cmp < 0)
			return get(root.left, key);
		else
			return get(root.right, key);
	}
	
	public void delete(K key) {
		if(key == null)
			throw new IllegalArgumentException("argument to delete() is null");
		root = delete(root, key);
	}
	
	private Node delete(Node root, K key) {
		if(root == null)
			return root;
		
		int cmp = key.compareTo(root.key);
		
		if(cmp < 0) {
			root.left = delete(root.left, key);
		}else if(cmp > 0) {
			root.right = delete(root.right, key);
		}else {
			if(root.right == null)
				root = root.left;
			else if(root.left == null)
				root = root.right;
			else {
				Node temp = min(root.right);
				root.right = delMin(root.right);
				temp.left = root.left;
				temp.right = root.right;
				root = temp;
			}
		}
		
		root.size = 1 + size(root.left) + size(root.right);
        root.height = 1 + Math.max(height(root.left), height(root.right));
		return balance(root);
	}
	
	private Node min(Node root) {
		if(root == null || root.left == null)
			return root;
		return min(root.left);
	}
	
	private Node delMin(Node root) {
		if(root == null)
			return root;
		if(root.left == null)
			return root.right;
		root.left = delMin(root.left);
		
		root.size = 1 + size(root.left) + size(root.right);
		root.height = 1 + Math.max(height(root.left), height(root.right));
		return balance(root);
	}
	
	public void put(K key, V value) {
		if(key == null)
			throw new IllegalArgumentException("argument to put() is null");
		if(value == null)
			delete(key);
		root = put(root, key, value);
	}
	
	private Node put(Node root, K key, V value) {
		if(root == null) 
			return new Node(key, value);
		
		int cmp = key.compareTo(root.key);
		
		if(cmp < 0) {
			root.left = put(root.left, key, value);
		}else if(cmp > 0){
			root.right = put(root.right, key, value);
		}else {
			root.value = value;
			return root;
		}
		root.size = 1 + size(root.left) + size(root.right);
        root.height = 1 + Math.max(height(root.left), height(root.right));
        
		return balance(root);
	}
	
	public Node balance(Node root) {
		int bf = balancedFactor(root);

		if(bf > 1) {
			if(balancedFactor(root.left) < -1)
				root.left = leftRotate(root.left);
			root = rightRotate(root);
		}else if(bf < -1) {
			if(balancedFactor(root.right) > 1)
				root.right = rightRotate(root.right);
			root = leftRotate(root);
		}
		return root;
	}
	
	private int balancedFactor(Node root) {
		return height(root.left) - height(root.right);
	}
	
	private Node leftRotate(Node root) {
		Node temp = root.right;
		root.right = temp.left;
		temp.left = root;
		
		temp.size = root.size;
		root.size = 1 + size(root.left) + size(root.right);
		root.height = 1 + Math.max(height(root.left), height(root.right));
		temp.height = 1 + Math.max(height(temp.left), height(temp.right));
		
		return temp;
	}
	private Node rightRotate(Node root) {
		Node temp = root.left;
		root.left = temp.right;
		temp.right = root;
		
		temp.size = root.size;
		root.size = 1 + size(root.left) + size(root.right);
		root.height = 1 + Math.max(height(root.left), height(root.right));
		temp.height = 1 + Math.max(height(temp.left), height(temp.right));
		
		return temp;
	}
	
	public static void main(String[] args) {
		AVLTree<Integer, String> tree = new AVLTree<>();
		System.out.println(tree.isEmpty());
		tree.put(3, "Ayush");
		System.out.println(tree.get(3));
		tree.put(2, "Simran");
		tree.put(1, "Cheeku");
		System.out.println(tree.get(2));
		System.out.println(tree.get(1));
		System.out.println(tree.balancedFactor(tree.root));
		tree.put(5, "Sim");
		tree.put(6, "Chee");
		System.out.println(tree.balancedFactor(tree.root));
		System.out.println(tree.root.height);
		tree.delete(5);
		System.out.println(tree.root.key);
		System.out.println(tree.get(3));
		System.out.println(tree.root.right.height);
		
	}
}
