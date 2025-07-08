package jedi.game.mysql;

import java.util.ArrayList;
import java.util.List;

public class MysqlArrayHolder {
	private List<Object[]> arrays = new ArrayList<>();

	public List<Object[]> getArrays() {
		return arrays;
	}

	public void setArrays(List<Object[]> arrays) {
		this.arrays = arrays;
	}
	
	
}
