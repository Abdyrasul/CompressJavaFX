/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancompress;

/**
 *
 * @author lenovo-zk
 */
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// A Tree node
class Node
{
	char ch;
	int freq;
	Node left = null, right = null;

	Node(char ch, int freq)
	{
		this.ch = ch;
		this.freq = freq;
	}

	public Node(char ch, int freq, Node left, Node right) {
		this.ch = ch;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
}

class Huffman
{

    public  static Map<Character, String> huffmanCodeGlobal =null;
    public static StringBuilder encodedString = null;
    public static String originalBytes = null;
    public static Node parent = null;
    public static  StringBuilder origin = new StringBuilder();

    public Map<Character, String> getHuffmanCodeGlobal() {
        return huffmanCodeGlobal;
    }

    public void setHuffmanCodeGlobal(Map<Character, String> huffmanCodeGlobal) {
        Huffman.huffmanCodeGlobal = huffmanCodeGlobal;
    }

    public StringBuilder getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(StringBuilder encodedString) {
        Huffman.encodedString = encodedString;
    }
    
    public Huffman() {
    }
    
	// traverse the Huffman Tree and store Huffman Codes in a map.
	public static void encode(Node root, String str, Map<Character,String> huffmanCode)
	{
		if (root == null)
			return;

		// found a leaf node
		if (root.left == null && root.right == null) {
			huffmanCode.put(root.ch, str);
		}

		encode(root.left, str + '0', huffmanCode);
		encode(root.right, str + '1', huffmanCode);
	}

	// traverse the Huffman Tree and decode the encoded string
	public static int decode(Node root, int index, StringBuilder sb)
	{
		if (root == null)
			return index;

		// found a leaf node
		if (root.left == null && root.right == null)
		{
                        origin.append(root.ch);
//			System.out.print(root.ch);
			return index;
		}

		index++;

		if (sb.charAt(index) == '0')
			index = decode(root.left, index, sb);
		else
			index = decode(root.right, index, sb);

		return index;
	}
         static String strToBinary(String s) 
    { 
        int n = s.length(); 
   StringBuilder origin = new StringBuilder();
        for (int i = 0; i < n; i++)  
        { 
            // convert each char to 
            // ASCII value 
            int val = Integer.valueOf(s.charAt(i)); 
  
            // Convert ASCII value to binary 
            String bin = ""; 
            while (val > 0)  
            { 
                if (val % 2 == 1) 
                { 
                    bin += '1'; 
                } 
                else
                    bin += '0'; 
                val /= 2; 
            } 
            bin = reverse(bin); 
            origin.append(bin);
  
//            System.out.print(bin + " "); 
        } 
        return origin.toString();
    } 
          static String reverse(String input)  
    {
        char[] a = input.toCharArray();
        int l, r = 0;
        r = a.length - 1;

        for (l = 0; l < r; l++, r--) {
            // Swap values of l and r  
            char temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }
        return String.valueOf(a);
    }

	// Builds Huffman Tree and huffmanCode and decode given input text
	public static void buildHuffmanTree(String text)
	{
		// count frequency of appearance of each character
		// and store it in a map
		Map<Character, Integer> freq = new HashMap<>();
                StringBuilder original = new StringBuilder();
		for (char c: text.toCharArray()) {
                        original.append(c);
			freq.put(c, freq.getOrDefault(c, 0) + 1);
		}

                originalBytes = original.toString();
		// Create a priority queue to store live nodes of Huffman tree
		// Notice that highest priority item has lowest frequency
		PriorityQueue<Node> pq;
		pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

            // Create a leaf node for each character and add it
            // to the priority queue.
            freq.entrySet().forEach((entry) -> {
                pq.add(new Node(entry.getKey(), entry.getValue()));
            });

		// do till there is more than one node in the queue
		while (pq.size() != 1)
		{
			// Remove the two nodes of highest priority
			// (lowest frequency) from the queue
			Node left = pq.poll();
			Node right = pq.poll();

			// Create a new internal node with these two nodes as children
			// and with frequency equal to the sum of the two nodes
			// frequencies. Add the new node to the priority queue.
			int sum = left.freq + right.freq;
			pq.add(new Node('\0', sum, left, right));
		}

		// root stores pointer to root of Huffman Tree
		Node root = pq.peek();

                parent = root;
		// traverse the Huffman tree and store the Huffman codes in a map
		Map<Character, String> huffmanCode = new HashMap<>();
		encode(root, "", huffmanCode);

		// print the Huffman codes
                
//		System.out.println("Huffman Codes are : " + huffmanCode);
//		System.out.println("Original string was : " + text);

		// print encoded string
		StringBuilder sb = new StringBuilder();
		for (char c: text.toCharArray()) {
			sb.append(huffmanCode.get(c));
		}
//		System.out.println("Encoded string is : " + sb);
                
                huffmanCodeGlobal = huffmanCode;
                encodedString = sb;

		// traverse the Huffman Tree again and this time
		// decode the encoded string
		int index = -1;
//		System.out.print("Decoded string is: ");
//		while (index < sb.length() - 2) {
//			index = decode(root, index, sb);
//		}
	}
        
        public static void Decompress(StringBuilder sb){
            int index = -1;
           while (index < sb.length() - 2) {
			index = decode(parent, index, sb);
		} 
        }

//	public static void main(String[] args)
//	{
//		String text = "Huffman coding is a data compression algorithm.";
//
//		buildHuffmanTree(text);
//	}
}