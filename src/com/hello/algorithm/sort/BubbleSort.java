package com.hello.algorithm.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.hello.algorithm.comm.Constants;

public class BubbleSort {
public static int calCount=0;//算法复杂度-计算次数累计值
	/**
	 * 冒泡排序-
	 * 复杂度与性能ref:ref:http://blog.csdn.net/whuslei/article/details/6442755
	 */
	public static void main(String[] args) {
		int []a=Constants.array;
		System.out.println("数组元素:");
		for(int v:a){
			System.out.print(v+"|");
		}
		System.out.println("\n数组长度:"+a.length);
		System.out.println("------冒泡开始:升序-------");
		long start=System.nanoTime();
		swap(a);
		System.out.println("Time cost:"+(System.nanoTime()-start)+"ns");
		System.out.println("升序序后数组元素:");
		for(int v:a){
			System.out.print(v+"|");
		}
		System.out.println("\n计算次数:"+calCount);
		start=System.nanoTime();
		statisticIntCount(a);
		System.out.println("统计频率Time Cost:"+(System.nanoTime()-start)+"ns");
		System.out.println("------冒泡开始:降序-------");
	    start=System.nanoTime();
	    reverse(a);
	    System.out.println("Time cost:"+(System.nanoTime()-start)+"ns");
		System.out.println("降序序后数组元素:");
		for(int v:a){
			System.out.print(v+"|");
		}
		System.out.println("\n计算次数:"+calCount);
	}

	private static void swap(int...a){
		int tmp;
		int m=a.length,n=a.length;
		for(int i=0;i<m;i++){
			for(int j=i+1;j<n;j++){
				if(a[i]>a[j]){/*升序*/
					tmp=a[i];
					a[i]=a[j];
					a[j]=tmp;
					calCount++;
				}
			}
		}
	}
	
	private static void reverse(int...a){
		if(calCount!=0){
			calCount=0;
		}
		int tmp;
		int m=a.length,n=a.length;
		for(int i=0;i<m;i++){
			for(int j=i+1;j<n;j++){
				if(a[i]<a[j]){/*降序*/
					tmp=a[i];
					a[i]=a[j];
					a[j]=tmp;
					calCount++;
				}
			}
		}
	}
	
	//出现次数降序排列
	private static void statisticIntCount(int...a){
		System.out.println("数字出现频率倒序");
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();//map->key存值,value统计字数
		for(int n:a){
			if(map.containsKey(n)){
				map.put(n, map.get(n)+1);
			}else{
				map.put(n, 1);
			}
		}
		Set<Entry<Integer,Integer>> entry=map.entrySet();
		Map<Integer,List<Integer>> sortMap=new TreeMap<Integer,List<Integer>>();//key存统计值,value存值
		Iterator<Entry<Integer,Integer>> it=entry.iterator();
		while(it.hasNext()){
			Entry<Integer,Integer> tmp=it.next();
			if(sortMap.containsKey(tmp.getValue())){
				List<Integer> tmpList=sortMap.get(tmp.getValue());
				tmpList.add(tmp.getKey());
				sortMap.put(tmp.getValue(), tmpList);
			}else{
				List<Integer> lis=new ArrayList<Integer>();
				lis.add(tmp.getKey());
				sortMap.put(tmp.getValue(),lis);
			}
		}
		Set<Integer> keys=sortMap.keySet();
		List<Integer> li=new ArrayList<Integer>(keys);
		for(int v=li.size(),s=v-1;s>=0;s--){
			System.out.println(sortMap.get(li.get(s))+"统计次数:"+li.get(s));
		}
	}
}
