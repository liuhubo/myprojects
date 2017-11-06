package com.hello.binary.tree;
/**
 * 
 */

import java.io.Serializable;

/**
 * @author liuhubo
 *二叉树
 *深度为h的二叉树最多有个结点(h>=1)，最少有h个结点
 */
public class NodeTree implements Serializable{
private NodeTree lchild;
private NodeTree rchild;
private Object v;
public NodeTree(Object v) {
	this.v = v;
}
public NodeTree getLchild() {
	return lchild;
}
public void setLchild(NodeTree lchild) {
	this.lchild = lchild;
}
public NodeTree getRchild() {
	return rchild;
}
public void setRchild(NodeTree rchild) {
	this.rchild = rchild;
}
public Object getV() {
	return v;
}
public void setV(Object v) {
	this.v = v;
}
	
}
