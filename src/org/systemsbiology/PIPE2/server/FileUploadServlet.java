package org.systemsbiology.PIPE2.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class FileUploadServlet extends HttpServlet {

	public static final String UPLOAD_DIRECTORY = "PIPEletResourceDir/IDMapperPipelet/uploads/";

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!ServletFileUpload.isMultipartContent(request))
			return;


		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String baseDir = this.getServletContext().getRealPath("/");
		List items = null;
		try {
			items = upload.parseRequest(request);

			for (Iterator i = items.iterator(); i.hasNext();) {
				FileItem item = (FileItem) i.next();

				if (item.isFormField())
					continue;

				//parse fileName out of name given by client (windows or linux (or mac))
				String fileName = item.getName();
				int forwardSlashIndex = fileName.lastIndexOf("/");
				int backwardSlashIndex = fileName.lastIndexOf("\\");
				if (forwardSlashIndex > backwardSlashIndex)
					fileName = fileName.substring(forwardSlashIndex + 1, fileName.length());
				else
					fileName = fileName.substring(backwardSlashIndex + 1, fileName.length());

				/* retrives size in byte of the file uploaded from the client machine */
//				Long sizeInBytes = item.getSize();
				/* Create a new file on server machine */
				File uploadedFile = new File(baseDir + UPLOAD_DIRECTORY + fileName);
				System.out.println("Writing file: " + uploadedFile.getAbsolutePath());

				/* following statement writes to uploadedFile in single stroke */
				item.write(uploadedFile);
			}
		} catch (FileUploadException e) {
			System.out.print(e.toString());
			e.printStackTrace();
			return;
		} catch (Exception e) {
			System.out.print(e.toString());
			e.printStackTrace();
			return;
		}
	}
}

