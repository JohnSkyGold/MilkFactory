package kr.co.jongnomilk.model;
import java.util.ArrayList;

public class DataArray {
	ArrayList<Data> arr = new ArrayList<>();
	int count;
	public ArrayList<Data> getArr() {
		return arr;
	}
	public void setArr(ArrayList<Data> arr) {
		this.arr = arr;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
