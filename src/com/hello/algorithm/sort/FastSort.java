/**
 * 
 */
package com.hello.algorithm.sort;

import com.hello.algorithm.comm.Constants;

/**
 * @author liuhubo
 *         快速排序--冒泡的改进,复杂度与性能ref:
 *         http://blog.csdn.net/whuslei/article/details/6442755
 */
public class FastSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = Constants.array;
		System.out.println(a.length);
		sort(0,a.length-1,a);
		System.out.println(a);
	}

	private static void sort(int low,int high,int...a){
		int start=low,end=high;
		if(start>end){
			return;
		}
		int mid=a[end];
		while(start<end){
			while(a[start]<=mid&&end>start){//从前向后
				start++;
			}
			swap(a[start],a[end]);
			while(a[end]>=mid&&end>start){//从后往前
				end--;
			}
			swap(a[start],a[end]);
		}
		sort(0,low,start-1);
		sort(high,end+1,high);
	}
	
	static void swap(int a,int b){
		int tmp=a;
		a=b;
		b=tmp;
	}
}
