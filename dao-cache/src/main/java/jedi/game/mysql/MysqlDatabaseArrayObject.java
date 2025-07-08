package jedi.game.mysql;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MysqlDatabaseArrayObject extends JSONArray{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -748724234888306053L;

	/**
     * 创建一个MysqlDatabaseArrayObject类
	 */
	public MysqlDatabaseArrayObject(){
		super();
	}
	
	/**
	 * 创建一个MysqlDatabaseArrayObject类
	 * 
	 * @param list
	 *            会被转化为ArrayList, 方便增减
	 */
	public <T> MysqlDatabaseArrayObject(Collection<T> list) {
		super(new ArrayList<>(list));
	}
	

    /**
     * 创建一个MysqlDatabaseArrayObject类<br/>
     * <br/>
     * boolean不是Object;<br/>
     * boolean[]和Boolean是Object <br/>
     * 如果直接使用Object...匹配, 则会匹配成boolean[]..., 并且只有一个boolean[0]值. <br/>
     * 预期匹配成boolean[]{true}, 实际上会被匹配成boolean[][]{{true}}  
     * 
     * @param elements
     */
	public MysqlDatabaseArrayObject(boolean... elements) {
        super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
    }

    /**
     * 创建一个MysqlDatabaseArrayObject类<br/>
     * <br/>
     * byte不是Object;<br/>
     * byte[]和Byte是Object <br/>
     * 如果直接使用Object...匹配, 则会匹配成byte[]..., 并且只有一个byte[0]值. <br/>
     * 预期匹配成byte[]{1,2,3}, 实际上会被匹配成byte[][]{{1,2,3}}  
     * 
     * @param elements
     */
	public MysqlDatabaseArrayObject(byte... elements) {
        super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
    }

    /**
     * 创建一个MysqlDatabaseArrayObject类<br/>
     * <br/>
     * char不是Object;<br/>
     * char[]和Character是Object <br/>
     * 如果直接使用Object...匹配, 则会匹配成char[]..., 并且只有一个char[0]值. <br/>
     * 预期匹配成char[]{'a','b','c'}, 实际上会被匹配成char[][]{{'a','b','c'}}  
     * 
     * @param elements
     */
    public MysqlDatabaseArrayObject(char... elements) {
        super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
    }

	/**
	 * 创建一个MysqlDatabaseArrayObject类<br/>
	 * <br/>
	 * double不是Object;<br/>
	 * double[]和Double是Object <br/>
	 * 如果直接使用Object...匹配, 则会匹配成double[]..., 并且只有一个double[0]值. <br/>
	 * 预期匹配成double[]{1.1,2.2,3.3}, 实际上会被匹配成double[][]{{1.1,2.2,3.3}}
	 *
	 * @param elements
	 */
	public MysqlDatabaseArrayObject(double... elements) {
		super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
	}

	/**
	 * 创建一个MysqlDatabaseArrayObject类<br/>
	 * <br/>
	 * float不是Object;<br/>
	 * float[]和Float是Object <br/>
	 * 如果直接使用Object...匹配, 则会匹配成float[]..., 并且只有一个float[0]值. <br/>
	 * 预期匹配成float[]{1.1,2.2,3.3}, 实际上会被匹配成float[][]{{1.1,2.2,3.3}}
	 * 
	 * @param elements
	 */
	public MysqlDatabaseArrayObject(float... elements) {
		super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
	}

    /**
     * 创建一个MysqlDatabaseArrayObject类<br/>
     * <br/>
     * int不是Object;<br/>
     * int[]和Integer是Object <br/>
     * 如果直接使用Object...匹配, 则会匹配成int[]..., 并且只有一个int[0]值. <br/>
     * 预期匹配成int[]{1,2,3}, 实际上会被匹配成int[][]{{1,2,3}}  
     * 
     * @param elements
     */
    public MysqlDatabaseArrayObject(int... elements) {
        super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
    }

    /**
     * 创建一个MysqlDatabaseArrayObject类<br/>
     * <br/>
     * long不是Object;<br/>
     * long[]和Long是Object <br/>
     * 如果直接使用Object...匹配, 则会匹配成long[]..., 并且只有一个long[0]值. <br/>
     * 预期匹配成long[]{1L,2L,3L}, 实际上会被匹配成long[][]{{1L,2L,3L}}  
     * 
     * @param elements
     */
    public MysqlDatabaseArrayObject(long... elements) {
        super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
    }
    /**
     * 创建一个MysqlDatabaseArrayObject类<br/>
     * <br/>
     * short不是Object;<br/>
     * short[]和Short是Object <br/>
     * 如果直接使用Object...匹配, 则会匹配成short[]..., 并且只有一个short[0]值. <br/>
     * 预期匹配成short[]{1,2,3}, 实际上会被匹配成short[][]{{1,2,3}}  
     * 
     * @param elements
     */
    public MysqlDatabaseArrayObject(short... elements) {
        super(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(elements))));
    }
    
    /**
     * 创建一个MysqlDatabaseArrayObject类
     * @param elements
     */
	public MysqlDatabaseArrayObject(String... elements) {
		// Arrays.asList 是使用Arrays.ArrayList而不是java.util.ArrayList, 所以无法增删减
		super(new ArrayList<>(Arrays.asList(elements)));
	}

	/**
	 * 创建一个MysqlDatabaseArrayObject类
	 * 
	 * @param elements
	 */
	public <T> MysqlDatabaseArrayObject(T[] elements) {
        // Arrays.asList 是使用Arrays.ArrayList而不是java.util.ArrayList, 所以无法增删减 
        super(new ArrayList<>(Arrays.asList(elements)));
    }
}
