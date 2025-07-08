package jedi.game.mysql;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MysqlDatabaseParameter {
	private static Pattern pattern=Pattern.compile("([?])");
    
	private String sql;
	private List<Object> msgs;
	
	public MysqlDatabaseParameter(String sql, List<Object> params) {
		this.sql=sql;
		this.msgs=params;
	}

    public void format() {
    	// 如果是空的, 直接返回
    	if(msgs.size()==0) {
    		return ;
    	}
    	// 如果没有array类型的, 直接返回
		Class<MysqlDatabaseArrayObject> claz = MysqlDatabaseArrayObject.class;
		Class<MysqlDatabaseInArrayObject> claz2 = MysqlDatabaseInArrayObject.class;
		Class<MysqlArrayHolder> claz3 = MysqlArrayHolder.class;
    	boolean containsArrayObject=false;
    	for(Object msg:msgs) {
			if (claz.isAssignableFrom(msg.getClass()) || claz2.isAssignableFrom(msg.getClass())
					|| claz3.isAssignableFrom(msg.getClass())) {
    			containsArrayObject=true;
    			break;
    		}
    	}
    	if(!containsArrayObject) {
    		return ;
    	}
    	
    	
    	Matcher matcher = pattern.matcher(sql);
    	if(!matcher.find()) {
    		return ;
    	}
    	
    	// 如果是Array类型的, 展开插入数据
    	StringBuilder sb=new StringBuilder();
    	int msgsIndex=0;
    	int maxMsgsIndex=msgs.size();
    	int addIndex=0;
    	for(int index=0,len=sql.length();index<len;) {
    		if (matcher.find(index)) {
    		    int endIndex=matcher.end();
                sb.append(sql.substring(index, endIndex-1));
                if(msgsIndex>=maxMsgsIndex) {
                    throw new IllegalArgumentException("Parameter[msgs] size mismatch");
                }
                Object msg = msgs.get(msgsIndex);
                if(claz.isAssignableFrom(msg.getClass())) {
                	// 是Array类型的数据
                    MysqlDatabaseArrayObject array = (MysqlDatabaseArrayObject) msg;
                    // 移除当前数据
                    msgs.remove(msgsIndex);
                    // 插入一列数据
                    if(array.size()>0){
	                    for(int i=0;i<array.size();i++) {
	                        sb.append("?,");
	                        msgs.add(msgsIndex+addIndex, array.get(i));
	                        addIndex++;
	                    }
                    }else{
                    	index = endIndex;
                    	continue;
                    }
                    sb.deleteCharAt(sb.length()-1);
				}else if(msg.getClass().isAssignableFrom(MysqlArrayHolder.class)) {
                	// 是Array类型的数据
					MysqlArrayHolder arrays = (MysqlArrayHolder) msg;
					List<Object[]> array = arrays.getArrays();
					// 移除当前数据
					msgs.remove(msgsIndex);
					// 插入一列数据
					if (array.size() > 0) {
						for (int i = 0; i < array.size(); i++) {
							Object[] obj = array.get(i);
							if (obj.length > 0) {
								sb.append("(");
								for (int j = 0; j < obj.length; j++) {
									sb.append("?,");
									msgs.add(msgsIndex + addIndex, obj[j]);
									addIndex++;
								}
								sb.insert(sb.length() - 1, ")");
							}
	                    }
					} else {
						index = endIndex;
						continue;
					}
					sb.deleteCharAt(sb.length() - 1);
				}
                else if (claz2.isAssignableFrom(msg.getClass())) {
					// 是Array类型的数据
					MysqlDatabaseInArrayObject array = (MysqlDatabaseInArrayObject) msg;
					// 移除当前数据
					msgs.remove(msgsIndex);
					// 插入一列数据
					if (array.size() > 0) {
						for (int i = 0; i < array.size(); i++) {
							MysqlDatabaseInArrayObject.MysqlDatabaseInObject obj = array.get(i);
							if (obj.size() > 0) {
								sb.append("(");
								for (int j = 0; j < obj.size(); j++) {
									sb.append("?,");
									msgs.add(msgsIndex + addIndex, obj.get(j));
									addIndex++;
								}
								sb.insert(sb.length() - 1, ")");
							}
	                    }
					} else {
						index = endIndex;
						continue;
					}
					sb.deleteCharAt(sb.length() - 1);
                }else {
                    sb.append("?");
                }
                if(addIndex>0){
	                msgsIndex = msgsIndex+addIndex;
	                maxMsgsIndex =maxMsgsIndex+addIndex;
	                addIndex = 0;
                }else{
                	msgsIndex++;
                }
                index = endIndex;
            } else {
            	// 把后面数据全部加进去
                sb.append(sql.substring(index));
                break;
            }
    	}
    	sql=sb.toString();
    }
	
	public String getSql() {
		return sql;
	}
	
	public List<Object> getMsgs() {
		return msgs;
	}
	
}
