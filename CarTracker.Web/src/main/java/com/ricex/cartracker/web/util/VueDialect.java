package com.ricex.cartracker.web.util;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class VueDialect extends AbstractDialect {

	@Override
	public String getPrefix() {
		return "vue";
	}
	
	public Set<IProcessor> getProcessors() {
		Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new VueElementProcessor());
		return processors;
	}

}
