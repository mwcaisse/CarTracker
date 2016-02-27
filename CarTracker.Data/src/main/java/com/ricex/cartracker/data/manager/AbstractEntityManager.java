package com.ricex.cartracker.data.manager;

import java.util.List;

import com.ricex.cartracker.data.entity.AbstractEntity;
import com.ricex.cartracker.data.mapper.EntityMapper;

public class AbstractEntityManager<T extends AbstractEntity> {
	
	
	protected EntityMapper<T> entityMapper;
	
	protected AbstractEntityManager() {
	
	}		
	
	public List<T> getAll() {
		return entityMapper.getAll();
	}
	
	public T get(long id) {
		return entityMapper.get(id);
	}

}
