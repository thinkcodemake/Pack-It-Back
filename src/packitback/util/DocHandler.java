package packitback.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class DocHandler {
	
	private Document doc;
	
	public DocHandler(String filename) throws JDOMException, IOException{
		doc = loadFile(filename);
	}

	private Document loadFile(String filename) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(filename));
		return doc;
	}
	
	public ArrayList<String> getGames(){
		ArrayList<String> games = new ArrayList<String>();
		
		for(Element e : doc.getRootElement().getChildren("Game")){
			Element n = e.getChild("Name");
			games.add(n.getText());
		}
		
		return games;
	}
	
	public ArrayList<String> getSets(String game){
		ArrayList<String> sets = new ArrayList<String>();
		
		for(Element e : doc.getRootElement().getChildren("Game")){
			String eGameName = e.getChild("Name").getText();
			
			if (eGameName == game){
				for(Element f : e.getChildren("Set")){
					Element n = f.getChild("Name");
					sets.add(n.getText());
				}
			}
		}
		
		return sets;
		
	}
	
	public String getPack(String game, String set){
		StringBuilder builder = new StringBuilder();
		
		ArrayList<Element> builds = getBuilds(game, set);
		
		ObjectPicker<Element> packPicker = new ObjectPicker<Element>();
		
		for (Element e : builds){
			double weight = Double.parseDouble(e.getChild("Weight").getText());
			packPicker.add(e, weight);
		}
		
		Element packBuild = packPicker.pickObject();
		
		//TODO Temp jazz
		if(packBuild != null){
			return packBuild.getName();
		} else {
			//TODO Tweak or throw NullPointerException
			return "Uh Oh. Something's wrong.";
		}
	}
	
	private ArrayList<Element> getBuilds(String game, String set){
		ArrayList<Element> builds = new ArrayList<Element>();
		
		for (Element e : doc.getRootElement().getChildren("Game")){
			if (e.getChild("Name").getText().equals(game)){
				for (Element f : e.getChildren("Set")){
					if (f.getChild("Name").getText().equals(set)){
						for (Element g : f.getChild("PackDistribution").getChildren("Build")){
							builds.add(g);
						}
					}
				}
			}
		}
		
		return builds;
	}
}
