package com.ricex.cartracker.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

public class VueElementProcessor extends AbstractMarkupSubstitutionElementProcessor {


	protected VueElementProcessor() {
		super("view");
	}

	@Override
	protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
	
		//generate a unique ID for the pageVm element, so we can use multiple of these on same page
		String pageVmId = getRandomPageVmId();	
		//pull the name of the view to load from the element
		String viewName = element.getAttributeValue("name");
		
		//create the pageVm element
		Element pageVm = new Element("div");
		pageVm.setAttribute("id", pageVmId);
		
		//create the script element to load the view
		Element loadViewScript = new Element("script");
		loadViewScript.setAttribute("type", "text/javascript");
		
		//create the script text and inject it into the load element
		Text scriptText = new Text("");
		scriptText.setContent(getLoadScript(viewName, pageVmId), true); // it is a script, don't want to escape it
		loadViewScript.addChild(scriptText);
		
		pageVm.addChild(loadViewScript);
		
		//Add all of the nodes
		List<Node> nodes = new ArrayList<Node>();		
		nodes.add(pageVm);
		//nodes.add(loadViewScript);
		
		return nodes;
	}
	
	/**
	 * 	Creates the Load Script that will be used to load the view
	 * 
	 * @param viewName Name of the view to load
	 * @param pageVmId The ID of the pageVm element
	 * @return The Load Script
	 */
	private String getLoadScript(String viewName, String pageVmId) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("navigationPromise.then(function () {	\n");
		builder.append("	require(['").append(viewName).append("'], function(vm) { \n");
		builder.append("		vm('#").append(pageVmId).append("');\n");
		builder.append("	});\n");
		builder.append("});");
		
		return builder.toString();
	}
	
	private String getRandomPageVmId() {
		return "pageVm_" + UUID.randomUUID().toString();
	}

	@Override
	public int getPrecedence() {
		return 1000;
	}

}
