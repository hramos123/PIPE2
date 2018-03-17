package org.systemsbiology.PIPE2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.systemsbiology.PIPE2.client.KeywordSearchService;
import org.systemsbiology.PIPE2.domain.Namelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class KeywordSearchServiceImpl extends RemoteServiceServlet implements KeywordSearchService {
    public static final String KEYWORD_LOOKUP_DATA_DIR = "PIPEletResourceDir" + File.separator + "KeywordLookupPipelet" + File.separator;
	public static final String GENE_ONTOLOGY_FILEPATH = KEYWORD_LOOKUP_DATA_DIR + "gene2go_HMY.tsv";
	public static final String UNIPROT_FILEPATH = KEYWORD_LOOKUP_DATA_DIR + "sp_kw_";
//	public static final String GENE_ONTOLOGY_FILEPATH = KEYWORD_LOOKUP_DATA_DIR + "gene2go";
    private String baseDir;

    public ArrayList<Namelist> runGOKeywordSearch(String text, String organism) throws Exception {
        String tax_id = "9606";
        if(organism.equals("Saccharomyces cerevisiae")){
            tax_id = "4932";
        }else if(organism.equals("Mus musculus")){
            tax_id = "10090";
        }
        String[] words = text.split("\\W+");

        //searching GO
        baseDir = this.getServletContext().getRealPath(File.separator);
		System.out.println(baseDir + GENE_ONTOLOGY_FILEPATH);
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(baseDir + GENE_ONTOLOGY_FILEPATH));
			String newLineOfText;
			while ((newLineOfText = bufferedReader.readLine()) != null) {
				String[] data = newLineOfText.split("\t");
				if(data.length < 8 || !data[0].equals(tax_id) || data[0].startsWith("#"))
					continue;
                int match_counter = 0;
                for(int i=0; i < words.length; i++)
                    if(data[5].contains(words[i]))
                        match_counter++;
                if(match_counter == words.length){
                    if(!map.containsKey(data[5]))
                        map.put(data[5], new ArrayList<String>());
                    map.get(data[5]).add(data[1]);
                }
            }
        }catch(IOException e){
            System.out.println("In KeywordSearchServiceImpl: " + e.toString());
            throw new Exception(e.toString());
        }

        //set up return array
        ArrayList<Namelist> retVal = new ArrayList<Namelist>();
        for (String s : map.keySet()) {
            retVal.add(new Namelist(s, organism, map.get(s).toArray(new String[map.get(s).size()])));
        }
        return retVal;  
    }

    public ArrayList<Namelist> runUniProtKeywordSearch(String text, String organism) throws Exception {
        String sp_org_filepath = UNIPROT_FILEPATH;
        if(organism.equals("Homo sapiens")){
            sp_org_filepath += "9606";
        }else if(organism.equals("Saccharomyces cerevisiae")){
            sp_org_filepath += "4932";
        }else if(organism.equals("Mus musculus")){
            sp_org_filepath += "10090";
        }else if(organism.equals("Rattus norvegicus")){
            sp_org_filepath += "10116";
        }
        sp_org_filepath += ".tsv";
        String[] words = text.split("\\W+");

        //searching UniProt file(s)
        baseDir = this.getServletContext().getRealPath(File.separator);
		System.out.println(baseDir + sp_org_filepath);
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(baseDir + sp_org_filepath));
			String newLineOfText;
			while ((newLineOfText = bufferedReader.readLine()) != null) {
				String[] data = newLineOfText.split("\t");
				int match_counter = 0;
                for(int i=0; i < words.length; i++)
                    if(data[0].toLowerCase().contains(words[i].toLowerCase()))
                        match_counter++;
                if(match_counter ==words.length){//>= 1){
                    if(!map.containsKey(data[0]))
                        map.put(data[0], new ArrayList<String>());
                    map.get(data[0]).add(data[1]);
                }
            }
        }catch(IOException e){
            System.out.println("In KeywordSearchServiceImpl: " + e.toString());
            throw new Exception(e.toString());
        }

        //set up return array
        ArrayList<Namelist> retVal = new ArrayList<Namelist>();
        for (String s : map.keySet()) {
            String[] names = map.get(s).get(0).split("; ");
            retVal.add(new Namelist(s, organism, names));
        }
        return retVal;
    }
}