package org.systemsbiology.PIPE2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.systemsbiology.PIPE2.client.VennDiagramService;
import org.systemsbiology.PIPE2.domain.Namelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
* Copyright (C) 2009 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*
*/
public class VennDiagramServiceImpl extends RemoteServiceServlet implements VennDiagramService {
    public Namelist[] getIntersection(Namelist[] lists) {
        int i;
        for(i = 0; i < 3 && lists[i] != null; ){
            uniquifyNamelist(lists[i++]);
        }

        if(i == 2){
                lists[3] = getIntersectionList(lists,new int[]{1,2});
                lists[7] = uniqueToFirstOf(lists[0], lists[3]);
                lists[8] = uniqueToFirstOf(lists[1], lists[3]);
                lists[7].setName(lists[7].getName() + " (unique)");
                lists[8].setName(lists[8].getName() + " (unique)");
            }else if(i == 3){
                lists[4] = getIntersectionList(lists,new int[]{2,3});
                lists[5] = getIntersectionList(lists,new int[]{1,3});
                lists[6] = getIntersectionList(lists,new int[]{1,2,3});
                lists[7] = uniqueToFirstOf(lists[7], lists[5]);
                lists[8] = uniqueToFirstOf(lists[8], lists[4]);
                lists[9] = uniqueToFirstOf(lists[2], lists[4]);
                lists[9] = uniqueToFirstOf(lists[9], lists[5]);
                lists[9].setName(lists[9].getName() + " (unique)");
            }
        return lists;
    }

    private Namelist uniqueToFirstOf(Namelist nl1, Namelist nl2) {
        Namelist retVal = new Namelist(nl1.getName(), nl1.getSpecies(), new String[]{});
        ArrayList<String> newlist = new ArrayList<String>();
        for(int i=0; i < nl1.getNames().length; i++){
            int j = 0;
            for (; j < nl2.getNames().length;j++){
                if(nl1.getNames()[i].equals(nl2.getNames()[j]))
                    break;
            }
            if(j == nl2.getNames().length)
                newlist.add(nl1.getNames()[i]);
        }
        retVal.setNames(newlist.toArray(new String[newlist.size()]));
        return retVal;
    }
    public Namelist getIntersectionList(Namelist[] namelists, int []idx) {
        Namelist retVal = new Namelist();
        Set<String> uniqItems = new HashSet<String>();

        int curNumberOfNamelists;
        for(curNumberOfNamelists = 0; curNumberOfNamelists < 3 && namelists[curNumberOfNamelists] != null; ){
            curNumberOfNamelists++;
        }

        if(idx.length < 2 || curNumberOfNamelists < 2)
            return null;
        for(int index : idx)
            if(index > curNumberOfNamelists)
                return null;
        for(String first : namelists[idx[0] - 1].getNames())
            for(String second : namelists[idx[1] - 1].getNames()){
                if(!first.equals(second))
                    continue;
                if(idx.length > 2  && curNumberOfNamelists > 2){
                    for(String third : namelists[idx[2] - 1].getNames())
                        if(second.equals(third)){
                            uniqItems.add(third);
                            break;
                        }
                }else{
                    if(first.equals(second)){
                        uniqItems.add(second);
                        break;
                    }
                }
            }
        String newName = namelists[idx[0] - 1].getName();
        for(int i = 1; i < idx.length; i++)
            newName += " -intersect- " + namelists[idx[i] - 1].getName();
        retVal.setName(newName);
        retVal.setSpecies(namelists[idx[0] - 1].getSpecies());
        retVal.setNames(uniqItems.toArray(new String[uniqItems.size()]));
        return retVal;
    }
    private void uniquifyNamelist(Namelist nl) {
        String[] data = nl.getNames();
		HashMap tmp = new HashMap();
        String [] curItem;
        String delimiter;
		for (int j = 0; j < data.length; j++){
            delimiter = "";
            if (data[j].contains(",")){
                delimiter = ",";
            }else if(data[j].contains(";")){
                delimiter = ";";
            }
            if(delimiter.equals(""))
                curItem = new String[]{data[j]};
            else{
                String[] items = data[j].split(delimiter);
                curItem = new String[items.length];
                for(int k = 0; k < items.length; k++)
                    curItem[k] = items[k].trim();
            }
            for(String str : curItem){
                if (!str.equals("") && !tmp.containsKey(str)) {
				    tmp.put(str, new Integer(j));
                }
			}
        }
		String[] retStr = new String[tmp.size()];
		for (int k = 0; k < tmp.keySet().size(); k++)
			retStr[k] = (String) tmp.keySet().toArray()[k];
		nl.setNames(retStr);
    }
}