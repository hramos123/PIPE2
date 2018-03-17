package org.systemsbiology.PIPE2.domain;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
/**
 * Encapsulates the notion of a network graph.
 */

import java.io.Serializable;
import java.util.*;

//--------------------------------------------------------------------
public class Network implements Serializable{// implements GaggleData {
	List<Interaction> interactionList = new ArrayList<Interaction>();
    Map<String, HashMap<String, String>> nodeAttributes =
            new HashMap<String, HashMap<String, String>>();
    Map<String, HashMap<String, String>> edgeAttributes =
            new HashMap<String, HashMap<String, String>>();
    Set<String> nodes = new HashSet<String>();
    String species = "unknown";
    String name;
    Tuple metadata;

    //--------------------------------------------------------------------
    public Network() {
    }

    //--------------------------------------------------------------------
    public void add(Interaction interaction) {
        //this doesn't work: if(!interactionList.contains(interaction))
	    //so do it manually
	    for(Interaction curInteraction : interactionList)
	        if(curInteraction.getSource().equals(interaction.getSource())
			        && curInteraction.getTarget().equals(interaction.getTarget())
			        && curInteraction.getType().equals(interaction.getType()))
	            return;
	    //just make sure these nodes are there:
	    add(interaction.source);
	    add(interaction.target);
	    interactionList.add(interaction);
    }

    //--------------------------------------------------------------------
    public void add(String nodeName) {
        nodes.add(nodeName);
    }

    //--------------------------------------------------------------------
    public void add(Interaction[] interactions) {
        for (Interaction interaction : interactions) add(interaction);
    }

    //--------------------------------------------------------------------
    public Interaction[] getInteractions() {
        return interactionList.toArray(new Interaction[interactionList.size()]);
    }

    //--------------------------------------------------------------------
    public int nodeCount() {
        return nodes.size();
    }

    //--------------------------------------------------------------------
    public String[] getNodes() {
        return nodes.toArray(new String[nodes.size()]);
    }

    //--------------------------------------------------------------------
    public HashSet<String> getConnectedNodes() {
        HashSet<String> result = new HashSet<String>();
        Interaction[] interactions = getInteractions();
        for (Interaction interaction : interactions) {
            result.add(interaction.getSource());
            result.add(interaction.getTarget());
        } // for i

        return result;

    } // getConnectedNodes

    //--------------------------------------------------------------------
    public String[] getOrphanNodes() {
        HashSet<String> result = new HashSet<String>();
        HashSet connectedNodes = getConnectedNodes();
        String[] allNodes = getNodes();
        for (String node : allNodes) {
            if (!connectedNodes.contains(node))
                result.add(node);
        }
        return result.toArray(new String[result.size()]);

    } // getOrphanNodes

    //--------------------------------------------------------------------
    public int getOrphanNodeCount() {
        return getOrphanNodes().length;
    }

    //--------------------------------------------------------------------
    public int edgeCount() {
        return interactionList.size();
    }

    protected void validateObjectType(Object value) {
        if (!(value instanceof Double ||
                value instanceof String ||
                value instanceof Integer ||
                value instanceof Vector
        )) {
            throw new IllegalArgumentException("Value must be a String, Double, or Integer.");
        }
    }
    //--------------------------------------------------------------------
/*
 * an edgeName looks like: node1 (interaction) node2.
 */
    public void addEdgeAttribute(String edgeName, String attributeName, String value) {
        validateObjectType(value);
        if (!edgeAttributes.containsKey(attributeName))
            edgeAttributes.put(attributeName, new HashMap<String, String>());

        HashMap<String, String> attributeHash = edgeAttributes.get(attributeName);
        attributeHash.put(edgeName, value);

    } // addEdgeAttribute

    //--------------------------------------------------------------------
    public void addNodeAttribute(String nodeName, String attributeName, String value) {
        validateObjectType(value);
        if (!nodeAttributes.containsKey(attributeName))
            nodeAttributes.put(attributeName, new HashMap<String, String>());

        HashMap<String, String> attributeHash = nodeAttributes.get(attributeName);
        attributeHash.put(nodeName, value);

    } // addNodeAttribute

    //--------------------------------------------------------------------
    public String[] getNodeAttributeNames() {
        return nodeAttributes.keySet().toArray(new String[nodeAttributes.size()]);
    }

    //--------------------------------------------------------------------
    public String[] getEdgeAttributeNames() {
        return edgeAttributes.keySet().toArray(new String[edgeAttributes.size()]);
    }

    //--------------------------------------------------------------------
    public HashMap getEdgeAttributes(String attributeName) {
        return edgeAttributes.get(attributeName);
    }

    //--------------------------------------------------------------------
    public HashMap getNodeAttributes(String attributeName) {
        return nodeAttributes.get(attributeName);
    }

    //--------------------------------------------------------------------
    public void setSpecies(String newValue) {
        species = newValue;
    }

    //--------------------------------------------------------------------
    public String getSpecies() {
        return species;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Tuple getMetadata() {
        return metadata;
    }

    public void setMetadata(Tuple metadata) {
        this.metadata = metadata;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Interaction[] interactions = getInteractions();
        for (Interaction interaction : interactions) {
            sb.append(interaction.toString());
            sb.append("\n");
        }

        return sb.toString();

    } // toString
//--------------------------------------------------------------------

	public void addMetadata(String key, String value) {
		if(metadata == null)
			metadata = new Tuple();
		metadata.set(key, value);

	}

	public boolean containsNode(String interactingNode) {
		return nodes.contains(interactingNode);
	}

	//remove node, associated interactions, and node attributes
	public void removeNode(String nodeName) {
		//remove any node attributes pertaining to this node
		String[] attributes = getNodeAttributeNames();
		for(String attribute : attributes){
			HashMap attributeHashMap = getNodeAttributes(attribute);
			attributeHashMap.remove(nodeName);
		}

		//remove interactions pertaining to this node
		for(Interaction interaction : getInteractions()){
			if(interaction.getSource().equals(nodeName) || interaction.getTarget().equals(nodeName))
				interactionList.remove(interaction);
		}

		//remove node
		nodes.remove(nodeName);
	}

	public String toJSONString() {
		String retStr = "{\"name\":\"" + getName() + "\", " +
				"\"species\":\"" + getSpecies() + "\", \"nodes\":[";
		for(int i=0; i < getNodes().length; i++){
			if(i > 0)
				retStr += ",";
			retStr += "\"" + getNodes()[i] + "\"";
		}
		retStr += "], \"interactions\":[";
		for(int i = 0; i < getInteractions().length; i++){
			if(i > 0)
				retStr += ",";
			retStr += "{\"source\":\"" + getInteractions()[i].getSource() + "\",";
			retStr +=  "\"target\":\"" + getInteractions()[i].getTarget() + "\"}";
		}
		retStr += "],";
		retStr += "\"nodeAttributes\":{";
		String [] attributeNames = getNodeAttributeNames();
		for(int i=0; i < attributeNames.length; i++){
			if(i > 0)
				retStr += ",";
			retStr += "\"" + attributeNames[i] +"\":{";
			HashMap attributes = getNodeAttributes(attributeNames[i]);
			String[] nodeNames = (String[]) attributes.keySet().toArray(new String[attributes.keySet().toArray().length]);
			for(int j = 0; j < nodeNames.length;j++){
				if(j > 0)
					retStr += ",";
				retStr += "\"" + nodeNames[j] + "\":\"" + attributes.get(nodeNames[j]) + "\"";
			}
			retStr += "}";
		}
		retStr += "}";
		retStr += "}";
		return retStr;
	}
} // class Network

