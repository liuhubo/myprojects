package com.hello.binary.tree;
/**
 * 
 */

/**
 * @author liuhubo
 *
 */
public class BinaryTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
     NodeTree root=new NodeTree(9);
     NodeTree n2=new NodeTree(1);
     NodeTree n3=new NodeTree(3);root.setLchild(n2);root.setRchild(n3);
     NodeTree n4=new NodeTree(2);
     NodeTree n5=new NodeTree(4);n2.setLchild(n4);n2.setRchild(n5);
     NodeTree n6=new NodeTree(8);n3.setLchild(n6);
     NodeTree n7=new NodeTree(6);
     NodeTree n8=new NodeTree(7);n4.setLchild(n7);n4.setRchild(n8);
     NodeTree n9=new NodeTree(5);
     NodeTree n10=new NodeTree(10);n5.setLchild(n9);n5.setRchild(n10);
     NodeTree n11=new NodeTree(12);
     NodeTree n12=new NodeTree(13);n9.setLchild(n11);n9.setRchild(n12);
     NodeTree n13=new NodeTree(17);
     NodeTree n14=new NodeTree(18);n12.setLchild(n13);n12.setRchild(n14);
     NodeTree n15=new NodeTree(20);n13.setLchild(n15);
     selectTreeRight(root);
     System.out.println("Depth:"+depth(root));
	}

	//先序遍历-最左节点开始遍历，顺序从左往右
	private static  void selectTreeLeft(NodeTree tree){
		if(tree==null){
			return;
		}
		System.out.println(tree.getV());
		selectTreeLeft(tree.getLchild());
		selectTreeLeft(tree.getRchild());
	}
	
	//中序遍历-最左节点开始遍历，顺序从左往右
		private static  void selectTreeMid(NodeTree tree){
			if(tree==null){
				return;
			}
			selectTreeMid(tree.getLchild());
			System.out.println(tree.getV());
			selectTreeMid(tree.getRchild());
		}
		
		//后序遍历-最左节点开始遍历，顺序从左往右
		private static  void selectTreeRight(NodeTree tree){
			if(tree==null){
				return;
			}
			selectTreeRight(tree.getLchild());
			selectTreeRight(tree.getRchild());
			System.out.println(tree.getV());
		}
		
		/*二叉树深度*/
		private static int depth(NodeTree root){
			if(root==null){
				return 0;
			}
			int h1,h2;
			h1=depth(root.getLchild());
			h2=depth(root.getRchild());
			return h1>h2?h1+1:h2+1;
		}
		
}
