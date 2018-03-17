package org.systemsbiology.PIPE2.domain;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.systemsbiology.PIPE2.client.FirstService;


/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class NetworkTests extends GWTTestCase  {
	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";     		
	}

	public void testConstructor(){
		Network nw = buildTestNetwork();

		for(int i = 0; i < nw.getInteractions().length; i++)
			System.out.println(nw.getInteractions()[i].getSource() + "\t" +
					nw.getInteractions()[i].getType() + "\t" + nw.getInteractions()[i].getTarget());
	}

	public void testGetNetworkOverRPC(){
		FirstService.App.getInstance().getTestNetwork(new AsyncCallback(){
			public void onSuccess(Object o) {
				finishTest();
		}
			public void onFailure(Throwable throwable) {
				fail();
			}
		});
		delayTestFinish(20000);
	}

	public void testJSONstring(){
		Network nw = new Network();
//		assertTrue(nw.toJSONString().equals("{\"name\":\"null\", \"species\":\"unknown\", \"nodes\":[], \"interactions\":[]}"));
		nw =  buildTestNetwork();
		assertTrue(nw.toJSONString().equals("{\"name\":\"testNetwork\", \"species\":\"unknown\", \"nodes\":[], \"interactions\":[{\"source\":\"0\",\"target\":\"1\"},{\"source\":\"0\",\"target\":\"2\"},{\"source\":\"0\",\"target\":\"3\"},{\"source\":\"0\",\"target\":\"4\"},{\"source\":\"1\",\"target\":\"2\"},{\"source\":\"1\",\"target\":\"3\"},{\"source\":\"1\",\"target\":\"4\"},{\"source\":\"2\",\"target\":\"3\"},{\"source\":\"2\",\"target\":\"4\"},{\"source\":\"3\",\"target\":\"4\"}]}"));
	}

	public void testRemoveNode(){
		Network unoDos= buildTestNetwork();
		unoDos.removeNode("2");
		for(Interaction interaction : unoDos.getInteractions())
			assertTrue(!(interaction.getSource().equals("2") || interaction.getTarget().equals("2")));
		for(String node : unoDos.getNodes())
			assertFalse(node.equals("2"));
	}

	private Network buildTestNetwork() {
		Network retVal =new Network();
		retVal.setName("testNetwork");
		for(int i = 0; i < 5; i++){
			for(int j = i+1; j < 5; j++){
				Interaction cal = new Interaction("" + i, "" + j, "protein-protein");
				retVal.add(cal);
			}
		}
		return retVal;
	}
}
