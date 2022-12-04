package org.example;

import java.util.HashMap;
import java.util.Map;

public class DataRoot
{
	private Map<Long, Foo> content= new HashMap<>();

	public DataRoot()
	{
		super();
	}

	public Map<Long, Foo> getContent() {
		return content;
	}
}