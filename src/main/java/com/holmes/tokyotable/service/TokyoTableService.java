/**
 * 
 */
package com.holmes.tokyotable.service;

import java.util.List;

/**
 * @author yangjiandong
 *
 */
public interface TokyoTableService {
	
	public void insert(String key, List<Pair> pairs);
	
	public List<String> search(SearchRex... rexs);
	
	public int delete(String key);
	
}
