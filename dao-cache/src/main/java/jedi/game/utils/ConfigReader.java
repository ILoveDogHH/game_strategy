package jedi.game.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
	private Map<String, String> values;
	public enum ConfigFileType {
		json("json"),
		properties("properties");
	    
	    private final String value;
	    private ConfigFileType(String value) {
	        this.value=value;
	    }
	    
	    public String getValue() {
	        return value;
	    }
	}
    public ConfigReader(String filename, ConfigFileType type) {
    	switch(type) {
	    	case json:
	            values=readJson(filename);
	    		break;
	    	case properties:
	            values=readProperties(filename);
	    		break;
    		default:
    			values=new HashMap<>();
    			break;
    	}
    }

    /**
     * 读取json格式的配置
     * @param filename
     * @return
     */
    private Map<String, String> readJson(String filename) {
        Map<String, String> valueTmp = new HashMap<>();
        try (FileInputStream fis=new FileInputStream(new File(filename));
        		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        		BufferedReader br=new BufferedReader(isr);){
        	
            String jsonString = "";
            String tempString = null;
            while ((tempString = br.readLine()) != null) {  
                jsonString = jsonString + tempString;  
            }
            
            JSONObject json=JSON.parseObject(jsonString);
            json.forEach((key, val)->{
            	if(key.equals("_comment")) {
					return ;
            	}
            	if(valueTmp.containsKey(key)) {
                	String errMsg = String.format("Warning: configuration conflict, file[%s]:key[%s]",filename, key);
                	return ;
            	}
            	valueTmp.put(String.valueOf(key), String.valueOf(val));
            });
        }catch (IOException | JSONException e){
            throw new JSONException(e.getMessage());
        }

        return valueTmp;
	}
    
    /**
     * 读取json格式的配置
     * @param filename
     * @return
     */
    private Map<String, String> readProperties(String filename) {
    	Map<String, String> valuesTmp = new HashMap<>();
        Properties p=new Properties();
        try (InputStream is=new FileInputStream(new File(filename))){
            p.load(is);
            p.forEach((key, val)->{
                valuesTmp.put(String.valueOf(key), String.valueOf(val));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valuesTmp;
	}
    
    public String getString(String key) {
        if(!values.containsKey(key)) {
            return "";
        }
        return values.get(key);
    }
    
    public int getInt(String key) {
        if(!values.containsKey(key)) {
            return 0;
        }
        return Integer.valueOf(values.get(key));
    }
    public long getLong(String key) {
        if(!values.containsKey(key)) {
            return 0;
        }
        return Long.valueOf(values.get(key));
    }
    public Boolean getBoolean(String key) {
    	if(!values.containsKey(key)) {
			return false;
    	}
    	return Boolean.valueOf(values.get(key));
    }
    public Byte getByte(String key) {
    	if(!values.containsKey(key)) {
    		return null;
    	}
    	return Byte.valueOf(values.get(key));
    }
    public boolean containsKey(String key) {
    	return values.containsKey(key);
    }
    public Map<String, String> getValues() {
    	return values;
    }
}
