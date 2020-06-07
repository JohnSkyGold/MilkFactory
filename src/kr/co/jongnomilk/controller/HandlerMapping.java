package kr.co.jongnomilk.controller;

import java.util.HashMap;


 
public class HandlerMapping { 
	private HashMap<String, Controller> lists = new HashMap<String, Controller>();
	public HandlerMapping() {
		initConfig();
	}
	void initConfig() {
		lists.put("/login.do", new LoginController());
		lists.put("/milksterlizer.do", new MilkSterlizerController());
		lists.put("/control.do", new ControlMilkSterlizerController());
		lists.put("/monitor.do", new PhotoMonitoringController());
		lists.put("/test.do", new ControllerTest());
		lists.put("/test2.do", new ControllerTest2());
	}
	
	public Controller getController(String path) {
		return lists.get(path);
	}
}
