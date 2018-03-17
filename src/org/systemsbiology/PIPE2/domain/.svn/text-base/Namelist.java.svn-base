package org.systemsbiology.PIPE2.domain;

import java.io.Serializable;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
/**
 * A list of identifiers.
 */
public class Namelist implements Serializable { //implements GaggleData {
	private String name;
	private String species;
	private String[] names;
    //private Tuple metadata;

    public Namelist() {} // no-arg ctor required

    public Namelist(String name, String species, String[] names) {
        this.name = name;
        this.species = species;
        this.names = names;
    }

    public Namelist(String species, String[] names) {
        this(null, species, names);
    }

    public String getName() {
		return name;
	}

	public String getSpecies() {
		return species;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String toJSONString(){
		StringBuffer retStr = new StringBuffer();
		retStr.append("{\"name\":\"" + getName() + "\",\"species\":\"" + getSpecies() + "\",");
		retStr.append("\"names\":[");
		for(int i = 0; i < names.length;i++){
			if(i > 0)
				retStr.append(",");
			retStr.append("\""+names[i]+"\"");
		}
		retStr.append("]}");
		return retStr.toString();
	}

    /*public Tuple getMetadata() {
        return metadata;
    }

    public void setMetadata(Tuple metadata) {
        this.metadata = metadata;
    }*/
}
