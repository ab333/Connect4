public class TreeNode {
		public TreeNode(int newAction, Grid newBoard, int newDepth)
		{
			action=newAction; 
			depth=newDepth; 
			board=newBoard; 
		}
		public void setParent(TreeNode node) {
			parent=node; 
		}
		private int action; 
		private TreeNode parent; 
		private Grid board; 
		private int depth; 
		public int successor[] = {0,1,2,3,4,5,6}; 
		public int successorLenght()
		{
			return successor.length; 
		}
		public int getAction()
		{
			return action; 
		}
		protected int getDepth()
		{
			return depth; 
		}
		protected TreeNode getParent() {
			return parent; 
		}
		
		public Grid getBoard()
		{
			return board; 
		}
		public TreeNode searchNode (int value)
		{
			//TODO
			return this; 
		}
	
}

