package org.systemsbiology.PIPE2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.systemsbiology.PIPE2.client.FirstService;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Interaction;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class FirstServiceImpl extends RemoteServiceServlet implements FirstService {
	public DataMatrix getTestDataMatrix() {
		DataMatrix result =	new DataMatrix("testDM.txt");
		result.setSize(10, 10);
		result.setDefault(0.12345);
		return result;
	}

	public Network getTestNetwork() {
		Network nw = new Network();
				for(int i = 0; i < 5; i++){
					for(int j = i+1; j < 5; j++){
						Interaction cal = new Interaction("" + i, "" + j, "protein-protein");
						nw.add(cal);
					}
				}
		return nw;
	}

	public Namelist getTestNamelist() {
//		String[] ids = {"AEBP1", "CD36", "SCARB2", "CD44", "CD72", "COL6A2", "COL6A3", "COL12A1", "VCAN", "DSG2", "FN1",
//				"HSPG2", "ITGAM", "ITGAV", "ITGB1", "ITGB2", "KNG1", "LAMA4", "LAMC1", "LGALS3BP", "BCAM", "MFAP4",
//				"PTPRF", "THBS1", "THY1", "COL14A1", "AOC3", "POSTN", "EMILIN1"};
//
//		String[] ids = {"274", "658", "773", "1855", "2139", "2563", "3845", "3983", "4212",
//				"5338", "5470", "5475", "5715", "5718", "5781", "5829", "5925", "6176", "7844",
//				"8224", "8289", "8520", "8553", "8573", "8682", "8795", "9639", "9710", "9751",
//				"9925", "10059", "10076", "10746", "23074", "23174", "23336", "23351", "23504",
//				"25794", "26059", "54904", "57488", "64760", "80184", "83872", "89887", "114823",
//				"259197", "282969", "440955"};
String[] ids = {"YKL060C", "YFR053C", "YBR248C", "YML070W", "YHR174W", "YLR180W", "YNR043W", "YCL050C",
                "YDR385W","YOR133W", "YLL024C", "YER055C", "YGL253W", "YPL091W", "YGR192C",
                "YLR390W-A", "YLR178C", "YMR307W", "YOR184W", "YPR069C", "YHR068W", "YJL172W", "YLR109W",
                "YNR014W", "YER067W", "YOR323C", "YBR109C", "YPL117C", "YNL079C"};
//        System.out.println("what the...");
        Namelist retVal = new Namelist("Yeast Demo List", "Saccharomyces cerevisiae", ids);
//		System.out.println("what the... 2222");
        return retVal;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		out.println("hello from FirstServiceImpl.doGet ()");
	}

}