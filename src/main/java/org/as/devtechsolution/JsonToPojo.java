package org.as.devtechsolution;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson1Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class JsonToPojo {
	
	public static void main(String[] args) {
		String pacgName= "org.as.devtechsolution";
		File inputJson= new File("."+ File.separator+ "UCC.json");
		File outputPojoDirectory= new File("."+ File.separator+ "convertedPojo");
		
		try {
			new JsonToPojo().convert2Json(inputJson.toURL(), outputPojoDirectory, pacgName, inputJson.getName().replace(".json", ""));
			
		} catch (IOException e) {
			System.out.println("Encounter Error"+ e.getMessage());
			e.printStackTrace();
			
			
		}
		
	}
	
	public void convert2Json(URL inputJson, File outputPojoDirectory, String pkgeName, String className) throws IOException{
		
		JCodeModel codeModel= new JCodeModel();
		URL source= inputJson;
		GenerationConfig config= new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() {
				return true;
			};
			public SourceType getSourceType() {
				return SourceType.JSON;
				
			}
		};
		SchemaMapper mapper= new SchemaMapper(new RuleFactory(config, new Jackson1Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, className, pkgeName, source);
		codeModel.build(outputPojoDirectory);
	}

}
